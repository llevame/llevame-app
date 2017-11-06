package com.llevame_app_project;

import com.llevame_app_project.Data.LoginResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationListener implements Callback<LoginResponseData>{

    private Registrant registrant;
    private LoginResponseData response;

    public RegistrationListener(Registrant registrant){
        this.registrant = registrant;
    }

    @Override
    public void onResponse(Call<LoginResponseData> call, Response<LoginResponseData> response) {
        this.response = response.body();
        registrant.finishedRegistering(this.response);
    }

    @Override
    public void onFailure(Call<LoginResponseData> call, Throwable t) {
        registrant.finishedRegisteringWithError(t);
    }

}
