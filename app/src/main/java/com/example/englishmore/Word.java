package com.example.englishmore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Word implements Serializable {
    String worditself;
    String explain;
    String example;
    ArrayList<String> rel;
    ArrayList<String> syn;
    ArrayList<String> near;


    public Word(String worditself, String explain, String example,ArrayList<String> rel, ArrayList<String> syn, ArrayList<String> near) {
        this.worditself = worditself;
        this.explain = explain;
        this.example = example;
        this.rel = rel;
        this.syn = syn;
        this.near = near;

    }

    public String getWorditself() {
        return worditself;
    }

    public String toString() {
        String text = "<p>"+"<b>Explanation: </b>" + explain + "</p>" +"<p>" +"<b>Example: </b>" + example +"</p>";


        text += "<p>"+"<b>near : </b>";

        for (String each : near) {
            text += " " + each;
        }
        text += "</p>";

        text += "<p>"+"<b>relatives: </b>";
        for (String each : rel) {
            text += " " + each;
        }
        text += "</p>";

        text += "<p>"+"<b>synonyms: </b>";
        for (String each : syn) {
            text += " " + each;
        }
        text +="</p>";

        return text;
    }



}
