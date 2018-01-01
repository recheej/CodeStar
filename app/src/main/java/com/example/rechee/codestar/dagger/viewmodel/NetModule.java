package com.example.rechee.codestar.dagger.viewmodel;

import com.example.rechee.codestar.MainScreen.GithubService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by reche on 1/1/2018.
 */

@Module
public class NetModule {
    private static final String GITHUB_BASE_URL = "https://api.github.com/";

    @Provides
    @ViewModelScope
    public GithubService githubService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GithubService.class);
    }
}