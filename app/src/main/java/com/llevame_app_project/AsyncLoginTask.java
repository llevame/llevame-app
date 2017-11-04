package com.llevame_app_project;
import android.os.AsyncTask;

import com.llevame_app_project.Data.LoginResponseData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

class AsyncLoginTask extends AsyncTask<String, String, Void>{

    private LoginActivity loginActivity;
    private String userName;
    private String password;
    private boolean successfullyConnectionWithServer;
    private LoginResponseData responseData;

    public AsyncLoginTask(LoginActivity aLoginActivity){
        this.loginActivity = aLoginActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        successfullyConnectionWithServer = false;
        ApiUtils.getLoginServices().loginUser(params[0],params[1]).enqueue(
                new Callback<LoginResponseData>() {

                    @Override
                    public void onResponse(Call<LoginResponseData> call, Response<LoginResponseData> response) {
                        successfullyConnectionWithServer = true;
                        responseData = response.body();

                    }

                    @Override
                    public void onFailure(Call<LoginResponseData> call, Throwable t) {
                        successfullyConnectionWithServer = false;
                    }
                }
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!successfullyConnectionWithServer)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        if(!successfullyConnectionWithServer) {
            loginActivity.loginFinishedWithAnError("Server couldn't be reached");
            return;
        }
        if(!responseData.getSuccess())
            loginActivity.loginFinishedWithAnError(responseData.getError().getDescription());
        else
            loginActivity.loginFinishedSuccessfully(responseData.getLoginData().getToken(),
                    responseData.getLoginData().isDriver());
    }
}