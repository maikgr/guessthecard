package com.github.orangezonegame.guesswhogame.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.github.orangezonegame.guesswhogame.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;

public class MainMenuActivity extends AppCompatActivity {

    @BindView(R.id.et_room_code)
    EditText etRoomCode;

    @BindView(R.id.button_host)
    FButton buttonHost;

    @BindView(R.id.button_join)
    FButton buttonJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
    }
}
