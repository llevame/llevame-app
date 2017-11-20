package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.UserData.DriverData.NearbyDriversResponseData;
import com.llevame_app_project.Data.UserData.PassengerData.PassengerResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PassengerServices {
    @GET("api/v1/users/{id}/profile")
    Call<PassengerResponseData> getUser(@Path("id") String user,
                                        @Header("Authorization") String BearerPlusToken);
    @GET("api/v1/drivers")
    Call<NearbyDriversResponseData> getNearbyDrivers(
            @Header("Authorization") String BearerPlusToken);
}
