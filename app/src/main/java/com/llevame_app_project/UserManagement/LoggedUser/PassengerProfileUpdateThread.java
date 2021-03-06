package com.llevame_app_project.UserManagement.LoggedUser;

import com.llevame_app_project.Data.UserData.PassengerData.PassengerResponseData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by mauro on 08/11/17.
 */

public class PassengerProfileUpdateThread extends Thread {

    private Response<PassengerResponseData> response;

    @Override
    public void run() {
        AppServerSession session = AppServerSession.getCurrentSession();
        String bearer = "Bearer ";
        String bearerPlusToken =  bearer.concat(session.getToken());
        try {
            response = ApiUtils.getPassengerServices().getMyUser(bearerPlusToken)
            .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //The response can be null.
    public Response<PassengerResponseData> getResponse(){
        return response;
    }
}
