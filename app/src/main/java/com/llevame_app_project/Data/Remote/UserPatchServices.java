package com.llevame_app_project.Data.Remote;


import android.location.Location;

import com.llevame_app_project.Data.LocationData;
import com.llevame_app_project.Data.LocationForServerData;
import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.PatchResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface UserPatchServices {
    @PATCH("api/v1/account/me")
    Call<PatchResponseData> notifyLocation(@Header("Authorization") String BearerPlusToken,
                                           @Body LocationForServerData location);
}
