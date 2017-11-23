package com.llevame_app_project.Data.UserData.LocationData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TentativeTripStartEnd {

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
