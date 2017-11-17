package com.llevame_app_project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;

import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.R;

public class RegistrationActivity extends AppCompatActivity {
    boolean isPassenger;
    private EditText mEmailView;
    private EditText mPasswordView;
    private FirstRegistrationForm firstForm = new FirstRegistrationForm();

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

        mPasswordView = (EditText) findViewById(R.id.textPassword);
        mEmailView = (EditText) findViewById(R.id.textEmail);

        Button mNextStepButton = (Button) findViewById(R.id.next_step_button);
        mNextStepButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, CardRegistrationActivity.class);
                firstForm.isDriver = !isPassenger;
                firstForm.email = mEmailView.getText().toString();
                firstForm.password = mPasswordView.getText().toString();
                intent.putExtra("firstForm", firstForm);
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
