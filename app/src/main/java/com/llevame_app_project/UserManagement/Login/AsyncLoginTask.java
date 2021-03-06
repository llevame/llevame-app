package com.llevame_app_project.UserManagement.Login;

import android.os.AsyncTask;

import com.llevame_app_project.Activities.LoginActivity;
import com.llevame_app_project.Data.UserData.SessionData.LoginResponseData;
import com.llevame_app_project.Data.UserData.SessionData.PasswordData;
import com.llevame_app_project.Data.Remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsyncLoginTask extends AsyncTask<String, String, Void>{

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
        PasswordData password = new PasswordData();
        password.setPassword(params[1]);
        userName = params[0];
        ApiUtils.getLoginServices().loginUser(userName,password).enqueue(
                new Callback<LoginResponseData>() {

                    @Override
                    public void onResponse(Call<LoginResponseData> call, Response<LoginResponseData> response) {
                        if(response.isSuccessful()) {
                            successfullyConnectionWithServer = true;
                            responseData = response.body();
                        }else{
                            successfullyConnectionWithServer = false;
                        }

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
        for(int i = 0; i < 3; i++) {
            if (!successfullyConnectionWithServer)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
            loginActivity.loginFinishedSuccessfully(responseData.getLoginData().getToken(),userName,
                    responseData.getLoginData().isDriver());
    }
}