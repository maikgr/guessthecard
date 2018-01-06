package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.github.orangezonegame.guesswhogame.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.et_room_code)
    EditText etRoomCode;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        context = getApplicationContext();
        etRoomCode.setText(randomRoomCode());
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
        Intent intent = new Intent(context, HostWaitingRoomActivity.class);
        intent.putExtra("Room Code", etRoomCode.getText().toString());
        startActivity(intent);
    }

    @OnClick(R.id.button_join)
    public void joinRoom(){
        //String roomCode = etRoomCode.getText().toString().toUpperCase();
        startActivity(new Intent(context, PickMainCardActivity.class));
    }
}
