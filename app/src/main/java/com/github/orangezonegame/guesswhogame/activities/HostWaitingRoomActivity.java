package com.github.orangezonegame.guesswhogame.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.orangezonegame.guesswhogame.R;

/**
 * Created by Maik on 1/1/2018.
 */

public class HostWaitingRoomActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_waiting_room);

        context = getApplicationContext();
    }
}
