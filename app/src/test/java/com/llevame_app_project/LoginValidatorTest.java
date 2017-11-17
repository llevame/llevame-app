package com.llevame_app_project;

import com.llevame_app_project.UserManagement.Login.LoginValidator;

import org.junit.Test;

//Local unit test, which will execute on the development machine (host).

public class LoginValidatorTest {
    @Test
    public void anEmailIsNotValidWithoutAtSymbol(){
        LoginValidator loginValidator = new LoginValidator();
        assert(loginValidator.isEmailValid("a@b.com"));
    }

    @Test
    public void aPasswordIsNotValidIfItIsShorterThan4Characters(){
        LoginValidator loginValidator = new LoginValidator();
        assert(loginValidator.isPasswordValid("abc"));
    }

}