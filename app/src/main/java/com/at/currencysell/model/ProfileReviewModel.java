package com.at.currencysell.model;

/**
 * Created by FT on 7/26/2017.
 */

public class ProfileReviewModel {
    private String review_userName;
    private String comment;
    private String review_user_picture;
    private String date;


    public String getReview_userName() {
        return review_userName;
    }

    public void setReview_userName(String review_userName) {
        this.review_userName = review_userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReview_user_picture() {
        return review_user_picture;
    }

    public void setReview_user_picture(String review_user_picture) {
        this.review_user_picture = review_user_picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
