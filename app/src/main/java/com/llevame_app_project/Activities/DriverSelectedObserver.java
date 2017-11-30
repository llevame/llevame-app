package com.llevame_app_project.Activities;


public class DriverSelectedObserver implements AppObserver {

    PassengerActivity activity;
    DriverSelectedObserver(PassengerActivity activity){
        this.activity = activity;
    }
    @Override
    public void notifyObserver() {
        activity.onDriverSelected();
    }
}
