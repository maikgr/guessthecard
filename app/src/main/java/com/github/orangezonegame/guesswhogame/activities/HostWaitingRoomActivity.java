package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Maik on 1/1/2018.
 */

public class HostWaitingRoomActivity extends AppCompatActivity {

    @BindView(R.id.tv_room_code)
    TextView roomCodeTextView;

    private Context context;
    private String roomCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_waiting_room);
        ButterKnife.bind(this);

        context = getApplicationContext();
        Intent intent = getIntent();
        roomCode = intent.getStringExtra("Room Code");
        roomCodeTextView.setText(roomCode);
    }

    @OnClick(R.id.button_cancel)
    public void cancelHostRoom(){
        startActivity(new Intent(context, MainMenuActivity.class));
    }
}
