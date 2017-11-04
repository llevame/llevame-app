package com.llevame_app_project.Data.Remote;
import com.llevame_app_project.Data.Remote.LoginServices;
public class ApiUtils {
    public static final String BASE_URL = "https://safe-savannah-49286.herokuapp.com/";

    public static LoginServices getLoginServices() {
        return RetrofitClient.getClient(BASE_URL).create(LoginServices.class);
    }

    public static RegistrationServices getRegistrationServices() {
        return RetrofitClient.getClient(BASE_URL).create(RegistrationServices.class);
    }
}
