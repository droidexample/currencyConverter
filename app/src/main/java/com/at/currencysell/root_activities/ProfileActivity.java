package com.at.currencysell.root_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.at.currencysell.ProfileUpdateActivity;
import com.at.currencysell.R;
import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.root_activities.BaseActivity;
import com.at.currencysell.tabpager.TabsPagerAdapterProfile;
import com.at.currencysell.utils.PersistentUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity {

    private List<HomeListModel> nameList = new ArrayList<HomeListModel>();
    private ListView listView;


    private LinearLayout ll_review;
    private LinearLayout ll_rates;
    private ViewPager mViewPager;
    private TabsPagerAdapterProfile mAdapter;
    private TextView txt_user_name;
    private ImageView img_profile_photo;
    private LinearLayout ll_edit_profile;
    private AQuery mAQuery;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_profile,frameLayout);
        mContext = this;
        mAQuery = new AQuery(mContext);
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
        ll_edit_profile = (LinearLayout) this.findViewById(R.id.ll_edit_profile);
        ll_edit_profile.setOnClickListener(listener);
        img_profile_photo = (ImageView)this.findViewById(R.id.img_profile_photo);
        txt_user_name = (TextView) this.findViewById(R.id.txt_user_name);


        txt_user_name.setText(PersistentUser.getUserName(mContext));

        mAQuery.id(img_profile_photo).image(PersistentUser.getUSERPIC(mContext), true, true);

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
                case R.id.ll_edit_profile:
                    Intent intent = new Intent(mContext, ProfileUpdateActivity.class);
                    startActivity(intent);
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
