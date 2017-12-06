package com.llevame_app_project.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.DriverServices;
import com.llevame_app_project.Data.Remote.PassengerServices;
import com.llevame_app_project.Data.UserData.BalanceData;
import com.llevame_app_project.Data.UserData.DriverData.DriverResponseData;
import com.llevame_app_project.Data.UserData.PassengerData.PassengerResponseData;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceActivity extends AppCompatActivity {

    Intent backToMainActivityIntent;

    private class BackButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(backToMainActivityIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        Bundle bundle = getIntent().getExtras();

        Button backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        backToMainActivityButton.setOnClickListener(new BackButtonListener());

        if(AppServerSession.getCurrentSession().isDriver()){
            setDriverScreen();
            asyncSetDriverBalance();
        }else{
            setPassengerScreen();
            asyncSetPassengerBalance();
        }

        if(bundle.containsKey("tripCost")){
            setLastTripStatus(bundle.getFloat("tripCost"));
        }else{
            hideLastTripStatus();
            hideBackToMainActivityButton();
        }
    }

    private void hideLastTripStatus(){
        TextView lastTripTag = findViewById(R.id.lastTripTagText);
        TextView lastTripCost = findViewById(R.id.lastTripCostText);
        lastTripCost.setVisibility(View.GONE);
        lastTripTag.setVisibility(View.GONE);
    }

    private void setDriverScreen(){
        TextView balanceTagText = findViewById(R.id.balanceTagText);
        balanceTagText.setText("Total revenue:");

        TextView lastTripTag = findViewById(R.id.lastTripTagText);
        lastTripTag.setText("Revenue of last trip:");

        Button backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        backToMainActivityButton.setText("Find new passengers");
        backToMainActivityIntent = new Intent(this, DriverActivity.class);
    }

    private void setPassengerScreen(){
        TextView balanceTagText = findViewById(R.id.balanceTagText);
        balanceTagText.setText("Total spent:");

        TextView lastTripTag = findViewById(R.id.lastTripTagText);
        lastTripTag.setText("Cost of the last trip:");

        Button backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        backToMainActivityButton.setText("Make a new trip");
        backToMainActivityIntent = new Intent(this, PassengerActivity.class);
    }

    private void setLastTripStatus(float cost){
        TextView lastTripCost = findViewById(R.id.lastTripCostText);
        String text = String.valueOf(cost).concat(" ").concat("ARS");
        lastTripCost.setText(text);
    }


    private class PassengerBalanceCallback implements Callback<PassengerResponseData> {


        @Override
        public void onResponse(Call<PassengerResponseData> call, Response<PassengerResponseData> response) {
            setBalance(response.body().getPassengerData().getBalance());
        }


        @Override
        public void onFailure(Call<PassengerResponseData> call, Throwable t) {

        }
    }

    private void asyncSetPassengerBalance(){
        PassengerServices service = ApiUtils.getPassengerServices();
        String bearerToken = AppServerSession.getCurrentSession().getBearerToken();
        service.getMyUser(bearerToken).enqueue(new PassengerBalanceCallback());
    }

    private void setBalance(BalanceData balanceData) {
        TextView balanceView = findViewById(R.id.balanceText);
        float balance = balanceData.getBalance();
        String currency = balanceData.getCurrency();
        String textToShow = String.valueOf(balance).concat(" ").concat(currency);
        balanceView.setText(textToShow);
    }

    private class DriverBalanceCallback implements Callback<DriverResponseData> {

        @Override
        public void onResponse(Call<DriverResponseData> call, Response<DriverResponseData> response) {
            setBalance(response.body().getDriverData().getBalance());
        }


        @Override
        public void onFailure(Call<DriverResponseData> call, Throwable t) {

        }
    }

    private void asyncSetDriverBalance(){
        DriverServices service = ApiUtils.getDriverServices();
        String bearerToken = AppServerSession.getCurrentSession().getBearerToken();
        service.getMyUser(bearerToken).enqueue(new DriverBalanceCallback());
    }

    void hideBackToMainActivityButton(){
        Button backToMainActivityButton = findViewById(R.id.backToMainActivityButton);
        backToMainActivityButton.setVisibility(View.GONE);
    }
}
