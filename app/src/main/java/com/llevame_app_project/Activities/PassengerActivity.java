package com.llevame_app_project.Activities;
import android.content.Intent;
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

import com.google.android.gms.maps.model.CameraPosition;
import com.llevame_app_project.R;

/*TO DO: Work around to fix google map crash

 */

public class PassengerActivity extends AppCompatActivity{

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

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private NearbyDriverFragment nearbyDriverFragment;
    private TravelFragment travelFragment;
    private DriverSelectedListener driverSelectedListener = new DriverSelectedListener(this);
    private String selectedDriver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new PageChangeListener());
        getSupportActionBar().setTitle("Llevame");
        travelFragment = new TravelFragment();
        nearbyDriverFragment = new NearbyDriverFragment();
        nearbyDriverFragment.setObserver(driverSelectedListener);
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
