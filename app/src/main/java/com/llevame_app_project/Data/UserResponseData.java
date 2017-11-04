package com.llevame_app_project.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UserResponseData {

    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("result")
    @Expose
    private UserData userData;

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setResult(UserData userData) {
        this.userData = userData;
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
