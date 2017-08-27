package com.at.currencysell;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.at.currencysell.adapter.ScanListAdapter;
import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.tabpager.TabsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private List<HomeListModel> nameList = new ArrayList<HomeListModel>();
    private ListView listView;
    private ScanListAdapter adapter;

    private LinearLayout ll_confirmed;
    private LinearLayout ll_scan;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        initUI();
    }


    private void initUI() {

        ll_confirmed = (LinearLayout) this.findViewById(R.id.ll_confirmed);
        ll_confirmed.setOnClickListener(listener);
        ll_scan = (LinearLayout) this.findViewById(R.id.ll_scan);
        ll_scan.setOnClickListener(listener);
        ll_confirmed.setSelected(true);

        listView = (ListView) findViewById(R.id.list_home);
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
                case R.id.ll_confirmed:
                    selecteddeselectedbutton(0);
                    break;
                case R.id.ll_scan:

                    selecteddeselectedbutton(1);

                    break;

            }

        }
    };

    public void selecteddeselectedbutton(int type) {

        mViewPager.setCurrentItem(type);

        switch (type) {
            case 0:
                ll_confirmed.setSelected(true);
                ll_scan.setSelected(false);

                break;

            case 1:
                ll_confirmed.setSelected(false);
                ll_scan.setSelected(true);

                break;
        }

    }

}
