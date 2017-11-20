package com.llevame_app_project.UserManagement.Registration;

import com.llevame_app_project.Data.UserData.DriverData.DriverData;
import com.llevame_app_project.Data.UserData.SessionData.LoginResponseData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mauro on 11/11/17.
 */

public class DriverRegistrationThread extends Thread{
    private DriverData driverToBeRegistered;
    private Response<LoginResponseData> response;

    DriverRegistrationThread(DriverData driver){
        this.driverToBeRegistered = driver;
    }

    @Override
    public void run() {
        try {
            response = ApiUtils.getRegistrationServices().registerUser(driverToBeRegistered.getEmail(),
                    driverToBeRegistered).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //The response can be null.
    public Response<LoginResponseData> getResponse(){
        return response;
    }
}
