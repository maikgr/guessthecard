package com.github.orangezonegame.guesswhogame.common;


import com.github.orangezonegame.guesswhogame.R;
import com.github.orangezonegame.guesswhogame.models.GuessCard;

/**
 * Created by Maik on 1/1/2018.
 */

public final class Constants {
    public static final String SERVER_URL = "https://fast-gorge-57364.herokuapp.com/";

    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String ROOMID = "roomID";
    public static final String PLAYERID = "playerID";

    public static final String HOST_RESULT = "host result";
    public static final String JOIN_RESULT = "join result";
    public static final String NEW_PLAYER = "new player";
    public static final String NEXT_RESULT = "next result";
    public static final String DISCONNECT_RESULT = "disconnect result";
    public static final String GUESS_RESULT = "guess result";

    public static final String HOST_SEND = "host";
    public static final String JOIN_SEND = "join";
    public static final String NEXT_SEND = "next";
    public static final String DISCONNECT_SEND = "disconnect";
    public static final String GUESS_SEND = "guess";

    public class RESULT {
        public static final int HOST_SUCCESS = 1;
        public static final int HOST_FAIL = 2;
        public static final int JOIN_SUCCESS = 3;
        public static final int JOIN_FAIL = 4;
        public static final int EXIT = 5;
        public static final int NEW_PLAYER = 6;
        public static final int START_FIRST = 7;
        public static final int START_SECOND = 8;
    }

    public static final GuessCard[] guessCards = {
            new GuessCard(0, "Alya Steenie", R.drawable.portrait1, false),
            new GuessCard(1, "Itziar Panni", R.drawable.portrait2, false),
            new GuessCard(2, "Radha Gunhild", R.drawable.portrait3, false),
            new GuessCard(3, "Kaveh Jesper", R.drawable.portrait4, false),
            new GuessCard(4, "Bogumir", R.drawable.portrait1, false),
            new GuessCard(5, "Herman Poly", R.drawable.portrait2, false),
            new GuessCard(6, "Jeanette Ley", R.drawable.portrait3, false),
            new GuessCard(7, "Sieghild", R.drawable.portrait4, false)
    };
}
