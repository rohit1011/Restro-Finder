package com.nextnepalit.ecommerce.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import com.nextnepalit.ecommerce.R;
import com.nextnepalit.ecommerce.data.models.Categories;
import com.nextnepalit.ecommerce.ui.adapters.NavMenuExpandableAdapter;
import com.nextnepalit.ecommerce.ui.fragments.AccountFragment;
import com.nextnepalit.ecommerce.ui.fragments.CartFragment;
import com.nextnepalit.ecommerce.ui.fragments.HomeFragment;
import com.nextnepalit.ecommerce.ui.fragments.SearchFragment;
import com.nextnepalit.ecommerce.viewmodels.HomeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ExpandableListView expandableListView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.navigation_search:
                    loadFragment(new SearchFragment());
                    return true;

                case R.id.navigation_cart:
                    loadFragment(new CartFragment());
                    return true;

                case R.id.navigation_account:
                    loadFragment(new AccountFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        expandableListView = findViewById(R.id.expandableListView);
        final NavMenuExpandableAdapter navMenuExpandableAdapter = new NavMenuExpandableAdapter(this);
        expandableListView.setAdapter(navMenuExpandableAdapter);
        homeViewModel.getCategoriesData().observe(this, new Observer<List<Categories>>() {
            @Override
            public void onChanged(@Nullable List<Categories> categories) {
                navMenuExpandableAdapter.setExpadableTitles(categories);
            }
        });


        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
    }
}
