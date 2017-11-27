package com.llevame_app_project.Data.UserData.LocationData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llevame_app_project.Data.UserData.ResponseData;

public class TripResponseData extends ResponseData {
    @SerializedName("result")
    @Expose
    private TripStatusData tripStatus;

    public TripStatusData getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatusData tripStatus) {
        this.tripStatus = tripStatus;
    }
}
