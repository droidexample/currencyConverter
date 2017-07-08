package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.at.currencysell.utils.AlertMessage;
import com.at.currencysell.utils.BaseUrl;
import com.at.currencysell.utils.BusyDialog;
import com.at.currencysell.utils.NetInfo;
import com.at.currencysell.utils.WebUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private RelativeLayout rl_login;
    private LinearLayout ll_back_login;
    private LinearLayout member_sign_up;
    private EditText et_email;
    private EditText et_password;
    private String userEmail;
    private String userPass;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initI();
    }

    private void initI() {

        et_email = (EditText) this.findViewById(R.id.et_email);
        et_password = (EditText) this.findViewById(R.id.et_password);
        rl_login = (RelativeLayout) this.findViewById(R.id.rl_login);
        rl_login.setOnClickListener(listener);
        member_sign_up = (LinearLayout) this.findViewById(R.id.member_sign_up);
        member_sign_up.setOnClickListener(listener);
        ll_back_login = (LinearLayout) this.findViewById(R.id.ll_back_login);
        ll_back_login.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.rl_login:
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.member_sign_up:
                    Intent in = new Intent(mContext, SignupActivity.class);
                    startActivity(in);
                    break;
                case R.id.ll_back_login:
                    LoginActivity.this.finish();
                    break;

            }

        }
    };


    public void valuationMethods() {

        userEmail = et_email.getText().toString();
        userPass = et_password.getText().toString();

        if (userEmail.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please Enter Your Email", Toast.LENGTH_LONG).show();
            return;
        } else if (!WebUtil.isValidEmailAddress(userEmail)) {
            Toast.makeText(mContext, "Email should be correct format", Toast.LENGTH_LONG).show();
            return;
        } else if (userPass.equalsIgnoreCase("")) {
            Toast.makeText(mContext, "Please Enter Your Password", Toast.LENGTH_LONG).show();

            return;
        } else {
            Intent intent = new Intent(mContext, HomeActivity.class);
            startActivity(intent);
            finish();
            signIn(userEmail, userPass);
        }

    }

    BusyDialog mBusyDialog;

    public void signIn(final String email, final String pass) {

        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

        mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.HttpUrl + "login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mBusyDialog.dismis();
                Log.w("Response", "are" + response);

//                try {
//                    JSONObject JSONresponse = new JSONObject(response.toString());
//
//                    int success = JSONresponse.getInt("success");
//                    if (success == 1) {
//                        JSONObject userData = JSONresponse.getJSONObject("user");
//                        PersistentUser.setUSERNAME(mContext, userData.getString("user_name"));
//                        PersistentUser.setUserEmail(mContext, userData.getString("email"));
//                        PersistentUser.setUSERPIC(mContext, userData.getString("image_url"));
//                        PersistentUser.setUserID(mContext, userData.getString("id"));
//                        PersistentUser.setLogin(mContext);
//                        Intent intent = new Intent(mContext, HomeActivity.class);
//
//                        startActivity(intent);
//                        LoginActivity.this.finish();
//                        Toast.makeText(mContext, "Login Successful", Toast.LENGTH_LONG).show();
//                        PersistentUser.setLogin(mContext);
//
//
//                    } else if (success == 0) {
//
//                        String error_message = JSONresponse.getString("message");
//                        Toast.makeText(mContext, error_message, Toast.LENGTH_LONG).show();
//
//                    }
//                } catch (JSONException e) {
//                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
//                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mBusyDialog.dismis();
                Log.d("response", "are" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", BaseUrl.Api_key);
                params.put("email", email);
                params.put("password", pass);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
