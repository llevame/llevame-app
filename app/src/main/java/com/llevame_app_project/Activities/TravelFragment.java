package com.llevame_app_project.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.llevame_app_project.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TravelFragment extends Fragment {

    public TravelFragment() {
    }
    private MapView mMapView;
    private GoogleMap currentGoogleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_travel, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Soy la vista con el viaje a hacer");
        mMapView = rootView.findViewById(R.id.mapViewTravel);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            setMapConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void setCurrentPosition(CameraPosition cameraPosition){
        if(currentGoogleMap != null)
            currentGoogleMap.moveCamera(
                    CameraUpdateFactory.newCameraPosition(cameraPosition)
            );
    }

    private void setMapConfig(){
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // For showing a move to my location button
                //TO DO: Check if the positioning services are enabled, asn ask to enable them
                currentGoogleMap = googleMap;
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                }
            }
        });
    }
}