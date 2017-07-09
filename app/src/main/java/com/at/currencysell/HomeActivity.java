package com.at.currencysell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.at.currencysell.tabpager.TabsPagerAdapter;

public class HomeActivity extends BaseActivity {

    private LinearLayout ll_people_need;
    private LinearLayout ll_people_have;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;
    private ImageView image_currency;

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

        image_currency = (ImageView) this.findViewById(R.id.image_currency);
        image_currency.setOnClickListener(listener);
        ll_people_need = (LinearLayout) this.findViewById(R.id.ll_people_need);
        ll_people_need.setOnClickListener(listener);
        ll_people_have = (LinearLayout) this.findViewById(R.id.ll_people_have);
        ll_people_have.setOnClickListener(listener);

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
                case R.id.image_currency:
                    Intent intent = new Intent(mContext,CurrencyNameActivity.class);
                    startActivity(intent);

                    break;

            }

        }
    };

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
