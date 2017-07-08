package com.at.currencysell.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.at.currencysell.R;
import com.at.currencysell.adapter.HomeAdapter;
import com.at.currencysell.holder.HomeList;
import com.at.currencysell.model.HomeListModel;

/**
 * Created by admin on 05/07/2017.
 */

public class FragmentPeopleNeed extends Fragment {
    private View view;
    private Context mContext;
    private HomeAdapter adapter;
    private ListView listview;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fagment_people_need, container, false);
        mContext = getActivity().getApplicationContext();
        initUI(view);
        return view;

    }

    private void initUI(View view) {
        listview = (ListView) view.findViewById(R.id.list_home);
        dummyData();

    }

    private void dummyData() {
        String[] user_name = {"MICHEAL SMITH", "JOHN SMITH", "PITER SMITH", "ROSIN SMITH", "MICHEAL SMITH"};


        for (int i = 0; i < user_name.length; i++) {
            HomeListModel nameItem = new HomeListModel();
            nameItem.setName(user_name[i]);


            HomeList.setEventinfo(nameItem);

        }

        adapter = new HomeAdapter(mContext,  HomeList.getAllEventDetailsInfos());
        listview.setAdapter(adapter);
    }

}
