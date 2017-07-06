package com.at.currencysell.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.at.currencysell.R;

/**
 * Created by admin on 05/07/2017.
 */

public class FragmentPeopleHave extends Fragment {


    private View view;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fagment_people_have, container, false);
        mContext = getActivity().getApplicationContext();
        initUI();
        return view;
    }

    private void initUI() {



    }


}
