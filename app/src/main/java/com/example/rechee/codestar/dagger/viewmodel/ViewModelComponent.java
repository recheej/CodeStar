package com.example.rechee.codestar.dagger.viewmodel;

import com.example.rechee.codestar.GameWinner.GameWinnerViewModel;
import com.example.rechee.codestar.MainScreen.MainViewModel;

import dagger.Subcomponent;

/**
 * Created by reche on 1/1/2018.
 */

@ViewModelScope
@Subcomponent(modules={RepositoryModule.class, NetModule.class})
public interface ViewModelComponent {
    void inject(MainViewModel mainViewModel);
    void inject(GameWinnerViewModel mainViewModel);
}
