package com.llevame_app_project.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;
import com.llevame_app_project.Data.UserData.DriverData.CarData;
import com.llevame_app_project.Data.UserData.SessionData.LoginResponseData;
import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.NotifyFirebaseTokenThread;
import com.llevame_app_project.UserManagement.Registration.Registrant;

public class CarRegistrationActivity extends AppCompatActivity {

    private EditText mModel;
    private EditText mPatent;
    private EditText mYear;
    private EditText mColor;
    private CheckBox cAc;
    private FirstRegistrationForm firstForm;
    private SecondRegistrationForm secondForm;
    private Registrant registrant = new Registrant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_registration);

        mModel = findViewById(R.id.textModel);
        mColor = findViewById(R.id.textColor);
        mPatent = findViewById(R.id.textPatent);
        mYear = findViewById(R.id.textYear);
        cAc = findViewById(R.id.checkBoxAc);

        firstForm = (FirstRegistrationForm) getIntent().getSerializableExtra("firstForm");
        secondForm = (SecondRegistrationForm) getIntent().getSerializableExtra("secondForm");

        Button mFinishButton = findViewById(R.id.finish_button);

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CarData carData = createCarData();
                try {
                    LoginResponseData response = registrant.register(firstForm,secondForm,carData);

                    if (!response.getSuccess()) {
                        throw new Throwable(response.getError().getDescription());
                    }

                    AppServerSession.createSession(true,firstForm.email,
                            response.getLoginData().getToken());
                    startNotifyFirebaseTokenTask();

                    Intent intent = new Intent(CarRegistrationActivity.this,
                            DriverActivity.class);
                    startActivity(intent);

                } catch (Throwable throwable) {
                    Toast.makeText(CarRegistrationActivity.this.getBaseContext(),
                            throwable.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            private void startNotifyFirebaseTokenTask(){
               String token = FirebaseInstanceId.getInstance().getToken();
               NotifyFirebaseTokenThread thread = new NotifyFirebaseTokenThread(token);
               thread.start();
            }

            private CarData createCarData(){
                CarData carData = new CarData();
                carData.setColor(mColor.getText().toString());
                carData.setHasAc(cAc.isChecked());
                carData.setModel(mModel.getText().toString());
                carData.setPatent(mPatent.getText().toString());
                carData.setYear(Integer.parseInt(mYear.getText().toString()));
                return carData;

            }
        }


        );



    }
}
