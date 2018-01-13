package com.github.orangezonegame.guesswhogame.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maik on 1/13/2018.
 */

public class GuessThisTurnActivity extends AppCompatActivity {

    @BindView(R.id.cv_card)
    CardView cardCardView;

    @BindView(R.id.image_card_frame)
    ImageView cardImageView;

    @BindView(R.id.tv_card_name)
    TextView cardName;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_this);
        ButterKnife.bind(this);

        context = getApplicationContext();
    }

    @OnClick(R.id.button_done)
    public void nextTurn(){
        throw new UnsupportedOperationException();
    }
}
