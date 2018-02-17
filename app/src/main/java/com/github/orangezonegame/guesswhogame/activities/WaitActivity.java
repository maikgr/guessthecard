package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.ServerApp;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.emitter.Emitter;

/**
 * Created by Maik on 2/11/2018.
 */

public class WaitActivity extends AppCompatActivity {
    private ServerApp serverApp;
    private Context context;

    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wait);
        ButterKnife.bind(this);

        context = getApplicationContext();
        serverApp = (ServerApp) getApplication();
        serverApp.attachListener(Constants.START_GAME, onStartGame);

        startLoading();
    }

    @Override
    protected void onResume() {
        super.onResume();
        serverApp.attachListener(Constants.START_GAME, onStartGame);
    }

    @Override
    protected void onPause() {
        super.onPause();
        serverApp.detachListener(Constants.START_GAME, onStartGame);
    }

    private void startLoading(){
        loadingIndicatorView.show();
    }

    private void stopLoading(){
        loadingIndicatorView.hide();
    }

    private Emitter.Listener onStartGame = new Emitter.Listener() {
        JSONObject resultData;
        boolean isPlayerOne;

        @Override
        public void call(Object... args) {
            String myID = new SharedPrefs(context).readString(Constants.PLAYERID);
            Log.i("WaitActivity", "Start game id: " + myID);

            JSONObject data = (JSONObject) args[0];
            try {
                Log.i("WaitActivity", "data: " +data.getJSONObject(Constants.CODE));
                resultData = data.getJSONObject(Constants.CODE);
                isPlayerOne = resultData.getString("playerOneID").equals(myID);
            }
            catch (JSONException e) {
                Log.i("WaitActivity", "Catch error");
                return;
            }

            if (isPlayerOne) {
                startActivity(new Intent(context, AskQuestionTurnActivity.class));
            }
            else {
                startActivity(new Intent(context, GuessThisTurnActivity.class));
            }
        }
    };
}
