package com.llevame_app_project;

import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;

import retrofit2.Response;

import static java.lang.Thread.sleep;

public class Registrant {

    private RegistrationListener listener= new RegistrationListener(this);
    private LoginResponseData responseData;
    private boolean thereWasAnErrorInRegistration = false;
    private boolean finishedRegistration;
    private Throwable registeringError;

    //To be called by the listener when it finishes doing his job
    void finishedRegistering(LoginResponseData response){
        this.responseData = response;
        thereWasAnErrorInRegistration = false;
        finishedRegistration = true;
    }

    void finishedRegisteringWithError(Throwable t){
        registeringError = t;
        thereWasAnErrorInRegistration = true;
        finishedRegistration = true;
    }


    public LoginResponseData register(FirstRegistrationForm firstForm,
                  SecondRegistrationForm secondForm) throws Throwable{

        thereWasAnErrorInRegistration = false;
        Response<LoginResponseData> response;
        final PassengerData data;

        data = createPassengerData(firstForm,secondForm);
        ApiUtils.getRegistrationServices().registerUser(data.getEmail(),
                        data).enqueue(listener);
        waitForAResponse();
        if(thereWasAnErrorInRegistration)
            throw registeringError;
        return responseData;
    }

    private void waitForAResponse(){
        int i = 0;
        while(!finishedRegistration && i < 5){
            try {
                sleep(500);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!(i<5)){
            registeringError = new Throwable("Kaboom");
            thereWasAnErrorInRegistration = true;
        }
    }

    PassengerData createPassengerData(FirstRegistrationForm firstForm,
                           SecondRegistrationForm secondForm){
        PassengerData passengerData = new PassengerData();
        passengerData.setEmail(firstForm.email.toString());
        passengerData.setCreditCardNumber(secondForm.creditCardNumber.toString());
        passengerData.setFirstName(secondForm.firstName.toString());
        passengerData.setLastName(secondForm.lastName.toString());
        passengerData.setPassword(firstForm.password);
        passengerData.setDriver(false);
        return passengerData;
    }
}
