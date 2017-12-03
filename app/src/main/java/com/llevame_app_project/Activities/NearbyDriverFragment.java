package com.llevame_app_project.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.PassengerServices;
import com.llevame_app_project.Data.UserData.DriverData.DriverData;
import com.llevame_app_project.Data.UserData.DriverData.NearbyDriversResponseData;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.Manifest;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import bolts.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyDriverFragment extends Fragment {

    private class MapUpdateTask extends TimerTask {

        @Override
        public void run() {
            updateMap();
        }
    }

    private class RetrofitListener implements Callback<NearbyDriversResponseData> {

        @Override
        public void onResponse(Call<NearbyDriversResponseData> call,
                               Response<NearbyDriversResponseData> response) {
            if(response.body().getNearbyDrivers() != null){
                updateMapWith(response.body().getNearbyDrivers());
            }
        }

        @Override
        public void onFailure(Call<NearbyDriversResponseData> call, Throwable t) {

        }
    }

    private class DriverMarkerListener implements GoogleMap.OnInfoWindowClickListener{

        @Override
        public void onInfoWindowClick(Marker marker) {
            selectedDriverUsername =(String) marker.getTag();
            Log.i("Marker:", "Selected: " + selectedDriverUsername);
            if(observer != null)
                observer.notifyObserver();
        }

    }

    View rootView;
    private String selectedDriverUsername;
    private AppObserver observer;
    private GoogleMap currentGoogleMap;
    Timer timer = new Timer();
    public void setObserver(AppObserver observer){
        this.observer = observer;
    }

    public void updateMapWith(final List<DriverData> nearbyDrivers){

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                currentGoogleMap = googleMap;
                currentGoogleMap.clear();
                BitmapDescriptor icon =
                        BitmapDescriptorFactory.fromResource(
                                R.drawable.motorcycle
                        );
                for (DriverData driver : nearbyDrivers) {

                    if(driver.getLocation() != null) {
                        Log.i("UpdatingMap:", "Driver:" + driver.getLastName());

                        LatLng position = new LatLng(driver.getLocation().getLatitude(),
                                driver.getLocation().getLongitude());

                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(position)
                                .title(driver.getFirstName() + " " + driver.getLastName())
                                .snippet(makeDriverSnippet(driver))
                                .icon(icon)
                        );
                        marker.setTag(driver.getEmail());
                    }
                }
            }

            String makeDriverSnippet(DriverData driver){
                return(
                    driver.getCar().getModel() + "\n" +
                    driver.getCar().getPatent() + "\n" +
                    driver.getCar().getYear() + "\n" +
                    driver.getCar().getColor()
                    );
            }
        });
    }

    public void updateMap(){
        PassengerServices services = ApiUtils.getPassengerServices();
        services.getNearbyDrivers(AppServerSession.getCurrentSession().getBearerToken())
                .enqueue(new RetrofitListener());
    }

    MapView mMapView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_nearby_driver, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // For showing a move to my location button
                //TO DO: Check if the positioning services are enabled, asn ask to enable them
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }
                setMapMarkerInfoLayout(googleMap);
                googleMap.setOnInfoWindowClickListener(new DriverMarkerListener());
                currentGoogleMap = googleMap;
            }
        });

        timer.scheduleAtFixedRate(new MapUpdateTask(), 0, 5000);
        mMapView.onResume();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    void setMapMarkerInfoLayout(GoogleMap googleMap){
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(rootView.getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(rootView.getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                title.setTextSize(26);

                TextView snippet = new TextView(rootView.getContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());
                snippet.setTextSize(22);

                Button selectDriver  = new Button(rootView.getContext());
                selectDriver.setText("Select");

                info.addView(title);
                info.addView(snippet);
                info.addView(selectDriver);
                return info;
            }
        });
    }

    public String getSelectedDriverUsername(){
        return selectedDriverUsername;
    }

    public CameraPosition getCameraPosition(){
        if(currentGoogleMap == null)
            return null;
        return currentGoogleMap.getCameraPosition();
    }


    public void setCurrentPosition(CameraPosition cameraPosition){
        if(currentGoogleMap != null)
            currentGoogleMap.moveCamera(
                    CameraUpdateFactory.newCameraPosition(cameraPosition)
            );
    }

    public void resumeMap(){
        if(mMapView != null) {
            mMapView.onResume();
        }
    }

    public void stopMap(){
        if(mMapView != null) {
            mMapView.onStop();
        }
    }
}