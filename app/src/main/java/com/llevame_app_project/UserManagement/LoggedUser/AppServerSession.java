package com.llevame_app_project.UserManagement.LoggedUser;

public class AppServerSession {
    private String id;
    private String token;
    private Boolean isDriver;
    private static AppServerSession currentSession;
    private static boolean isCreated;

    public static AppServerSession getCurrentSession(){
        return currentSession;
    }

    public static void createSession(boolean isDriver, String id,String token){
        currentSession = new AppServerSession(isDriver,id,token);
        isCreated = true;
    }

    public static boolean isCreated(){
        return isCreated;
    }

    public static void destroySession(){
        isCreated = false;
        currentSession = null;
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

    public String getBearerToken(){
        String bearer = "Bearer ";
        String bearerPlusToken =  bearer.concat(token);
        return bearerPlusToken;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
