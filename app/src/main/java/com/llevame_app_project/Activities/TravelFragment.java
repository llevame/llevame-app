package com.llevame_app_project.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.llevame_app_project.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TravelFragment extends Fragment {

    public TravelFragment() {
    }
    private MapView mMapView;


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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }
}