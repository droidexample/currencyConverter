package com.at.currencysell.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.at.currencysell.R;
import com.at.currencysell.adapter.MyRequestsAdapter;
import com.at.currencysell.holder.MyRequestsList;
import com.at.currencysell.model.MyReqestActiveMoel;

/**
 * Created by admin on 05/07/2017.
 */

public class FragmentMyRequestFinish extends Fragment {


    private View view;
    private Context mContext;
    private MyRequestsAdapter adapter;
    private ListView listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fagment_my_request_active, container, false);
        mContext = getActivity().getApplicationContext();
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        listview = (ListView) view.findViewById(R.id.list_home);
        dummyData();

    }

    private void dummyData() {
        String[] names = {"USD","USD","USD","USD","USD","USD","USD"};


        for (int i = 0; i < names.length; i++) {
            MyReqestActiveMoel nameItem = new MyReqestActiveMoel();
            nameItem.setCurrency_name(names[i]);


            MyRequestsList.setEventinfo(nameItem);

        }

        adapter = new MyRequestsAdapter(mContext,  MyRequestsList.getAllEventDetailsInfos());
        listview.setAdapter(adapter);
    }


}
