package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.at.currencysell.adapter.CurrencyNameAdapter;
import com.at.currencysell.holder.AllCurrencyList;
import com.at.currencysell.model.Currency_Names;
import com.at.currencysell.utils.AllCountryName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class CurrencyNameActivity extends AppCompatActivity {

    public JSONObject jsonObj_names = null;
    String s_names, s_rates = null;
    private ListView listview;
    private CurrencyNameAdapter adapter_listview;
    private Context mContext;
    private EditText edt_search;
    private LinearLayout ll_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_name);

        mContext = this;

        initI();
    }

    private void initI() {

        listview = (ListView) this.findViewById(R.id.listview);
        edt_search = (EditText) this.findViewById(R.id.edt_search);

        // for scarch list
        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    adapter_listview.getFilter().filter(s.toString());

                } catch (Exception e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra("MESSAGE", AllCurrencyList.getDolorList(position).getShort_name());
                setResult(1, intent);
                finish();
            }
        });

        add_country_names(AllCountryName.ALLCOUNTRYNAME);

        ll_back = (LinearLayout) this.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrencyNameActivity.this.finish();
            }
        });
    }




    public void add_country_names(String response) {

        try {
            jsonObj_names = new JSONObject(response);
            s_names = jsonObj_names.getJSONObject("currencies").toString();
            Log.w("s_name", "are" + s_names);

        } catch (JSONException e) {
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
        }


        AllCurrencyList.removeAllCurrencyList();
        s_names = s_names.replace("{", "");
        s_names = s_names.replace("}", "");
        s_names = s_names.replace("\"", "");

        StringTokenizer stoke = new StringTokenizer(s_names, ",");

        while (stoke.hasMoreElements()) {

            String temp = stoke.nextElement().toString();
            String split[] = temp.split(":");

            AllCurrencyList.setmCurrencyList(new Currency_Names(split[0], split[1]));


        }


        Collections.sort(AllCurrencyList.getmAllCurrencyList(), new Comparator<Currency_Names>() {
            @Override
            public int compare(Currency_Names n1, Currency_Names n2) {
                return n1.short_name.compareTo(n2.short_name);
            }
        });

        adapter_listview = new CurrencyNameAdapter(this, R.layout.row_item_currency_name, AllCurrencyList.getmAllCurrencyList());
        listview.setAdapter(adapter_listview);
        adapter_listview.notifyDataSetChanged();


    }


}
