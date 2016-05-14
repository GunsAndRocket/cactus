package com.gunsandrocket.ua.cactus.view.view_holder;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.gunsandrocket.ua.cactus.activity.EventDetailsActivity;
import com.gunsandrocket.ua.cactus.base.BaseViewHolder;
import com.gunsandrocket.ua.cactus.databinding.EventsListItemBinding;
import com.gunsandrocket.ua.cactus.model.Event;

public class EventViewHolder extends BaseViewHolder<Event> {

    EventsListItemBinding binding;

    public EventViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bindView(Event item, Context activity) {
        binding.setEvent(item);
        binding.eventsListItem
                .setOnClickListener(view -> activity
                        .startActivity(new Intent(activity, EventDetailsActivity.class)));
    }
}
