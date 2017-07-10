package com.at.currencysell.holder;


import com.at.currencysell.model.Currency_Names;

import java.util.Vector;

/**
 * Created by admin on 19/03/2017.
 */

public class AllCurrencyList {

    public static Vector<Currency_Names> mAllCurrencyList = new Vector<Currency_Names>();


    public static Vector<Currency_Names> getmAllDolorList() {
        return mAllCurrencyList;
    }

    public static void setmAllDolorList(Vector<Currency_Names> mAllCurrencyList) {
        AllCurrencyList.mAllCurrencyList = mAllCurrencyList;
    }


    public static Currency_Names getDolorList(int postion) {
        return mAllCurrencyList.elementAt(postion);
    }

    public static void setmDolorList(Currency_Names _mAllDolorList) {
        AllCurrencyList.mAllCurrencyList.addElement(_mAllDolorList);
    }

    public static void removeAlltopimage() {
        AllCurrencyList.mAllCurrencyList.removeAllElements();

    }

}
