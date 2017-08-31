package com.at.currencysell.root_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.at.currencysell.MyRequestWantActivity;
import com.at.currencysell.R;
import com.at.currencysell.ScanActivity;
import com.at.currencysell.SignupFirstActivity;
import com.at.currencysell.core.logout.LogoutContract;
import com.at.currencysell.core.logout.LogoutPresenter;
import com.at.currencysell.slidermenu.SlidingMenu;
import com.at.currencysell.utils.PersistentUser;


public class BaseActivity extends AppCompatActivity implements LogoutContract.View{

    Context mContext;
    LinearLayout ll_silding;


    SlidingMenu slide_me;
    LinearLayout top_menu_btn;
    FrameLayout frameLayout;

    private LinearLayout ll_home;
    private RelativeLayout rl_suggestion_to_do;
    private RelativeLayout rl_give_suggestion;
    private RelativeLayout rl_suggestion_volunteer_work;
    private TextView tv_sign_out;


    private LinearLayout ll_tab_home;
    private LinearLayout ll_tab_my_requests;
    private RelativeLayout rl_tab_add;
    private LinearLayout ll_tab_chat;
    private LinearLayout ll_tab_profile;
    private TextView text_title;
    private TextView tv_name;
    ImageView user_profile_photo;


    private ImageView img_scan;
    private ImageView img_search;
    private RelativeLayout rl_search;
    private AQuery mAQuery;
    private LogoutPresenter mLogoutPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = this;

        mAQuery = new AQuery(mContext);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        initUIBaseActivity();

    }


    public void initUIBaseActivity() {


        // slide menu
        top_menu_btn = (LinearLayout) this.findViewById(R.id.ll_silding);
        top_menu_btn.setOnClickListener(listener);
        slide_me = new SlidingMenu(mContext);
        slide_me.setMode(SlidingMenu.LEFT);
        slide_me.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slide_me.setShadowDrawable(R.drawable.ic_menu);
        slide_me.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slide_me.setFadeDegree(0.8f);
        slide_me.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slide_me.setMenu(R.layout.home_left_menu);
        slide_me.setSlidingEnabled(true);
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        text_title = (TextView) findViewById(R.id.text_title);
        user_profile_photo = (ImageView)findViewById(R.id.user_profile_photo);


        // for naviagtion drower
        tv_name = (TextView) this.findViewById(R.id.tv_name);
        tv_sign_out = (TextView) findViewById(R.id.tv_sign_out);
        tv_sign_out.setOnClickListener(listener);
        mLogoutPresenter = new LogoutPresenter(this);

        img_scan = (ImageView) findViewById(R.id.img_scan);
        img_scan.setOnClickListener(listener);
        img_search = (ImageView) findViewById(R.id.img_search);
        img_search.setOnClickListener(listener);
        rl_search = (RelativeLayout) findViewById(R.id.rl_search);

        // for tab view
        ll_tab_home = (LinearLayout) findViewById(R.id.ll_tab_home);
        ll_tab_home.setOnClickListener(listener);
        ll_tab_my_requests = (LinearLayout) findViewById(R.id.ll_tab_my_requests);
        ll_tab_my_requests.setOnClickListener(listener);
        rl_tab_add = (RelativeLayout) findViewById(R.id.rl_tab_add);
        rl_tab_add.setOnClickListener(listener);
        ll_tab_chat = (LinearLayout) findViewById(R.id.ll_tab_chat);
        ll_tab_chat.setOnClickListener(listener);
        ll_tab_profile = (LinearLayout) findViewById(R.id.ll_tab_profile);
        ll_tab_profile.setOnClickListener(listener);


        // Set Persintition Data
        tv_name.setText(PersistentUser.getUserName(mContext));
        mAQuery.id(user_profile_photo).image(PersistentUser.getUSERPIC(mContext), true, true);

    }


    //  listener of Side Menu
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_silding:
                    slide_me.toggle();
                    break;

                case R.id.tv_sign_out:

                    PersistentUser.resetAllData(mContext);
                  //  PersistentUser.clearCurrentUser(mContext);
                    //LoginManager.getInstance().logOut();
                    mLogoutPresenter.logout();
                    Intent mIntent = new Intent(mContext, SignupFirstActivity.class);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mIntent);
                    finish();
                    break;

                case R.id.ll_tab_home:
                    selecteddeselectedTab(0);
                     mIntent = new Intent(mContext, HomeActivity.class);
                    startActivity(mIntent);
                    break;

                case R.id.ll_tab_my_requests:
                    selecteddeselectedTab(1);
                    mIntent = new Intent(mContext, MyRequestsActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.rl_tab_add:
                    selecteddeselectedTab(2);
                    mIntent = new Intent(mContext, MyRequestWantActivity.class);
                    startActivity(mIntent);
                    break;

                case R.id.ll_tab_chat:
                    selecteddeselectedTab(3);
                    mIntent = new Intent(mContext, ChatActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.ll_tab_profile:
                    selecteddeselectedTab(4);

                    mIntent = new Intent(mContext, ProfileActivity.class);
                    startActivity(mIntent);
                    break;

                case R.id.img_scan:

                    mIntent = new Intent(mContext, ScanActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.img_search:

                   if (rl_search.getVisibility()==View.GONE){
                       rl_search.setVisibility(View.VISIBLE);
                       img_scan.setVisibility(View.GONE);
                   }else {
                       rl_search.setVisibility(View.GONE);
                       img_scan.setVisibility(View.VISIBLE);
                   }
                    break;

                default:
                    break;


            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);


    }


    public void selecteddeselectedTab(int type) {
        if (type == 0) {

            text_title.setText("HOME");
            ll_tab_home.setSelected(true);
            ll_tab_my_requests.setSelected(false);
            ll_tab_chat.setSelected(false);
            ll_tab_profile.setSelected(false);

        } else if (type == 1) {

            text_title.setText("MY REQUESTS");
            ll_tab_my_requests.setSelected(true);
            ll_tab_home.setSelected(false);
            ll_tab_chat.setSelected(false);
            ll_tab_profile.setSelected(false);

        } else if (type == 2) {
            rl_tab_add.setSelected(true);
            ll_tab_my_requests.setSelected(false);
            ll_tab_home.setSelected(false);
            ll_tab_chat.setSelected(false);
            ll_tab_profile.setSelected(false);

        } else if (type == 3) {

            text_title.setText("CHATS");
            ll_tab_chat.setSelected(true);
            ll_tab_my_requests.setSelected(false);
            ll_tab_home.setSelected(false);
            ll_tab_profile.setSelected(false);

        } else if (type == 4) {
            text_title.setText(" ");
            ll_tab_profile.setSelected(true);
            ll_tab_chat.setSelected(false);
            ll_tab_my_requests.setSelected(false);
            ll_tab_home.setSelected(false);


        }
    }


    @Override
    public void onLogoutSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogoutFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
