package com.at.currencysell.holder;

import com.at.currencysell.model.HomeListModel;

import java.util.Vector;

/**
 * Created by Shokiul Islam on 5/9/2016.
 */
public class HomeList {

    public static Vector<HomeListModel> allPeopleHaveList = new Vector<HomeListModel>();

    public static Vector<HomeListModel> getAllPeopleHave() {
        return allPeopleHaveList;
    }
    public static void setAllPeopleHave(Vector<HomeListModel> allPeopleHave) {
        HomeList.allPeopleHaveList = allPeopleHave;
    }
    public static HomeListModel getmAllPeopleHave(int pos) {
        return allPeopleHaveList.elementAt(pos);
    }
    public static void setPeopleHaveinfo(HomeListModel _alldonorList) {
        HomeList.allPeopleHaveList.addElement(_alldonorList);
    }
    public static void removeAllPeopleHaveList() {
        HomeList.allPeopleHaveList.removeAllElements();
    }
}
