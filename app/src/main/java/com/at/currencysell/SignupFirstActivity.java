package com.at.currencysell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SignupFirstActivity extends AppCompatActivity {
    LinearLayout ll_email_login;
    LinearLayout ll_signup;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_first);
        mContext = this;
        initUI();
    }

    private void initUI(){
        ll_email_login = (LinearLayout)this.findViewById(R.id.ll_email_login);
        ll_email_login.setOnClickListener(listener);
        ll_signup = (LinearLayout)this.findViewById(R.id.ll_signup);
        ll_signup.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.ll_email_login:
                Intent  intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_signup:
                    Intent  intentsignup = new Intent(mContext,SignupActivity.class);
                    startActivity(intentsignup);
                    break;


            }

        }
    };
}
