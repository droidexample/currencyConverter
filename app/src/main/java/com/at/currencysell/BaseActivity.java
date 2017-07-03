package com.at.currencysell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.at.currencysell.slidermenu.SlidingMenu;


public class BaseActivity extends AppCompatActivity {

    Context mContext;
    LinearLayout ll_silding;


    private SlidingMenu slide_me;
    private LinearLayout top_menu_btn;
    FrameLayout frameLayout;

    private LinearLayout ll_home;
    private RelativeLayout rl_suggestion_to_do;
    private RelativeLayout rl_give_suggestion;
    private RelativeLayout rl_suggestion_volunteer_work;
    private RelativeLayout rl_suggestion_improve_app;
    private RelativeLayout rl_gallery;
    private RelativeLayout rl_about;
    private RelativeLayout rl_supporter;
    private RelativeLayout rl_share_app;
    private RelativeLayout rl_sharing_setting;
    private RelativeLayout rl_account_setting;
    private RelativeLayout rl_Logout;


    private LinearLayout ll_tab_home;
    private LinearLayout ll_tab_my_requests;
    private RelativeLayout rl_tab_add;
    private LinearLayout ll_tab_chat;
    private LinearLayout ll_tab_profile;
    private RelativeLayout li_settings;
    private TextView text_title;


    private ImageView img_scan;

   // private AQuery mAQuery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = this;
       // mAQuery = new AQuery(mContext);

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


        // for naviagtion drower
//        ll_home = (LinearLayout) findViewById(R.id.ll_home);
//        ll_home.setOnClickListener(listener);
//        rl_suggestion_to_do = (RelativeLayout) findViewById(R.id.rl_suggestion_to_do);
//        rl_suggestion_to_do.setOnClickListener(listener);
//        rl_give_suggestion = (RelativeLayout) findViewById(R.id.rl_give_suggestion);
//        rl_give_suggestion.setOnClickListener(listener);
//        rl_suggestion_volunteer_work = (RelativeLayout) findViewById(R.id.rl_suggestion_volunteer_work);
//        rl_suggestion_volunteer_work.setOnClickListener(listener);
//
//        rl_suggestion_improve_app = (RelativeLayout) findViewById(R.id.rl_suggestion_improve_app);
//        rl_gallery = (RelativeLayout) findViewById(R.id.rl_gallery);
//        rl_gallery.setOnClickListener(listener);
//        rl_about = (RelativeLayout) findViewById(R.id.rl_about);
//        rl_about.setOnClickListener(listener);
//        rl_supporter = (RelativeLayout) findViewById(R.id.rl_supporter);
//        rl_supporter.setOnClickListener(listener);
//        rl_share_app = (RelativeLayout) findViewById(R.id.rl_share_app);
//        rl_share_app.setOnClickListener(listener);
//        rl_account_setting = (RelativeLayout) findViewById(R.id.rl_account_setting);
//        rl_account_setting.setOnClickListener(listener);
//        rl_sharing_setting = (RelativeLayout) findViewById(R.id.rl_sharing_setting);
//        rl_Logout = (RelativeLayout) findViewById(R.id.rl_Logout);
//        rl_Logout.setOnClickListener(listener);
//        img_give_suggestion = (ImageView) findViewById(R.id.img_give_suggestion);
//        img_give_suggestion.setOnClickListener(listener);
//        li_settings = (RelativeLayout) findViewById(R.id.li_settings);
//        li_settings.setOnClickListener(listener);
//        img_settings = (ImageView) this.findViewById(R.id.img_settings);
//        tv_name = (TextView) this.findViewById(R.id.tv_name);
//        view_suggestion_volunteer_work = (View) this.findViewById(R.id.view_suggestion_volunteer_work);
//        view_suggestion_improve_app = (View) this.findViewById(R.id.view_suggestion_improve_app);
//        view_sharing_setting = (View) this.findViewById(R.id.view_sharing_setting);
//        view_account_setting = (View) this.findViewById(R.id.view_account_setting);
//
//

        img_scan = (ImageView)findViewById(R.id.img_scan);
        img_scan.setOnClickListener(listener);

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
//        ImageView user_profile_photo = (ImageView) this.findViewById(R.id.user_profile_photo);
//
//
//        // Set Persintition Data
//        tv_name.setText(PersistentUser.getUserName(mContext));
//        mAQuery.id(user_profile_photo).image(PersistentUser.getUSERPIC(mContext), true, true);



    }


    //  listener of Side Menu
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_silding:
                    slide_me.toggle();
                    break;

                case R.id.ll_tab_home:
                    selecteddeselectedTab(0);
                    Intent mIntent = new Intent(mContext, HomeActivity.class);
                    startActivity(mIntent);
                    break;

                case R.id.ll_tab_my_requests:
                    selecteddeselectedTab(1);
                    mIntent = new Intent(mContext, MyRequestsActivity.class);
                    startActivity(mIntent);
                    break;
                case R.id.rl_tab_add:
                    selecteddeselectedTab(2);
                    mIntent = new Intent(mContext, MyRequestHaveActivity.class);
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

        }else if (type == 3) {

            text_title.setText("CHATS");
            ll_tab_chat.setSelected(true);
            ll_tab_my_requests.setSelected(false);
            ll_tab_home.setSelected(false);
            ll_tab_profile.setSelected(false);

        }else if (type == 4) {
            text_title.setText(" ");
            ll_tab_profile.setSelected(true);
            ll_tab_chat.setSelected(false);
            ll_tab_my_requests.setSelected(false);
            ll_tab_home.setSelected(false);


        }
    }


}
