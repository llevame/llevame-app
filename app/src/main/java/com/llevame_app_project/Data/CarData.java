package com.llevame_app_project.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 11/11/17.
 */

public class CarData {

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("patent")
    @Expose
    private String patent;

    @SerializedName("hasAC")
    @Expose
    private boolean hasAc;

    @SerializedName("year")
    @Expose
    private int year;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public boolean isHasAc() {
        return hasAc;
    }

    public void setHasAc(boolean hasAc) {
        this.hasAc = hasAc;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
