package com.llevame_app_project;


import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.llevame_app_project.Activities.AppObservable;
import com.llevame_app_project.Activities.AppObserver;

import java.util.ArrayList;
import java.util.Map;

public class FirebaseService extends FirebaseMessagingService {


    private LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if(remoteMessage.getData() != null){
            Map<String, String> data = remoteMessage.getData();
            boolean isEmpty = data.isEmpty();
            if(data.containsKey("trip")){
                Intent intent = new Intent("Trip");
                intent.putExtra("tripId",data.get("trip"));
                Log.d("Trip","id: " +  data.get("trip"));
                broadcaster.sendBroadcast(intent);
            }

        }
    }
}
