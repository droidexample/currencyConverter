package com.at.currencysell.holder;

import com.at.currencysell.model.MyReqestActiveMoel;

import java.util.Vector;

/**
 * Created by Shokiul Islam on 5/9/2016.
 */
public class MyRequestsFinishList {

    public static Vector<MyReqestActiveMoel> allReqestActiveList = new Vector<MyReqestActiveMoel>();

    public static Vector<MyReqestActiveMoel> getAllReqestActive() {
        return allReqestActiveList;
    }
    public static void setAllReqestActive(Vector<MyReqestActiveMoel> allEventinfo) {
        MyRequestsFinishList.allReqestActiveList = allEventinfo;
    }
    public static MyReqestActiveMoel getReqestActiveinfo(int pos) {
        return allReqestActiveList.elementAt(pos);
    }
    public static void setReqestActiveinfo(MyReqestActiveMoel _alldonorList) {
        MyRequestsFinishList.allReqestActiveList.addElement(_alldonorList);
    }
    public static void removeAllReqestActiveList() {
        MyRequestsFinishList.allReqestActiveList.removeAllElements();
    }
}
