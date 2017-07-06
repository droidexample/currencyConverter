package com.at.currencysell;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.at.currencysell.tabpager.TabsPagerAdapter;

public class HomeActivity extends BaseActivity {

    private LinearLayout ll_people_need;
    private LinearLayout ll_people_have;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;


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

        ll_people_need = (LinearLayout) this.findViewById(R.id.ll_people_need);
        ll_people_need.setOnClickListener(listener);
        ll_people_have = (LinearLayout) this.findViewById(R.id.ll_people_have);
        ll_people_have.setOnClickListener(listener);


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
                selecteddeselectedTab(position);
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
                    mViewPager.setCurrentItem(0);

                    break;
                case R.id.ll_people_have:
                    mViewPager.setCurrentItem(1);


                    break;

            }

        }
    };
}
