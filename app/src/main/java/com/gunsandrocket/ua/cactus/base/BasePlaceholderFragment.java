package com.gunsandrocket.ua.cactus.base;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.databinding.FragmentListBinding;

public class BasePlaceholderFragment extends Fragment {

    private FragmentListBinding binding;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static BasePlaceholderFragment newInstance(int sectionNumber) {
        BasePlaceholderFragment fragment = new BasePlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);

        return binding.getRoot();
    }

}