package com.llevame_app_project.Data.Remote;


import android.location.Location;

import com.llevame_app_project.Data.LoginResponseData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface UserPatchServices {
    @PATCH("api/v1/account/me")
    Call<LoginResponseData> notifyLocation(@Header("Authorization") String BearerPlusToken,
                                           @Body Location location);
}
