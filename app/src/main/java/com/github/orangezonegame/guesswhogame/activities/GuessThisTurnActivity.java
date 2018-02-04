package com.github.orangezonegame.guesswhogame.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maik on 1/13/2018.
 */

public class GuessThisTurnActivity extends AppCompatActivity {

    @BindView(R.id.layout_card)
    View cardViewInclude;



    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_this);
        ButterKnife.bind(this);
        GuessCardCardView cardView = new GuessCardCardView();
        ButterKnife.bind(cardView, cardViewInclude);

        context = getApplicationContext();
        int mainCardId = new SharedPrefs(context).readInt(SharedPrefs.TAG_MAINCARD);
        GuessCard mainCard = Constants.GUESS_CARDS[mainCardId];

        cardView.cardImageView.setImageResource(mainCard.getResourceId());
        cardView.cardName.setText(mainCard.getName());
    }

    @OnClick(R.id.button_done)
    public void nextTurn(){
        startActivity(new Intent(context, AskQuestionTurnActivity.class));
    }

    class GuessCardCardView{
        @BindView(R.id.image_card_frame)
        ImageView cardImageView;

        @BindView(R.id.tv_card_name)
        TextView cardName;
    }
}
