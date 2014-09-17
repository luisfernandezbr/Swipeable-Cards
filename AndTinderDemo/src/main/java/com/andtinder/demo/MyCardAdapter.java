package com.andtinder.demo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardStackAdapter;

/**
 * Created by luisfernandez on 7/10/14.
 */
public class MyCardAdapter extends CardStackAdapter {



    public MyCardAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getCardView(int position, CardModel model, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.my_card_layout, parent, false);
            assert convertView != null;
        }

        ViewGroup viewGroup = (ViewGroup) convertView.findViewById(R.id.cardLayout);
        viewGroup.setBackgroundColor(Color.argb(255 * model.percent / 125, 0, 0, 0));

        TextView text =  (TextView) convertView.findViewById(R.id.cardText);
        text.setText(model.getTitle());
        text.setVisibility(View.VISIBLE);
        text.setTypeface(TypefaceUtil.getTypeface(getContext(), "erasdust.ttf"));

        switch (model.colorRes) {
            case 1: {
                text.setTextColor(getContext().getResources().getColor(R.color.red));
                text.setBackgroundResource(R.drawable.border_1);
                break;
            }

            case 2: {
                text.setTextColor(getContext().getResources().getColor(R.color.yellow));
                text.setBackgroundResource(R.drawable.border_2);
                break;
            }

            case 3: {
                text.setTextColor(getContext().getResources().getColor(R.color.green));
                text.setBackgroundResource(R.drawable.border_3);
                break;
            }

            default: {
                text.setVisibility(View.GONE);
            }

        }



//        ((ImageView) convertView.findViewById(com.andtinder.R.id.image)).setImageDrawable(model.getCardImageDrawable());
//        ((TextView) convertView.findViewById(com.andtinder.R.id.title)).setText(model.getTitle());
//        ((TextView) convertView.findViewById(com.andtinder.R.id.description)).setText(model.getDescription());

        return convertView;
    }
}
