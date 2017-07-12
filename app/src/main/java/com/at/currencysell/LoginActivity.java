package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
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
import com.at.currencysell.utils.PersistentUser;
import com.at.currencysell.utils.WebUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
                    valuationMethods();
                    /*Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();*/
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


      /*  et_email.setText("tareq236@gmail.com");
        et_password.setText("123456");*/
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
                Log.w("response", "are" + response);

                try {
                    JSONObject JSONresponse = new JSONObject(response);

                    int success = JSONresponse.getInt("success");
                    if (success == 1) {
                        String success_message = JSONresponse.getString("message");
                        JSONObject userData = JSONresponse.getJSONObject("result");
                        PersistentUser.setUSERNAME(mContext, userData.getString("first_name"));
                        PersistentUser.setUserEmail(mContext, userData.getString("email"));
                       // PersistentUser.setUSERPIC(mContext, userData.getString("picture"));
                        PersistentUser.setUserID(mContext, userData.getString("user_id"));
                        PersistentUser.setUSERDATA(mContext, ""+userData);
                        PersistentUser.setLogin(mContext);
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        Toast.makeText(mContext, success_message, Toast.LENGTH_LONG).show();


                    } else if (success == 0) {

                        String error_message = JSONresponse.getString("message");
                        Toast.makeText(mContext, error_message, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
                }


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
