package com.llevame_app_project.Data.UserData.SessionData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 03/11/17.
 */

public class LoginData {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @SerializedName("token")
    @Expose
    private String token;

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    @SerializedName("isDriver")
    @Expose
    private boolean isDriver;
}
