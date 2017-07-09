package com.at.currencysell;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.at.currencysell.adapter.ChatListAdapter;
import com.at.currencysell.holder.HomeList;
import com.at.currencysell.model.HomeListModel;

public class ChatActivity extends BaseActivity {
    private ListView listview;
    private ChatListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_chat,frameLayout);
        mContext = this;
        selecteddeselectedTab(3);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        initUI();
    }


    private void initUI(){
        listview = (ListView) findViewById(R.id.listview);
        dummyData();

    }

    private void dummyData() {
        String[] user_name = {"MICHEAL SMITH","JOHN SMITH","PITER SMITH","ROSIN SMITH","MICHEAL SMITH"};


        for (int i = 0; i < user_name.length; i++) {
            HomeListModel nameItem = new HomeListModel();
            nameItem.setName(user_name[i]);


            HomeList.setEventinfo(nameItem);

        }

        adapter = new ChatListAdapter(mContext,  HomeList.getAllEventDetailsInfos());
        listview.setAdapter(adapter);
    }
}
