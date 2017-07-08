package com.at.currencysell.holder;

import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.model.MyReqestActiveMoel;

import java.util.Vector;

/**
 * Created by Shokiul Islam on 5/9/2016.
 */
public class MyRequestsList {

    public static Vector<MyReqestActiveMoel> allBloodDonorList = new Vector<MyReqestActiveMoel>();

    public static Vector<MyReqestActiveMoel> getAllEventDetailsInfos() {
        return allBloodDonorList;
    }
    public static void setAllEventinfo(Vector<MyReqestActiveMoel> allEventinfo) {
        MyRequestsList.allBloodDonorList = allEventinfo;
    }
    public static MyReqestActiveMoel getEventEventDetailsInfos(int pos) {
        return allBloodDonorList.elementAt(pos);
    }
    public static void setEventinfo(MyReqestActiveMoel _alldonorList) {
        MyRequestsList.allBloodDonorList.addElement(_alldonorList);
    }
    public static void removeAllBloodDonorList() {
        MyRequestsList.allBloodDonorList.removeAllElements();
    }
}
