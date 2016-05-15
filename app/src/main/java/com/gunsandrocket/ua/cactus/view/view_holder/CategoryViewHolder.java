package com.gunsandrocket.ua.cactus.view.view_holder;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.gunsandrocket.ua.cactus.base.BaseViewHolder;
import com.gunsandrocket.ua.cactus.databinding.CategoriesListItemBinding;
import com.gunsandrocket.ua.cactus.model.Category;

public class CategoryViewHolder extends BaseViewHolder<Category> {

    CategoriesListItemBinding binding;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bindView(Category item, Context context) {
        binding.setCategory(item);
    }
}
