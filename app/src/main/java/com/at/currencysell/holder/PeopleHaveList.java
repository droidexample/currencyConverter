package com.at.currencysell.holder;

import com.at.currencysell.model.HomeListModel;

import java.util.Vector;

/**
 * Created by Shokiul Islam on 5/9/2016.
 */
public class PeopleHaveList {

    public static Vector<HomeListModel> allPeopleHaveList = new Vector<HomeListModel>();

    public static Vector<HomeListModel> getAllPeopleHave() {
        return allPeopleHaveList;
    }
    public static void setAllPeopleHave(Vector<HomeListModel> allPeopleHave) {
        PeopleHaveList.allPeopleHaveList = allPeopleHave;
    }
    public static HomeListModel getEventEventDetailsInfos(int pos) {
        return allPeopleHaveList.elementAt(pos);
    }
    public static void setPeopleHaveinfo(HomeListModel _alldonorList) {
        PeopleHaveList.allPeopleHaveList.addElement(_alldonorList);
    }
    public static void removeAllPeopleHaveList() {
        PeopleHaveList.allPeopleHaveList.removeAllElements();
    }
}
