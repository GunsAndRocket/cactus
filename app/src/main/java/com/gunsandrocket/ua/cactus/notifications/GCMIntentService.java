package com.gunsandrocket.ua.cactus.notifications;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.activity.EventDetailsActivity;
import com.gunsandrocket.ua.cactus.activity.MainActivity;
import com.gunsandrocket.ua.cactus.model.Event;

import java.util.Date;


/**
 * Created by dmytro on 04.04.16.
 */
public class GCMIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;

    private static final String TAG = "TAG";

    private NotificationManager notificationManager;

    public GCMIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("TAG", "new push");

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging googleCloudMessaging = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = googleCloudMessaging.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                processNotification("Send error: ", extras);
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                processNotification("Deleted messages on server: ", extras);
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Post notification of received message.
                processNotification("Received: ", extras);
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void processNotification(String type, Bundle extras) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        final String messageValue = extras.getString("message");

        String name = extras.getString("name");
        String descr = extras.getString("descr");
        String tag = extras.getString("tag");
        String organiser = extras.getString("organiser");
        String vkLink = extras.getString("vkLink");
        String place = extras.getString("place");
        Date startDate = (Date)extras.get("startDate");
        String imageUrl = extras.getString("imageUrl");
        Integer followers = extras.getInt("followers");
        Event event = new Event(tag, organiser, name, descr, startDate, vkLink, place, followers, null, imageUrl);

        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(Event.EVENT, event);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        long[] vibrate = { 0, 100, 200, 300 };
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.cast_ic_notification_0)
                .setContentTitle("Kpi Events")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(messageValue))
                .setContentText(messageValue)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setVibrate(vibrate);

        mBuilder.setContentIntent(contentIntent);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        // notify activity
        Intent intentNewPush = new Intent("new-push-event");
        intentNewPush.putExtra("message", messageValue);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intentNewPush);

        Log.i(TAG, "Broadcasting event " + "new-push-event" + " with data: " + messageValue);
    }
}
