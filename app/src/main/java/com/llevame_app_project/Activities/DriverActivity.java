package com.llevame_app_project.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.DriverServices;
import com.llevame_app_project.Data.UserData.LocationData.LocationData;
import com.llevame_app_project.Data.UserData.LocationData.StatusData;
import com.llevame_app_project.Data.UserData.LocationData.TripPatchResponseData;
import com.llevame_app_project.Data.UserData.LocationData.TripResponseData;
import com.llevame_app_project.FirebaseService;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LocationService.LocationOnServerUpdater;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.llevame_app_project.Data.UserData.LocationData.StatusData.ACCEPTED;
import static com.llevame_app_project.Data.UserData.LocationData.StatusData.STARTED;


public class DriverActivity extends AppCompatActivity {

    private GoogleMap googleMap;
    private int amountOfPolylines = 0;
    ArrayList<Polyline> tripsPolyline = new ArrayList<>();
    ArrayList<Marker> tripsMarkers = new ArrayList<>();
    private boolean keepsAcceptingTrips = true;
    private String acceptedTripId;
    private Button startTripButton;
    private ImageButton openChatButton;
    private Button tripEndedBUtton;

    private class TripStatusCallback implements Callback<TripResponseData> {

        @Override
        public void onResponse(Call<TripResponseData> call, Response<TripResponseData>
                response) {
            if (response.isSuccessful() && response.body().getSuccess()) {
                List<LocationData> trip = response.body().getTripStatus().getTrip();
                Polyline thisTripPolyline;
                String userName = response.body().getTripStatus().getPassenger();
                if(isFacebookUserName(userName))
                    userName = makePretty(userName);

                String tripId = response.body().getTripStatus().getId();
                thisTripPolyline = googleMap.addPolyline(createPolyLineFrom(trip));
                thisTripPolyline.setTag(tripId);
                tripsPolyline.add(thisTripPolyline);

                Marker marker =
                        googleMap.addMarker(createTripOriginMarker(trip,userName));
                marker.setTag(tripId);
                tripsMarkers.add(marker);
            }
        }

        private MarkerOptions createTripOriginMarker(List<LocationData> trip,
                                                     String passenger){
            MarkerOptions origin = new MarkerOptions();
            origin.title("Starting point");
            origin.snippet("Passenger: \n " + passenger);
            LatLng originPosition = new LatLng(trip.get(0).getLatitude(),
                    trip.get(0).getLongitude());
            origin.position(originPosition);
            return origin;
        }

        @Override
        public void onFailure(Call<TripResponseData> call, Throwable t) {

        }

        boolean isFacebookUserName(String userName){
            return(!userName.contains("@"));
        }

        String makePretty(String username){
            return username.replaceAll("[^A-Za-z]","");
        }
    }

    private class TripAcceptedCallback implements Callback<TripPatchResponseData>{

        @Override
        public void onResponse(Call<TripPatchResponseData> call, Response<TripPatchResponseData> response) {
            String tripId = response.body().getTripCreationData().getTripId();
            updatePolylines(tripId);
            updateMarkers(tripId);
            keepsAcceptingTrips = false;
            acceptedTripId = tripId;
            startTripButton.setVisibility(Button.VISIBLE);
            openChatButton.setVisibility(Button.VISIBLE);
        }

        private void updatePolylines(String tripId){
            ArrayList<Polyline> newTripsPolyline = new ArrayList();
            for(Polyline polyline: tripsPolyline){
                String polyId = (String) polyline.getTag();
                if(!polyId.equals(tripId)){
                    polyline.remove();
                }else{
                    newTripsPolyline.add(polyline);
                }
            }
            tripsPolyline = newTripsPolyline;
        }

        private void updateMarkers(String tripId){

            ArrayList<Marker> newTripsMarkers = new ArrayList();
            for(Marker marker: tripsMarkers){
                String markerId = (String) marker.getTag();
                if(!markerId.equals(tripId)){
                    marker.remove();
                }else{
                    newTripsMarkers.add(marker);
                }
            }
            tripsMarkers = newTripsMarkers;
            setMapMarkerInfoLayoutAccepted(googleMap);
        }

        @Override
        public void onFailure(Call<TripPatchResponseData> call, Throwable t) {

        }
    }

    private class TripStartedCallback implements Callback<TripPatchResponseData>{

        @Override
        public void onResponse(Call<TripPatchResponseData> call, Response<TripPatchResponseData> response) {
            startTripButton.setVisibility(Button.GONE);
            tripEndedBUtton.setVisibility(Button.VISIBLE);
            LocationOnServerUpdater.getInstance().tripStarted(acceptedTripId);
        }

        private void updatePolylines(String tripId){
            ArrayList<Polyline> newTripsPolyline = new ArrayList();
            for(Polyline polyline: tripsPolyline){
                String polyId = (String) polyline.getTag();
                if(!polyId.equals(tripId)){
                    polyline.remove();
                }else{
                    newTripsPolyline.add(polyline);
                }
            }
            tripsPolyline = newTripsPolyline;
        }

        private void updateMarkers(String tripId){

            ArrayList<Marker> newTripsMarkers = new ArrayList();
            for(Marker marker: tripsMarkers){
                String markerId = (String) marker.getTag();
                if(!markerId.equals(tripId)){
                    marker.remove();
                }else{
                    newTripsMarkers.add(marker);
                }
            }
            tripsMarkers = newTripsMarkers;
            setMapMarkerInfoLayoutAccepted(googleMap);
        }

        @Override
        public void onFailure(Call<TripPatchResponseData> call, Throwable t) {

        }
    }

    private class MapReadyCallback implements OnMapReadyCallback{

        @Override
        public void onMapReady(GoogleMap pGoogleMap) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                pGoogleMap.setMyLocationEnabled(true);
            }
            googleMap = pGoogleMap;
            setMapMarkerInfoLayout(googleMap);
            googleMap.setOnInfoWindowClickListener(new AcceptTripListener());
        }

    }

    private class AcceptTripListener implements GoogleMap.OnInfoWindowClickListener{
        @Override
        public void onInfoWindowClick(Marker marker) {
            DriverServices service = ApiUtils.getDriverServices();
            String tripId = (String) marker.getTag();
            StatusData status = new StatusData(ACCEPTED);
            String bearerToken = AppServerSession.getCurrentSession().getBearerToken();
            service.patchTripStatus(tripId, bearerToken,status).
                    enqueue(new TripAcceptedCallback());
        }
    }

    private class StartTripButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            DriverServices service = ApiUtils.getDriverServices();
            StatusData status = new StatusData(STARTED);
            String bearerToken = AppServerSession.getCurrentSession().getBearerToken();
            service.patchTripStatus(acceptedTripId, bearerToken,status).
                    enqueue(new TripStartedCallback());
        }
    }

    private class StartChatButtonListener implements ImageButton.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DriverActivity.this,
                    ChatActivity.class);
            intent.putExtra("tripId", acceptedTripId);
            startActivity(intent);
        }
    }

    private class EndTripButtonListener implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            DriverServices service = ApiUtils.getDriverServices();
            StatusData status = new StatusData(StatusData.ENDED);
            String bearerToken = AppServerSession.getCurrentSession().getBearerToken();
            service.patchTripStatus(acceptedTripId, bearerToken,status).
                    enqueue(new TripFinishedCallback());
        }
    }

    private class TripFinishedCallback implements Callback<TripPatchResponseData>{

        @Override
        public void onResponse(Call<TripPatchResponseData> call, Response<TripPatchResponseData> response) {
            tripEndedBUtton.setVisibility(View.GONE);
            openChatButton.setVisibility(View.GONE);
            Toast.makeText(getBaseContext(),"Trip has ended successfully",
                    Toast.LENGTH_LONG)
            .show();
            keepsAcceptingTrips = true;
            googleMap.clear();
        }

        @Override
        public void onFailure(Call<TripPatchResponseData> call, Throwable t) {

        }
    }

    private int getColorNumber(int i){
        switch (i){
            case 1:  return(Color.GREEN);
            case 2:  return(Color.BLUE);
            case 3:  return(Color.YELLOW);
            case 4:  return(Color.MAGENTA);
            case 5:  return(Color.CYAN);
        }
        return Color.GREEN;
    }

    private PolylineOptions createPolyLineFrom(List<LocationData> mainTrip) {
        PolylineOptions polyline = new PolylineOptions();
        amountOfPolylines++;
        for (LocationData location: mainTrip){
            polyline.add(
                    new LatLng(location.getLatitude(),
                            location.getLongitude())
            );
        }
        polyline.color(getColorNumber(amountOfPolylines));
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
        MapView mMapView = findViewById(R.id.driverMapView);
        mMapView.onCreate(savedInstanceState);

        openChatButton = findViewById(R.id.open_chat);
        openChatButton.setOnClickListener(new StartChatButtonListener());

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new MapReadyCallback());
        mMapView.onResume();
        startTripButton = findViewById(R.id.button_start_trip);
        startTripButton.setOnClickListener(new StartTripButtonListener());
        openChatButton = findViewById(R.id.open_chat);

        tripEndedBUtton = findViewById(R.id.button_end_trip);
        tripEndedBUtton.setOnClickListener(new EndTripButtonListener());
    }

    private void onNewTrip(String tripId) {
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
        if (intent.getStringExtra("type").equals("1") && keepsAcceptingTrips)
            onNewTrip(intent.getStringExtra("tripId"));
        }
    };

    void setMapMarkerInfoLayout(GoogleMap googleMap){
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getBaseContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getBaseContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                title.setTextSize(26);

                TextView snippet = new TextView(getBaseContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());
                snippet.setTextSize(22);

                Button selectTrip  = new Button(getBaseContext());
                selectTrip.setText("Accept trip");

                info.addView(title);
                info.addView(snippet);
                info.addView(selectTrip);
                return info;
            }
        });
    }
    void setMapMarkerInfoLayoutAccepted(GoogleMap googleMap){
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getBaseContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getBaseContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                title.setTextSize(26);

                TextView snippet = new TextView(getBaseContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());
                snippet.setTextSize(22);

                info.addView(title);
                info.addView(snippet);
                return info;
            }
        });
    }
}
