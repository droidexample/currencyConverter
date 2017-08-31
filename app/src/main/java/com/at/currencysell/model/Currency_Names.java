package com.at.currencysell.model;

public class Currency_Names {

    public String short_name;
    public String abrivation;


    public Currency_Names(String short_name, String abrivation) {
        this.short_name = short_name;
        this.abrivation = abrivation;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getAbrivation() {
        return abrivation;
    }

    public void setAbrivation(String abrivation) {
        this.abrivation = abrivation;
    }
}
