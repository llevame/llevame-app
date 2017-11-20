package com.llevame_app_project.Activities;


public class DriverSelectedListener implements AppListenerInterface {

    PassengerActivity activity;
    DriverSelectedListener(PassengerActivity activity){
        this.activity = activity;
    }
    @Override
    public void notifyObserver() {
        activity.onDriverSelected();
    }
}
