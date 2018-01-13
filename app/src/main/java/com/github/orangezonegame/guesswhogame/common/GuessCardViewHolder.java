package com.github.orangezonegame.guesswhogame.common;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.AdapterView;
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
    ImageView cardOverlay;

    private View itemView;
    private int cardState;

    public GuessCardViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
        setState(View.INVISIBLE);
    }

    public void bind(final GuessCard guessCard, final GuessCardAdapter.GuessCardOnItemClickListener listener){
        final GuessCardViewHolder viewHolder = this;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(viewHolder, guessCard);
            }
        });
    }

    public void toggleState(){
        if (cardState == View.INVISIBLE){
            setState(View.VISIBLE);
        } else{
            setState(View.INVISIBLE);
        }
    }

    public int getState(){
        return cardState;
    }

    private void setState(int viewState){
        cardOverlay.setVisibility(viewState);
        cardState = viewState;
    }

    public void enable(){
        setState(View.VISIBLE);
    }

    public void disable(){
        setState(View.INVISIBLE);
    }
}
