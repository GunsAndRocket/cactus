package com.gunsandrocket.ua.cactus.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.model.Event;
import com.gunsandrocket.ua.cactus.util.LocalSaver;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import rx.*;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by dmytro on 03.04.16.
 */
public class QbDao {
    private final String PASSWORD = "password";
    private Context context;

    public QbDao(Context context) {
        this.context = context;
    }

    public void createSession(String idUser, QBEntityCallback<QBSession> callback){
        QBAuth.createSession(new QBUser(idUser, PASSWORD), callback);
    }

    public void singUp(String idUser, QBEntityCallback<QBUser> callback){
        QBAuth.createSession(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                Log.d("TAG", "Success create app session");
                QBUsers.signUp(new QBUser(idUser, PASSWORD), callback);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("TAG", "Application session error: " + e.toString());
            }
        });
    }



    public void login(String idUser, QBEntityCallback<QBUser> callback){
        QBUsers.signIn(new QBUser(idUser, PASSWORD), callback);
    }


    public Observable getEventsByCategory(String category){
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.eq("type", category);
        return makeObservable(requestBuilder);

    }

    //change it in future (lazy load)
    public Observable getEvents(){
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.sortDesc("startDate");
        return makeObservable(requestBuilder);
    }

    //need check
    public Observable getRecommendedEvents(){
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        SharedPreferences prefs = context.getSharedPreferences(
                context.getResources().getString(R.string.tags_list), Context.MODE_PRIVATE);
        Set<String> tags = prefs.getStringSet(context.getResources().getString(R.string.tags_list), null);
        if(tags == null) {
            tags = new HashSet<String>();
            tags.add("no_tag");
        }

        requestBuilder.in("tag", tags.toArray());

        return makeObservable(requestBuilder);
    }

    public Observable getUserTags(){
        LocalSaver localSaver = new LocalSaver(context);
        return Observable.create((obj)->{
            QBUser user = null;
            ArrayList<String> tags = new ArrayList<String>();
            try {
                user = QBUsers.getUserByLogin(localSaver.getUserId());
            } catch (QBResponseException e) {
                e.printStackTrace();
            }
            if(user != null)
                tags.addAll(user.getTags());
            ((Subscriber) obj).onNext(tags);
            ((Subscriber) obj).onCompleted();
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable setUserTags(ArrayList<String> tags){
        return Observable.create((obj)->{
            LocalSaver localSaver = new LocalSaver(context);
            QBUser user = null;
            try {
                user = QBUsers.getUserByLogin(localSaver.getUserId());
                user.setTags(new StringifyArrayList<String>(tags));
                QBUsers.updateUser(user);
                ((Subscriber) obj).onCompleted();
            } catch (QBResponseException e) {
                ((Subscriber) obj).onError(e);
                e.printStackTrace();
            }


        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }

    public void addEvent(Event event, QBEntityCallback<QBCustomObject> callback){
        QBCustomObject object = new QBCustomObject();
        object.setClassName("Events");

        object.putString("name", event.getName());
        object.putString("description", event.getDescription());
        object.putString("place", event.getPlace());
        object.putString("tag", event.getTag());
        object.putString("organiser", event.getOrganiser());
        object.putDate("startDate", event.getStartDate());
        object.putString("vkLink", event.getVkLink());
        object.putInteger("followers", 0);
        object.putString("image_url", event.getImageUrl());
        QBCustomObjects.createObject(object, callback);
    }


    private Observable makeObservable(QBRequestGetBuilder requestBuilder){
        return Observable.create((obj) -> {
            ArrayList<QBCustomObject> customObjects = null;
            try {
                customObjects =
                        QBCustomObjects.getObjects("Events", requestBuilder, new Bundle());
            } catch (QBResponseException e) {
                e.printStackTrace();
            }
            ArrayList<Event> events = new ArrayList<Event>();
            if(customObjects != null)
                for (QBCustomObject co : customObjects) {
                    Date startDate = null;
                    try {
                        startDate = co.getDate("startDate", null);
                    } catch (ParseException e) {
                        Log.d("TAG", "Bad date");
                    }
                    Integer followers = 0;
                    try {
                        followers = co.getInteger("followers");
                    }catch (NumberFormatException e){
                        followers = 0;
                    }


                        Event event = new Event(co.getString("tag"), co.getString("organiser"), co.getString("name"),
                                co.getString("description"), startDate,
                                co.getString("vkLink"), co.getString("place"), followers,
                                co.getCreatedAt(), co.getString("image_url"));
                        events.add(event);

                }

                ((Subscriber) obj).onNext(events);
                ((Subscriber) obj).onCompleted();
            })
            .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
