package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.common.Constants;
import com.github.orangezonegame.guesswhogame.common.ServerApp;
import com.github.orangezonegame.guesswhogame.common.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

/**
 * Created by Maik on 1/1/2018.
 */

public class HostWaitingRoomActivity extends AppCompatActivity {

    @BindView(R.id.tv_room_code)
    TextView roomCodeTextView;

    private Context context;
    private String roomCode;
    private ServerApp serverApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_waiting_room);
        ButterKnife.bind(this);

        context = getApplicationContext();
        Intent intent = getIntent();
        roomCode = intent.getStringExtra("Room Code");
        roomCodeTextView.setText(roomCode);

        serverApp = (ServerApp) getApplication();
        serverApp.attachListener(Constants.NEW_PLAYER, onPlayerJoined);
    }

    @OnClick(R.id.button_cancel)
    public void cancelHostRoom(){
        startActivity(new Intent(context, MainMenuActivity.class));
    }

    private Emitter.Listener onPlayerJoined = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            //serverApp.detachListener(Constants.JOIN_RESULT, onJoinResult);
            JSONObject data = (JSONObject) args[0];
            Log.i("HostWaitingRoom", "in join result");

            int resultCode;
            String message, playerID, roomID;

            try {
                resultCode = data.getInt(Constants.CODE);
                Log.i("HostWaitingRoom", "code: " + resultCode);
            } catch (JSONException e) {
                Log.i("HostWaitingRoom", "JOIN: Catch error");
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
}
