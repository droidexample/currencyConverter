package com.at.currencysell;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateRequestActivity extends AppCompatActivity {
    private RelativeLayout rl_create_request;
    private TextView tv_currency_name_want;
    private ImageView img_currency_want;
    private EditText et_currency_rate_want;
    private TextView tv_currency_name_have;
    private ImageView img_currency_have;
    private EditText et_currency_rate_have;
    private TextView tv_exchange_rate;
    private TextView tv_expiry_date;
    private TextView tv_exchane_location;
    private EditText tv_remarks;
    private TextView tv_rate;
    private Context mContext;
    private int resIdone;
    private int resIdtwo;
    private String rate;
    private String first_curr;
    private String second_curr;
    private LinearLayout ll_back;
    private RelativeLayout rl_expiry_date;
    private static final int DATE_DIALOG_ID = 9999;
    private int year, month, day;
    private float want = (float) 00.00;
    private float have = (float) 00.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        mContext = this;
        iniUI();
    }

    private void iniUI() {

        tv_currency_name_want = (TextView) this.findViewById(R.id.tv_currency_name_want);
        et_currency_rate_want = (EditText) this.findViewById(R.id.et_currency_rate_want);
        img_currency_want = (ImageView) this.findViewById(R.id.img_currency_want);
        tv_currency_name_have = (TextView) this.findViewById(R.id.tv_currency_name_have);
        img_currency_have = (ImageView) this.findViewById(R.id.img_currency_have);
        et_currency_rate_have = (EditText) this.findViewById(R.id.et_currency_rate_have);
        tv_rate = (TextView) this.findViewById(R.id.tv_rate);
        tv_exchange_rate = (TextView) this.findViewById(R.id.tv_exchange_rate);
        tv_expiry_date = (TextView) this.findViewById(R.id.tv_expiry_date);
        tv_exchane_location = (TextView) this.findViewById(R.id.tv_exchane_location);
        tv_remarks = (EditText) this.findViewById(R.id.tv_remarks);

        ll_back = (LinearLayout) this.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateRequestActivity.this.finish();
            }
        });
        rl_expiry_date = (RelativeLayout) this.findViewById(R.id.rl_expiry_date);
        rl_expiry_date.setOnClickListener(listener);

        rl_create_request = (RelativeLayout) this.findViewById(R.id.rl_create_request);
        rl_create_request.setOnClickListener(listener);
        Intent intent = getIntent();
        rate = intent.getStringExtra("RATE");
        first_curr = intent.getStringExtra("CURRNAMEWANT");
        second_curr = intent.getStringExtra("CURRNAMEHAVE");
        tv_rate.setText(rate);
        tv_exchange_rate.setText(rate);
        tv_currency_name_want.setText(first_curr);
        resIdone = mContext.getResources().getIdentifier(first_curr.toLowerCase(), "drawable", mContext.getPackageName());
        resIdtwo = mContext.getResources().getIdentifier(second_curr.toLowerCase(), "drawable", mContext.getPackageName());
        tv_currency_name_have.setText(second_curr);
        img_currency_want.setImageResource(resIdone);
        img_currency_have.setImageResource(resIdtwo);
        et_currency_rate_have.setText(rate);
        et_currency_rate_want.setText("1");

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        tv_expiry_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

        et_currency_rate_have.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                want = Float.parseFloat(et_currency_rate_want.getText().toString());
                have = Float.parseFloat(et_currency_rate_have.getText().toString());
                String exchange_rate = String.valueOf(have / want);
                tv_exchange_rate.setText(exchange_rate);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_currency_rate_want.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    want = Float.parseFloat(et_currency_rate_want.getText().toString());
                    have = Float.parseFloat(et_currency_rate_have.getText().toString());
                    String exchange_rate = String.valueOf(have / want);
                    tv_exchange_rate.setText(exchange_rate);
                    Float f = Float.parseFloat(et_currency_rate_want.getText().toString()) * Float.parseFloat(rate);
                    et_currency_rate_have.setText(String.valueOf(f));

                } catch (Exception e) {

                } finally {
                    want = (float) 00.00;
                    have = (float) 00.00;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.rl_expiry_date:
                    showDialog(DATE_DIALOG_ID);
                    break;
                case R.id.rl_create_request:
                    doWebRequestForCreateRequest();
                    break;

            }

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 9999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }

        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub

                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        tv_expiry_date.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    BusyDialog mBusyDialog;

    public void doWebRequestForCreateRequest() {

        // PeopleHaveList.removeAllPeopleHaveList();

        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

        mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, BaseUrl.HttpUrl + "insert-currency-want", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.w("response", "are" + response);
                mBusyDialog.dismis();
                Toast.makeText(mContext, "Request Sucess", Toast.LENGTH_LONG).show();
                CreateRequestActivity.this.finish();
/*
                try {



                    JSONObject JsonObject = new JSONObject(response);
                    JSONArray JsonMain = JsonObject.getJSONArray("result");
                    for (int i = 0; i < JsonMain.length(); i++) {
                        JSONObject m = JsonMain.getJSONObject(i);
                        HomeListModel model = new HomeListModel();
                        model.setName(m.getString("user_name"));
                        model.setEnd_date(m.getString("end_date"));
                        model.setLocation(m.getString("location"));
                        model.setWant_currency(m.getString("have_crr"));
                        model.setWant_amount(m.getString("have_amount"));
                        model.setPay_currency(m.getString("sell_crr"));
                        model.setPay_amount(m.getString("sell_amount"));
                        model.setImage_url(m.getString("image"));
                        PeopleHaveList.setPeopleHaveinfo(model);

                    }

                    adapter = new HomeAdapter(mContext,  PeopleHaveList.getAllPeopleHave());
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mBusyDialog.dismis();


                } catch (JSONException e) {
                    e.printStackTrace();
                    mBusyDialog.dismis();

                }*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("response", "are" + error);
                mBusyDialog.dismis();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", BaseUrl.Api_key);
                params.put("user_id", PersistentUser.getUserID(mContext));
                params.put("want_amount", et_currency_rate_want.getText().toString());
                params.put("want_crr", tv_currency_name_want.getText().toString());
                params.put("pay_amount", et_currency_rate_have.getText().toString());
                params.put("pay_crr", tv_currency_name_have.getText().toString());
                params.put("end_date", tv_expiry_date.getText().toString());
                params.put("location", "India");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }


}
