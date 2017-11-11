package com.llevame_app_project.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 08/11/17.
 */

public class PassengerResponseData {
    @SerializedName("error")
    @Expose
    private ErrorData error;

    @SerializedName("result")
    @Expose
    private PassengerData passengerData;

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public ErrorData getError() {
        return error;
    }

    public void ErrorData(ErrorData error) {
        this.error = error;
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

    public UserData getPassengerData() {
        return passengerData;
    }

    public void setPassengerData(PassengerData passengerData) {
        this.passengerData = passengerData;
    }
}
