package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.ServerApp;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.et_room_code)
    EditText etRoomCode;

    private Context context;
    private ServerApp serverApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        serverApp = (ServerApp) getApplication();
        context = getApplicationContext();
        etRoomCode.setText(randomRoomCode());
        SharedPrefs prefs = new SharedPrefs(context);
        prefs.remove();
        prefs.write(SharedPrefs.TAG_MAXSPAN, getScreenMaxSpan());
    }

    private String randomRoomCode(){
        final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final int roomCodeLimit = 6;

        StringBuilder roomCode = new StringBuilder();
        while (roomCode.length() < roomCodeLimit){
            int stringIndex = (int)(Math.random() * ALPHA_NUMERIC.length());
            roomCode.append(ALPHA_NUMERIC.charAt(stringIndex));
        }

        return roomCode.toString();
    }

    @OnClick(R.id.button_host)
    public void hostRoom(){
        Log.i("MainMenuActivity", "host");
        String roomCode = etRoomCode.getText().toString();
        serverApp.attachListener(Constants.HOST_RESULT, onHostResult);
        new SharedPrefs(context).write(SharedPrefs.TAG_STATE, Constants.HOST_SEND);
        sendRoomID(Constants.HOST_SEND, roomCode);
    }

    @OnClick(R.id.button_join)
    public void joinRoom(){
        String roomCode = etRoomCode.getText().toString();
        serverApp.attachListener(Constants.JOIN_RESULT, onJoinResult);
        new SharedPrefs(context).write(SharedPrefs.TAG_STATE, Constants.JOIN_SEND);
        sendRoomID(Constants.JOIN_SEND, roomCode);
    }

    @OnClick(R.id.button_test)
    public void testApp(){
        startActivity(new Intent(context, PickMainCardActivity.class));
    }

    public void sendRoomID(String signal, String roomCode) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("roomID", roomCode);
        serverApp.send(signal, data);
    }

    public void launchHost(String roomCode) {
        Intent intent = new Intent(context, HostWaitingRoomActivity.class);
        intent.putExtra("Room Code", roomCode);
        startActivity(intent);
    }

    private Emitter.Listener onHostResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //serverApp.detachListener(Constants.HOST_RESULT, onHostResult);

            JSONObject data = (JSONObject) args[0];

            Log.i("MainMenuActivity", "in host result");

            String message, roomID;
            int playerID, resultCode;

            try {
                resultCode = data.getInt(Constants.CODE);
                message = data.getString(Constants.MESSAGE);
                playerID = data.getInt(Constants.PLAYERID);
                roomID = data.getString(Constants.ROOMID);
                Log.i("MainMenuActivity", "code: " + resultCode + ", message: " + message);
            } catch (JSONException e) {
                Log.i("MainMenuActivity", "Catch error");
                return;
            }

            if (resultCode == Constants.RESULT.HOST_SUCCESS) {
                launchHost(roomID);
                // plus save playerID
            }
            else {
                // display error message
            }
        }
    };

    private Emitter.Listener onJoinResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //serverApp.detachListener(Constants.JOIN_RESULT, onJoinResult);

            JSONObject data = (JSONObject) args[0];
            Log.i("MainMenuActivity", "in join result");

            String message, roomID;
            int playerID, resultCode;

            try {
                resultCode = data.getInt(Constants.CODE);
                message = data.getString(Constants.MESSAGE);
                playerID = data.getInt(Constants.PLAYERID);
                roomID = data.getString(Constants.ROOMID);
                Log.i("MainMenuActivity", "code: " + resultCode + ", message: " + message);
            } catch (JSONException e) {
                Log.i("MainMenuActivity", "JOIN: Catch error");
                return;
            }

            if (resultCode == Constants.RESULT.JOIN_SUCCESS) {
                //String roomCode = etRoomCode.getText().toString().toUpperCase();
                startActivity(new Intent(context, PickMainCardActivity.class));
            }
            else {
                // display error message
            }
        }
    };

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
