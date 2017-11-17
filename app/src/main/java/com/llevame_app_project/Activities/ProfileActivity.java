package com.llevame_app_project.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.llevame_app_project.UserManagement.LoggedUser.Profile;
import com.llevame_app_project.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView emailView;
    private TextView creditCardNumberView;
    private Button  logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Llevame - Settings");
        nameView = this.findViewById(R.id.text_view_name);
        emailView = this.findViewById(R.id.text_view_email);
        creditCardNumberView = this.findViewById(R.id.text_view_credit_card_number);
        Profile profile = new Profile();
        try {
            profile.updateDataFromServer();
            nameView.setText(profile.getFirstName().concat(profile.getLastName()));
            emailView.setText(profile.getEmail());
            creditCardNumberView.setText(profile.getCreditCardNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }

        logoutButton = (Button) this.findViewById(R.id.button_log_out);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }
}
