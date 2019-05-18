package com.example.englishmore;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Random;

public class CardActivity extends Activity
{
/**View elements**/
    TextView progressText;
    CardFrontFragment front = new CardFrontFragment();
    CardBackFragment back = new CardBackFragment();
/*data structure*/
    ArrayList<Word> wordList = new ArrayList<Word>();
    ArrayList<Integer> masteredList = new ArrayList<Integer>();
/*global variable*/
    int wordCounter = 0;
    Random rand;
    private static boolean mShowingBack = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        progressText = findViewById(R.id.card_progress);
        getFragmentManager()
                .beginTransaction()
                .add(R.id.words, front)
                .commit();
        rand =new Random(25);

        downloadWords();

       getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
           @Override
           public void onBackStackChanged() {
               front.changeText(wordList.get(wordCounter).getWorditself(),wordCounter);
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
            progressText.setText("Already mastered "+ masteredList.size());
            getFragmentManager().popBackStack();
            mShowingBack = false;
            return;


        }  // to indicate the current card state, whether in front or back
        mShowingBack = true;
        back.setText(wordList.get(wordCounter).getWorditself(),wordList.get(wordCounter).toString(),wordCounter);
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
                .replace(R.id.words, back )
                .addToBackStack(null)
                .commit();

    }

/* Initialize the card front when the download finishes*/
    private void initializeCardFront(String inText)
    {
        front.changeText(inText,wordCounter);
    }

/* download words with volley*/
    private void downloadWords()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"https://nineteencommas.github.io/EnglishMoreJsons/testwords.json",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        wordList = JsonParser.getAllWords(response);
                        if(wordList.size()== 0)
                        {
                            initializeCardFront("nothing happened. i'm so sorry");
                        }
                        else
                        {
                            initializeCardFront(wordList.get(0).getWorditself());
                            progressText.setText("");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APP", error.toString());
            }
        });
        MyVolley.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    /*return the state of Word with index as input*/
    public boolean ifMastered(Integer in_index)
    {
        if(masteredList.contains(in_index))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*add a word to the masteredList with its index, when the I know the word button is pressed*/
    public void addMastered(Integer index)
    {
        if(!masteredList.contains(index))
        {
            masteredList.add(index);
        }// else do nothing
    }

    /* remove a word from the masteredList with its index, when the I don't know button is  pressed*/
    public void removeMastered(Integer index)
    {
        if(masteredList.contains(index))
        {
            masteredList.remove(index);

        } // else do nothing
    }

}

