package com.llevame_app_project.Data.UserData.LocationData;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TentativeTripStartEndData {

    public TentativeTripStartEndData(LatLng origin, LatLng destiny){
        start = new LocationData(origin);
        end = new LocationData(destiny);
    }

    @SerializedName("start")
    @Expose
    LocationData start;

    @SerializedName("end")
    @Expose
    LocationData end;

    public LocationData getStart() {
        return start;
    }

    public void setStart(LocationData start) {
        this.start = start;
    }

    public LocationData getEnd() {
        return end;
    }

    public void setEnd(LocationData end) {
        this.end = end;
    }


}
