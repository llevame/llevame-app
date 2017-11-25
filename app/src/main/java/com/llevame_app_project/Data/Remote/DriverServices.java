package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.UserData.DriverData.DriverResponseData;
import com.llevame_app_project.Data.UserData.LocationData.TripStatusData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DriverServices {
    @GET("api/v1/drivers/{id}/profile")
    Call<DriverResponseData> getUser(@Path("id") String user,
                                     @Header("Authorization") String BearerPlusToken);

    @GET("api/v1/trips/{tripId}")
    Call<TripStatusData> getTripStatus(@Path("id") String id,
                                       @Header("Authorization") String BearerPlusToken);
}
