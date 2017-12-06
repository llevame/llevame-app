package com.llevame_app_project.Data.UserData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceData {

    @Expose
    @SerializedName("currency")
    private String currency;

    @Expose
    @SerializedName("value")
    private float balance;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
