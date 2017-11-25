package com.llevame_app_project.Activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.DriverServices;
import com.llevame_app_project.Data.Remote.PassengerServices;
import com.llevame_app_project.Data.UserData.LocationData.LocationData;
import com.llevame_app_project.Data.UserData.LocationData.TripResponseData;
import com.llevame_app_project.Data.UserData.LocationData.TripStatusData;
import com.llevame_app_project.FirebaseService;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverActivity extends AppCompatActivity {

    private class TripStatusCallback implements Callback<TripResponseData> {

        @Override
        public void onResponse(Call<TripResponseData> call, Response<TripResponseData>
                response) {
            if(response.isSuccessful()) {
                List<LocationData> trip = response.body().getTripStatus().getTrip();
            }
        }

        @Override
        public void onFailure(Call<TripResponseData> call, Throwable t) {

        }
    }

    private PolylineOptions createPolyLineFrom(List<LocationData> mainTrip) {
        PolylineOptions polyline = new PolylineOptions();
        for (LocationData location: mainTrip){
            polyline.add(
                    new LatLng(location.getLatitude(),
                            location.getLongitude())
            );
        }
        polyline.color(Color.GREEN);
        return polyline;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, FirebaseService.class);
        startService(intent);
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("Trip"));
        setContentView(R.layout.activity_driver);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    private void onFirebaseNotification(String tripId) {
        Toast.makeText(getApplicationContext(), "Someone wants to travel with you!",
                Toast.LENGTH_LONG).show();
        String bearerToken = AppServerSession.getCurrentSession().getBearerToken();
        DriverServices service = ApiUtils.getDriverServices();
        service.getTripStatus(tripId,bearerToken).enqueue(new TripStatusCallback());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_driver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_settings) {
            startActivity(new Intent(DriverActivity.this, ProfileActivity.class));
        }else if(id == R.id.car_settings){
            startActivity(new Intent(DriverActivity.this, CarSettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            onFirebaseNotification(intent.getStringExtra("tripId"));
        }
    };
}
