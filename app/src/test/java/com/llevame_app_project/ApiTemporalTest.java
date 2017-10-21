package com.llevame_app_project;

import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.UserServices;
import org.junit.Test;
import com.llevame_app_project.Data.*;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

import static java.lang.Thread.sleep;

public class ApiTemporalTest {
    @Test
    public void primerTestApi() throws InterruptedException {
        UserServices userServices = ApiUtils.getUserServices();
        System.out.println(userServices.getUser("59dee3b2734d1d76fed7fbf9").
                request().url().toString());
        userServices.getUser("59dee3b2734d1d76fed7fbf9").enqueue(new Callback<UserResponseData>() {
            @Override
            public void onResponse(Call<UserResponseData> call, Response<UserResponseData> response) {
                System.out.println("Hola");

                if (response.isSuccessful()) {
                    System.out.println("Soy: ");
                    System.out.println(response.body().getUserData().getLastname());
                    assert (response.body().getUserData().getId() == "59def48f734d1d76fed802e0");
                } else {
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<UserResponseData> call, Throwable t) {
                assert(false);
            }
        });
        sleep(10000);
    }
}
