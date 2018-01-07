package com.github.orangezonegame.guesswhogame.models;

/**
 * Created by Maik on 1/6/2018.
 */

public class GuessCard{
    private int id;
    private String name;
    private int resourceId;
    private boolean isChosen;

    public GuessCard(int id, String name, int resourceId, boolean isChosen) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.isChosen = isChosen;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public boolean getIsChosen(){
        return isChosen;
    }

    public void setIsChosen(boolean isChosen){
        this.isChosen = isChosen;
    }
}
