package com.gunsandrocket.ua.cactus.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.gunsandrocket.ua.cactus.R;
import com.gunsandrocket.ua.cactus.databinding.ActivityDrawerBinding;
import com.gunsandrocket.ua.cactus.fragment.CategoriesFragment;
import com.gunsandrocket.ua.cactus.fragment.FeedFragment;

public class DrawerActivity extends AppCompatActivity {

    public ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawer);
        setupNavigationView(binding.navigationView, binding.drawer);

        if (savedInstanceState == null) {
            binding.navigationView.getMenu().findItem(R.id.menu_item_main).setChecked(true);
            changeFragment(R.id.container, new FeedFragment());
        }
    }

    private void setupNavigationView(NavigationView nav, final DrawerLayout drawer) {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawer.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.menu_item_main:
                        changeFragment(R.id.container, new FeedFragment());
                        break;

                    case R.id.menu_item_categories:
                        changeFragment(R.id.container, new CategoriesFragment());
                        break;

                    case R.id.menu_item_subscriptions:
                        //start subscriptions
                        break;

                    case R.id.menu_item_settings:
                        //start settings
                        break;
                }
                return true;
            }
        });
    }

    private void changeFragment(@IdRes int id, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(id, fragment)
                .commit();
    }
}
