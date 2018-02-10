package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.GuessCardAdapter;
import com.github.orangezonegame.guesswhogame.common.GuessCardViewHolder;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maik on 1/1/2018.
 */

public class PickMainCardActivity extends AppCompatActivity {

    @BindView(R.id.rv_cards)
    RecyclerView cardsRecyclerView;

    private Context context;
    private GuessCardViewHolder lastClickedViewHolder;
    private List<GuessCard> cards;
    private int selectedCardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_main_card);
        ButterKnife.bind(this);

        context = getApplicationContext();

        cards = new ArrayList<>();
        for(GuessCard card : Constants.GUESS_CARDS){
            try {
                cards.add((GuessCard) card.clone());
            } catch(CloneNotSupportedException e){}
        }

        int screenSpan = new SharedPrefs(context).readInt(SharedPrefs.TAG_MAXSPAN);
        GridLayoutManager layoutManager = new GridLayoutManager(context, screenSpan);
        cardsRecyclerView.setLayoutManager(layoutManager);

        GuessCardAdapter adapter = new GuessCardAdapter(cards, new GuessCardAdapter.GuessCardOnItemClickListener() {
            @Override
            public void onItemClick(GuessCardViewHolder view, GuessCard guessCard) {
                if(lastClickedViewHolder != null){
                    lastClickedViewHolder.setState(View.INVISIBLE);
                }
                view.toggleState();
                selectedCardId = guessCard.getId();
                lastClickedViewHolder = view;
            }
        });

        cardsRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.button_confirm)
    public void confirmSelection(){
        new SharedPrefs(context).write(SharedPrefs.TAG_MAINCARD, selectedCardId);
        startActivity(new Intent(context, GuessThisTurnActivity.class));
    }
}
