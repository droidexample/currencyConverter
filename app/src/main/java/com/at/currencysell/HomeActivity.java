package com.at.currencysell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.at.currencysell.tabpager.TabsPagerAdapter;

public class HomeActivity extends BaseActivity {

    private LinearLayout ll_people_need;
    private LinearLayout ll_people_have;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;
    private RelativeLayout rl_currency;
    private TextView tv_currency_name;
    private ImageView img_flag;
    private int resId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        mContext = this;
        selecteddeselectedTab(0);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        slide_me.setSlidingEnabled(false);
        initUI();
    }


    private void initUI() {
        tv_currency_name = (TextView) this.findViewById(R.id.tv_currency_name);
        rl_currency = (RelativeLayout) this.findViewById(R.id.rl_currency);
        rl_currency.setOnClickListener(listener);
        ll_people_need = (LinearLayout) this.findViewById(R.id.ll_people_need);
        ll_people_need.setOnClickListener(listener);
        ll_people_have = (LinearLayout) this.findViewById(R.id.ll_people_have);
        ll_people_have.setOnClickListener(listener);

        img_flag=(ImageView)this.findViewById(R.id.img_flag);
        ll_people_need.setSelected(true);

        //=============View page for sliding============
        mViewPager = (ViewPager) findViewById(R.id.pager_home);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                selecteddeselectedbutton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.ll_people_need:
                    selecteddeselectedbutton(0);
                    break;
                case R.id.ll_people_have:

                    selecteddeselectedbutton(1);

                    break;
                case R.id.rl_currency:
                    Intent intent = new Intent(mContext,CurrencyNameActivity.class);
                   startActivityForResult(intent,1);

                    break;

            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            String currency=data.getStringExtra("MESSAGE");
            Toast.makeText(mContext,currency,Toast.LENGTH_LONG).show();
            tv_currency_name.setText(currency);
            resId = mContext.getResources().getIdentifier(currency.toLowerCase(), "drawable",mContext.getPackageName());
            img_flag.setImageResource(resId);

        }
    }

    public void selecteddeselectedbutton(int type) {

        mViewPager.setCurrentItem(type);

        switch (type) {
            case 0:
                ll_people_need.setSelected(true);
                ll_people_have.setSelected(false);

                break;

            case 1:
                ll_people_need.setSelected(false);
                ll_people_have.setSelected(true);

                break;
        }

    }


}
