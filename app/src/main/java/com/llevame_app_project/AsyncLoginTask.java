package com.llevame_app_project;
import android.os.AsyncTask;
import retrofit2.http.GET;

class AsyncLoginTask extends AsyncTask<String, String, String>{

    @Override
    protected void onPreExecute() {
        //Setup precondition to execute some task
    }

    @Override
    protected String doInBackground(String... params) {
        //@GET("https://safe-savannah-49286.herokuapp.com/api/v1/users")

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        //Show the result obtained from doInBackground
    }
}