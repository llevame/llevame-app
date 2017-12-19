package com.llevame_app_project.Data.UserData.SessionData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacebookLoginData {

    public FacebookLoginData(String token){
        this.token = token;
    }

    public FacebookLoginData(){
    }

    @SerializedName("fb_token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
