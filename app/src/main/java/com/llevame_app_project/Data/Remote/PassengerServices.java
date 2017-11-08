package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.PassengerData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PassengerServices {
    //TO DO: Update it to work with the new server
    //It must send a token.
    @GET("api/v1/users/{id}/profile")
    Call<PassengerData> getUser(@Path("id") String user);
}
