package com.llevame_app_project.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.llevame_app_project.R;

public class ChatActivity extends AppCompatActivity {

    private class SendMessageListener implements ImageButton.OnClickListener{

        @Override
        public void onClick(View v) {

            input.setText("");
        }
    }

    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        input = (EditText)findViewById(R.id.input);
        FloatingActionButton sendMessageButton =
                (FloatingActionButton)findViewById(R.id.send_message_button);

        sendMessageButton.setOnClickListener(new SendMessageListener());
    }
}
