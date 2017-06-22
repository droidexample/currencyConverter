package com.at.currencysell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.at.currencysell.adapter.HomeListAdapter;
import com.at.currencysell.adapter.ScanListAdapter;
import com.at.currencysell.model.HomeListModel;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    private List<HomeListModel> nameList = new ArrayList<HomeListModel>();
    private ListView listView;
    private ScanListAdapter adapter;
    String[] names = {"MICHEAL SMITH","JOHN SMITH","PITER SMITH","ROSIN SMITH","MICHEAL SMITH"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        initUI();
    }


    private void initUI(){
        for (int i = 0;i<names.length;i++){
            HomeListModel nameItem = new HomeListModel();
            nameItem.setName(names[i]);

            nameList.add(nameItem);

        }

        listView = (ListView) findViewById(R.id.list_home);
        adapter=new ScanListAdapter(this,nameList);
        listView.setAdapter(adapter);

    }
}
