package com.at.currencysell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.at.currencysell.holder.AllCurrencyList;
import com.at.currencysell.model.Currency_Names;
import com.at.currencysell.utils.AlertMessage;
import com.at.currencysell.utils.AllCountryName;
import com.at.currencysell.utils.NetInfo;
import com.at.currencysell.utils.PersistentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

public class MyRequestWantActivity extends AppCompatActivity {


    private ListView listView;
    private DolorListAdapter mDolorListAdapter;
    private Context mContext;

    private EditText edt_search;
    private TextView tv_from_currency;
    private TextView tv_to_currency;
    private RelativeLayout rl_convert;

    private LinearLayout ll_back;
    public static String url_Result = null;
    private RelativeLayout rl_next_request;
    public JSONObject jsonObj_result = null;
    private String s_names = null;
    private String first_country_short;
    private String second_country_short;
    private String final_Result = null;
    private String temp = null;
    private String from_amount = "1";
    private String refinedNumber;
    private JSONObject jsonObj_names = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request_have);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        mContext = this;


        initUI();
    }


    private void initUI() {
        edt_search = (EditText) this.findViewById(R.id.edt_search);
        rl_convert = (RelativeLayout) this.findViewById(R.id.rl_convert);
        tv_from_currency = (TextView) this.findViewById(R.id.tv_from_currency);
        tv_to_currency = (TextView) this.findViewById(R.id.tv_to_currency);
        listView = (ListView) findViewById(R.id.list_my_request_have);
        rl_next_request = (RelativeLayout) this.findViewById(R.id.rl_next_request);
        rl_next_request.setOnClickListener(listener);



        // for scarch list
        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mDolorListAdapter.getFilter().filter(s.toString());


            }
        });

        ll_back = (LinearLayout) this.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequestWantActivity.this.finish();
            }
        });


        add_country_names();


    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.rl_next_request:

                    String get_cc_link = getResources().getString(R.string.Free_CC_link);
                    url_Result = get_cc_link + first_country_short + "_" + second_country_short;
                    doWebRequestforMarketRate();
                    break;


            }

        }
    };


    public void add_country_names() {

        String currency_names = AllCountryName.ALLCOUNTRYNAME;

        try {
            jsonObj_names = new JSONObject(currency_names);
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


        mDolorListAdapter = new DolorListAdapter(this, R.layout.my_request_have_list_item, AllCurrencyList.getmAllCurrencyList());
        listView.setAdapter(mDolorListAdapter);
        mDolorListAdapter.notifyDataSetChanged();


    }

    public void doWebRequestforMarketRate() {


        if (!NetInfo.isOnline(mContext)) {
            AlertMessage.showMessage(mContext, "Status", "Please check internet Connection");
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_Result, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.w("response", "are" + response);
                try {

                    jsonObj_result = new JSONObject(response);

                    final_Result = jsonObj_result.getJSONObject("results").toString();

                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                }

                add_country_Result();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("response", "are" + error);

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


    class DolorListAdapter extends ArrayAdapter<Currency_Names> {
        public Activity activity;
        private Vector<Currency_Names> originalList;
        private Vector<Currency_Names> chatList;
        private CityFilter filter;
        int resId;


        public DolorListAdapter(Activity a, int textViewResourceId, Vector<Currency_Names> cityLists) {
            super(a, textViewResourceId, cityLists);
            this.chatList = new Vector<Currency_Names>();
            this.originalList = new Vector<Currency_Names>();
            this.chatList.addAll(cityLists);
            this.originalList.addAll(cityLists);
            this.activity = a;

        }

        @Override
        public Filter getFilter() {
            if (filter == null) {
                filter = new CityFilter();
            }
            return filter;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            final Currency_Names listModel = AllCurrencyList.getDolorList(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.my_request_have_list_item, null);
                holder = new ViewHolder();

                holder.currency_name = (TextView) convertView.findViewById(R.id.currency_name);
                holder.full_name = (TextView) convertView.findViewById(R.id.full_name);
                holder.imageView = (ImageView) convertView.findViewById(R.id.currency_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.currency_name.setText(listModel.getShort_name());
            holder.full_name.setText(listModel.getShort_name());

            resId = activity.getResources().getIdentifier(listModel.getShort_name().toLowerCase(), "drawable", activity.getPackageName());


            if (listModel.getShort_name().contains("TRY")) {
                resId = activity.getResources().getIdentifier("tnd", "drawable", activity.getPackageName());
                holder.imageView.setImageResource(resId);
            } else if (resId == 0) {
                resId = activity.getResources().getIdentifier("xdr", "drawable", activity.getPackageName());

                holder.imageView.setImageResource(resId);
            }
            holder.imageView.setImageResource(resId);


            holder.currency_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    rl_convert.setVisibility(View.VISIBLE);
                    tv_from_currency.setText(listModel.getShort_name());
                    tv_to_currency.setText(listModel.getShort_name());
                    first_country_short = tv_from_currency.getText().toString();


                }
            });

            holder.full_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rl_convert.setVisibility(View.VISIBLE);
                    tv_to_currency.setText(listModel.getShort_name());
                    second_country_short = tv_to_currency.getText().toString();

                }
            });


            return convertView;

        }

        class ViewHolder {
            TextView currency_name;
            TextView full_name;
            ImageView imageView;

        }

        private class CityFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if (constraint != null && constraint.toString().length() > 0) {
                    Vector<Currency_Names> filteredItems = new Vector<Currency_Names>();

                    for (int i = 0, l = originalList.size(); i < l; i++) {
                        Currency_Names country = originalList.get(i);
                        if (country.getShort_name().toString().toLowerCase().contains(constraint)) {
                            filteredItems.add(country);
                        }
                    }
                    result.count = filteredItems.size();
                    result.values = filteredItems;
                } else {
                    synchronized (this) {
                        result.values = originalList;
                        result.count = originalList.size();
                    }
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                chatList = (Vector<Currency_Names>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = chatList.size(); i < l; i++)

                    add(chatList.get(i));
                notifyDataSetInvalidated();
            }
        }


    }


    public void add_country_Result() {

        final_Result = final_Result.replace("{", "");
        final_Result = final_Result.replace("}", "");
        final_Result = final_Result.replace("\"", "");

        StringTokenizer stok = new StringTokenizer(final_Result, ",");

        while (stok.hasMoreElements()) {
            temp = stok.nextElement().toString();

            if (temp.indexOf("val") != -1) {
                String split[] = temp.split(":");


                NumberFormat f = NumberFormat.getInstance(Locale.US);


                double temp_Amount2 = Double.parseDouble(split[2]);
                DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));

                double temp_Amount1 = Double.parseDouble(from_amount);
                DecimalFormat df1 = new DecimalFormat("#.#########", new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));

                double result = temp_Amount1 * temp_Amount2;
                DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));


                f.setGroupingUsed(false);
                refinedNumber = f.format(result);

                Intent intent = new Intent(mContext, CreateRequestActivity.class);
                intent.putExtra("RATE", refinedNumber);
                intent.putExtra("CURRNAMEWANT", first_country_short);
                intent.putExtra("CURRNAMEHAVE", second_country_short);
                startActivity(intent);

            }
            temp = stok.nextElement().toString();


            if (temp.indexOf("val") != -1) {
                String split[] = temp.split(":");


                NumberFormat f = NumberFormat.getInstance(Locale.US);

                double temp_Amount2 = Double.parseDouble(split[1]);
                DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));

                double temp_Amount1 = Double.parseDouble(from_amount);
                DecimalFormat df1 = new DecimalFormat("#.#########", new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));

                double result = temp_Amount1 * temp_Amount2;
                DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));


                f.setGroupingUsed(false);
                refinedNumber = f.format(result);


                Intent intent = new Intent(mContext, CreateRequestActivity.class);
                intent.putExtra("RATE", refinedNumber);
                intent.putExtra("CURRNAMEWANT", first_country_short);
                intent.putExtra("CURRNAMEHAVE", second_country_short);
                startActivity(intent);


            }

            temp = stok.nextElement().toString();


            if (temp.indexOf("val") != -1) {

                String split[] = temp.split(":");


                NumberFormat f = NumberFormat.getInstance(Locale.US);

                double temp_Amount2 = Double.parseDouble(split[1]);
                DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));

                double temp_Amount1 = Double.parseDouble(from_amount);
                DecimalFormat df1 = new DecimalFormat("#.#########", new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));

                double result = temp_Amount1 * temp_Amount2;
                DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));


                f.setGroupingUsed(false);
                refinedNumber = f.format(result);

                Intent intent = new Intent(mContext, CreateRequestActivity.class);
                intent.putExtra("RATE", refinedNumber);
                intent.putExtra("CURRNAMEWANT", first_country_short);
                intent.putExtra("CURRNAMEHAVE", second_country_short);
                startActivity(intent);

            }

            temp = stok.nextElement().toString();


            if (temp.indexOf("val") != -1) {
                String split[] = temp.split(":");

                NumberFormat f = NumberFormat.getInstance(Locale.US);

                double temp_Amount2 = Double.parseDouble(split[1]);

                DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));

                double temp_Amount1 = Double.parseDouble(from_amount);
                DecimalFormat df1 = new DecimalFormat("#.#########", new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));

                double result = temp_Amount1 * temp_Amount2;
                DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));

                f.setGroupingUsed(false);
                refinedNumber = f.format(result);


                Intent intent = new Intent(mContext, CreateRequestActivity.class);
                intent.putExtra("RATE", refinedNumber);
                intent.putExtra("CURRNAMEWANT", first_country_short);
                intent.putExtra("CURRNAMEHAVE", second_country_short);
                startActivity(intent);


            }

        }


    }


}
