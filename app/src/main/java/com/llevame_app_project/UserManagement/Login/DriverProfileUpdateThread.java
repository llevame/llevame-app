package com.llevame_app_project.UserManagement.Login;

import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;
import com.llevame_app_project.Data.UserData.DriverData.DriverResponseData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import java.io.IOException;

import retrofit2.Response;

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
