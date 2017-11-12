package com.llevame_app_project.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 11/11/17.
 */

public class DriverResponseData {

    @SerializedName("error")
    @Expose
    private ErrorData error;

    @SerializedName("result")
    @Expose
    private DriverData driverData;

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

    public DriverData getDriverData() {
        return driverData;
    }

    public void setDriverData(DriverData driverData) {
        this.driverData = driverData;
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
