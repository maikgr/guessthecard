package com.github.orangezonegame.guesswhogame.common;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

import java.util.ArrayList;
import java.util.List;

public class GuessCardAdapter extends RecyclerView.Adapter<GuessCardViewHolder> implements AdapterView.OnItemClickListener {

    public interface GuessCardOnItemClickListener {
        void onItemClick(GuessCardViewHolder guessCardViewHolder, GuessCard guessCard);
    }

    private List<GuessCard> guessCardList;
    private List<GuessCardViewHolder> guessCardHolderList;
    private int lastPosition = -1;

    public GuessCardAdapter(List<GuessCard> guessCardList) {
        this.guessCardList = guessCardList;
        guessCardHolderList = new ArrayList<>();
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
        guessCardHolderList.add(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        toggleSingle(i);
    }

    public void toggleSingle(int position) {
        if(lastPosition == -1){
            guessCardHolderList.get(position).enable();
        } else{
            guessCardHolderList.get(lastPosition).disable();
            guessCardHolderList.get(position).enable();
        }
        lastPosition = position;
        notifyDataSetChanged();
    }

    public List<GuessCard> getGuessCardList() {
        return guessCardList;
    }
}
