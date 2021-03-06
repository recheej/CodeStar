package com.example.rechee.codestar.dagger.viewmodel;

import com.example.rechee.codestar.GameWinner.GameParticipantListViewModel;
import com.example.rechee.codestar.GameWinner.GameWinnerViewModel;
import com.example.rechee.codestar.MainScreen.MainViewModel;
import com.example.rechee.codestar.dagger.activity.ActivityComponent;
import com.example.rechee.codestar.dagger.activity.ViewModelModule;

import dagger.Subcomponent;

/**
 * Created by reche on 1/1/2018.
 */

@ViewModelScope
@Subcomponent(modules={RepositoryModule.class, NetModule.class})
public interface ViewModelComponent {
    void inject(MainViewModel mainViewModel);
    void inject(GameParticipantListViewModel mainViewModel);
    void inject(GameWinnerViewModel mainViewModel);

    ActivityComponent plus(ViewModelModule viewModelModule);
}
