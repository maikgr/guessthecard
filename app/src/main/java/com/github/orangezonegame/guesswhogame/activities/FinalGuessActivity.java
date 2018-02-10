package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.GuessCardAdapter;
import com.github.orangezonegame.guesswhogame.common.GuessCardViewHolder;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maik on 1/13/2018.
 */

public class FinalGuessActivity extends AppCompatActivity {
    @BindView(R.id.rv_cards)
    RecyclerView cardsRecyclerView;

    private Context context;
    private List<GuessCard> cards;
    private GuessCardViewHolder lastSelectedViewHolder;
    private int selectedCardId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_guess);
        ButterKnife.bind(this);

        context = getApplicationContext();
        SharedPrefs prefs = new SharedPrefs(context);
        String cardsJson = prefs.readString(SharedPrefs.TAG_ACTIVECARDS);
        cards = new Gson().fromJson(cardsJson, new TypeToken<List<GuessCard>>(){}.getType());

        GridLayoutManager layoutManager = new GridLayoutManager(context, prefs.readInt(SharedPrefs.TAG_MAXSPAN));
        cardsRecyclerView.setLayoutManager(layoutManager);

        GuessCardAdapter adapter = new GuessCardAdapter(cards, new GuessCardAdapter.GuessCardOnItemClickListener() {
            @Override
            public void onItemClick(GuessCardViewHolder view, GuessCard guessCard) {
                if(lastSelectedViewHolder != null)
                    lastSelectedViewHolder.setState(View.INVISIBLE);
                view.toggleState();
                lastSelectedViewHolder = view;
                selectedCardId = guessCard.getId();
            }
        });

        cardsRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.button_cancel)
    public void cancelFinalGuess(){
        startActivity(new Intent(context, AskQuestionTurnActivity.class));
    }

    @OnClick(R.id.button_confirm)
    public void confirmFinalGuess(){
        try{
            Intent intent = new Intent(context, GameOverActivity.class);
            intent.putExtra(Constants.SELF_CARD_ID, selectedCardId);
            startActivity(intent);
        } catch(NullPointerException | IndexOutOfBoundsException e){
            Toast toast = Toast.makeText(context, "Please select a card", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
