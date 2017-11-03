package com.llevame_app_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.DriverData;

public class RegistrationActivity extends AppCompatActivity {
    boolean isPassenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Llevame - Registration");

        isPassenger = true;
        RadioGroup radiogroup;
        radiogroup=(RadioGroup)findViewById(R.id.radioGroup);
        radiogroup.check(R.id.passengerRadioButton);

        Button mNextStepButton = (Button) findViewById(R.id.next_step_button);
        mNextStepButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, CardRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.passengerRadioButton:
                if (checked) {
                    isPassenger = true;
                }
                break;
            case R.id.driverRadioButton:
                if (checked) {
                    isPassenger = false;
                }
                break;
        }
    }
}
