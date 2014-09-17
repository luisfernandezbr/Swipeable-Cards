/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.andtinder.demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.PercetageObserver;
import com.andtinder.view.SimpleCardStackAdapter;

public class MainActivity extends Activity {

    /**
     * This variable is the container that will host our cards
     */
	private CardContainer mCardContainer;

    private ImageView mButton1, mButton2, mButton3;

    MyCardAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainlayout);


        mButton1 = (ImageView) findViewById(R.id.button1);
        mButton2 = (ImageView) findViewById(R.id.button2);
        mButton3 = (ImageView) findViewById(R.id.button3);


        mButton1.animate().setDuration(2000);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsUnlike();

                final ViewGroup layout = (ViewGroup) findViewById(R.id.layoutview);
                layout.setVisibility(View.GONE);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsNeutral();

                final ViewGroup layout = (ViewGroup) findViewById(R.id.layoutview);
                layout.setVisibility(View.GONE);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsLike();

                final ViewGroup layout = (ViewGroup) findViewById(R.id.layoutview);
                layout.setVisibility(View.GONE);
            }
        });


		mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        mCardContainer.setPercentObserver(new PercetageObserver() {
            @Override
            public void likePercent(int percent) {
                Log.i("PERCENT_OBS","likePercent: " + percent);
                mButton3.setBackgroundColor(Color.argb(255 * percent / 100, 134, 165, 38));

//                CardModel card = (CardModel) adapter.getItem(0);
//
//                card.percent = percent;
//
//                adapter.notifyDataSetChanged();

            }

            @Override
            public void unlikePercent(int percent) {
                Log.i("PERCENT_OBS","unlikePercent: " + percent);
                mButton1.setBackgroundColor(Color.argb(255 * percent / 100, 185, 35, 0));
            }

            @Override
            public void cancel() {
                mButton1.setBackgroundColor(Color.TRANSPARENT);
                mButton2.setBackgroundColor(Color.TRANSPARENT);
                mButton3.setBackgroundColor(Color.TRANSPARENT);

            }
        });

		Resources r = getResources();

		//SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);
        adapter = new MyCardAdapter(this);

//		adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
//		adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2)));
//		adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3)));
//		adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.drawable.picture1)));
//		adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.drawable.picture2)));
//		adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.drawable.picture3)));
//		adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));


        CardModel cardModel = new CardModel("", "Description goes here", r.getDrawable(R.drawable.picture1));
        cardModel.setOnClickListener(new CardModel.OnClickListener() {
           @Override
           public void OnClickListener() {
               Log.i("DRAG_TEST - Swipeable Cards","I am pressing the card");
           }
        });

        cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {

            @Override
            public void onDislike() {
                Log.i("DRAG_TEST - Swipeable Cards","I dislike the card");
               buttonsUnlike();
            }

            @Override
            public void onLike() {
                Log.i("DRAG_TEST - Swipeable Cards","I like the card");
                //mButton3.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));


                buttonsLike();
            }
        });
        adapter.add(cardModel);

		mCardContainer.setAdapter(adapter);
	}

    private void buttonsUnlike() {
        mButton1.setBackgroundColor(Color.argb(255, 185, 35, 0));
        mButton2.setBackgroundColor(Color.TRANSPARENT);
        mButton3.setBackgroundColor(Color.TRANSPARENT);

        CardModel card = (CardModel)adapter.getItem(0);

        card.setTitle("NÃO GOSTEI");
        card.colorRes = 1;
        card.percent = 80;

        animateCard(card);
        //adapter.notifyDataSetChanged();
    }

    private void buttonsNeutral() {
        mButton1.setBackgroundColor(Color.TRANSPARENT);
        mButton2.setBackgroundColor(Color.argb(180, 234, 193, 53));
        mButton3.setBackgroundColor(Color.TRANSPARENT);

        CardModel card = (CardModel)adapter.getItem(0);

        card.setTitle("NEUTRO");
        card.colorRes = 2;
        card.percent = 80;

        animateCard(card);
        //adapter.notifyDataSetChanged();
    }

    private void buttonsLike() {
        mButton1.setBackgroundColor(Color.TRANSPARENT);
        mButton2.setBackgroundColor(Color.TRANSPARENT);
        mButton3.setBackgroundColor(Color.argb(255, 134, 165, 38));


        CardModel card = (CardModel)adapter.getItem(0);

        card.setTitle("GOSTEI");
        card.colorRes = 3;
        card.percent = 80;

        animateCard(card);

        //adapter.notifyDataSetChanged();
    }

    private void animateCard(final CardModel model) {
        final ViewGroup layout = (ViewGroup) findViewById(R.id.mainLayout_card);
        layout.setVisibility(View.GONE);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new LinearInterpolator()); //add this
        fadeIn.setDuration(600);

        AnimationSet animation = new AnimationSet(false); //change to false

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.i("DRAG_TEST_ANIM - Swipeable Cards","onAnimationStart");
                layout.setVisibility(View.VISIBLE);

                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.cardLayout);
                viewGroup.setBackgroundColor(Color.argb(150, 0, 0, 0));

                TextView text =  (TextView) findViewById(R.id.cardText);
                text.setText(model.getTitle());
                text.setVisibility(View.VISIBLE);
                text.setTypeface(TypefaceUtil.getTypeface(getApplicationContext(), "erasdust.ttf"));

                switch (model.colorRes) {
                    case 1: {
                        text.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                        text.setBackgroundResource(R.drawable.border_1);
                        break;
                    }

                    case 2: {
                        text.setTextColor(getApplicationContext().getResources().getColor(R.color.yellow));
                        text.setBackgroundResource(R.drawable.border_2);
                        break;
                    }

                    case 3: {
                        text.setTextColor(getApplicationContext().getResources().getColor(R.color.green));
                        text.setBackgroundResource(R.drawable.border_3);
                        break;
                    }

                    default: {
                        text.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.i("DRAG_TEST_ANIM - Swipeable Cards","onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i("DRAG_TEST_ANIM - Swipeable Cards","onAnimationRepeat");
            }
        });
        animation.addAnimation(fadeIn);

        if (layout.getAnimation() != null) {
            Log.i("DRAG_TEST_ANIM - Swipeable Cards","Canceling");
            layout.getAnimation().reset();
            layout.getAnimation().cancel();
            layout.setAnimation(null);
        }

        layout.setAnimation(animation);



    }
}
