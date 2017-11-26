package com.llevame_app_project.Data.UserData.LocationData;


import android.os.AsyncTask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusData {

    static final int CREATED = 0;
    static final int ACCEPTED = 1;
    static final int STARTED = 2;
    static final int ENDED = 3;
    static final int CANCELLED = 4;

    public StatusData(int status){
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
