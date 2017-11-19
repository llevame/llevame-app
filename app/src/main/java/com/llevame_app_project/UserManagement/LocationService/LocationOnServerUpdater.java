package com.llevame_app_project.UserManagement.LocationService;
import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

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

    Location lastKnownLocation;
    private final Object lock = new Object();

    @Override
    public void run(){
        //noinspection InfiniteLoopStatement
        while(true){
            if(lastKnownLocation != null){
                synchronized (lock) {
                    Log.i("LocationUpdater:", "Updating gps to:" + lastKnownLocation);
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