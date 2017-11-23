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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.llevame_app_project.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TravelFragment extends Fragment {

    private MapView mMapView;
    private GoogleMap currentGoogleMap;

    private class MapClickListener implements GoogleMap.OnMapClickListener{
        @Override
        public void onMapClick(LatLng latLng) {
            if(!originPlaced) {
                currentGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("origin"));
                originPlaced=true;
                originLatLng = latLng;
            }else if(!destinyPlaced){
                currentGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("destiny"));
                destinyPlaced=true;
                destinyLatLng = latLng;
            }

        }
    }

    private boolean originPlaced = false;
    private boolean destinyPlaced = false;
    private LatLng originLatLng;
    private LatLng destinyLatLng;

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
                googleMap.setOnMapClickListener(new MapClickListener());
            }
        });
    }

    public CameraPosition getCameraPosition(){
        if(currentGoogleMap == null)
            return null;
        return currentGoogleMap.getCameraPosition();
    }


    public void setCurrentPosition(CameraPosition cameraPosition){
        if(currentGoogleMap != null && cameraPosition != null)
            currentGoogleMap.moveCamera(
                    CameraUpdateFactory.newCameraPosition(cameraPosition)
            );
    }


}