package com.llevame_app_project.Data.UserData.LocationData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llevame_app_project.Data.UserData.ResponseData;

public class TentativeTripDataResponse extends ResponseData {

    @SerializedName("result")
    @Expose
    private TentativeTripData tentativeTripData;

    public TentativeTripData getTentativeTripData() {
        return tentativeTripData;
    }

    public void setTentativeTripData(TentativeTripData tentativeTripData) {
        this.tentativeTripData = tentativeTripData;
    }
}
