package com.at.currencysell.tabpager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.at.currencysell.fragment.FragmentMyRequestActive;
import com.at.currencysell.fragment.FragmentMyRequestFinish;
import com.at.currencysell.fragment.FragmentPeopleHave;
import com.at.currencysell.fragment.FragmentPeopleNeed;


/**
 * Created by Prosanto on 2/4/17.
 */


public class TabsPagerAdapterMyRequests extends FragmentPagerAdapter {
    public TabsPagerAdapterMyRequests(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new FragmentMyRequestActive();
            case 1:
                return new FragmentMyRequestFinish();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}