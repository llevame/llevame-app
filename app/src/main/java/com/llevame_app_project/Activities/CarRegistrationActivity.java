package com.llevame_app_project.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.llevame_app_project.Forms.FirstRegistrationForm;
import com.llevame_app_project.Forms.SecondRegistrationForm;
import com.llevame_app_project.R;

public class CarRegistrationActivity extends AppCompatActivity {

    private EditText mModel;
    private EditText mPatent;
    private EditText mYear;
    private EditText mColor;
    private CheckBox cAc;
    private FirstRegistrationForm firstForm;
    private SecondRegistrationForm secondForm;

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
                Intent intent = new Intent(CarRegistrationActivity.this,
                        DriverActivity.class);
                startActivity(intent);
            }
        });


    }
}
