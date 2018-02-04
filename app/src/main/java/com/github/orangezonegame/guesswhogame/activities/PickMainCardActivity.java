package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.GuessCardAdapter;
import com.github.orangezonegame.guesswhogame.common.GuessCardViewHolder;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import java.util.ArrayList;
import java.util.Arrays;
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
        cards = getGuessCard();
        GridLayoutManager layoutManager = new GridLayoutManager(context, getScreenMaxSpan());
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
        Log.v("Card pick", "Selected= " + cards.get(selectedCardId).getName());
        new SharedPrefs(context).write(SharedPrefs.TAG_MAINCARD, String.valueOf(selectedCardId));
        startActivity(new Intent(context, GuessThisTurnActivity.class));
    }

    private List<GuessCard> getGuessCard(){
        return Arrays.asList(Constants.guessCards);
    }

    private int getScreenMaxSpan(){
        final int cardWidthInDp = 130;
        final int screenMarginInDp = 16;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return pxToDp(metrics.widthPixels - (screenMarginInDp * 2)) / cardWidthInDp;
    }

    private int pxToDp(int px){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (metrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
