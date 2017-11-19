package com.llevame_app_project.UserManagement.LoggedUser;

/**
 * Created by mauro on 03/11/17.
 */

public class AppServerSession {
    private String id;
    private String token;
    private Boolean isDriver;
    private static AppServerSession currentSession;

    public static AppServerSession getCurrentSession(){
        return currentSession;
    }

    public static void createSession(boolean isDriver, String id,String token){
        currentSession = new AppServerSession(isDriver,id,token);
    }

    private AppServerSession(boolean isDriver, String id,String token){
        this.id = id;
        this.isDriver = isDriver;
        this.token = token;
    }

    public boolean isDriver(){
        return isDriver;
    }
    public String getToken(){
        return token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}