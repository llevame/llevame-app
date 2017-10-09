package com.llevame_app_project;

class LoginValidator {

    LoginValidator(){

    }
    /**Checks if the email contains an @ symbol*/
    public boolean isEmailValid(String email) {
        return email.contains("@");
    }
    /**Checks if the password contains more than 4 characters*/
    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
