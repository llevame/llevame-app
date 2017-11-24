package com.llevame_app_project.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.PassengerServices;
import com.llevame_app_project.Data.UserData.LocationData.LocationData;
import com.llevame_app_project.Data.UserData.LocationData.TentativeTripData;
import com.llevame_app_project.Data.UserData.LocationData.TentativeTripDataResponse;
import com.llevame_app_project.Data.UserData.LocationData.TentativeTripStartEndData;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                updateMapWithTrip();
            }
        }
    }

    private void updateMapWithTrip(){
        PassengerServices service = ApiUtils.getPassengerServices();
        TentativeTripStartEndData startEnd = new TentativeTripStartEndData(originLatLng,
                destinyLatLng);
        service.getTentativeTrips(AppServerSession.getCurrentSession().getBearerToken(),
                startEnd).enqueue(new Callback<TentativeTripDataResponse>() {

            @Override
            public void onResponse(Call<TentativeTripDataResponse> call, Response<TentativeTripDataResponse> response) {
                if(response.isSuccessful()){
                    updateFragmentWith(response.body().getTentativeTripData());
                }
            }

            @Override
            public void onFailure(Call<TentativeTripDataResponse> call, Throwable t) {

            }
        });
    }

    ArrayList<PolylineOptions> trips = new ArrayList<PolylineOptions>();
    private boolean originPlaced = false;
    private boolean destinyPlaced = false;
    private LatLng originLatLng;
    private LatLng destinyLatLng;

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

    private void updateFragmentWith(TentativeTripData response){
        trips.clear();
        for(List<LocationData> trip: response.getTravels()){
            trips.add(createPolyLineFrom(trip));
        }
        currentGoogleMap.addPolyline(trips.get(0));
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_travel, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Soy la vista con el viaje a hacer");
        mMapView = rootView.findViewById(R.id.mapViewTravel);
        mMapView.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            setMapConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.onPause();
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
    public void onResume() {
        super.onResume();

    }

    public void resumeMap(){
        mMapView.onResume();
    }

    public void stopMap(){
        if(mMapView != null) {
            mMapView.onStop();
        }
    }

}