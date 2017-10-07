package com.llevame_app_project;

public class MockupServer {
    boolean attemptLogin(String email, String password){
        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }
        if(email.contains("valid"))
            return true;
    return false;
    }
}
