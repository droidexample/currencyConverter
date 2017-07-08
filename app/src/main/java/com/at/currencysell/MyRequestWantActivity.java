package com.at.currencysell;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.at.currencysell.holder.AllDolorList;
import com.at.currencysell.model.MyReqestActiveMoel;

import java.util.Vector;

public class MyRequestWantActivity extends AppCompatActivity {


    private ListView listView;
    private DolorListAdapter mDolorListAdapter;
    private Context mContext;

    private EditText edt_search;
    private TextView tv_from_currency;
    private TextView tv_to_currency;
    private RelativeLayout rl_convert;


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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


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


        dummyData();

    }


    private void dummyData() {
        String[] currency_short_form = {"usd", "euro", "jpy", "pound", "cad", "mxn", "hkd"};
        String[] currency_full_form = {"Us Dollar", "Euro", "Japanese Yen", "UK", "Canadian Dollar", "Mexican Peso", "Hong Kong"};
        int[] images = {R.drawable.ic_usa, R.drawable.ic_eur_img, R.drawable.ic_japan, R.drawable.ic_uk, R.drawable.ic_franch, R.drawable.ic_usa, R.drawable.ic_uk};

        for (int i = 0; i < currency_short_form.length; i++) {
            MyReqestActiveMoel nameItem = new MyReqestActiveMoel();
            nameItem.setCurrency_name(currency_short_form[i]);
            nameItem.setCurrency_full_name(currency_full_form[i]);
            nameItem.setImage(images[i]);

            AllDolorList.setmDolorList(nameItem);

        }

        mDolorListAdapter = new DolorListAdapter(this, R.layout.my_request_have_list_item, AllDolorList.getmAllDolorList());
        listView.setAdapter(mDolorListAdapter);
    }


    class DolorListAdapter extends ArrayAdapter<MyReqestActiveMoel> {
        Context mContext;
        private Vector<MyReqestActiveMoel> originalList;
        private Vector<MyReqestActiveMoel> chatList;
        private CityFilter filter;


        public DolorListAdapter(Context context, int textViewResourceId, Vector<MyReqestActiveMoel> cityLists) {
            super(context, textViewResourceId, cityLists);
            this.chatList = new Vector<MyReqestActiveMoel>();
            this.originalList = new Vector<MyReqestActiveMoel>();
            this.chatList.addAll(cityLists);
            this.originalList.addAll(cityLists);
            this.mContext = context;

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
            final MyReqestActiveMoel listModel = AllDolorList.getDolorList(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.my_request_have_list_item, null);
                holder = new ViewHolder();

                holder.currency_name = (TextView) convertView.findViewById(R.id.currency_name);
                holder.full_name = (TextView) convertView.findViewById(R.id.full_name);
                holder.image = (ImageView) convertView.findViewById(R.id.currency_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.currency_name.setText(listModel.getCurrency_name());
            holder.full_name.setText(listModel.getCurrency_full_name());


            holder.currency_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    rl_convert.setVisibility(View.VISIBLE);
                    tv_from_currency.setText(listModel.getCurrency_name());
                    tv_to_currency.setText(listModel.getCurrency_name());


                }
            });

            holder.full_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rl_convert.setVisibility(View.VISIBLE);
                    tv_to_currency.setText(listModel.getCurrency_full_name());

                }
            });


            return convertView;

        }

        class ViewHolder {
            TextView currency_name;
            TextView full_name;
            ImageView image;

        }

        private class CityFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if (constraint != null && constraint.toString().length() > 0) {
                    Vector<MyReqestActiveMoel> filteredItems = new Vector<MyReqestActiveMoel>();

                    for (int i = 0, l = originalList.size(); i < l; i++) {
                        MyReqestActiveMoel country = originalList.get(i);
                        if (country.getCurrency_name().toString().toLowerCase().contains(constraint)) {
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
                chatList = (Vector<MyReqestActiveMoel>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = chatList.size(); i < l; i++)

                    add(chatList.get(i));
                notifyDataSetInvalidated();
            }
        }

    }


}
