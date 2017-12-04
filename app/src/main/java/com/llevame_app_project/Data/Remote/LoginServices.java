package com.llevame_app_project.Data.Remote;

import com.llevame_app_project.Data.UserData.ResponseData;
import com.llevame_app_project.Data.UserData.SessionData.FacebookLoginData;
import com.llevame_app_project.Data.UserData.SessionData.LoginResponseData;
import com.llevame_app_project.Data.UserData.SessionData.PasswordData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface LoginServices {

    @PATCH("api/v1/account/{userName}")
    Call<LoginResponseData> loginUser(@Path("userName") String userName,
                                      @Body PasswordData password);

    @GET("api/v1/facebookUsers")
    Call<ResponseData> loginFacebookUser(@Body FacebookLoginData loginData);
}
