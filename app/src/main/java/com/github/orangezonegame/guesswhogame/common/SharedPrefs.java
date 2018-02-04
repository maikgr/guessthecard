package com.github.orangezonegame.guesswhogame.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Maik on 2/4/2018.
 */

public class SharedPrefs {

    public static final String TAG_MAINCARD = "main card";
    public static final String TAG_MAXSPAN = "max span";

    private static final String KEY = "_key";
    private SharedPreferences prefs;

    public SharedPrefs(Context context){
        prefs = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public void write(String tag, String value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(tag, value);
        editor.apply();
    }

    public void write(String tag, int value){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(tag, value);
        editor.apply();
    }

    public String readString(String tag){
        String empty = "";
        return prefs.getString(tag, empty);
    }

    public int readInt(String tag){
        int empty = 0;
        return prefs.getInt(tag, empty);
    }
}