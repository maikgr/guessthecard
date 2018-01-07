package com.github.orangezonegame.guesswhogame.common;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maik on 1/6/2018.
 */

public class GuessCardViewHolder extends ViewHolder implements View.OnClickListener {
    @BindView(R.id.image_card_frame)
    ImageView cardImage;

    @BindView(R.id.tv_card_name)
    TextView cardName;

    @BindView(R.id.cv_card)
    CardView cardCardView;

    public GuessCardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onClick(View view) {

    }
}
