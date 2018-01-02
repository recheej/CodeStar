package com.example.rechee.codestar.dagger.viewmodel;

import com.example.rechee.codestar.GameWinner.RepoRepository;
import com.example.rechee.codestar.GameWinner.RepoRepositoryNetwork;
import com.example.rechee.codestar.GithubService;
import com.example.rechee.codestar.MainScreen.UserRepository;
import com.example.rechee.codestar.MainScreen.UserRepositoryNetwork;

import dagger.Module;
import dagger.Provides;

/**
 * Created by reche on 1/1/2018.
 */

@Module
public class RepositoryModule {

    @Provides
    @ViewModelScope
    UserRepository userRepository(GithubService githubService) {
        return new UserRepositoryNetwork(githubService);
    }

    @Provides
    @ViewModelScope
    RepoRepository repoRepository(GithubService githubService) {
        return new RepoRepositoryNetwork(githubService);
    }
}