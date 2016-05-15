package com.gunsandrocket.ua.cactus.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.databinding.ActivityEventDetailsBinding;
import com.gunsandrocket.ua.cactus.model.Event;

import java.util.Date;

public class EventDetailsActivity extends AppCompatActivity {

    ActivityEventDetailsBinding binding;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Event event = (Event) getIntent().getExtras().getSerializable(Event.EVENT);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_details);

        binding.setEvent(event);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
}
