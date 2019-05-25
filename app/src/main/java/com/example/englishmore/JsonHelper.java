package com.example.englishmore;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class JsonHelper {

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

    public static String topicInfoToString(String topic, int topicProgress)
    {
        JSONObject json = new JSONObject();
        try{
            json.put("topic",topic);
            json.put("topicProgress",topicProgress);
        }
        catch (JSONException e)
        {
            Log.d("JsonHelper", "something wrong with topicInfoToString");
        }
       return json.toString();

    }
    public static int getTopicProgressFromTopicInfo(String info)
    {
        int topicProgress = 0;
        try
        {
            JSONObject jsonObject = new JSONObject(info);
            topicProgress = jsonObject.getInt("topicProgress");
        }
        catch(JSONException e)
        {
            Log.d("JsonHelper", "something wrong with getTopicProgressFromTopicInfo");

        }
        return topicProgress;

    }



    public static ArrayList<ArrayList> getTopicFromTopicInfo(String topicInfo)
    {

        ArrayList<ArrayList> myList = new ArrayList<>();
        ArrayList<String> topicName = new ArrayList<>();
        ArrayList<Integer> topicTotal = new ArrayList<>();
        try {
            JSONArray topicArray = new JSONArray(topicInfo);
            for (int i = 0; i < topicArray.length(); i++) {
                JSONObject eachtopic = topicArray.getJSONObject(i);
                topicName.add(eachtopic.getString("topic"));
                topicTotal.add(eachtopic.getInt("total"));
            }
        }
        catch(JSONException e)
        {
            Log.d("JSON", "getTopicFromTopicInfo: something wrong");
        }

        if(topicName.size() == 0)
        {

            topicName.add("Something wrong with getTopicFromTopicInfo");
        }
        if(topicTotal.size() == 0)
        {
            topicTotal.add(-1);
        }
        myList.add(topicName);
        myList.add(topicTotal);
        return myList;

    }


    public static ArrayList<Integer> getDeckerProgress(String inString)
    {
        ArrayList<Integer> progress = new ArrayList<>();
        try {
            JSONArray topicArray = new JSONArray(inString);
            for (int i = 0; i < topicArray.length(); i++) {
                progress.add((Integer)topicArray.get(i));
            }
        }
        catch(JSONException e)
        {
            Log.d("JSON","Something wrong with getDeckerProgress");
        }

        return progress;
    }

    public static String putDeckerProgressToString(ArrayList<Integer> progress)
    {
        Gson gson = new Gson();
        return  gson.toJson(progress);
    }

    public static ArrayList<String> getTopicFromBasicInfo(String instring)
    {
        ArrayList<String> topic = new ArrayList<>();
        try {
            JSONArray topicArray = new JSONArray(instring);
            for (int i = 0; i < topicArray.length(); i++) {
                topic.add((String) topicArray.get(i));
            }
        }
        catch(JSONException e)
        {
            Log.d("JSON","Something wrong with getDeckerProgress");
        }

        return topic;
    }
}