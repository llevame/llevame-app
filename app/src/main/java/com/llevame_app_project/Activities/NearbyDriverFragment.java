package com.llevame_app_project.Activities;

import android.app.Service;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.PassengerServices;
import com.llevame_app_project.Data.UserData.DriverData.DriverData;
import com.llevame_app_project.Data.UserData.DriverData.NearbyDriversResponseData;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.Manifest;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbyDriverFragment extends Fragment {

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


    public void updateMapWith(List<DriverData> nearbyDrivers){
        for (DriverData driver : nearbyDrivers) {
            Log.i("UpdatingMap:", "Driver:" + driver.getLastName());
        }
    }

    public void updateMap(){
        PassengerServices services = ApiUtils.getPassengerServices();
        services.getNearbyDrivers(AppServerSession.getCurrentSession().getBearerToken())
        .enqueue(new RetrofitListener());
    }

    MapView mMapView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nearby_driver, container, false);

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

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
            }
        });

        updateMap();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
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
}