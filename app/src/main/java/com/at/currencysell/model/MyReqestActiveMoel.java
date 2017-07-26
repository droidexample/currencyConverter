package com.at.currencysell.model;



public class MyReqestActiveMoel {
    private String currency_have;
    private String currency_need;
    private String amount_have;
    private String amount_need;
    private String end_date;
    private int image;

    public String getCurrency_have() {
        return currency_have;
    }

    public void setCurrency_have(String currency_have) {
        this.currency_have = currency_have;
    }



    public String getCurrency_need() {
        return currency_need;
    }

    public void setCurrency_need(String currency_need) {
        this.currency_need = currency_need;
    }



    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAmount_have() {
        return amount_have;
    }

    public void setAmount_have(String amount_have) {
        this.amount_have = amount_have;
    }

    public String getAmount_need() {
        return amount_need;
    }

    public void setAmount_need(String amount_need) {
        this.amount_need = amount_need;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
