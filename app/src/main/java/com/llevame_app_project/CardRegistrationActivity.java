package com.llevame_app_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llevame_app_project.Data.PassengerData;
import com.llevame_app_project.Data.Remote.ApiUtils;
import com.llevame_app_project.Data.Remote.RegistrationServices;
import com.llevame_app_project.Data.ResponseData;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;

import java.io.IOException;

import retrofit2.Response;

public class CardRegistrationActivity extends AppCompatActivity {

    private FirstRegistrationForm firstForm;
    private SecondRegistrationForm secondForm = new SecondRegistrationForm();
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mCreditCardNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_registration);
        getSupportActionBar().setTitle("Llevame - Registration");
        firstForm = (FirstRegistrationForm) getIntent().getSerializableExtra("firstForm");
        Button mNextStepButton = (Button) findViewById(R.id.next_step_button);
        mFirstName = (EditText) findViewById(R.id.textFirstName);
        mLastName = (EditText) findViewById(R.id.textLastName);
        mCreditCardNumber = (EditText) findViewById(R.id.textCreditCard);

        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                secondForm.firstName = mFirstName.getText().toString();
                secondForm.lastName = mLastName.getText().toString();
                secondForm.creditCardNumber = mCreditCardNumber.getText().toString();

                if (firstForm.isDriver) {
                    intent = new Intent(CardRegistrationActivity.this,
                            CarRegistrationActivity.class);
                    intent.putExtra("firstForm", firstForm);
                    intent.putExtra("secondForm", secondForm);
                    startActivity(intent);
                } else {

                    try {
                        registerNewPassenger();
                        intent = new Intent(CardRegistrationActivity.this,
                                PassengerActivity.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        Toast.makeText(CardRegistrationActivity.this.getBaseContext(),
                                            "Couldn't reach the server",
                                            Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private void registerNewPassenger() throws IOException {
                PassengerData passengerData = new PassengerData();
                Response<ResponseData> response;

                passengerData.setEmail(firstForm.email.toString());
                passengerData.setCreditCardNumber(secondForm.creditCardNumber.toString());
                passengerData.setFirstName(secondForm.firstName.toString());
                passengerData.setLastName(secondForm.lastName.toString());
                passengerData.setPassword(firstForm.password);
                //TO DO: Check that everything went alright.
                //Currently server notifies a problem when there isn't
                response = ApiUtils.getRegistrationServices().registerUser(passengerData.getEmail(),
                        passengerData).execute();
            }

        });
    }
}
