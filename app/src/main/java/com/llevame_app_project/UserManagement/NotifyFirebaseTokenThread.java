package com.llevame_app_project.UserManagement;

import android.util.Log;

import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.UserPatchServices;
import com.llevame_app_project.Data.UserData.FirebaseData.FirebaseTokenData;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import java.io.IOException;

public class NotifyFirebaseTokenThread extends Thread {

    private String firebaseToken;
    public NotifyFirebaseTokenThread(String token){
        firebaseToken = token;
    }

    @Override
    public void run() {
        UserPatchServices service;
        service = ApiUtils.getUserPatchServices();
        String bearerToken = AppServerSession.getCurrentSession().
                getBearerToken();
        FirebaseTokenData idData = new FirebaseTokenData(firebaseToken);

        try {
            service.notifyFirebaseToken(bearerToken,idData).execute();
        } catch (IOException e) {
            Log.e("FirebaseToken:", "Error");
        }
    }
}
