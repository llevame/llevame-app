package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.LoginResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface LoginServices {
    @FormUrlEncoded
    @PATCH("api/v1/account/{userName}")
    Call<LoginResponseData> loginUser(@Path("userName") String userName,
                                      @Field("password") String password);
}
