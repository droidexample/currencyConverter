package com.at.currencysell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.at.currencysell.adapter.HomeListAdapter;
import com.at.currencysell.adapter.ProfileAdapter;
import com.at.currencysell.model.HomeListModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity {

    private List<HomeListModel> nameList = new ArrayList<HomeListModel>();
    private ListView listView;
    private ProfileAdapter adapter;
    String[] names = {"1 Good - Micheal","2 Good - John","3 Good - David","1 Good - Rosin","2 Good - Micheal"};

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

        initUI();
    }
    private void initUI(){
        for (int i = 0;i<names.length;i++){
            HomeListModel nameItem = new HomeListModel();
            nameItem.setName(names[i]);

            nameList.add(nameItem);

        }

        listView = (ListView) findViewById(R.id.list_home);
        adapter=new ProfileAdapter(this,nameList);
        listView.setAdapter(adapter);

    }

}
