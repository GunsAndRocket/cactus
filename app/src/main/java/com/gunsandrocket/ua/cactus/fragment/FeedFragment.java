package com.gunsandrocket.ua.cactus.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunsandrocket.ua.cactus.EventListAdapter;
import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.base.BaseFragment;
import com.gunsandrocket.ua.cactus.dao.QbDao;
import com.gunsandrocket.ua.cactus.databinding.FragmentFeedBinding;
import com.gunsandrocket.ua.cactus.databinding.FragmentListBinding;
import com.gunsandrocket.ua.cactus.model.Event;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class FeedFragment extends BaseFragment {

    private FragmentFeedBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false);
        binding.container.setAdapter(new SectionsPagerAdapter(getChildFragmentManager()));
        binding.tabs.setupWithViewPager(binding.container);
        activity.setSupportActionBar(binding.toolbar);

        Log.d("TAG", "in FF.onCreateView");
        return binding.getRoot();
    }

    public static class PlaceholderFragment extends Fragment {

        protected FragmentListBinding binding;

        public static final String ARG_SECTION_NUMBER = "section_number";

        public static final int SECTION_ALL = 0;
        public static final int SECTION_RECOMMENDED = 1;

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

            Log.d("TAG", "in BPF.onCreateView");
            return binding.getRoot();
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d("TAG", "In onActivityCreated");

            //Here we have to set adapter and bind it with data provider
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            Log.d("TAG", "section = " + section);
            QbDao dao = new QbDao(getActivity());
            Observable<List<Event>> observable = dao.getEvents();

            EventListAdapter adapter = new EventListAdapter(R.layout.events_list_item);
            binding.list.setLayoutManager(new GridLayoutManager(getContext(), 1));
            binding.list.setAdapter(adapter);
//            switch (section) {
//                case SECTION_ALL:
//                    Log.d("TAG", "in all");
//                    observable = dao.getEvents();
//                    break;
//
//                case SECTION_RECOMMENDED:
//                    Log.d("TAG", "in recommended");
//                    observable = dao.getRecommendedEvents();
//                    break;
//            }


            observable.subscribe(new Subscriber<List<Event>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("TAG", "Error in subscriber: " + e.toString() );
                }

                @Override
                public void onNext(List<Event> events) {
                    Log.d("TAG", "In onNext");
                    adapter.addItems(events);
                }
            });
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("TAG", "in getItem");
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PlaceholderFragment.SECTION_ALL:
                    return getResources().getString(R.string.all_events);
                case PlaceholderFragment.SECTION_RECOMMENDED:
                    return getResources().getString(R.string.recommended_events);
            }
            return null;
        }
    }
}
