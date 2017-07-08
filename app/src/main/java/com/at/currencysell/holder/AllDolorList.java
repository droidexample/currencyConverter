package com.at.currencysell.holder;


import com.at.currencysell.model.MyReqestActiveMoel;

import java.util.Vector;

/**
 * Created by admin on 19/03/2017.
 */

public class AllDolorList {

    public static Vector<MyReqestActiveMoel> mAllDolorList = new Vector<MyReqestActiveMoel>();


    public static Vector<MyReqestActiveMoel> getmAllDolorList() {
        return mAllDolorList;
    }

    public static void setmAllDolorList(Vector<MyReqestActiveMoel> mAllDolorList) {
        AllDolorList.mAllDolorList = mAllDolorList;
    }


    public static MyReqestActiveMoel getDolorList(int postion) {
        return mAllDolorList.elementAt(postion);
    }

    public static void setmDolorList(MyReqestActiveMoel _mAllDolorList) {
        AllDolorList.mAllDolorList.addElement(_mAllDolorList);
    }

    public static void removeAlltopimage() {
        AllDolorList.mAllDolorList.removeAllElements();

    }

}
