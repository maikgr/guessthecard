package com.github.orangezonegame.guesswhogame.models;

/**
 * Created by Maik on 1/6/2018.
 */

public class GuessCard{
    private int id;
    private String name;
    private int resourceId;

    public GuessCard(int id, String name, int resourceId) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
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
}
