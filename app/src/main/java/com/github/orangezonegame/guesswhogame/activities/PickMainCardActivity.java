package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.GuessCardAdapter;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maik on 1/1/2018.
 */

public class PickMainCardActivity extends AppCompatActivity {

    @BindView(R.id.rv_cards)
    RecyclerView cardsRecyclerView;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_main_card);
        ButterKnife.bind(this);

        context = getApplicationContext();
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        cardsRecyclerView.setLayoutManager(layoutManager);

        GuessCardAdapter adapter = new GuessCardAdapter(getGuessCard());
        cardsRecyclerView.setAdapter(adapter);
    }

    private List<GuessCard> getGuessCard(){
        List<GuessCard> guessCardList = new ArrayList<>();
        guessCardList.add(new GuessCard(1, "Alya Steenie", R.drawable.portrait1));
        guessCardList.add(new GuessCard(2, "Itziar Panni", R.drawable.portrait2));
        guessCardList.add(new GuessCard(3, "Radha Gunhild", R.drawable.portrait3));
        guessCardList.add(new GuessCard(4, "Kaveh Jesper", R.drawable.portrait4));
        guessCardList.add(new GuessCard(5, "Bogumir Þórvaldr", R.drawable.portrait1));
        guessCardList.add(new GuessCard(6, "Herman Polycarp", R.drawable.portrait2));
        guessCardList.add(new GuessCard(7, "Jeanette Leyton", R.drawable.portrait3));
        guessCardList.add(new GuessCard(8, "Sieghild Orit", R.drawable.portrait4));

        return guessCardList;
    }
}
