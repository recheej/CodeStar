package com.example.rechee.codestar.GameWinner;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rechee.codestar.CodeStarApplication;
import com.example.rechee.codestar.GameWinner.UserReposFragment.ListListener;
import com.example.rechee.codestar.MainScreen.MainActivity;
import com.example.rechee.codestar.R;
import com.example.rechee.codestar.ViewModelFactory;
import com.example.rechee.codestar.dagger.activity.ViewModelModule;
import com.example.rechee.codestar.dagger.viewmodel.RepositoryModule;

import javax.inject.Inject;

public class GameWinnerActivity extends AppCompatActivity implements ListListener {

    private String userIdOne;
    private String userIdTwo;
    private UserPagerAdapter userPagerAdapter;
    private GameWinnerViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_winner);

        final ViewGroup winnerContainer = findViewById(R.id.container_winner);
        final ViewGroup tieContainer = findViewById(R.id.container_tie);

        final TextView winningUsernameTextView = findViewById(R.id.textView_winnerUsername);

        Intent intent = getIntent();
        userIdOne = intent.getStringExtra(MainActivity.USER_ID_ONE);
        userIdTwo = intent.getStringExtra(MainActivity.USER_ID_TWO);

        ViewPager userPager = findViewById(R.id.viewpager_user);
        this.userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager());
        userPager.setAdapter(userPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(userPager);

        ((CodeStarApplication) getApplicationContext())
                .getApplicationComponent()
                .plus(new RepositoryModule())
                .plus(new ViewModelModule())
                .inject(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GameWinnerViewModel.class);
        viewModel.getWinningUser().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String winningUsername) {
                tieContainer.setVisibility(View.GONE);
                winnerContainer.setVisibility(View.VISIBLE);

                winningUsernameTextView.setText(winningUsername);
            }
        });

        viewModel.getTie().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                tieContainer.setVisibility(View.VISIBLE);
                winnerContainer.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void OnStarCountReceived(String username, int count) {
        viewModel.recordCount(username, count);
    }

    public class UserPagerAdapter extends FragmentPagerAdapter {

        UserPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return UserReposFragment.newInstance(userIdOne);
                case 1:
                    return UserReposFragment.newInstance(userIdTwo);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return userIdOne;
                case 1:
                    return userIdTwo;
                default:
                    return "";
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        fm .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        finish();
    }
}
