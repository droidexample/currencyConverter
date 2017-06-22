package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {
    private RelativeLayout rl_login;
    private LinearLayout ll_back_login;
    private LinearLayout member_sign_up;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initI();
    }

    private void initI(){
        rl_login = (RelativeLayout)this.findViewById(R.id.rl_login);
        rl_login.setOnClickListener(listener);
        member_sign_up = (LinearLayout)this.findViewById(R.id.member_sign_up);
        member_sign_up.setOnClickListener(listener);
        ll_back_login = (LinearLayout)this.findViewById(R.id.ll_back_login);
        ll_back_login.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.rl_login:
                    Intent intent = new Intent(mContext,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.member_sign_up:
                    Intent in = new Intent(mContext,SignupActivity.class);
                    startActivity(in);
                    break;
                case R.id.ll_back_login:
                    LoginActivity.this.finish();
                    break;

            }

        }
    };
}
