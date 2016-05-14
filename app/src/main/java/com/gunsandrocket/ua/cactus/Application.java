package com.gunsandrocket.ua.cactus;

import com.quickblox.core.QBSettings;
import com.vk.sdk.VKSdk;

/**
 * Created by dmytro on 22.03.16.
 */
public class Application extends android.app.Application {
    static final String APP_ID = "37788";
    static final String AUTH_KEY = "eGzjCrVbcOPu6dE";
    static final String AUTH_SECRET = "zk7yJdRPDTGRm-n";
    static final String ACCOUNT_KEY = "gHzxSyxTqbt7Q7jL17Cr";

    @Override
    public void onCreate() {
        super.onCreate();
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        VKSdk.initialize(this);
    }
}
