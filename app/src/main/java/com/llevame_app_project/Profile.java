package com.llevame_app_project;

import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.PassengerResponseData;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.UserData;

import retrofit2.Response;

public class Profile {

    UserData passengerData;

    public void updateDataFromServer() throws Exception{

        if(!AppServerSession.getCurrentSession().isDriver())
            updatePassengerData();
        else
            updateDriverData();
    }

    private void updatePassengerData() throws Exception {
        PassengerProfileUpdateThread thread =
                new PassengerProfileUpdateThread();
        thread.start();
        thread.join();
        Response<PassengerResponseData> response = thread.getResponse();
        if(!response.isSuccessful())
            throw new Exception(response.raw().message());
        this.passengerData = response.body().getPassengerData();
    }

    private void updateDriverData(){

    }

    public String getFirstName(){
        return passengerData.getFirstName();
    }

    public String getLastName(){
        return passengerData.getLastName();
    }

    public String getEmail(){
        return passengerData.getEmail();
    }

    public String getCreditCardNumber(){
        return passengerData.getCreditCardNumber();
    }
}
