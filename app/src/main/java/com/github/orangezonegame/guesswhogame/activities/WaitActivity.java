package com.github.orangezonegame.guesswhogame.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.orangezonegame.guesswhogame.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maik on 2/11/2018.
 */

public class WaitActivity extends AppCompatActivity {

    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        ButterKnife.bind(this);
    }

    private void startLoading(){
        loadingIndicatorView.show();
    }

    private void stopLoading(){
        loadingIndicatorView.hide();
    }
}
