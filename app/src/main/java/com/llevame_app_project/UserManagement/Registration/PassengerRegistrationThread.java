package com.llevame_app_project.UserManagement.Registration;

import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mauro on 08/11/17.
 */

public class PassengerRegistrationThread extends Thread {

    private PassengerData passengerToBeRegistered;
    private Response<LoginResponseData> response;
    PassengerRegistrationThread(PassengerData passenger){
        this.passengerToBeRegistered = passenger;
    }

    @Override
    public void run() {
        try {
            response = ApiUtils.getRegistrationServices().registerUser(passengerToBeRegistered.getEmail(),
                    passengerToBeRegistered).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //The response can be null.
    public Response<LoginResponseData> getResponse(){
        return response;
    }
}
