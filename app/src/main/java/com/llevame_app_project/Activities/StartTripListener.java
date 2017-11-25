package com.llevame_app_project.Activities;


public class StartTripListener implements AppListenerInterface {

    PassengerActivity activity;
    StartTripListener(PassengerActivity activity){
        this.activity = activity;
    }
    @Override
    public void notifyObserver() {
        activity.onStartTrip();
    }
}
