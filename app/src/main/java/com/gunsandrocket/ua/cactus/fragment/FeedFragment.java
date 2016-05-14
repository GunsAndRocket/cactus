package com.gunsandrocket.ua.cactus.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.activity.DrawerActivity;
import com.gunsandrocket.ua.cactus.databinding.FragmentFeedBinding;
import com.gunsandrocket.ua.cactus.databinding.FragmentListBinding;

public class FeedFragment extends Fragment {

    private FragmentFeedBinding binding;

    private DrawerActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.binding.drawer.openDrawer(Gravity.LEFT);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        binding.container.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
        binding.tabs.setupWithViewPager(binding.container);

        activity = (DrawerActivity) getActivity();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.setSupportActionBar(binding.toolbar);
        if (activity.getSupportActionBar() != null ) {
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private FragmentListBinding binding;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
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

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
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
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.all_events);
                case 1:
                    return getResources().getString(R.string.recommended_events);
            }
            return null;
        }
    }
}
