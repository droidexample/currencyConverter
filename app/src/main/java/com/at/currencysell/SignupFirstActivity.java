package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.at.currencysell.model.UserModel;
import com.at.currencysell.root_activities.HomeActivity;
import com.at.currencysell.utils.AlertMessage;
import com.at.currencysell.utils.BaseUrl;
import com.at.currencysell.utils.BusyDialog;
import com.at.currencysell.utils.NetInfo;
import com.at.currencysell.utils.PersistentUser;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SignupFirstActivity extends AppCompatActivity {
    private LinearLayout ll_email_login;
    private LinearLayout ll_signup;
    private RelativeLayout rl_fb_login;
    Context mContext;
    private CallbackManager callbackManager;
    URL profile_pic;
    UserModel user;
    private String firstname;
    private String email;
    BusyDialog mBusyDialog;
    private LoginButton loginButton;
    private String login_type = "2";
    private String social_status = "fab1234#$%dfs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        FacebookSdk.sdkInitialize(mContext);
        setContentView(R.layout.activity_signup_first);
        initUI();
    }

    private void initUI(){
        rl_fb_login = (RelativeLayout) this.findViewById(R.id.rl_fb_login);
        ll_email_login = (LinearLayout)this.findViewById(R.id.ll_email_login);
        ll_email_login.setOnClickListener(listener);
        ll_signup = (LinearLayout)this.findViewById(R.id.ll_signup);
        ll_signup.setOnClickListener(listener);



        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.at.currencysell", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
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

    @Override
    protected void onResume() {
        super.onResume();

        callbackManager= CallbackManager.Factory.create();

        loginButton= (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions("public_profile", "email","user_friends");


        rl_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBusyDialog = new BusyDialog(mContext, true, "Loading");
                mBusyDialog.show();

                loginButton.performClick();

                loginButton.invalidate();

                loginButton.registerCallback(callbackManager, mCallBack);

                loginButton.setPressed(false);

            }
        });

    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            mBusyDialog.dismis();


            // App code
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.w("Response", "are" + response + "");

                            String id = null;
                            try {
                                id = object.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                                Log.w("FB profile_pic", profile_pic + "");

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            }
                            try {
                                user = new UserModel();
                                user.facebookID = object.getString("id").toString();
                                user.name = object.getString("name").toString();
                                user.email = object.getString("email").toString();
                                PersistentUser.setCurrentUser(user,mContext);


                            }catch (Exception e){
                                Toast.makeText(mContext,e.toString(),Toast.LENGTH_LONG).show();
                            }

                            firstname = user.name;
                            email = user.email;
                            doWebRequestForFbLogin(firstname,firstname,email,login_type,social_status);



                        }

                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }


        @Override
        public void onCancel() {
            mBusyDialog.dismis();
        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(mContext,e.toString(),Toast.LENGTH_LONG).show();
            mBusyDialog.dismis();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void doWebRequestForFbLogin(final String first,final String last,final String mail, final String logintype,final String social_status) {

        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

        mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.HttpUrl + "registration", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mBusyDialog.dismis();
                Log.w("response", "are" + response);
                try {
                    JSONObject JSONresponse = new JSONObject(response);

                    int success = JSONresponse.getInt("success");
                    if (success == 1) {
                        JSONObject userData = JSONresponse.getJSONObject("result");
                        PersistentUser.setUSERNAME(mContext, userData.getString("first_name"));
                        PersistentUser.setUserEmail(mContext, userData.getString("email"));
                        PersistentUser.setUSERPIC(mContext, profile_pic.toString());
                        PersistentUser.setUserID(mContext, userData.getString("user_id"));
                        PersistentUser.setLogin(mContext);

                        Intent intent = new Intent(mContext, HomeActivity.class);
                        startActivity(intent);
                        SignupFirstActivity.this.finish();


                        Toast.makeText(mContext, "" + JSONresponse.getString("message"), Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(mContext, "" + JSONresponse.getInt("message"), Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException sd) {
                    Toast.makeText(mContext, sd.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mBusyDialog.dismis();
                Log.w("response", "are" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_key", BaseUrl.Api_key);
                params.put("first_name", first);
                params.put("last_name", last);
                params.put("email", mail);
                params.put("login_type", logintype);
                params.put("social_status", social_status);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }
}
