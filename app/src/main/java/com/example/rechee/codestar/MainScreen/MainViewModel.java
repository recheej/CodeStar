package com.example.rechee.codestar.MainScreen;

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
import com.example.rechee.codestar.R;
import com.example.rechee.codestar.dagger.viewmodel.RepositoryModule;

import javax.inject.Inject;

/**
 * Created by Rechee on 1/1/2018.
 */

public class MainViewModel extends AndroidViewModel {
    private final Context context;
    private LiveData<UserNameFormError> errorLiveData;
    private MutableLiveData<String> userNameLiveData;
    private UserNameFormError.ErrorTarget currentTarget;

    @Inject
    UserRepository userRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.context = application;

        ((CodeStarApplication) application)
                .getApplicationComponent()
                .newViewModelComponent(new RepositoryModule())
                .inject(this);

        userNameLiveData =  new MutableLiveData<>();

        this.errorLiveData = Transformations.map(userRepository.getError(), new Function<Enumerations.Error, UserNameFormError>() {
            @Override
            public UserNameFormError apply(Enumerations.Error input) {
                switch (input){
                    case INVALID_USERNAME:
                        return new UserNameFormError(input, currentTarget,
                                context.getString(R.string.invalid_username));
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
    }

    public LiveData<User> getUser(String username, UserNameFormError.ErrorTarget target){
        this.currentTarget = target;
        return userRepository.getUser(username);
    }

    public LiveData<UserNameFormError> getError() {
        return errorLiveData;
    }
}
