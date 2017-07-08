package com.at.currencysell.model;



public class MyReqestActiveMoel {
    private String currency_name;
    private String currency_full_name;
    private int image;

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrency_full_name() {
        return currency_full_name;
    }

    public void setCurrency_full_name(String currency_full_name) {
        this.currency_full_name = currency_full_name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
