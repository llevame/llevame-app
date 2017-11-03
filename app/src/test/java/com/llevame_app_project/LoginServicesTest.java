package com.llevame_app_project;

import com.llevame_app_project.Data.Remote.ApiUtils;
import org.junit.Test;
import com.llevame_app_project.Data.*;
import com.llevame_app_project.Data.Remote.LoginServices;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

public class LoginServicesTest {
    @Test
    public void thereIsAnErrorTryingToLoginANonExistentUser() throws InterruptedException {
        LoginServices userServices = ApiUtils.getLoginServices();
        //This user doesn't exist in our database.
        userServices.loginUser("123","aPassword").enqueue(
            new Callback<LoginResponseData>() {

                @Override
                public void onResponse(Call<LoginResponseData> call, Response<LoginResponseData> response) {
                   assertFalse(response.body().getSuccess());
                }

                @Override
                public void onFailure(Call<LoginResponseData> call, Throwable t) {
                    fail();
                }
            }
        );
        sleep(1500);
    }
}
