package com.llevame_app_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CardRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_registration);
        getSupportActionBar().setTitle("Llevame - Registration");

        boolean isPassenger = getIntent().getBooleanExtra("isPassenger",true);
        Button mNextStepButton = (Button) findViewById(R.id.next_step_button);
        if(isPassenger) {
            mNextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CardRegistrationActivity.this,
                            PassengerActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}
