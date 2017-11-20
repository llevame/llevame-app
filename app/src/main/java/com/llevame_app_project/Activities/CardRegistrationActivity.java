package com.llevame_app_project.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;
import com.llevame_app_project.Data.UserData.SessionData.LoginResponseData;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.Registration.Registrant;

public class CardRegistrationActivity extends AppCompatActivity {

    private FirstRegistrationForm firstForm;
    private SecondRegistrationForm secondForm = new SecondRegistrationForm();
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mCreditCardNumber;
    private Registrant registrant = new Registrant();

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

                secondForm.firstName = mFirstName.getText().toString();
                secondForm.lastName = mLastName.getText().toString();
                secondForm.creditCardNumber = mCreditCardNumber.getText().toString();

                if (firstForm.isDriver) {
                    goToTheNextRegistrationActivity(firstForm, secondForm);

                } else {

                    try {

                        LoginResponseData response = registrant.register(firstForm, secondForm);

                        if (!response.getSuccess()) {
                            throw new Throwable(response.getError().getDescription());
                        }

                        AppServerSession.createSession(false,firstForm.email,
                                                        response.getLoginData().getToken());
                        Intent intent = new Intent(CardRegistrationActivity.this,
                                PassengerActivity.class);
                        startActivity(intent);

                    } catch (Throwable throwable) {

                        Toast.makeText(CardRegistrationActivity.this.getBaseContext(),
                                throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private void goToTheNextRegistrationActivity(FirstRegistrationForm firstForm,
                                                      SecondRegistrationForm secondForm){
                Intent intent;
                intent = new Intent(CardRegistrationActivity.this,
                        CarRegistrationActivity.class);
                intent.putExtra("firstForm", firstForm);
                intent.putExtra("secondForm", secondForm);
                startActivity(intent);
            }
        });
    }
}
