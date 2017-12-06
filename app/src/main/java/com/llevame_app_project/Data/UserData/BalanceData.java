package com.llevame_app_project.Data.UserData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceData {

    @Expose
    @SerializedName("currency")
    String currency;

    @Expose
    @SerializedName("balance")
    float balance;
}
