package com.gunsandrocket.ua.cactus;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.gunsandrocket.ua.cactus.base.BaseRecyclerViewAdapter;
import com.gunsandrocket.ua.cactus.model.Event;
import com.gunsandrocket.ua.cactus.view.view_holder.EventViewHolder;

public class EventListAdapter extends BaseRecyclerViewAdapter<Event, EventViewHolder> {

    public EventListAdapter(@LayoutRes int layoutRes) {
        super(layoutRes);
    }

    @Override
    protected EventViewHolder viewHolder(View view) {
        return new EventViewHolder(view);
    }

}
