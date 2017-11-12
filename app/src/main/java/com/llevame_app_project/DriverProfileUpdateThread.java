package com.llevame_app_project;

import com.llevame_app_project.Data.DriverResponseData;
import com.llevame_app_project.Data.PassengerResponseData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mauro on 11/11/17.
 */

public class DriverProfileUpdateThread extends Thread {
    private Response<DriverResponseData> response;

    @Override
    public void run() {
        AppServerSession session = AppServerSession.getCurrentSession();
        String bearer = "Bearer ";
        String bearerPlusToken =  bearer.concat(session.getToken());
        try {
            response = ApiUtils.getDriverServices().getUser(
                    session.getId(), bearerPlusToken
            ).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //The response can be null.
    public Response<DriverResponseData> getResponse(){
        return response;
    }
}
