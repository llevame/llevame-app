package com.llevame_app_project;

class LoginValidator {

    LoginValidator(){

    }

    public boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
