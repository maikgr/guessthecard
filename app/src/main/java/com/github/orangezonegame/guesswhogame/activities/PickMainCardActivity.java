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
import com.github.orangezonegame.guesswhogame.common.ServerApp;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

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
    private ServerApp serverApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_main_card);
        ButterKnife.bind(this);

        context = getApplicationContext();
        serverApp = (ServerApp) getApplication();

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
        serverApp.attachListener(Constants.CARD_RESULT, onCardResult);

        new SharedPrefs(context).write(SharedPrefs.TAG_MAINCARD, selectedCardId);
        Map<String, String> data = new HashMap<String, String>();
        data.put("roomID", new SharedPrefs(context).readString(Constants.ROOMID));
        data.put("playerID", new SharedPrefs(context).readString(Constants.PLAYERID));
        data.put("cardID", String.valueOf(selectedCardId));
        serverApp.send(Constants.CARD_SEND, data);

        startActivity(new Intent(context, WaitActivity.class));
    }

    private Emitter.Listener onCardResult = new Emitter.Listener() {
        String message;
        JSONObject resultData;
        boolean isFull, isPlayerOne;

        @Override
        public void call(Object... args) {
            String myID = new SharedPrefs(context).readString(Constants.PLAYERID);
            Log.i("PickMainCardActivity", "Card result: "+myID);

            JSONObject data = (JSONObject) args[0];
            try {
                Log.i("PickMainCardActivity", "data: " +data.getJSONObject(Constants.CODE));

                resultData = data.getJSONObject(Constants.CODE);
                isFull = resultData.getBoolean("isFull");
                isPlayerOne = resultData.getString("playerOneID").equals(myID);
            }
            catch (JSONException e) {
                Log.i("PickMainCardActivity", "Catch error");
                return;
            }

            if (isFull) {
                if (isPlayerOne) {
                    startActivity(new Intent(context, AskQuestionTurnActivity.class));
                }
                else {
                    startActivity(new Intent(context, GuessThisTurnActivity.class));
                }
            }
            else {
                startActivity(new Intent(context, WaitActivity.class));
            }
        }
    };
}
