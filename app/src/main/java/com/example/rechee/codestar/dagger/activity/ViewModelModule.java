package com.example.rechee.codestar.dagger.activity;

import android.content.Context;

import com.example.rechee.codestar.GameWinner.RepoRepository;
import com.example.rechee.codestar.GameWinner.RepoRepositoryNetwork;
import com.example.rechee.codestar.GithubService;
import com.example.rechee.codestar.MainScreen.MainViewModel;
import com.example.rechee.codestar.MainScreen.UserRepository;
import com.example.rechee.codestar.MainScreen.UserRepositoryNetwork;
import com.example.rechee.codestar.ViewModelFactory;

import javax.inject.Named;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reche on 1/1/2018.
 */

@Module
public class ViewModelModule {

    @Provides
    @ActivityScope
    ViewModelFactory viewModelFactory(Lazy<UserRepository> userRepositoryLazy,
                                      @Named("applicationContext") Context context,
                                      Lazy<RepoRepository> repoRepositoryLazy) {
        return new ViewModelFactory(userRepositoryLazy, context, repoRepositoryLazy);
    }
}