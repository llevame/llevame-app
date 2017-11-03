package com.llevame_app_project;

/**
 * Created by mauro on 03/11/17.
 */

public class AppserverSession {
    private static AppserverSession ourInstance;;
    private String token;

    public static AppserverSession createInstance(String token) {
        return ourInstance;
    }

    public static AppserverSession getInstance() {
        return ourInstance;
    }

    public static boolean thereIsAnOpenedSession(){
        return(ourInstance != null);
    }

    private AppserverSession(String aToken) {
        this.token = aToken;
    }

}
