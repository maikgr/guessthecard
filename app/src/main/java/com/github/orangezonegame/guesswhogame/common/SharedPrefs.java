package com.github.orangezonegame.guesswhogame.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Maik on 2/4/2018.
 */

public class SharedPrefs {

    public static final String TAG_MAINCARD = "main card";

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

    public String read(String tag){
        String empty = "";
        return prefs.getString(tag, empty);
    }
}