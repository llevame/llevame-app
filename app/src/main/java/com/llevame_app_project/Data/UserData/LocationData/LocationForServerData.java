package com.llevame_app_project.Data.UserData.LocationData;


import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llevame_app_project.Data.UserData.LocationData.LocationData;

public class LocationForServerData {

    @SerializedName("location")
    @Expose
    LocationData locationData;

    public LocationForServerData(Location location){
        this.locationData = new LocationData(location);
    }

}
