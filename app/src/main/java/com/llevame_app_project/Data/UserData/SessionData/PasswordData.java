package com.llevame_app_project.Data.UserData.SessionData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PasswordData {
    @SerializedName("password")
    @Expose
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
