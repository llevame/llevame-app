package com.llevame_app_project.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 30/10/17.
 */

public class DriverData extends UserData{
    @SerializedName("carModel")
    @Expose
    private String carModel;

    @SerializedName("carColor")
    @Expose
    private String carColor;

    @SerializedName("carPatent")
    @Expose
    private String carPatent;

    @SerializedName("carHasAC")
    @Expose
    private boolean carHasAc;

    @SerializedName("music")
    @Expose
    private String music;

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarPatent() {
        return carPatent;
    }

    public void setCarPatent(String carPatent) {
        this.carPatent = carPatent;
    }

    public boolean isCarHasAc() {
        return carHasAc;
    }

    public void setCarHasAc(boolean carHasAc) {
        this.carHasAc = carHasAc;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }
}
