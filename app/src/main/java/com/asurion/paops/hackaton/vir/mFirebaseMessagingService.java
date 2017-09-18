package com.asurion.paops.hackaton.vir;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.amazonaws.mobileconnectors.lex.interactionkit.ui.InteractiveVoiceView;
import com.asurion.paops.hackaton.vir.models.LexData;
import com.asurion.paops.hackaton.vir.models.NotificationData;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by iamjsed on 16/09/2017.
 */

public class mFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FBMsgSvc";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ


        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        String icon = remoteMessage.getNotification().getIcon();
        String sound = remoteMessage.getNotification().getSound();
        LexData data = null;

        int id = 0;
        Object obj = remoteMessage.getData().get("id");
        if ( obj != null ) {
            data = new LexData(
                    Integer.valueOf(remoteMessage.getData().get("id")),
                    remoteMessage.getData().get("bot_name").toString(),
                    remoteMessage.getData().get("bot_alias").toString(),
                    remoteMessage.getData().get("utterance").toString()
            );


        }



        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            SendNotification(new NotificationData(id, title, body, sound, icon, data));
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void scheduleJob(){ return; }
    private void handleNow() { return; }

    private void SendNotification(NotificationData notificationData) {
        Intent intent = new Intent(this, InteractiveVoiceActivity.class);
        intent.putExtra(NotificationData.TEXT, notificationData.getNotificationBody());
        intent.putExtra(LexData.BOT_NAME, notificationData.getData().getBot_name());
        intent.putExtra(LexData.BOT_ALIAS, notificationData.getData().getBot_alias());
        intent.putExtra(LexData.BOT_UTTERANCE, notificationData.getData().getUtterance());

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = null;
        try {

            notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(URLDecoder.decode(notificationData.getNotificationTitle(), "UTF-8"))
                    .setContentText(URLDecoder.decode(notificationData.getNotificationBody(), "UTF-8"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (notificationBuilder != null) {
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationData.getId(), notificationBuilder.build());
        } else {
            Log.d(TAG, "ERROR DIE ERROR DIE !!!");
        }
    }

}
