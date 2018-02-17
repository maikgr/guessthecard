package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.GuessCardAdapter;
import com.github.orangezonegame.guesswhogame.common.GuessCardViewHolder;
import com.github.orangezonegame.guesswhogame.common.ServerApp;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<GuessCard> cards;
    private SharedPrefs prefs;
    private ServerApp serverApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        ButterKnife.bind(this);

        context = getApplicationContext();
        serverApp = (ServerApp) getApplication();
        prefs = new SharedPrefs(context);
        String cardJson = prefs.readString(SharedPrefs.TAG_SAVEDCARDS);
        if (!cardJson.equals("")) {
            cards = new Gson().fromJson(cardJson, new TypeToken<List<GuessCard>>(){}.getType());
        } else {
            cards = new ArrayList<>();
            for(GuessCard card : Constants.GUESS_CARDS){
                try {
                    cards.add((GuessCard) card.clone());
                } catch(CloneNotSupportedException e){}
            }
        }

        int screenSpan = new SharedPrefs(context).readInt(SharedPrefs.TAG_MAXSPAN);
        GridLayoutManager layoutManager = new GridLayoutManager(context, screenSpan);
        cardsRecyclerView.setLayoutManager(layoutManager);

        GuessCardAdapter adapter = new GuessCardAdapter(context, cards, new GuessCardAdapter.GuessCardOnItemClickListener() {
            @Override
            public void onItemClick(GuessCardViewHolder view, GuessCard guessCard) {
                view.toggleState();
                int selectedId = guessCard.getId();
                boolean isChosen = guessCard.getIsChosen();

                cards.get(selectedId).setIsChosen(isChosen);
            }
        });

        cardsRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.button_done)
    public void nextTurn(){
        String savedCardsJson = new Gson().toJson(cards);
        prefs.write(SharedPrefs.TAG_SAVEDCARDS, savedCardsJson);

        Map<String, String> data = new HashMap<String, String>();
        data.put("roomID", new SharedPrefs(context).readString(Constants.ROOMID));
        data.put("playerID", new SharedPrefs(context).readString(Constants.PLAYERID));
        serverApp.send(Constants.NEXT_SEND, data);
        startActivity(new Intent(context, GuessThisTurnActivity.class));
    }

    @OnClick(R.id.button_answer)
    public void finalGuess(){
        List<GuessCard> activeCards = new ArrayList<>();
        for(GuessCard card : cards){
            if(!card.getIsChosen()) activeCards.add(card);
        }

        prefs.write(SharedPrefs.TAG_ACTIVECARDS, new Gson().toJson(activeCards));

        startActivity(new Intent(context, FinalGuessActivity.class));
    }
}
