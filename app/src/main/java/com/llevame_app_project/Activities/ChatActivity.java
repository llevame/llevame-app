package com.llevame_app_project.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.llevame_app_project.R;
import com.llevame_app_project.UserManagement.LoggedUser.AppServerSession;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {



    private class SendMessageListener implements ImageButton.OnClickListener{

        @Override
        public void onClick(View v) {
            if( AppServerSession.getCurrentSession().isDriver())
                dbRef.child("DriverMessage").setValue(input.getText().toString());
            else
                dbRef.child("PassengerMessage").setValue(input.getText().toString());
            input.setText("");
        }
    }

    private class ReceiveMessageListener implements ValueEventListener{

        String lastDriverMessage;
        String lastPassengerMessage;

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            String newDriverMessage =
                    dataSnapshot.child("DriverMessage").getValue(String.class);

            if(newDriverMessage != null){
                if(lastDriverMessage == null ||
                        !newDriverMessage.equals(lastDriverMessage)){
                    messageList.add("Driver: "+newDriverMessage);
                    lastDriverMessage = newDriverMessage;
                }
            }

            String newPassengerMessage =
                    dataSnapshot.child("PassengerMessage").getValue(String.class);

            if(newPassengerMessage != null){
                if(lastPassengerMessage == null ||
                        !newPassengerMessage.equals(lastPassengerMessage)){

                    messageList.add("Passenger: "+newPassengerMessage);
                    lastPassengerMessage = newPassengerMessage;
                }
            }
            adapter.notifyDataSetChanged();
            messageListView.setSelection(messageList.size()-1);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    private ArrayAdapter<String> adapter;
    ArrayList<String> messageList =new ArrayList<>();
    ListView messageListView;
    DatabaseReference dbRef;

    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        input = (EditText)findViewById(R.id.input);
        FloatingActionButton sendMessageButton =
                (FloatingActionButton)findViewById(R.id.send_message_button);
        sendMessageButton.setOnClickListener(new SendMessageListener());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(getIntent().getExtras().getString("tripId"));
        dbRef.addValueEventListener(new ReceiveMessageListener());
        messageListView = findViewById(R.id.list_of_messages);
        adapter=new ArrayAdapter<String>(this,
                R.layout.message_layout,
                messageList);
        messageListView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
