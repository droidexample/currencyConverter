package com.at.currencysell.holder;


import com.at.currencysell.model.MyReqestHaveMoel;

import java.util.Vector;

/**
 * Created by admin on 19/03/2017.
 */

public class AllDolorList {

    public static Vector<MyReqestHaveMoel> mAllDolorList = new Vector<MyReqestHaveMoel>();


    public static Vector<MyReqestHaveMoel> getmAllDolorList() {
        return mAllDolorList;
    }

    public static void setmAllDolorList(Vector<MyReqestHaveMoel> mAllDolorList) {
        AllDolorList.mAllDolorList = mAllDolorList;
    }


    public static MyReqestHaveMoel getDolorList(int postion) {
        return mAllDolorList.elementAt(postion);
    }

    public static void setmDolorList(MyReqestHaveMoel _mAllDolorList) {
        AllDolorList.mAllDolorList.addElement(_mAllDolorList);
    }

    public static void removeAlltopimage() {
        AllDolorList.mAllDolorList.removeAllElements();

    }

}
