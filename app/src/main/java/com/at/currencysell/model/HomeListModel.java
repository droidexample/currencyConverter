package com.at.currencysell.model;



public class HomeListModel {
    private String name;
    private String end_date;
    private String location;
    private String want_currency;
    private String want_amount;
    private String pay_currency;
    private String pay_amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWant_currency() {
        return want_currency;
    }

    public void setWant_currency(String want_currency) {
        this.want_currency = want_currency;
    }

    public String getWant_amount() {
        return want_amount;
    }

    public void setWant_amount(String want_amount) {
        this.want_amount = want_amount;
    }

    public String getPay_currency() {
        return pay_currency;
    }

    public void setPay_currency(String pay_currency) {
        this.pay_currency = pay_currency;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }
}
