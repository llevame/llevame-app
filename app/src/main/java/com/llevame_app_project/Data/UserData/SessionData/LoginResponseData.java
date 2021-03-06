package com.llevame_app_project.Data.UserData.SessionData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llevame_app_project.Data.ErrorData;
import com.llevame_app_project.Data.UserData.SessionData.LoginData;
import com.llevame_app_project.Data.UserData.UserData;

public class LoginResponseData {
    @SerializedName("error")
    @Expose
    private ErrorData error;

    @SerializedName("result")
    @Expose
    private LoginData loginData;

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

    public LoginData getLoginData() {
        return loginData;
    }

    public void setResult(UserData userData) {
        this.loginData = loginData;
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
