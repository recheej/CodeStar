package com.example.rechee.codestar;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.rechee.codestar.MainScreen.User;
import com.example.rechee.codestar.MainScreen.UserRepository;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rechee on 1/1/2018.
 */

public class UserRepositoryNetwork implements UserRepository {

    private final GithubService githubService;

    private MutableLiveData<User> userLiveData;
    private MutableLiveData<Enumerations.Error> errorLiveData;

    @Inject
    public UserRepositoryNetwork(GithubService githubService){
        this.githubService = githubService;
        errorLiveData = new MutableLiveData<>();
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
                    if(response.code() == 403){
                        if(response.headers().get("X-RateLimit-Remaining") != null){
                            String rateRemaining = response.headers().get("X-RateLimit-Remaining");
                            if(Integer.parseInt(rateRemaining) == 0){
                                errorLiveData.postValue(Enumerations.Error.RATE_LIMITED);
                                return;
                            }
                        }
                    }

                    errorLiveData.postValue(Enumerations.Error.INVALID_USERNAME);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorLiveData.postValue(Enumerations.Error.NO_INTERNET);
            }
        });

        return userLiveData;
    }

    @Override
    public LiveData<Enumerations.Error> getError() {
        return errorLiveData;
    }
}
