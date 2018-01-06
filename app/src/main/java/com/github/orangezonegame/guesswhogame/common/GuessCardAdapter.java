package com.github.orangezonegame.guesswhogame.common;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import java.util.List;

/**
 * Created by Maik on 1/6/2018.
 */

public class GuessCardAdapter extends RecyclerView.Adapter<GuessCardViewHolder> {

    private List<GuessCard> guessCardList;

    public GuessCardAdapter(List<GuessCard> guessCardList) {
        this.guessCardList = guessCardList;
    }

    @Override
    public int getItemCount() {
        return guessCardList.size();
    }

    @Override
    public GuessCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guess_card, parent, false);
        return new GuessCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuessCardViewHolder holder, int position) {
        GuessCard card = guessCardList.get(position);
        holder.cardImage.setImageResource(card.getResourceId());
        holder.cardName.setText(card.getName());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
