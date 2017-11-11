package com.llevame_app_project;

import com.llevame_app_project.Data.LoginData;
import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mauro on 08/11/17.
 */

public class RegistrationThread extends Thread {

    private PassengerData passengerToLogin;
    private Response<LoginResponseData> response;
    RegistrationThread(PassengerData passenger){
        this.passengerToLogin = passenger;
    }
    @Override
    public void run() {
        try {
            response = ApiUtils.getRegistrationServices().registerUser(passengerToLogin.getEmail(),
                    passengerToLogin).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //The response can be null.
    public Response<LoginResponseData> getResponse(){
        return response;
    }
}
