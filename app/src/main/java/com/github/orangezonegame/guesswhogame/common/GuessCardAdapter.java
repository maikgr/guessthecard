package com.github.orangezonegame.guesswhogame.common;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import java.util.List;

public class GuessCardAdapter extends RecyclerView.Adapter<GuessCardViewHolder> {

    public interface GuessCardOnItemClickListener {
        void onItemClick(GuessCardViewHolder guessCardViewHolder, GuessCard guessCard);
    }

    private List<GuessCard> guessCardList;
    private GuessCardOnItemClickListener listener;
    private Context context;

    public GuessCardAdapter(Context context, List<GuessCard> guessCardList, GuessCardOnItemClickListener listener) {
        this.guessCardList = guessCardList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return guessCardList.size();
    }

    @Override
    public GuessCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_guess_card, parent, false);
        return new GuessCardViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(GuessCardViewHolder holder, int position) {
        GuessCard card = guessCardList.get(position);
        holder.cardImage.setImageResource(card.getResourceId());
        holder.cardName.setText(card.getName());
        holder.setIsChosen(card.getIsChosen());
        holder.bind(card, listener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
