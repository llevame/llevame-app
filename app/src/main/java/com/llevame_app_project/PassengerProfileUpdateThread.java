package com.llevame_app_project;

import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mauro on 08/11/17.
 */

public class PassengerProfileUpdateThread extends Thread {

    private Response<PassengerData> response;

    @Override
    public void run() {
        AppServerSession session = AppServerSession.getCurrentSession();

        try {
            response = ApiUtils.getPassengerServices().getUser(
                    session.getId()
            ).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //The response can be null.
    public Response<PassengerData> getResponse(){
        return response;
    }
}
