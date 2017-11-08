package com.llevame_app_project;

import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;

import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * Created by mauro on 05/11/17.
 */

public class RegistrantTests {
    Registrant registrant = new Registrant();

    @Test
    public void blabla(){
        LoginResponseData response;

        FirstRegistrationForm firstForm = new FirstRegistrationForm();
        SecondRegistrationForm secondForm = new SecondRegistrationForm();
        firstForm.isDriver = false;
        firstForm.password = "12345";
        firstForm.email = "alpha@gmail.com";
        secondForm.creditCardNumber = "12345";
        secondForm.firstName = "alpha";
        secondForm.lastName = "Jorge";

        try {
            response = registrant.register(firstForm, secondForm);

        } catch (Throwable throwable) {
            fail();
        }
    }
}
