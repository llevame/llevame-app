package com.llevame_app_project.Data.Remote;
import com.llevame_app_project.Data.Remote.UserServices;
public class ApiUtils {
    public static final String BASE_URL = "https://safe-savannah-49286.herokuapp.com/";

    public static UserServices getUserServices() {
        return RetrofitClient.getClient(BASE_URL).create(UserServices.class);
    }
}
