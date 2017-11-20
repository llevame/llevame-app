package com.llevame_app_project.Data.UserData.DriverData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llevame_app_project.Data.ErrorData;

import java.util.List;

public class NearbyDriversResponseData {

    @SerializedName("error")
    @Expose
    private ErrorData error;

    @SerializedName("result")
    @Expose
    private List<DriverData> nearbyDrivers;

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public ErrorData getError() {
        return error;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }

    public List<DriverData> getNearbyDrivers() {
        return nearbyDrivers;
    }

    public void setNearbyDrivers(List<DriverData> nearbyDrivers) {
        this.nearbyDrivers = nearbyDrivers;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
