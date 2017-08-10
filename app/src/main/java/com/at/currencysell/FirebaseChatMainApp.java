package com.at.currencysell;


import android.app.Application;
import android.support.multidex.MultiDex;

public class FirebaseChatMainApp extends Application {
    private static boolean sIsChatActivityOpen = false;

    public static boolean isChatActivityOpen() {
        return sIsChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean isChatActivityOpen) {
        FirebaseChatMainApp.sIsChatActivityOpen = isChatActivityOpen;
    }

    @Override
    public void onCreate() {

        MultiDex.install(this);
        super.onCreate();

    }
}