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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

/**
 * Created by Maik on 1/13/2018.
 */

public class FinalGuessActivity extends AppCompatActivity {
    @BindView(R.id.rv_cards)
    RecyclerView cardsRecyclerView;

    private Context context;
    private ServerApp serverApp;
    private List<GuessCard> cards;
    private GuessCardViewHolder lastSelectedViewHolder;
    private GuessCard lastSelectedCard;
    private int selectedCardId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_guess);
        ButterKnife.bind(this);

        context = getApplicationContext();
        serverApp = (ServerApp) getApplication();

        serverApp.attachListener(Constants.END_GAME, onEndGame);

        SharedPrefs prefs = new SharedPrefs(context);
        String cardsJson = prefs.readString(SharedPrefs.TAG_ACTIVECARDS);
        cards = new Gson().fromJson(cardsJson, new TypeToken<List<GuessCard>>(){}.getType());

        GridLayoutManager layoutManager = new GridLayoutManager(context, prefs.readInt(SharedPrefs.TAG_MAXSPAN));
        cardsRecyclerView.setLayoutManager(layoutManager);

        GuessCardAdapter adapter = new GuessCardAdapter(context, cards, new GuessCardAdapter.GuessCardOnItemClickListener() {
            @Override
            public void onItemClick(GuessCardViewHolder view, GuessCard guessCard) {
                if(lastSelectedViewHolder != null){
                    lastSelectedViewHolder.setIsChosen(false);
                    lastSelectedCard.setIsChosen(false);
                }
                view.setIsChosen(true);
                guessCard.setIsChosen(true);
                selectedCardId = guessCard.getId();
                lastSelectedViewHolder = view;
                lastSelectedCard = guessCard;
            }
        });

        cardsRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serverApp.attachListener(Constants.END_GAME, onEndGame);
    }

    @OnClick(R.id.button_cancel)
    public void cancelFinalGuess(){
        startActivity(new Intent(context, AskQuestionTurnActivity.class));
    }

    @OnClick(R.id.button_confirm)
    public void confirmFinalGuess(View view){
        try{
            if (selectedCardId != -1) {
                Map<String, String> data = new HashMap<String, String>();
                data.put("roomID", new SharedPrefs(context).readString(Constants.ROOMID));
                data.put("playerID", new SharedPrefs(context).readString(Constants.PLAYERID));
                data.put("cardID", Integer.toString(selectedCardId));
                serverApp.send(Constants.GUESS_SEND, data);
                view.setVisibility(View.INVISIBLE);
                serverApp.attachListener(Constants.END_GAME, onEndGame);
            }

        } catch(NullPointerException | IndexOutOfBoundsException e){
            Toast toast = Toast.makeText(context, "Please select a card", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private Emitter.Listener onEndGame = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0], resultData;
            int guessCard, realCard;

            try {
                Log.i("FinalGuess", "END GAME");

                resultData = data.getJSONObject(Constants.CODE);
                guessCard = resultData.getInt("guessCard");
                realCard = resultData.getInt("realCard");

                Intent intent = new Intent(context, GameOverActivity.class);
                intent.putExtra("isGuesser", true);
                intent.putExtra("guess card", guessCard);
                intent.putExtra("real card", realCard);

                startActivity(intent);
            } catch (JSONException e) {
                Log.i("MainMenuActivity", "Catch error");
            }
        }
    };
}
