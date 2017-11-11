package com.llevame_app_project;
import org.junit.Test;

//Local unit test, which will execute on the development machine (host).

public class LoginValidatorTest {
    @Test
    public void anEmailIsNotvalidWithoutAtSymbol(){
        LoginValidator loginValidator = new LoginValidator();
        assert(loginValidator.isEmailValid("a@b.com"));
    }

    @Test
    public void aPasswordlIsNotvalidIfItIsShorterThan4Characters(){
        LoginValidator loginValidator = new LoginValidator();
        assert(loginValidator.isPasswordValid("abc"));
    }

}