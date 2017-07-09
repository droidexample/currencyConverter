package com.at.currencysell.tabpager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.at.currencysell.fragment.FragmentPeopleHave;
import com.at.currencysell.fragment.FragmentPeopleNeed;
import com.at.currencysell.fragment.FragmentProfileReview;


/**
 * Created by Prosanto on 2/4/17.
 */


public class TabsPagerAdapterProfile extends FragmentPagerAdapter {
    public TabsPagerAdapterProfile(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new FragmentProfileReview();
            case 1:
                return new FragmentPeopleNeed();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}