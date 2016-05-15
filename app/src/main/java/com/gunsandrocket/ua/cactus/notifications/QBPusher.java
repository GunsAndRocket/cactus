package com.gunsandrocket.ua.cactus.notifications;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBEnvironment;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.messages.model.QBNotificationChannel;
import com.quickblox.messages.model.QBNotificationType;
import com.quickblox.messages.model.QBPushType;
import com.quickblox.messages.model.QBSubscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.gunsandrocket.ua.cactus.model.Event;

/**
 * Created by dmytro on 04.04.16.
 */
public class QBPusher {
    private final String SENDER_ID = "842384108834";
    private Context context;

    public QBPusher(Context context) {
        this.context = context;
    }

    public void pushEventByTag(Event newEvent,  QBEntityCallback<QBEvent> callback){
        StringifyArrayList<String> usersTags = new StringifyArrayList<String>();
        usersTags.add(newEvent.getTag());

        QBEvent event = new QBEvent();
        event.setUserTagsAny(usersTags);
        event.setEnvironment(QBEnvironment.DEVELOPMENT);
        event.setNotificationType(QBNotificationType.PUSH);
        event.setPushType(QBPushType.GCM);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("data.message", newEvent.getName());
        data.put("data.type", "welcome message");

        data.put("data.name", newEvent.getName());
        data.put("data.descr", newEvent.getDescription());
        data.put("data.tag", newEvent.getTag());
        data.put("data.organiser", newEvent.getOrganiser());
        data.put("data.vkLink", newEvent.getVkLink());
        data.put("data.place", newEvent.getPlace());
        data.put("data.startDate", newEvent.getStartDate());
        data.put("data.imageUrl", newEvent.getImageUrl());
        data.put("data.followers", newEvent.getFollowers());

        event.setMessage(data);

        QBPushNotifications.createEvent(event, callback);
    }

    public void subscribeToPushNotifications() {
        (new Thread(){
            public void run(){
                try {
                    String regId = GoogleCloudMessaging.getInstance(context).register(SENDER_ID);
                    Handler h = new Handler(context.getMainLooper());
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            QBSubscription subscription = new QBSubscription(QBNotificationChannel.GCM);
                            subscription.setEnvironment(QBEnvironment.DEVELOPMENT);
                            //
                            String deviceId;
                            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(
                                    Context.TELEPHONY_SERVICE);
                            if (mTelephony.getDeviceId() != null) {
                                deviceId = mTelephony.getDeviceId(); //*** use for mobiles
                            } else {
                                deviceId = Settings.Secure.getString(context.getContentResolver(),
                                        Settings.Secure.ANDROID_ID); //*** use for tablets
                            }
                            subscription.setDeviceUdid(deviceId);
                            //
                            subscription.setRegistrationID(regId);
                            //
                            QBPushNotifications.createSubscription(subscription, new QBEntityCallback<ArrayList<QBSubscription>>() {

                                @Override
                                public void onSuccess(ArrayList<QBSubscription> subscriptions, Bundle args) {
                                    Log.d("TAG", "Notification subscribed");
                                }

                                @Override
                                public void onError(QBResponseException error) {
                                    Log.d("TAG", "Notification error: " + error.toString());
                                }
                            });
                        }
                    });


                } catch (IOException e) {
                    Log.d("TAG", "Subscription error: " + e.toString());
                }
            }
        }).start();
    }
}
