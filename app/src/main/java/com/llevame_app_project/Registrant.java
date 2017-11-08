package com.llevame_app_project;

import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;

import retrofit2.Response;

import static java.lang.Thread.sleep;

public class Registrant {

    private RegistrationThread registrationThread;
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
}
