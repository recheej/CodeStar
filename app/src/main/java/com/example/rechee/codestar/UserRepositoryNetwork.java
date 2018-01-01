package com.example.rechee.codestar;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.rechee.codestar.MainScreen.GithubService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rechee on 1/1/2018.
 */

public class UserRepositoryNetwork implements UserRepository {

    private final GithubService githubService;

    private MutableLiveData<User> userLiveData;

    public UserRepositoryNetwork(GithubService githubService){
        this.githubService = githubService;
    }

    @Override
    public LiveData<User> getUser(String username) {

        userLiveData = new MutableLiveData<>();

        Call<User> call = githubService.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    //we got 200, user is authenticated
                    userLiveData.postValue(response.body());
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
