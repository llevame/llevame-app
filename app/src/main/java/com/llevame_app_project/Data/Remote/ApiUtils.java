package com.llevame_app_project.Data.Remote;

public class ApiUtils {

    private static final String BASE_URL = "https://safe-savannah-49286.herokuapp.com/";

    public static LoginServices getLoginServices() {
        return RetrofitClient.getClient(BASE_URL).create(LoginServices.class);
    }

    public static RegistrationServices getRegistrationServices() {
        return RetrofitClient.getClient(BASE_URL).create(RegistrationServices.class);
    }

    public static PassengerServices getPassengerServices() {
        return RetrofitClient.getClient(BASE_URL).create(PassengerServices.class);
    }

    public static DriverServices getDriverServices() {
        return RetrofitClient.getClient(BASE_URL).create(DriverServices.class);
    }

    public static UserPatchServices getUserPatchServices() {
        return RetrofitClient.getClient(BASE_URL).create(UserPatchServices.class);
    }
}
