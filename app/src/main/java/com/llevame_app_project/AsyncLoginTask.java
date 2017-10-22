package com.llevame_app_project;
import android.os.AsyncTask;
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
    protected void onPostExecute(Boolean successfullyConnection) {
        if(!successfullyConnection)
            loginActivity.loginFinishedWithWrongCredentials();
        loginActivity.loginFinishedSuccessfully("aToken");
    }
}