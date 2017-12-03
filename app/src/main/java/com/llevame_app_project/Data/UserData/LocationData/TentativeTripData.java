package com.llevame_app_project.Data.UserData.LocationData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TentativeTripData {
    @SerializedName("directions")
    @Expose
    private List<List<LocationData>> travels;

    @SerializedName("cost")
    @Expose
    private float cost;

    public List<List<LocationData>> getTravels() {
        return travels;
    }

    public void setTravels(List<List<LocationData>> travels) {
        this.travels = travels;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
