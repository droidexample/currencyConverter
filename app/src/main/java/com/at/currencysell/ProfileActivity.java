package com.at.currencysell;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.tabpager.TabsPagerAdapterProfile;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity {

    private List<HomeListModel> nameList = new ArrayList<HomeListModel>();
    private ListView listView;


    private LinearLayout ll_review;
    private LinearLayout ll_rates;
    private ViewPager mViewPager;
    private TabsPagerAdapterProfile mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile,frameLayout);
        mContext = this;
        selecteddeselectedTab(4);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        slide_me.setSlidingEnabled(false);
        initUI();
    }
    private void initUI(){

        ll_review = (LinearLayout) this.findViewById(R.id.ll_review);
        ll_review.setOnClickListener(listener);
        ll_rates = (LinearLayout) this.findViewById(R.id.ll_rates);
        ll_rates.setOnClickListener(listener);
        ll_review.setSelected(true);

        listView = (ListView) findViewById(R.id.list_home);

        //=============View page for sliding============
        mViewPager = (ViewPager) findViewById(R.id.pager_home);
        mAdapter = new TabsPagerAdapterProfile(getSupportFragmentManager());
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
                case R.id.ll_review:
                    selecteddeselectedbutton(0);
                    break;
                case R.id.ll_rates:
                    selecteddeselectedbutton(1);
                    break;

            }

        }
    };

    public void selecteddeselectedbutton(int type) {

        mViewPager.setCurrentItem(type);

        switch (type) {
            case 0:
                ll_review.setSelected(true);
                ll_rates.setSelected(false);

                break;

            case 1:
                ll_review.setSelected(false);
                ll_rates.setSelected(true);

                break;
        }

    }



}
