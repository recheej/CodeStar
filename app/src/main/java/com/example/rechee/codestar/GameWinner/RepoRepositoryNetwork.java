package com.example.rechee.codestar.GameWinner;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.rechee.codestar.Enumerations;
import com.example.rechee.codestar.GithubService;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rechee on 1/1/2018.
 */

public class RepoRepositoryNetwork implements RepoRepository {

    private final GithubService githubService;

    private MutableLiveData<List<Repo>> reposLiveData;
    private MutableLiveData<Enumerations.Error> errorLiveData;

    @Inject
    public RepoRepositoryNetwork(GithubService githubService){
        this.githubService = githubService;
        errorLiveData = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<Repo>> getRepos(String username) {

        reposLiveData = new MutableLiveData<>();

        Call<List<Repo>> call = githubService.getRepos(username);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful()){
                    //we got 200, user is authenticated
                    reposLiveData.postValue(response.body());
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
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                errorLiveData.postValue(Enumerations.Error.NO_INTERNET);
            }
        });

        return reposLiveData;
    }

    @Override
    public LiveData<Enumerations.Error> getError() {
        return errorLiveData;
    }
}
