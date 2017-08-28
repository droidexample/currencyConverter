package com.at.currencysell.root_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.at.currencysell.R;
import com.at.currencysell.SignupFirstActivity;
import com.at.currencysell.utils.PersistentUser;


public class SplashActivity extends AppCompatActivity {

    Context mContext;
    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splase);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mContext = this;
        mHandler.postDelayed(mPendingLauncherRunnable, 2000);


    }

    private final Runnable mPendingLauncherRunnable = new Runnable() {
        @Override
        public void run() {
            if (PersistentUser.isLogged(mContext)) {
                Intent mm = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(mm);
            } else {
                Intent mm = new Intent(SplashActivity.this, SignupFirstActivity.class);
                startActivity(mm);
            }

           /* if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                Intent mm = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(mm);
           } else {
                Intent mm = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(mm);
            }*/

            SplashActivity.this.finish();

        }
    };
}
