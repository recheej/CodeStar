package com.example.rechee.codestar.GameWinner;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.rechee.codestar.CodeStarApplication;
import com.example.rechee.codestar.Enumerations;
import com.example.rechee.codestar.MainScreen.UserNameFormError;
import com.example.rechee.codestar.R;
import com.example.rechee.codestar.dagger.viewmodel.RepositoryModule;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rechee on 1/1/2018.
 */

public class GameWinnerViewModel extends AndroidViewModel {
    private final Context context;
    private LiveData<UserNameFormError> error;
    private LiveData<List<Repo>> repos;
    private MutableLiveData<String> username;

    @Inject
    RepoRepository repoRepository;

    public GameWinnerViewModel(@NonNull Application application) {
        super(application);
        this.context = application;

        ((CodeStarApplication) application)
                .getApplicationComponent()
                .newViewModelComponent(new RepositoryModule())
                .inject(this);

        username = new MutableLiveData<>();

        this.error = Transformations.map(repoRepository.getError(), new Function<Enumerations.Error, UserNameFormError>() {
            @Override
            public UserNameFormError apply(Enumerations.Error input) {
                switch (input){
                    case NO_INTERNET:
                        return new UserNameFormError(input, UserNameFormError.ErrorTarget.GENERAL,
                                context.getString(R.string.no_internet));
                    case RATE_LIMITED:
                        return new UserNameFormError(input, UserNameFormError.ErrorTarget.GENERAL,
                                context.getString(R.string.error_rate_limited));
                    default:
                        return null;
                }
            }
        });

        this.repos = Transformations.switchMap(username, new Function<String, LiveData<List<Repo>>>() {
            @Override
            public LiveData<List<Repo>> apply(String username) {
                return repoRepository.getRepos(username);
            }
        });
    }

    public void setUsername(String usernameValue){
        username.postValue(usernameValue);
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public LiveData<UserNameFormError> getError() {
        return error;
    }
}
