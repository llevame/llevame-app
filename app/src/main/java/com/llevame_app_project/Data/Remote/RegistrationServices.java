package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.DriverData;
import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PassengerData;

import java.sql.Driver;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mauro on 04/11/17.
 */

public interface RegistrationServices {

    @POST("api/v1/account/{userName}")
    Call<PassengerData> registerUser(@Path("userName") String userName,
                                  @Body PassengerData passengerData);
}
