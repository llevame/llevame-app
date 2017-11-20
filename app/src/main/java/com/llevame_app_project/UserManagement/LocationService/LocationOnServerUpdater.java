package com.llevame_app_project.UserManagement.LocationService;
import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;
import com.llevame_app_project.Data.UserData.LocationData.LocationForServerData;
import com.llevame_app_project.Data.UserData.PatchResponseData;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.UserPatchServices;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import java.io.IOException;

public class LocationOnServerUpdater extends Thread {


    //If you use the app context nothing bad happens
    @SuppressLint("StaticFieldLeak")
    private static LocationOnServerUpdater daemon;

    public static LocationOnServerUpdater getInstance(){
        if(daemon == null) {
            daemon = new LocationOnServerUpdater();
        }
        return daemon;
    }

    private LocationOnServerUpdater(){

    }

    private Location lastKnownLocation;
    private final Object lock = new Object();

    @Override
    public void run(){
        UserPatchServices service = ApiUtils.getUserPatchServices();
        //noinspection InfiniteLoopStatement
        while(true){
            if(lastKnownLocation != null && AppServerSession.isCreated()){
                AppServerSession session = AppServerSession.getCurrentSession();
                String bearer = "Bearer ";
                String bearerPlusToken =  bearer.concat(session.getToken());
                LocationForServerData locationData;
                synchronized (lock) {
                     locationData = new LocationForServerData(lastKnownLocation);
                }
                PatchResponseData response;
                Log.i("LocationUpdater:", "Sending location to server:" + lastKnownLocation);
                try {
                    response = service.notifyLocation(bearerPlusToken,
                            locationData).execute().body();
                    if(response.getSuccess() != true){
                        String statusCode = response.getStatusCode().toString();
                        Log.e("LocationUpdater:", "connection failed, error: " +
                                statusCode);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateLocation(Location location){
        synchronized (lock) {
            lastKnownLocation = location;
        }
    }
}