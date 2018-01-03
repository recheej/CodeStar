package com.example.rechee.codestar.dagger.activity;

import com.example.rechee.codestar.GameWinner.GameParticipantListViewModel;
import com.example.rechee.codestar.GameWinner.GameWinnerActivity;
import com.example.rechee.codestar.GameWinner.GameWinnerViewModel;
import com.example.rechee.codestar.GameWinner.UserReposFragment;
import com.example.rechee.codestar.MainScreen.MainActivity;
import com.example.rechee.codestar.MainScreen.MainViewModel;
import com.example.rechee.codestar.dagger.viewmodel.NetModule;

import dagger.Subcomponent;

/**
 * Created by reche on 1/1/2018.
 */

@ActivityScope
@Subcomponent(modules={ViewModelModule.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(GameWinnerActivity gameWinnerActivity);
    void inject(UserReposFragment userReposFragment);
}
