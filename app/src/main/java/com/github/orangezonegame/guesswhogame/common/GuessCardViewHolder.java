package com.github.orangezonegame.guesswhogame.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maik on 1/6/2018.
 */

public class GuessCardViewHolder extends ViewHolder {
    @BindView(R.id.image_card_frame)
    ImageView cardImage;

    @BindView(R.id.tv_card_name)
    TextView cardName;

    @BindView(R.id.cv_card)
    CardView cardCardView;

    @BindView(R.id.overlay)
    ConstraintLayout cardOverlay;

    private boolean isChosen;
    private Context context;

    public GuessCardViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
        setIsChosen(false);
    }

    public void bind(final GuessCard guessCard, final GuessCardAdapter.GuessCardOnItemClickListener listener){
        final GuessCardViewHolder viewHolder = this;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(viewHolder, guessCard);
                guessCard.setIsChosen(isChosen);
            }
        });
    }

    public void setIsChosen(boolean isChosen){
        this.isChosen = isChosen;
        if(isChosen){
            cardOverlay.setBackground(context.getDrawable(R.drawable.overlay));
        } else{
            cardOverlay.setBackgroundResource(0);
        }
    }

    public void toggleState(){
        setIsChosen(!isChosen);
    }
}
