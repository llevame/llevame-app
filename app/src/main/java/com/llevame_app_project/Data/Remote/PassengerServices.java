package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.PassengerResponseData;
import com.llevame_app_project.Data.UserData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface PassengerServices {
    //TO DO: Update it to work with the new server
    //It must send a token.
    @GET("api/v1/users/{id}/profile")
    Call<PassengerResponseData> getUser(@Path("id") String user,
                                        @Header("Authorization") String BearerPlusToken);
}
