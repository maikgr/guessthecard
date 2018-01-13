package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.github.orangezonegame.guesswhogame.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maik on 1/13/2018.
 */

public class AskQuestionTurnActivity extends AppCompatActivity {
    @BindView(R.id.rv_cards)
    RecyclerView cardsRecyclerView;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_done)
    public void nextTurn(){
        throw new UnsupportedOperationException();
    }

    @OnClick(R.id.button_answer)
    public void finalGuess(){
        startActivity(new Intent(context, FinalGuessActivity.class));
    }
}
