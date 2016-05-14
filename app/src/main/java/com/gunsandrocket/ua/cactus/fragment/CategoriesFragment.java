package com.gunsandrocket.ua.cactus.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.base.BaseFragment;
import com.gunsandrocket.ua.cactus.base.BasePlaceholderFragment;
import com.gunsandrocket.ua.cactus.databinding.FragmentCategoriesBinding;

public class CategoriesFragment extends BaseFragment {

    private FragmentCategoriesBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false);
        binding.container.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
        binding.tabs.setupWithViewPager(binding.container);
        activity.setSupportActionBar(binding.toolbar);

        return binding.getRoot();
    }

    public static class PlaceholderFragment extends BasePlaceholderFragment {

        public static final int SECTION_SCIENCE = 0;
        public static final int SECTION_ENTERTAINMENT = 1;
        public static final int SECTION_SPORT = 2;

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            //Here we have to set adapter and bind it with data provider
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PlaceholderFragment.SECTION_SCIENCE:
                    return getResources().getString(R.string.science_categorie);
                case PlaceholderFragment.SECTION_ENTERTAINMENT:
                    return getResources().getString(R.string.entertainment_categorie);
                case PlaceholderFragment.SECTION_SPORT:
                    return getResources().getString(R.string.sport_categorie);
            }
            return null;
        }
    }
}
