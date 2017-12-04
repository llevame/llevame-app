package com.llevame_app_project.Data.Remote;
import com.llevame_app_project.Data.UserData.DriverData.DriverData;
import com.llevame_app_project.Data.UserData.ResponseData;
import com.llevame_app_project.Data.UserData.SessionData.FacebookLoginData;
import com.llevame_app_project.Data.UserData.SessionData.LoginResponseData;
import com.llevame_app_project.Data.UserData.PassengerData.PassengerData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mauro on 04/11/17.
 */

public interface RegistrationServices {

    @POST("api/v1/account/{userName}")
    Call<LoginResponseData> registerUser(@Path("userName") String userName,
                                         @Body PassengerData passengerData);

    @POST("api/v1/account/{userName}")
    Call<LoginResponseData> registerUser(@Path("userName") String userName,
                                         @Body DriverData driverData);
}
