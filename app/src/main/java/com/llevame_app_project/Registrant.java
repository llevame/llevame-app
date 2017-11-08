package com.llevame_app_project;

import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;

import retrofit2.Response;

import static java.lang.Thread.sleep;

public class Registrant {

    RegistrationThread registrationThread;
    private boolean finishedRegistration;
    private Throwable registeringError;
    private Response response;

    public LoginResponseData register(FirstRegistrationForm firstForm,
                  SecondRegistrationForm secondForm) throws Throwable{

        Response<LoginResponseData> response;
        final PassengerData data;

        data = createPassengerData(firstForm,secondForm);
        registrationThread = new RegistrationThread(data);
        registrationThread.start();
        registrationThread.join();
        response = registrationThread.getResponse();
        if(!response.isSuccessful()){
            throw new Throwable(response.raw().message());
        }
        return response.body();
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
