package com.example.englishmore;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class CardActivity extends BasicActivity {
    /**
     * View elements
     **/
    TextView progressText;
    CardFrontFragment front = new CardFrontFragment();
    CardBackFragment back = new CardBackFragment();
    /*data structure*/
    ArrayList<Word> wordList = new ArrayList<Word>();
    ArrayList<Integer> masteredList = new ArrayList<Integer>();
    /*flag and counter variable*/
    int wordCounter = 0;
    Random rand;
    private static boolean mShowingBack = false;

    private String deckerName;
    private String topic;
    private int deckerIndex;
    int previousMastered = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        deckerName = bundle.getString("deckerName");
        topic = bundle.getString("topic");
        deckerIndex = bundle.getInt("deckerIndex");
        Log.d("card", deckerName);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_card);
        getLayoutInflater().inflate(R.layout.activity_card, frame);

        progressText = findViewById(R.id.card_progress);
        getProgress();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.words, front)
                .commit();
        rand = new Random(25);
        downloadWords();

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                front.changeText(wordList.get(wordCounter).getWorditself(), wordCounter);
            }
        });
        // when the card front fragment is popped up from the stack, only its view will be reconstruced,
        // the data is no longer held in the object of front
        // don't understand

    }

    /* fragment transaction*/
    public void flipCard() {

        if (mShowingBack) {

            wordCounter = rand.nextInt(wordList.size());
            progressText.setText("Already mastered " + masteredList.size());
            getFragmentManager().popBackStack();
            mShowingBack = false;
            return;
        }  // to indicate the current card state, whether in front or back
        mShowingBack = true;
        back.setText(wordList.get(wordCounter).getWorditself(), wordList.get(wordCounter).toString(), wordCounter);
        //in the first place the back object is created within the flipCard(),
        // and the data update works,
        // if only create the back object once within the activity,
        // it no longer works to update the data, if the setText() does not update the strWord and strDetail
        // that's because the view itself is destroyed and recreated after the replace,
        // only data hold in the back object, the onCreateView will be called again

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out) // self-defined fragment transition animation
                .replace(R.id.words, back)
                .addToBackStack(null)
                .commit();

    }

    /* Initialize the card front when the download finishes*/
    private void initializeCardFront(String inText) {
        front.changeText(inText, wordCounter);
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("progress", masteredList.size());
        intent.putExtra("deckerIndex", deckerIndex);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setProgress();

    }

    /* download words with volley*/
    private void downloadWords() {
        // normally http is not allowed for android connection, we open the  cleartextTrafficPermitted="true" in network_security_config
        //since I'm too lazy to put the server to https
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://35.178.77.171:5000/get_word_list?decker=" + deckerName,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        wordList = JsonHelper.getAllWords(response);
                        if (wordList.size() == 0) {
                            initializeCardFront("nothing happened. i'm so sorry");
                        } else {
                            initializeCardFront(wordList.get(0).getWorditself());
                            // progressText.setText("");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", error.toString());
            }
        });
        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    /*return the state of Word with index as input*/
    public boolean ifMastered(Integer in_index) {
        if (masteredList.contains(in_index)) {
            return true;
        } else {
            return false;
        }
    }

    /*add a word to the masteredList with its index, when the I know the word button is pressed*/
    public void addMastered(Integer index) {
        if (!masteredList.contains(index)) {
            masteredList.add(index);
        }// else do nothing
    }

    /* remove a word from the masteredList with its index, when the I don't know button is  pressed*/
    public void removeMastered(Integer index) {
        if (masteredList.contains(index)) {
            masteredList.remove(index);

        } // else do nothing
    }


    public void getProgress() {
        SharedPreferences preferences = getSharedPreferences("com.example.englishmore.userProgress", MODE_PRIVATE);
        ArrayList<Integer> afterParsed = JsonHelper.getDeckerProgress(preferences.getString(deckerName, "defaultValueforTopicInfo"));
        for (Integer each : afterParsed) {
            masteredList.add(each);
        }
        progressText.setText("Already mastered " + String.valueOf(masteredList.size()));
        previousMastered = afterParsed.size();
    }

    public void setProgress() {

        JSONObject jsonObject = new JSONObject();
        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("com.example.englishmore.basicInfo", MODE_PRIVATE);
        String username = mPreferences.getString("username","username");
        mPreferences = getApplicationContext().getSharedPreferences("com.example.englishmore.userProgress", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();


        preferencesEditor.putInt(topic, mPreferences.getInt(topic, 0) + masteredList.size() - previousMastered);
        preferencesEditor.putString(deckerName, JsonHelper.putDeckerProgressToString(masteredList));
        preferencesEditor.putInt(deckerName + "num", masteredList.size());
        try
        {
            jsonObject.put("topic",topic);
            jsonObject.put("deckerProgress",JsonHelper.putDeckerProgressToString(masteredList));
            jsonObject.put("deckerName",deckerName);
            jsonObject.put("deckerNum",masteredList.size());
            jsonObject.put("username",username);
            jsonObject.put("topicProgress",masteredList.size() - previousMastered);
        }
        catch (JSONException E)
        {
            Log.d("setprogress", E.toString());
        }

        preferencesEditor.commit();
        sendWorkPostRequest(jsonObject);

    }


    private void sendWorkPostRequest(JSONObject jsonBody) {

            String URL = "http://35.178.77.171:5000/post_progress";

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("set progress to web", "onResponse: "+response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    onBackPressed();

                }
            });

        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(jsonOblect);


}



}

