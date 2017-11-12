package com.llevame_app_project.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.llevame_app_project.Profile;
import com.llevame_app_project.R;

public class CarSettingsActivity extends AppCompatActivity {

    private TextView mPatent;
    private TextView mColor;
    private TextView mYear;
    private TextView mModel;
    private CheckBox cAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_settings);
        mPatent = this.findViewById(R.id.sTextPatent);
        mColor = this.findViewById(R.id.sTextColor);
        mYear = this.findViewById(R.id.sTextYear);
        mModel = this.findViewById(R.id.sTextModel);
        cAc = this.findViewById(R.id.settingsAcCheckBox);
        cAc.setEnabled(false);
        Profile profile = new Profile();
        try {
            profile.updateDataFromServer();
            mPatent.setText(profile.getCarData().getPatent());
            mColor.setText(profile.getCarData().getColor());
            mModel.setText(profile.getCarData().getModel());
            cAc.setChecked(profile.getCarData().isHasAc());
            mYear.setText(String.valueOf(profile.getCarData().getYear()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
