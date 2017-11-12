package com.llevame_app_project;

import com.llevame_app_project.Data.CarData;
import com.llevame_app_project.Data.DriverData;
import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;

import java.sql.Driver;

import retrofit2.Response;

import static java.lang.Thread.sleep;

public class Registrant {

    private PassengerRegistrationThread passengerRegistrationThread;
    private DriverRegistrationThread driverRegistrationThread;
    private boolean finishedRegistration;
    private Throwable registeringError;
    private Response response;

    public LoginResponseData register(FirstRegistrationForm firstForm,
                  SecondRegistrationForm secondForm) throws Throwable{

        Response<LoginResponseData> response;
        final PassengerData data;

        data = createPassengerData(firstForm,secondForm);
        passengerRegistrationThread = new PassengerRegistrationThread(data);
        passengerRegistrationThread.start();
        passengerRegistrationThread.join();
        response = passengerRegistrationThread.getResponse();
        if(!response.isSuccessful()){
            throw new Throwable(response.raw().message());
        }
        return response.body();
    }


    private PassengerData createPassengerData(FirstRegistrationForm firstForm,
                                              SecondRegistrationForm secondForm){
        PassengerData passengerData = new PassengerData();
        passengerData.setEmail(firstForm.email);
        passengerData.setCreditCardNumber(secondForm.creditCardNumber);
        passengerData.setFirstName(secondForm.firstName);
        passengerData.setLastName(secondForm.lastName);
        passengerData.setPassword(firstForm.password);
        passengerData.setDriver(false);
        return passengerData;
    }

    public LoginResponseData register(FirstRegistrationForm firstForm,
                                      SecondRegistrationForm secondForm,
                                      CarData carData) throws Throwable{

        Response<LoginResponseData> response;
        final DriverData data;

        data = createDriverData(firstForm,secondForm, carData);
        driverRegistrationThread = new DriverRegistrationThread(data);
        driverRegistrationThread.start();
        driverRegistrationThread.join();
        response = driverRegistrationThread.getResponse();
        if(!response.isSuccessful()){
            throw new Throwable(response.raw().message());
        }
        return response.body();
    }

    private DriverData createDriverData(FirstRegistrationForm firstForm,
                                              SecondRegistrationForm secondForm,
                                        CarData carData){
        DriverData driverData = new DriverData();
        driverData.setEmail(firstForm.email);
        driverData.setCreditCardNumber(secondForm.creditCardNumber);
        driverData.setFirstName(secondForm.firstName);
        driverData.setLastName(secondForm.lastName);
        driverData.setPassword(firstForm.password);
        driverData.setDriver(true);
        driverData.setCar(carData);
        return driverData;
    }


}
