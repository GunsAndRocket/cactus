package com.gunsandrocket.ua.cactus;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gunsandrocket.ua.cactus.databinding.ActivityDrawerBinding;

/**
 * Created by dnt on 5/14/16.
 */
public class DrawerActivity extends AppCompatActivity {

    private ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawer);
    }
}
