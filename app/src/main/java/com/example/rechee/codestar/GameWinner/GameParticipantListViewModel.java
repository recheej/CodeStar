package com.example.rechee.codestar.GameWinner;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.rechee.codestar.CodeStarApplication;
import com.example.rechee.codestar.Enumerations;
import com.example.rechee.codestar.MainScreen.UserNameFormError;
import com.example.rechee.codestar.R;
import com.example.rechee.codestar.dagger.viewmodel.RepositoryModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Rechee on 1/1/2018.
 */

public class GameParticipantListViewModel extends ViewModel {
    private LiveData<UserNameFormError> error;
    private LiveData<List<Repo>> repos;
    private MutableLiveData<String> username;
    private MutableLiveData<List<Repo>> reposAsync;

    private RepoRepository repoRepository;

    public GameParticipantListViewModel(final RepoRepository repoRepository, final Context applicationContext) {
        super();

        this.repoRepository = repoRepository;
        this.username = new MutableLiveData<>();

        this.error = Transformations.map(repoRepository.getError(), new Function<Enumerations.Error, UserNameFormError>() {
            @Override
            public UserNameFormError apply(Enumerations.Error input) {
                switch (input){
                    case NO_INTERNET:
                        return new UserNameFormError(input, UserNameFormError.ErrorTarget.GENERAL,
                                applicationContext.getString(R.string.no_internet));
                    case RATE_LIMITED:
                        return new UserNameFormError(input, UserNameFormError.ErrorTarget.GENERAL,
                                applicationContext.getString(R.string.error_rate_limited));
                    default:
                        return null;
                }
            }
        });

        this.repos = Transformations.switchMap(username, new Function<String, LiveData<List<Repo>>>() {

            @Override
            public LiveData<List<Repo>> apply(String username) {

                reposAsync = new MutableLiveData<>();

                new GetReposTask().execute(new AsyncTaskParam(username, repoRepository, reposAsync));
                return Transformations.map(reposAsync, new Function<List<Repo>, List<Repo>>() {
                    @Override
                    public List<Repo> apply(List<Repo> input) {
                        if(input == null){
                            return new ArrayList<>();
                        }

                        Collections.sort(input, new Comparator<Repo>() {
                            @Override
                            public int compare(Repo repoOne, Repo repoTwo) {
                                return Integer.compare(repoOne.getStargazersCount(), repoTwo.getStargazersCount()) * -1;
                            }
                        });
                        return input;
                    }
                });
            }
        });
    }

    public void setUsername(String usernameValue){
        username.postValue(usernameValue);
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }

    public LiveData<Integer> getTotalStarCount() {
        return Transformations.map(repos, new Function<List<Repo>, Integer>() {
            @Override
            public Integer apply(List<Repo> input) {
                if(input == null){
                    return 0;
                }

                int sum = 0;

                for (Repo repo : input) {
                    sum += repo.getStargazersCount();
                }

                return sum;
            }
        });
    }

    public LiveData<UserNameFormError> getError() {
        return error;
    }

    private class AsyncTaskParam {
        private String username;
        private RepoRepository repository;
        private MutableLiveData<List<Repo>> user;

        private AsyncTaskParam(String username, RepoRepository repository, MutableLiveData<List<Repo>> user) {
            this.username = username;
            this.repository = repository;
            this.user = user;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public RepoRepository getRepository() {
            return repository;
        }

        public MutableLiveData<List<Repo>> getRepos() {
            return user;
        }
    }

    private static class GetReposTask extends AsyncTask<AsyncTaskParam, Void, Void> {

        @Override
        protected Void doInBackground(AsyncTaskParam... params) {
            AsyncTaskParam param = params[0];
            param.getRepos().postValue(param.getRepository().getRepos(param.getUsername()));
            return null;
        }
    }
}
