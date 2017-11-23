package com.llevame_app_project.Data.Remote;


import com.llevame_app_project.Data.UserData.FirebaseData.FirebaseTokenData;
import com.llevame_app_project.Data.UserData.LocationData.LocationForServerData;
import com.llevame_app_project.Data.UserData.ResponseData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;

public interface UserPatchServices {
    @PATCH("api/v1/account/me")
    Call<ResponseData> notifyLocation(@Header("Authorization") String BearerPlusToken,
                                      @Body LocationForServerData location);

    @PATCH("api/v1/account/me")
    Call<ResponseData> notifyFirebaseToken(@Header("Authorization") String BearerPlusToken,
                                           @Body FirebaseTokenData deviceId);

}
