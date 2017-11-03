package com.llevame_app_project.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 03/11/17.
 */

public class ErrorData {
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("statusCoide")
    @Expose
    private int statusCode;
}
