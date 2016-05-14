package com.gunsandrocket.ua.cactus.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.MenuItem;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.activity.MainActivity;

public abstract class BaseFragment extends Fragment {

    protected MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (MainActivity) getActivity();
        setHasOptionsMenu(true);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (activity.getSupportActionBar() != null ) {
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
