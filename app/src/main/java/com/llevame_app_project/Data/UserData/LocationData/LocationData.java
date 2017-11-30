package com.llevame_app_project.Data.UserData.LocationData;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationData {

    public LocationData(Location location){
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
    public LocationData(LatLng location ){
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
