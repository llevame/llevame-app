package com.llevame_app_project.Activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.CameraPosition;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.PassengerServices;
import com.llevame_app_project.Data.UserData.LocationData.LocationData;
import com.llevame_app_project.Data.UserData.LocationData.TripIdResponseData;
import com.llevame_app_project.Data.UserData.LocationData.TripToCreateData;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*TO DO: Work around to fix google map crash

 */

public class PassengerActivity extends AppCompatActivity{

    private class StartChatButtonListener implements ImageButton.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PassengerActivity.this,
                    ChatActivity.class);
            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
    }

    static final int  NUM_PAGE_NEARBY_DRIVER = 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private class PageChangeListener implements OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        /*
        Stop and resume, part of a workaround to fix
        googlemap crash with two running at the same time.
        Issue post of google maps:
        https://issuetracker.google.com/issues/35822688#c34
        */
        public void onPageSelected(int position) {
            CameraPosition camPosition;
            if(position == NUM_PAGE_NEARBY_DRIVER){
                travelFragment.stopMap();
                nearbyDriverFragment.resumeMap();
                camPosition = travelFragment.getCameraPosition();
                nearbyDriverFragment.setCurrentPosition(camPosition);
            }else{
                travelFragment.resumeMap();
                nearbyDriverFragment.stopMap();
                camPosition = nearbyDriverFragment.getCameraPosition();
                travelFragment.setCurrentPosition(camPosition);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class StartTripCallback implements Callback<TripIdResponseData> {
        @Override
        public void onResponse(Call<TripIdResponseData> call, Response<TripIdResponseData> response) {
            Toast.makeText(PassengerActivity.this,
                    "Now waiting for the driver!", Toast.LENGTH_LONG).show();
            tripId = response.body().getTripCreationData().getTripId();
        }

        @Override
        public void onFailure(Call<TripIdResponseData> call, Throwable t) {

        }
    }

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private NearbyDriverFragment nearbyDriverFragment;
    private TravelFragment travelFragment;
    private DriverSelectedObserver driverSelectedListener = new DriverSelectedObserver(this);
    private StartTripObserver startTripListener = new StartTripObserver(this);
    private String selectedDriver;
    private String tripId;
    private ImageButton startChatButton;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("type").equals("2")) {
                Toast.makeText(getApplicationContext(),
                        "The driver has accepted to make the trip",
                        Toast.LENGTH_LONG).show();
                startChatButton.setVisibility(View.VISIBLE);
            }else if(intent.getStringExtra("type").equals("3")){
                Toast.makeText(getApplicationContext(),
                        "Trip has finished",
                        Toast.LENGTH_LONG).show();
                startChatButton.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());

        getSupportActionBar().setTitle("Llevame");

        travelFragment = new TravelFragment();
        travelFragment.setObserver(startTripListener);

        nearbyDriverFragment = new NearbyDriverFragment();
        nearbyDriverFragment.setObserver(driverSelectedListener);

        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("Trip"));

        startChatButton = findViewById(R.id.open_chat);
        startChatButton.setOnClickListener(new StartChatButtonListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_passenger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(PassengerActivity.this, ProfileActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDriverSelected() {
        this.selectedDriver = nearbyDriverFragment.getSelectedDriverUsername();
        //nearbyDriverFragment.stopMap();
        //travelFragment.resumeMap();
        mViewPager.setCurrentItem(1);
    }

    public void onStartTrip() {
        PassengerServices services = ApiUtils.getPassengerServices();
        List<LocationData> tripLocationData = travelFragment.getTripSelected();
        String bearerToken = AppServerSession.getCurrentSession().getBearerToken();

        if(selectedDriver != null && tripLocationData != null) {
            TripToCreateData tripToCreate = new TripToCreateData(selectedDriver,
                    tripLocationData);

            services.requestToStartATrip(bearerToken, tripToCreate)
                    .enqueue(new StartTripCallback());
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragmentToShow;
            if(position == NUM_PAGE_NEARBY_DRIVER)
                fragmentToShow = nearbyDriverFragment;
            else {
                fragmentToShow = travelFragment;
            }
            return fragmentToShow;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }

}
