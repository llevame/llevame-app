package com.llevame_app_project.UserManagement.LoggedUser;

import com.llevame_app_project.Data.UserData.DriverData.CarData;
import com.llevame_app_project.Data.UserData.DriverData.DriverData;
import com.llevame_app_project.Data.UserData.DriverData.DriverResponseData;
import com.llevame_app_project.Data.UserData.PassengerData.PassengerData;
import com.llevame_app_project.Data.UserData.PassengerData.PassengerResponseData;
import com.llevame_app_project.UserManagement.Login.DriverProfileUpdateThread;

import retrofit2.Response;

public class Profile {

    PassengerData passengerData;
    DriverData driverData;

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

    private void updateDriverData() throws Exception{
        DriverProfileUpdateThread thread =
                new DriverProfileUpdateThread();
        thread.start();
        thread.join();
        Response<DriverResponseData> response = thread.getResponse();
        if(!response.isSuccessful())
            throw new Exception(response.raw().message());
        this.driverData = response.body().getDriverData();
    }

    public String getFirstName()
    {
        if(AppServerSession.getCurrentSession().isDriver())
            return driverData.getFirstName();
        else
            return passengerData.getFirstName();
    }

    public String getLastName(){
        if(AppServerSession.getCurrentSession().isDriver())
            return driverData.getLastName();
        else
            return passengerData.getLastName();
    }

    public String getEmail(){
        if(AppServerSession.getCurrentSession().isDriver())
            return driverData.getEmail();
        else
            return passengerData.getEmail();
    }

    public String getCreditCardNumber(){
        if(AppServerSession.getCurrentSession().isDriver())
            return driverData.getCreditCardNumber();
        else
            return passengerData.getCreditCardNumber();
    }

    public CarData getCarData(){
        return driverData.getCar();
    }
}
