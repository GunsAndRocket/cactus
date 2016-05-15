package com.gunsandrocket.ua.cactus.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewUtils {

    public static String getFollowersString(Integer count, String followers) {
        return String.valueOf(count) + " " + followers;
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).into(view);
    }

}