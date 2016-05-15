package com.gunsandrocket.ua.cactus;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.gunsandrocket.ua.cactus.base.BaseRecyclerViewAdapter;
import com.gunsandrocket.ua.cactus.model.Category;
import com.gunsandrocket.ua.cactus.view.view_holder.CategoryViewHolder;

/**
 * Created by dnt on 5/15/16.
 */
public class CategoriesListAdapter extends BaseRecyclerViewAdapter<Category, CategoryViewHolder>{

    public CategoriesListAdapter(@LayoutRes int layoutRes) {
        super(layoutRes);
    }

    @Override
    protected CategoryViewHolder viewHolder(View view) {
        return new CategoryViewHolder(view);
    }
}
