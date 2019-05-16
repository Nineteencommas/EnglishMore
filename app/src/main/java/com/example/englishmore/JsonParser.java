package com.example.englishmore;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class JsonParser{

    public static ArrayList<Word> getAllWords(String response) {
        ArrayList<Word> wordList = new ArrayList<>();
        try {
            JSONObject allwords = new JSONObject(response);
            Iterator keys = allwords.keys();
            while (keys.hasNext()) {
                String key = keys.next().toString();
                String value = allwords.optString(key);
                JSONArray each_word = new JSONArray(value);
                for (int i = 0; i < each_word.length(); i++) {
                    JSONObject each_meaning = each_word.getJSONObject(i);
                    String example = each_meaning.getString("example");
                    String explain = each_meaning.getString("explain");
                    Word word = new Word(key, explain, example, getTheList(each_meaning, "near"), getTheList(each_meaning, "syn"), getTheList(each_meaning, "rel"));

                    wordList.add(word);
                }
            }
        } catch (JSONException e) {

        }
        return wordList;
    }

    private static ArrayList getTheList(JSONObject jsonobject, String key)
    {
        ArrayList<String> list = new ArrayList<String>();
        try{
            JSONArray json_array = jsonobject.getJSONArray(key);
            for(int j = 0; j< json_array.length(); j++)
            {
                list.add(json_array.getString(j));
            }

        }
        catch(JSONException e)
        {
            Log.e("lala", e.toString());
        }

        return list;
    }
}