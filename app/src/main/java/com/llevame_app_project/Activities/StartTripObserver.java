package com.llevame_app_project.Activities;


public class StartTripObserver implements AppObserver {

    PassengerActivity activity;
    StartTripObserver(PassengerActivity activity){
        this.activity = activity;
    }
    @Override
    public void notifyObserver() {
        activity.onStartTrip();
    }
}
