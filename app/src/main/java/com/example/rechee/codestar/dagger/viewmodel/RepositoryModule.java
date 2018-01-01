package com.example.rechee.codestar.dagger.viewmodel;

import com.example.rechee.codestar.MainScreen.GithubService;
import com.example.rechee.codestar.UserRepository;
import com.example.rechee.codestar.UserRepositoryNetwork;

import dagger.Module;
import dagger.Provides;

/**
 * Created by reche on 1/1/2018.
 */

@Module
public class RepositoryModule {

    @Provides
    @ViewModelScope
    UserRepository providesApplication(GithubService githubService) {
        return new UserRepositoryNetwork(githubService);
    }
}