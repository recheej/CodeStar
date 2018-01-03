package com.example.rechee.codestar;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.rechee.codestar.GameWinner.GameParticipantListViewModel;
import com.example.rechee.codestar.GameWinner.GameWinnerViewModel;
import com.example.rechee.codestar.GameWinner.RepoRepository;
import com.example.rechee.codestar.MainScreen.MainViewModel;
import com.example.rechee.codestar.MainScreen.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

/**
 * Created by Rechee on 1/2/2018.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;
    private Lazy<RepoRepository> repoRepositoryLazy;
    private Lazy<UserRepository> userRepositoryLazy;

    @Inject
    public ViewModelFactory(Lazy<UserRepository> userRepositoryLazy,
                            @Named("applicationContext")Context context,
                            Lazy<RepoRepository> repoRepositoryLazy) {
        this.userRepositoryLazy = userRepositoryLazy;
        this.context = context;
        this.repoRepositoryLazy = repoRepositoryLazy;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass == MainViewModel.class){
            return (T) new MainViewModel(userRepositoryLazy.get(), context);
        }
        else if(modelClass == GameParticipantListViewModel.class){
            return (T) new GameParticipantListViewModel(repoRepositoryLazy.get(), context);
        }
        else if(modelClass == GameWinnerViewModel.class){
            return (T) new GameWinnerViewModel();
        }

        throw new RuntimeException("could not find that view model");
    }
}
