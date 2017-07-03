package com.at.currencysell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.at.currencysell.adapter.HomeListAdapter;
import com.at.currencysell.model.HomeListModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {
    private List<HomeListModel> nameList = new ArrayList<HomeListModel>();
    private ListView listView;
    private HomeListAdapter adapter;
    String[] names = {"MICHEAL SMITH","JOHN SMITH","PITER SMITH","ROSIN SMITH","MICHEAL SMITH"};
    private LinearLayout ll_people_need;
    private LinearLayout ll_people_have;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_home,frameLayout);
        mContext = this;
        selecteddeselectedTab(0);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

         initUI();
    }


    private void initUI(){

        ll_people_need = (LinearLayout)this.findViewById(R.id.ll_people_need);
        ll_people_need.setOnClickListener(listener);
        ll_people_have = (LinearLayout)this.findViewById(R.id.ll_people_have);
        ll_people_have.setOnClickListener(listener);

        for (int i = 0;i<names.length;i++){
            HomeListModel nameItem = new HomeListModel();
            nameItem.setName(names[i]);

            nameList.add(nameItem);

        }

        listView = (ListView) findViewById(R.id.list_home);
        adapter=new HomeListAdapter(this,nameList);
        listView.setAdapter(adapter);

    }


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.ll_people_need:
                    break;
                case R.id.ll_people_have:
                    break;

            }

        }
    };
}
