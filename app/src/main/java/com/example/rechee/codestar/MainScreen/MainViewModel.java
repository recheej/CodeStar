package com.example.rechee.codestar.MainScreen;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.example.rechee.codestar.Enumerations;
import com.example.rechee.codestar.R;

/**
 * Created by Rechee on 1/1/2018.
 */

public class MainViewModel extends ViewModel {
    private LiveData<UserNameFormError> errorLiveData;
    private UserNameFormError.ErrorTarget currentTarget;

    private MutableLiveData<String> username;
    private LiveData<User> user;

    private UserRepository userRepository;
    private MutableLiveData<User> userAsync;

    public MainViewModel(final UserRepository userRepository, final Context applicationContext) {
        super();

        username = new MutableLiveData<>();
        this.userRepository = userRepository;

        this.errorLiveData = Transformations.map(userRepository.getError(), new Function<Enumerations.Error, UserNameFormError>() {
            @Override
            public UserNameFormError apply(Enumerations.Error input) {
                switch (input){
                    case INVALID_USERNAME:
                        return new UserNameFormError(input, currentTarget,
                                applicationContext.getString(R.string.invalid_username));
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

        user = Transformations.switchMap(username, new Function<String, LiveData<User>>() {
            @Override
            public LiveData<User> apply(String input) {
                userAsync = new MutableLiveData<>();

                //do network request in background
                new GetUserTask().execute(new AsyncTaskParam(input, userRepository, userAsync));
                return userAsync;
            }
        });
    }

    public void setUsername(String usernameToSet){
        username.setValue(usernameToSet);
    }

    public LiveData<User> getUser(UserNameFormError.ErrorTarget target){
        this.currentTarget = target;
        return user;
    }

    public LiveData<UserNameFormError> getError() {
        return errorLiveData;
    }

    private class AsyncTaskParam {
        private String username;
        private UserRepository repository;
        private MutableLiveData<User> user;

        private AsyncTaskParam(String username, UserRepository repository, MutableLiveData<User> user) {
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

        public UserRepository getRepository() {
            return repository;
        }

        public MutableLiveData<User> getUser() {
            return user;
        }
    }

    private static class GetUserTask extends AsyncTask<AsyncTaskParam, Void, Void> {

        @Override
        protected Void doInBackground(AsyncTaskParam... params) {
            AsyncTaskParam param = params[0];
            param.getUser().postValue(param.getRepository().getUser(param.getUsername()));
            return null;
        }
    }
}
