package com.llevame_app_project;

/**
 * Created by mauro on 03/11/17.
 */

public class AppServerSession {
    private String token;
    private Boolean isDriver;
    private static AppServerSession currentSession;

    public static AppServerSession getCurrentSession(){
        return currentSession;
    }

    public static void createSession(boolean isDriver, String token){
        currentSession = new AppServerSession(isDriver,token);
    }

    private AppServerSession(boolean isDriver, String token){
        this.isDriver = isDriver;
        this.token = token;
    }

    public boolean isDriver(){
        return isDriver;
    }
    public String getToken(){
        return token;
    }

}
