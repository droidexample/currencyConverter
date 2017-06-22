package com.at.currencysell.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PersistentUser {


    private static final String PREFS_FILE_NAME = "volunteer";


    public static void setClick(Context c) {

        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("clicked", true).commit();
    }

    public static boolean isClicked(Context c) {
        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("clicked", false);
    }

}
