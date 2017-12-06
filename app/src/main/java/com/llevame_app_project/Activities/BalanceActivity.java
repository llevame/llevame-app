package com.llevame_app_project.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.DriverServices;
import com.llevame_app_project.Data.Remote.PassengerServices;
import com.llevame_app_project.Data.UserData.BalanceData;
import com.llevame_app_project.Data.UserData.PassengerData.PassengerResponseData;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        Bundle bundle = getIntent().getExtras();

        if(AppServerSession.getCurrentSession().isDriver()){
            setDriverScreen();
            //asyncSetDriverBalance();
        }else{
            setPassengerScreen();
            asyncSetPassengerBalance();
        }

        if(bundle.containsKey("tripCost")){
            setLastTripStatus(bundle.getFloat("tripCost"));
        }else{
            hideLastTripStatus();
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
    }

    private void setPassengerScreen(){
        TextView balanceTagText = findViewById(R.id.balanceTagText);
        balanceTagText.setText("Total spent:");

        TextView lastTripTag = findViewById(R.id.lastTripTagText);
        lastTripTag.setText("Cost of the last trip:");
    }

    private void setLastTripStatus(float cost){
        TextView lastTripCost = findViewById(R.id.lastTripCostText);
        lastTripCost.setText(String.valueOf(cost));
    }


    private class PassengerBalanceCallback implements Callback<PassengerResponseData> {


        @Override
        public void onResponse(Call<PassengerResponseData> call, Response<PassengerResponseData> response) {
            setPassengerBalance(response.body().getPassengerData().getBalance());
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

    private void setPassengerBalance(BalanceData balanceData) {
        TextView balanceView = findViewById(R.id.balanceText);
        float balance = balanceData.getBalance();
        String currency = balanceData.getCurrency();
        String textToShow = String.valueOf(balance) + " " + currency;
        balanceView.setText(textToShow);
    }
}
