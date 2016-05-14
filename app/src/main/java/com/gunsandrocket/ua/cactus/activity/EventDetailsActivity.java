package com.gunsandrocket.ua.cactus.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.databinding.ActivityEventDetailsBinding;
import com.gunsandrocket.ua.cactus.model.Event;

import java.util.Date;

public class EventDetailsActivity extends AppCompatActivity {

    ActivityEventDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_details);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
}
