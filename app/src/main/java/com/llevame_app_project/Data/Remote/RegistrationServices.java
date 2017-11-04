package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.ResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mauro on 04/11/17.
 */

public interface RegistrationServices {

    @POST("api/v1/account/{userName}")
    Call<ResponseData> registerUser(@Path("userName") String userName,
                                    @Body PassengerData passengerData);
}
