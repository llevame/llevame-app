package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.UserResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserServices {
    @GET("api/v1/users/{id}/profile")
    Call<UserResponseData> getUser(@Path("id") String user);
}
