package com.gunsandrocket.ua.cactus.util;

import android.app.Activity;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;


/**
 * Created by dmytro on 03.04.16.
 */
public class VkLinker {

    public void login(Activity activity){
        VKSdk.login(activity, VKScope.GROUPS, VKScope.NOHTTPS);
    }
}
