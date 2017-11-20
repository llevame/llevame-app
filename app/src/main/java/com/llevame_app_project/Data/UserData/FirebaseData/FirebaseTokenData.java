package com.llevame_app_project.Data.UserData.FirebaseData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirebaseTokenData {

    @SerializedName("firebaseToken")
    @Expose
    private String firebaseToken;

    public FirebaseTokenData(String deviceId){
        this.firebaseToken = deviceId;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
