package com.llevame_app_project;

/**
 * Created by mauro on 03/11/17.
 */

public class AppServerSession {
    private static String token;
    private static Boolean isDriver;

    public static void saveToken(String aToken) {
        token = aToken;
    }

    public static void setIsDriver(boolean pIsDriver){
        isDriver = pIsDriver;
    }
    public static boolean thereIsAValidToken(){
        return(token != null);
    }

    public String getToken(){
        return token;
    }

    public static boolean itIsADriverSession(){
        return isDriver;
    }

}
