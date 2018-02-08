package com.github.orangezonegame.guesswhogame.common;

import android.widget.ImageView;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;

import butterknife.BindView;

/**
 * Created by Maik on 2/7/2018.
 */

public class GuessCardCardView {
    @BindView(R.id.image_card_frame)
    public ImageView cardImageView;

    @BindView(R.id.tv_card_name)
    public TextView cardName;
}
