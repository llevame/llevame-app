package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.LoginResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LoginServices {
    @GET("api/v1/account/{userName}")
    Call<LoginResponseData> getUser(@Path("userName") String userName,
                                    @Header("Password") String password);
}
