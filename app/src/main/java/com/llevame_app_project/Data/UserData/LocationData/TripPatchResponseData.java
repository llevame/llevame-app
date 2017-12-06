package com.llevame_app_project.Data.UserData.LocationData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llevame_app_project.Data.UserData.ResponseData;

public class TripPatchResponseData extends ResponseData{
    @SerializedName("result")
    @Expose
    private TripIdData tripCreationData;

    public TripIdData getTripCreationData() {
        return tripCreationData;
    }

    public void setTripCreationData(TripIdData tripCreationData) {
        this.tripCreationData = tripCreationData;
    }
}
