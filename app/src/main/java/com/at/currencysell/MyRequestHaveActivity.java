package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.at.currencysell.adapter.MyRequestHaveListAdapter;

import com.at.currencysell.model.MyReqestActiveMoel;
import com.at.currencysell.utils.PersistentUser;

import java.util.ArrayList;
import java.util.List;

public class MyRequestHaveActivity extends AppCompatActivity {

    String[] currency_short_form = {"usd","euro","jpy","pound","cad","mxn","hkd"};
    String[] currency_full_form = {"Us Dollar","Euro","Japanese Yen","UK","Canadian Dollar","Mexican Peso","Hong Kong"};
    int[] images = {R.drawable.ic_usa,R.drawable.ic_eur_img,R.drawable.ic_japan,R.drawable.ic_uk,R.drawable.ic_franch,R.drawable.ic_usa,R.drawable.ic_uk};
    private List<MyReqestActiveMoel> currencyList = new ArrayList<MyReqestActiveMoel>();
    private ListView listView;
    private MyRequestHaveListAdapter adapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_have);
        mContext = this;

        ActionBar actionBar = getSupportActionBar();

        TextView textview = new TextView(mContext);

        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        textview.setText("I HAVE");
        textview.setTextColor(Color.WHITE);
        textview.setTextSize(20);
        textview.setGravity(Gravity.CENTER);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textview);

        initUI();
    }

    private void initUI(){
        for (int i = 0;i<currency_short_form.length;i++){
            MyReqestActiveMoel nameItem = new MyReqestActiveMoel();
            nameItem.setCurrency_name(currency_short_form[i]);
            nameItem.setCurrency_full_name(currency_full_form[i]);
            nameItem.setImage(images[i]);

            currencyList.add(nameItem);

        }

        listView = (ListView) findViewById(R.id.list_my_request_have);
        adapter=new MyRequestHaveListAdapter(this,currencyList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PersistentUser.setClick(mContext);
                Intent intent = new Intent(mContext,MyRequestWantActivity.class);
                startActivity(intent);
            }
        });

    }
}
