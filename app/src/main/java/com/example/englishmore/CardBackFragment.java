package com.example.englishmore;

import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CardBackFragment extends Fragment {
    private Button btn_know;
    private Button btn_not_know;
    private TextView wordItself;
    private TextView wordDetail;
    private String strItself;
    private String strDetail;
    private Integer index;
    public CardBackFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.card_back, container, false);
        btn_know = v.findViewById(R.id.btn_know);
        btn_not_know = v.findViewById(R.id.btn_not_know);
        wordItself = v.findViewById(R.id.card_back_worditself);
        wordDetail = v.findViewById(R.id.card_back_explain);

        if(!TextUtils.isEmpty(strItself))
        {
            wordItself.setText(strItself);
            wordDetail.setText(Html.fromHtml(strDetail));
        }

        btn_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardActivity)getActivity()).addMastered(index);
                ((CardActivity)getActivity()).flipCard();

            }
        });

        btn_not_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CardActivity)getActivity()).removeMastered(index);
                ((CardActivity)getActivity()).flipCard();
            }
        });
        return v;
    }
public void setText(String word, String explain,Integer index)
{
    this.index = index;
    if(wordItself == null)
    {
        strItself = word;
        strDetail = explain;
    }
    else
    {
        wordItself.setText(word);
        wordDetail.setText(Html.fromHtml(explain));
        strItself = word;
        strDetail = explain;
    } //when the fragment manager start to replace, onCreateView will be called again,
    // so need to make sure the strItself and strDetail is updated
}
}