package com.at.currencysell.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.at.currencysell.model.UserModel;

public class PersistentUser {


    private static final String PREFS_FILE_NAME = "currencysell";


    public static void setClick(Context c) {

        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("clicked", true).commit();
    }

    public static boolean isClicked(Context c) {
        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean("clicked", false);
    }

    public static void NotClicked(Context c) {

        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("clicked", false).commit();

    }

    public static void setCurrentUser(UserModel currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("current_user_value", currentUser);
        complexPreferences.commit();
    }



    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

}
