package com.greenback.cashflow.activities;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.greenback.cashflow.R;
import com.greenback.cashflow.databinding.ActivityMainBinding;
import com.greenback.cashflow.fragments.CalculatorFragment;
import com.greenback.cashflow.fragments.FaqFragment;
import com.greenback.cashflow.fragments.NewsFragment;
import com.greenback.cashflow.fragments.SettingsFragment;

import android.view.Menu;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    ScreenSlidePagerAdapter pagerAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.nav_view);

        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        int selectedPage = 0;
        viewPager.setCurrentItem(selectedPage, false);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(4);
        bottomNavigationView.getMenu().getItem(selectedPage).setChecked(true);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_calculator:
                        viewPager.setCurrentItem(0, false);
                        return true;
                    case R.id.navigation_news:
                        viewPager.setCurrentItem(1, false);
                        return true;
                    case R.id.navigation_faq:
                        viewPager.setCurrentItem(2, false);
                        return true;
                    case R.id.navigation_settings:
                        viewPager.setCurrentItem(3, false);
                        return true;
                }

                return false;
            }
        });
    }

    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CalculatorFragment();
                case 1:
                    return new NewsFragment();
                case 2:
                    return new FaqFragment();
                case 3:
                    return new SettingsFragment();
                default:
                    return new CalculatorFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

}