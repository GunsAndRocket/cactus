package com.gunsandrocket.ua.cactus.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import ua.gunsandrocket.kpievents.R;

/**
 * Created by dmytro on 17.04.16.
 */
public class LocalSaver {
    private Context context;
    SharedPreferences sp;
    private final String PREFERENCES = "preferences";
    private final String TAGS_SET = "tags_set";
    private SharedPreferences.Editor editor;

    public LocalSaver(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public String getUserId(){
        return sp.getString(context.getString(R.string.id_user),"");
    }

    public boolean isFirstLaunch(){
        String idUser = getUserId();
        if(idUser.equals(""))
            return true;
        return false;
    }

    public void saveUserId(String userId){
        editor.putString(context.getString(R.string.id_user), userId);
        editor.commit();
    }

    public Set<String> getTags(){
        return sp.getStringSet(context.getString(R.string.tags_list), null);
    }

    public void setTags(Set<String> tags){
        editor.putStringSet(context.getString(R.string.tags_list), tags);
        editor.commit();
    }

}
