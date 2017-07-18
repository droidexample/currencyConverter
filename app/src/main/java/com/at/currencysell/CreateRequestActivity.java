package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateRequestActivity extends AppCompatActivity {
    private RelativeLayout rl_create_request;
    private TextView tv_currency_name_want;
    private TextView tv_currency_rate_want;
    private TextView tv_currency_name_have;
    private TextView tv_currency_rate_have;
    private TextView tv_exchange_rate;
    private TextView tv_expiry_date;
    private TextView tv_exchane_location;
    private TextView tv_remarks;
    private TextView tv_rate;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        mContext = this;
        iniUI();
    }

    private void iniUI(){

        tv_currency_name_want = (TextView)this.findViewById(R.id.tv_currency_name_want);
        tv_currency_rate_want = (TextView)this.findViewById(R.id.tv_currency_rate_want);
        tv_currency_name_have = (TextView)this.findViewById(R.id.tv_currency_name_have);
        tv_currency_rate_have = (TextView)this.findViewById(R.id.tv_currency_rate_have);
        tv_rate = (TextView)this.findViewById(R.id.tv_rate);
        tv_exchange_rate = (TextView)this.findViewById(R.id.tv_exchange_rate);
        tv_expiry_date = (TextView)this.findViewById(R.id.tv_expiry_date);
        tv_exchane_location = (TextView)this.findViewById(R.id.tv_exchane_location);
        tv_remarks = (TextView)this.findViewById(R.id.tv_remarks);

        rl_create_request = (RelativeLayout)this.findViewById(R.id.rl_create_request);
        rl_create_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRequestActivity.this,MyRequestsActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String rate = intent.getStringExtra("RATE");
        tv_rate.setText(rate);
        tv_exchange_rate.setText(rate);

    }






}
