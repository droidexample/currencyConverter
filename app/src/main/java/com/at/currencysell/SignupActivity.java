package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SignupActivity extends AppCompatActivity {
    private LinearLayout ll_back_sign_up;
    private LinearLayout ll_member_login;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mContext = this;

        initUI();
    }

    private void initUI(){
        ll_back_sign_up = (LinearLayout)this.findViewById(R.id.ll_back_sign_up);
        ll_back_sign_up.setOnClickListener(listener);
        ll_member_login = (LinearLayout)this.findViewById(R.id.ll_member_login);
        ll_member_login.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.ll_back_sign_up:
                    SignupActivity.this.finish();
                    break;

                case R.id.ll_member_login:
                    Intent  intent = new Intent(mContext,LoginActivity.class);
                    startActivity(intent);
                    break;

            }

        }
    };
}
