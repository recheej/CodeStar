package com.example.rechee.codestar.MainScreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.rechee.codestar.Enumerations;
import com.example.rechee.codestar.GithubService;
import com.example.rechee.codestar.MainScreen.User;
import com.example.rechee.codestar.MainScreen.UserRepository;

import java.io.IOException;

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
    public User getUser(String username) {

        Call<User> call = githubService.getUser(username);

        try{
            Response response = call.execute();

            if(response.isSuccessful()){
                //we got 200, user is authenticated
                return (User) response.body();
            }
            else{
                if(response.code() == 403){
                    if(response.headers().get("X-RateLimit-Remaining") != null){
                        String rateRemaining = response.headers().get("X-RateLimit-Remaining");
                        if(Integer.parseInt(rateRemaining) == 0){
                            errorLiveData.postValue(Enumerations.Error.RATE_LIMITED);
                            return null;
                        }
                    }
                }

                errorLiveData.postValue(Enumerations.Error.INVALID_USERNAME);
            }
        }
        catch (IOException ioException){
            errorLiveData.postValue(Enumerations.Error.NO_INTERNET);
        }

        return null;
    }

    @Override
    public LiveData<Enumerations.Error> getError() {
        return errorLiveData;
    }
}
