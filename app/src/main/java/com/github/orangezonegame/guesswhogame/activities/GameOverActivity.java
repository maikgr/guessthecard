package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.GuessCardCardView;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maik on 2/7/2018.
 */

public class GameOverActivity extends AppCompatActivity{
    @BindView(R.id.tv_game_result)
    TextView gameResultText;

    @BindView(R.id.tv_self_card_text)
    TextView selfCardText;

    @BindView(R.id.tv_opponent_card_text)
    TextView opponentCardText;

    @BindView(R.id.self_card)
    View selfCardInclude;

    @BindView(R.id.opponent_card)
    View opponentCardInclude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ButterKnife.bind(this);

        Context context = getApplicationContext();
        List<GuessCard> cards = new ArrayList<>();
        Collections.addAll(cards, Constants.GUESS_CARDS);

        GuessCardCardView selfCardView = new GuessCardCardView();
        ButterKnife.bind(selfCardView, selfCardInclude);
        int selfCardId = getIntent().getIntExtra(Constants.SELF_CARD_ID, -1);
        GuessCard selfCard = cards.get(selfCardId);
        selfCardView.cardImageView.setImageResource(selfCard.getResourceId());
        selfCardView.cardName.setText(selfCard.getName());

        GuessCardCardView opponentCardView = new GuessCardCardView();
        ButterKnife.bind(opponentCardView, opponentCardInclude);
        int opponentCardId = 0;
        GuessCard opponentCard = cards.get(opponentCardId);
        opponentCardView.cardImageView.setImageResource(opponentCard.getResourceId());
        opponentCardView.cardName.setText(opponentCard.getName());

        String state = new SharedPrefs(context).readString(SharedPrefs.TAG_STATE);
        boolean isHost = state.equals(Constants.HOST_SEND);
        boolean isWinner = isHost ? selfCardId != opponentCardId : selfCardId == opponentCardId;

        if(isHost){
            if(isWinner) showWinnerAsHost();
            else  showLoserAsHost();
        } else {
            if(isWinner) showWinnerAsOpponent();
            else showLoserAsOpponent();
        }
    }

    private void showWinnerAsHost(){

    }

    private void showWinnerAsOpponent(){

    }

    private void showLoserAsHost(){

    }

    private void showLoserAsOpponent(){

    }
}
