package com.github.orangezonegame.guesswhogame.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.GuessCardCardView;
import com.github.orangezonegame.guesswhogame.common.ServerApp;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

/**
 * Created by Maik on 1/13/2018.
 */

public class GuessThisTurnActivity extends AppCompatActivity {
    private ServerApp serverApp;
    private Context context;
    private String myID;

    @BindView(R.id.layout_card)
    View cardViewInclude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_this);
        ButterKnife.bind(this);
        GuessCardCardView cardView = new GuessCardCardView();
        ButterKnife.bind(cardView, cardViewInclude);

        context = getApplicationContext();
        serverApp = (ServerApp) getApplication();
        myID = new SharedPrefs(context).readString(Constants.PLAYERID);

        int mainCardId = new SharedPrefs(context).readInt(SharedPrefs.TAG_MAINCARD);
        GuessCard mainCard = Constants.GUESS_CARDS[mainCardId];

        cardView.cardImageView.setImageResource(mainCard.getResourceId());
        cardView.cardName.setText(mainCard.getName());

        serverApp.attachListener(Constants.END_GAME, onEndGame);
        serverApp.attachListener(Constants.NEXT_RESULT, onNext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        serverApp.attachListener(Constants.END_GAME, onEndGame);
        serverApp.attachListener(Constants.NEXT_RESULT, onNext);
    }


    private Emitter.Listener onEndGame = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0], resultData;
            int guessCard, realCard;

            try {
                resultData = data.getJSONObject(Constants.CODE);
                guessCard = resultData.getInt("guessCard");
                realCard = resultData.getInt("realCard");
                Log.i("GuessActivity", "guessCard: " + guessCard);
                Log.i("GuessActivity", "realCard: " + realCard);

                Intent intent = new Intent(context, GameOverActivity.class);
                intent.putExtra("isGuesser", false);
                intent.putExtra("guess card", guessCard);
                intent.putExtra("real card", realCard);

                startActivity(intent);
            } catch (JSONException e) {
                Log.i("MainMenuActivity", "Catch error " + e);
                return;
            }
        }
    };


    private Emitter.Listener onNext = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            startActivity(new Intent(context, AskQuestionTurnActivity.class));
        }
    };

}
