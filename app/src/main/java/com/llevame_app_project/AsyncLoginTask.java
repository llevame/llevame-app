package com.llevame_app_project;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.llevame_app_project.LoginActivity;
class AsyncLoginTask extends AsyncTask<String, String, Boolean>{

    private LoginActivity loginActivity;

    public AsyncLoginTask(LoginActivity aLoginActivity){
        this.loginActivity = aLoginActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        return false;
    }

    @Override
    protected void onPostExecute(Boolean succesfullyConnection) {
        if(!succesfullyConnection)
            loginActivity.loginFinishedWithWrongCredentials();
        loginActivity.loginFinishedSuccessfully("aToken");
    }
}