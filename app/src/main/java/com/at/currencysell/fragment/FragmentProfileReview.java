package com.at.currencysell.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.at.currencysell.R;
import com.at.currencysell.adapter.ProfileAdapter;
import com.at.currencysell.holder.HomeList;
import com.at.currencysell.model.HomeListModel;

/**
 * Created by admin on 05/07/2017.
 */

public class FragmentProfileReview extends Fragment {


    private View view;
    private Context mContext;
    private ProfileAdapter adapter;
    private ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fagment_people_have, container, false);
        mContext = getActivity().getApplicationContext();
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        listview = (ListView) view.findViewById(R.id.list);
        dummyData();

    }

    private void dummyData() {
        String[] names = {"1 Good - Micheal","2 Good - John","3 Good - David","1 Good - Rosin","2 Good - Micheal"};


        for (int i = 0; i < names.length; i++) {
            HomeListModel nameItem = new HomeListModel();
            nameItem.setName(names[i]);


            HomeList.setPeopleHaveinfo(nameItem);

        }

        adapter = new ProfileAdapter(mContext, HomeList.getAllPeopleHave());
        listview.setAdapter(adapter);
    }


}
