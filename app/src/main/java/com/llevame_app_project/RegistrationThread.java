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

    PassengerData passengerToLogin;
    Response response;
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

    public Response getResponse(){
        return response;
    }
}
