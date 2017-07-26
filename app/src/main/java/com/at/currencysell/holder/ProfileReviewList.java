package com.at.currencysell.holder;

import com.at.currencysell.model.HomeListModel;
import com.at.currencysell.model.ProfileReviewModel;

import java.util.Vector;

/**
 * Created by Shokiul Islam on 5/9/2016.
 */
public class ProfileReviewList {

    public static Vector<ProfileReviewModel> allPeopleHaveList = new Vector<ProfileReviewModel>();

    public static Vector<ProfileReviewModel> getAllPeopleHave() {
        return allPeopleHaveList;
    }
    public static void setAllPeopleHave(Vector<ProfileReviewModel> allPeopleHave) {
        ProfileReviewList.allPeopleHaveList = allPeopleHave;
    }
    public static ProfileReviewModel getmAllPeopleHave(int pos) {
        return allPeopleHaveList.elementAt(pos);
    }
    public static void setPeopleHaveinfo(ProfileReviewModel _alldonorList) {
        ProfileReviewList.allPeopleHaveList.addElement(_alldonorList);
    }
    public static void removeAllPeopleHaveList() {
        ProfileReviewList.allPeopleHaveList.removeAllElements();
    }
}
