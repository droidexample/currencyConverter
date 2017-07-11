package com.at.currencysell.holder;


import com.at.currencysell.model.CurrencyRatesModel;
import com.at.currencysell.model.Currency_Names;

import java.util.Vector;

/**
 * Created by admin on 19/03/2017.
 */

public class AllCurrencyRateList {

    public static Vector<CurrencyRatesModel> mAllCurrencyRateList = new Vector<CurrencyRatesModel>();


    public static Vector<CurrencyRatesModel> getmAllCurrencyRateList() {
        return mAllCurrencyRateList;
    }

    public static void setmAllCurrencyRateList(Vector<CurrencyRatesModel> mAllCurrencyList) {
        AllCurrencyRateList.mAllCurrencyRateList = mAllCurrencyList;
    }


    public static CurrencyRatesModel getCurrencyRateList(int postion) {
        return mAllCurrencyRateList.elementAt(postion);
    }

    public static void setmCurrencyRateList(CurrencyRatesModel _mAllCurrencyList) {
        AllCurrencyRateList.mAllCurrencyRateList.addElement(_mAllCurrencyList);
    }

    public static void removeAllCurrencyRateList() {
        AllCurrencyRateList.mAllCurrencyRateList.removeAllElements();

    }

}
