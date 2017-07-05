package com.at.currencysell;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.at.currencysell.model.MyReqestHaveMoel;

import java.util.ArrayList;
import java.util.List;

public class MyRequestWantActivity extends AppCompatActivity {


    private List<MyReqestHaveMoel> currencyList = new ArrayList<MyReqestHaveMoel>();
    private ListView listView;
    private MyRequestHaveListAdaptertest adapter;
    private Context mContext;
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


        dummyData();

        initUI();
    }


    private void initUI() {

        rl_convert = (RelativeLayout) this.findViewById(R.id.rl_convert);
        tv_from_currency = (TextView) this.findViewById(R.id.tv_from_currency);
        tv_to_currency = (TextView) this.findViewById(R.id.tv_to_currency);
        listView = (ListView) findViewById(R.id.list_my_request_have);
        adapter = new MyRequestHaveListAdaptertest(this, currencyList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PersistentUser.NotClicked(mContext);
//                Intent intent = new Intent(mContext, CreateRequestActivity.class);
//                startActivity(intent);
            }
        });


    }


    class MyRequestHaveListAdaptertest extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater;
        public List<MyReqestHaveMoel> listItems;


        public MyRequestHaveListAdaptertest(Activity activity, List<MyReqestHaveMoel> listItems) {
            this.activity = activity;
            this.listItems = listItems;
        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int location) {
            return listItems.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (inflater == null)
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.my_request_have_list_item, null);

            final MyReqestHaveMoel listModel = listItems.get(position);
            TextView currency_name = (TextView) convertView.findViewById(R.id.currency_name);
            TextView full_name = (TextView) convertView.findViewById(R.id.full_name);
            ImageView image = (ImageView) convertView.findViewById(R.id.currency_image);


            currency_name.setText(listModel.getCurrency_name());
            full_name.setText(listModel.getCurrency_full_name());
            image.setImageResource(listModel.getImage());
            currency_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    rl_convert.setVisibility(View.VISIBLE);
                    tv_from_currency.setText(listModel.getCurrency_name());
                    tv_to_currency.setText(listModel.getCurrency_name());


                }
            });

            full_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rl_convert.setVisibility(View.VISIBLE);
                    tv_to_currency.setText(listModel.getCurrency_full_name());

                }
            });


            return convertView;
        }
    }


    private void dummyData() {
        String[] currency_short_form = {"usd", "euro", "jpy", "pound", "cad", "mxn", "hkd"};
        String[] currency_full_form = {"Us Dollar", "Euro", "Japanese Yen", "UK", "Canadian Dollar", "Mexican Peso", "Hong Kong"};
        int[] images = {R.drawable.ic_usa, R.drawable.ic_eur_img, R.drawable.ic_japan, R.drawable.ic_uk, R.drawable.ic_franch, R.drawable.ic_usa, R.drawable.ic_uk};

        for (int i = 0; i < currency_short_form.length; i++) {
            MyReqestHaveMoel nameItem = new MyReqestHaveMoel();
            nameItem.setCurrency_name(currency_short_form[i]);
            nameItem.setCurrency_full_name(currency_full_form[i]);
            nameItem.setImage(images[i]);

            currencyList.add(nameItem);

        }
    }


}
