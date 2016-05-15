package com.gunsandrocket.ua.cactus.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.dao.QbDao;
import com.gunsandrocket.ua.cactus.notifications.QBPusher;
import com.gunsandrocket.ua.cactus.util.LocalSaver;
import com.gunsandrocket.ua.cactus.util.VkLinker;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import rx.Subscriber;


/**
 * Created by dmytro on 03.04.16.
 */
public class StartActivity extends Activity {
    QbDao dao = new QbDao(this);
    VkLinker vkLinker = new VkLinker();
    SharedPreferences sharedPreferences;
    LocalSaver localSaver ;
    ProgressBar pb;
    Button btnVk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        pb = (ProgressBar)findViewById(R.id.pb);
        btnVk = (Button)findViewById(R.id.btn_vk);
        localSaver = new LocalSaver(this);

        if(!localSaver.isFirstLaunch()){
            pb.setVisibility(View.VISIBLE);
            dao.createSession(localSaver.getUserId(), new QBEntityCallback<QBSession>() {
                @Override
                public void onSuccess(QBSession qbSession, Bundle bundle) {
                    Log.d(getString(R.string.tag), "Success login");

                    localSaver.saveUserId(localSaver.getUserId());
                    dao.getUserTags().subscribe(new Subscriber<ArrayList<String>>() {
                        @Override
                        public void onCompleted() {
                            QBPusher pusher = new QBPusher(StartActivity.this);
                            pusher.subscribeToPushNotifications();


                            //add property for go to 'recommended events' tab
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ArrayList<String> tags) {
                            localSaver.setTags(new HashSet<String>(tags));
                        }
                    });



                }

                @Override
                public void onError(QBResponseException e) {
                    Log.d(getString(R.string.tag), "LogIn error: " + e.toString());
                }
            });
        }else{
            btnVk.setVisibility(View.VISIBLE);
        }

        //couple of line for getting fingerprint
        // copy this fingerprint to vk app cabinet from logs
        //then delete this lines
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        Log.d(getString(R.string.tag), "fingerprints: " + Arrays.toString(fingerprints));
        /////////////

        btnVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
                vkLinker.login(StartActivity.this);
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                Log.d(getString(R.string.tag), "On Result");
                dao.createSession(res.userId, new QBEntityCallback<QBSession>() {
                    @Override
                    public void onSuccess(QBSession qbUser, Bundle bundle) {
                        Log.d(getString(R.string.tag), "Success login");

                        localSaver.saveUserId(res.userId);
                        dao.getUserTags().subscribe(new Subscriber<ArrayList<String>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ArrayList<String> tags) {
                                localSaver.setTags(new HashSet<String>(tags));
                            }
                        });

                        QBPusher pusher = new QBPusher(StartActivity.this);
                        pusher.subscribeToPushNotifications();

                        //add property for go to 'recommended events' tab
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.d(getString(R.string.tag), "LogIn error: " + e.toString());

                        dao.singUp(res.userId, new QBEntityCallback<QBUser>() {
                            @Override
                            public void onSuccess(QBUser qbUser, Bundle bundle) {
                                Log.d(getString(R.string.tag), "Success SingUp");
                                dao.login(res.userId, new QBEntityCallback<QBUser>() {

                                    @Override
                                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                                        Log.d(getString(R.string.tag), "Success Login");

                                        localSaver.saveUserId(res.userId);

                                        QBPusher pusher = new QBPusher(StartActivity.this);
                                        pusher.subscribeToPushNotifications();

                                        //add property for go to 'categories' tab
                                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                                        finish();
                                    }

                                    @Override
                                    public void onError(QBResponseException e) {
                                        Log.d(getString(R.string.tag), "LogIn error: " + e.toString());
                                    }
                                });

                            }

                            @Override
                            public void onError(QBResponseException e) {
                                Log.d(getString(R.string.tag), "SingUp error: " + e.toString());
                            }
                        });
                    }
                });


            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
