package com.llevame_app_project;

import com.llevame_app_project.Data.Remote.ApiUtils;
import org.junit.Test;
import com.llevame_app_project.Data.*;
import com.llevame_app_project.Data.Remote.LoginServices;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

public class LoginServicesTest {
    @Test
    public void thereIsAnErrorTryingToLoginANonExistentUser() throws InterruptedException {
        LoginServices userServices = ApiUtils.getUserServices();
        //This user can't exist in our database, symbols are not allowed.
        System.out.println("hola");
        userServices.getUser("123").enqueue(
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
