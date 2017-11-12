package com.llevame_app_project.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mauro on 30/10/17.
 */

public class DriverData extends UserData{

    public DriverData(){
        this.setDriver(true);
    }

    @SerializedName("car")
    @Expose
    CarData car;

    public CarData getCar() {
        return car;
    }

    public void setCar(CarData car) {
        this.car = car;
    }

}
