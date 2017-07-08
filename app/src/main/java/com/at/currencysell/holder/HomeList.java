package com.at.currencysell.holder;

import com.at.currencysell.model.HomeListModel;

import java.util.Vector;

/**
 * Created by Shokiul Islam on 5/9/2016.
 */
public class HomeList {

    public static Vector<HomeListModel> allBloodDonorList = new Vector<HomeListModel>();

    public static Vector<HomeListModel> getAllEventDetailsInfos() {
        return allBloodDonorList;
    }
    public static void setAllEventinfo(Vector<HomeListModel> allEventinfo) {
        HomeList.allBloodDonorList = allEventinfo;
    }
    public static HomeListModel getEventEventDetailsInfos(int pos) {
        return allBloodDonorList.elementAt(pos);
    }
    public static void setEventinfo(HomeListModel _alldonorList) {
        HomeList.allBloodDonorList.addElement(_alldonorList);
    }
    public static void removeAllBloodDonorList() {
        HomeList.allBloodDonorList.removeAllElements();
    }
}
