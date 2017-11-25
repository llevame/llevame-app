package com.llevame_app_project.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
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
    private TextView textTripSelect;
    private int currentTrip;
    private Polyline currentTripPolyline;
    private TextView costText;
    ArrayList<PolylineOptions> trips = new ArrayList<PolylineOptions>();
    List<List<LocationData>> tripsLocationData;
    private boolean originPlaced = false;
    private boolean destinyPlaced = false;
    private LatLng originLatLng;
    private LatLng destinyLatLng;
    private AppListenerInterface observer;

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

    private class PreviousTripButtonClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if(trips.size() != 0){
                currentTripPolyline.remove();
                if(currentTrip == 1){
                    currentTrip = trips.size();
                }else{
                    currentTrip--;
                }
                //-1, arrayList starts counting from 0
                currentTripPolyline =
                        currentGoogleMap.addPolyline(trips.get(currentTrip - 1));
                textTripSelect.setText("Trip ("+ currentTrip +"/" + trips.size() +")");
            }

        }
    }

    private class NextTripButtonClickListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if(trips.size() != 0){
                currentTripPolyline.remove();
                if(currentTrip == trips.size()){
                    currentTrip = 1;
                }else{
                    currentTrip++;
                }
                //-1, arrayList starts counting from 0
                currentTripPolyline =
                        currentGoogleMap.addPolyline(trips.get(currentTrip - 1));
                textTripSelect.setText("Trip ("+ currentTrip +"/" + trips.size() +")");
            }

        }
    }

    private class DeleteTripButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            trips.clear();
            originPlaced = false;
            destinyPlaced = false;
            currentGoogleMap.clear();
            textTripSelect.setText("Trips");
            costText.setText("0");
        }
    }

    private class StartTripButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            observer.notifyObserver();
        }
    }

    private void updateMapWithTrip(){
        PassengerServices service = ApiUtils.getPassengerServices();
        TentativeTripStartEndData startEnd = new TentativeTripStartEndData(originLatLng,
                destinyLatLng);
        service.getTentativeTrips(AppServerSession.getCurrentSession().getBearerToken(),
                startEnd).enqueue(new Callback<TentativeTripDataResponse>() {

            @SuppressLint("SetTextI18n")
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
        costText.setText(String.valueOf(response.getCost()));
        trips.clear();
        tripsLocationData = response.getTravels();
        for(List<LocationData> trip: response.getTravels()){
            trips.add(createPolyLineFrom(trip));
        }
        currentTripPolyline = currentGoogleMap.addPolyline(trips.get(0));
        textTripSelect.setText("Trip (1/" + trips.size() +")");
        currentTrip = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_travel, container, false);
        textTripSelect = rootView.findViewById(R.id.trip_selection_label);
        ImageButton previousTripButton = rootView.findViewById(R.id.button_previous_trip);
        ImageButton nextTripButton = rootView.findViewById(R.id.button_next_trip);
        previousTripButton.setOnClickListener(new PreviousTripButtonClickListener());
        nextTripButton.setOnClickListener(new NextTripButtonClickListener());
        ImageButton deleteTripButton = rootView.findViewById(R.id.button_delete_travels);
        deleteTripButton.setOnClickListener(new DeleteTripButtonListener());
        Button startTrip = rootView.findViewById(R.id.button_start_trip);
        startTrip.setOnClickListener(new StartTripButtonListener());
        costText = rootView.findViewById(R.id.trip_cost_label);
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

    public void setObserver(AppListenerInterface observer){
        this.observer = observer;
    }

    public List<LocationData> getTripSelected(){
        return tripsLocationData.get(currentTrip-1);
    }

}