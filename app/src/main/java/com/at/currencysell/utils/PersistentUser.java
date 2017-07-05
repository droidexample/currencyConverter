package com.at.currencysell.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.at.currencysell.model.UserModel;

public class PersistentUser {


    private static final String PREFS_FILE_NAME = "currencysell";
    private static final String USERNAME = "username";
    private static final String USEREMAIL = "useremail";
    private static final String JSONUSER = "userdate";
    private static final String USERID = "uid";


    public static void setUSERNAME(final Context ctx, final String logindata) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.USERNAME, logindata);
        editor.commit();
    }

    public static void setUserEmail(final Context ctx, final String data) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(USEREMAIL, data);
        editor.commit();
    }

    public static String getUSERPIC(final Context ctx) {
        return ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE).getString(PersistentUser.JSONUSER, "");
    }

    public static void setUSERPIC(final Context ctx, final String picUrl) {
        final SharedPreferences prefs = ctx.getSharedPreferences(PersistentUser.PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(PersistentUser.JSONUSER, picUrl);
        editor.commit();
    }

    public static String getUserID(final Context ctx) {
        return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(USERID, "");
    }


    public static void setUserID(final Context ctx, final String id) {
        final SharedPreferences prefs = ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
        final Editor editor = prefs.edit();
        editor.putString(USERID, id);
        editor.commit();
    }

    public static void setLogin(Context c) {

        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("LOGIN", true).commit();
    }

    public static void logOut(Context c) {

        final SharedPreferences prefs = c.getSharedPreferences(PREFS_FILE_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putBoolean("LOGIN", false).commit();

    }


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

    public static void resetAllData(Context c) {
        setUSERNAME(c, "");
        setUserID(c, "");
        setUserEmail(c, "");
        logOut(c);
    }

}
