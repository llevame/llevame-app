package com.llevame_app_project.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.llevame_app_project.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Llevame - Settings");
    }
}
