package com.at.currencysell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class CreateRequestActivity extends AppCompatActivity {
    private RelativeLayout rl_create_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        iniUI();
    }

    private void iniUI(){

        rl_create_request = (RelativeLayout)this.findViewById(R.id.rl_create_request);
        rl_create_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateRequestActivity.this,MyRequestsActivity.class);
                startActivity(intent);
            }
        });

    }
}
