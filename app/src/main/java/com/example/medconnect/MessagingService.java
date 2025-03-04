package com.example.medconnect;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        //Handle notification message
        if(remoteMessage.getNotification() != null){
            // display the notification (use notification manager or anything)
        }
    }

    @Override
    public void onNewToken(String token){
        //save the token if needed (for eg. sending targeted notifications)
        
    }
}
