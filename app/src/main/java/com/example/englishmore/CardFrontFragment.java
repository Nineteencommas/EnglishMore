package com.example.englishmore;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CardFrontFragment extends Fragment {
    private Button btn_see_meaning;
    private TextView wordText;
    private String strWord;
    private Integer index;
    public CardFrontFragment() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.card_front, container, false);
        btn_see_meaning = v.findViewById(R.id.btn_see_meaning);
        wordText = v.findViewById(R.id.card_front_word_itself);

        if(!TextUtils.isEmpty(strWord))
        {
            wordText.setText(strWord);
        }


        btn_see_meaning.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                ((CardActivity)getActivity()).flipCard(); //get next word

            }
        });
        return v;

    }

    public void changeText(String inText,Integer index)
    {
        this.index = index;
        if(wordText == null)
        {
            strWord = inText;
        }
        else
        {
            wordText.setText(inText);
        }

    }

}