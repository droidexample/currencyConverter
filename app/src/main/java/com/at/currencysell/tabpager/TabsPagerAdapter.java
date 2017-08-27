package com.at.currencysell.tabpager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.at.currencysell.fragment.FragmentPeopleHave;
import com.at.currencysell.fragment.FragmentPeopleNeed;


/**
 * Created by Prosanto on 2/4/17.
 */


public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new FragmentPeopleNeed();

            case 1:
                return new FragmentPeopleHave();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}