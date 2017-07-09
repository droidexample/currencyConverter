package com.at.currencysell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.at.currencysell.adapter.Adapter_listview;
import com.at.currencysell.model.Currency_Names;
import com.at.currencysell.utils.AlertMessage;
import com.at.currencysell.utils.BaseUrl;
import com.at.currencysell.utils.BusyDialog;
import com.at.currencysell.utils.NetInfo;
import com.at.currencysell.utils.PersistentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CurrencyNameActivity extends AppCompatActivity {

    public JSONObject jsonObj_names=null            ;
    String  s_names=null            ;

    ListView listview                                           ;
    Adapter_listview adapter_listview                                   ;
    public static ArrayList<Currency_Names> list_currency_names_data         ;
    String ulr_curency_namees;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_name);

        mContext = this;

        initI();
    }

    private void initI(){

        listview = (ListView)this.findViewById(R.id.listview);
        list_currency_names_data= new ArrayList<>();
        getCrrencyName();
    }

    public void getCrrencyName() {
        //apilayer api key
        String key= getResources().getString(R.string.Currencylayer_Key);
        ulr_curency_namees="http://www.apilayer.net/api/list?access_key="+key+"&format=1";


        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }

      /*  mBusyDialog = new BusyDialog(mContext, true, "Loading");
        mBusyDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ulr_curency_namees, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              //  mBusyDialog.dismis();
                Log.w("response", "are" + response);

                try {
                    jsonObj_names = new JSONObject(response);
                    s_names= jsonObj_names.getJSONObject("currencies").toString();
                    Log.w("s_name", "are" + s_names);

                } catch (JSONException e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_LONG).show();
                }
                    add_country_names();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // mBusyDialog.dismis();
                Log.d("response", "are" + error);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }


    public void add_country_names()
    {
        s_names=s_names.replace("{","");
        s_names=s_names.replace("}","");
        s_names=s_names.replace("\"","");

        StringTokenizer stoke= new StringTokenizer(s_names,",");

        while(stoke.hasMoreElements())
        {

            String temp= stoke.nextElement().toString();
            String split[]= temp.split(":");

            list_currency_names_data.add(new Currency_Names(split[0], split[1]));



        }


        Collections.sort(list_currency_names_data, new Comparator<Currency_Names>() {
            @Override
            public int compare(Currency_Names n1, Currency_Names n2) {
                return n1.short_name.compareTo(n2.short_name);
            }
        });


        adapter_listview= new Adapter_listview(CurrencyNameActivity.this,list_currency_names_data);
        listview.setAdapter(adapter_listview);

        //pDialog.dismiss();


    }

}
