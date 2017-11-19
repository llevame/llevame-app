package com.llevame_app_project.Data;


import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationForServerData {

    @SerializedName("location")
    @Expose
    LocationData locationData;

    public LocationForServerData(Location location){
        this.locationData = new LocationData(location);
    }

}
