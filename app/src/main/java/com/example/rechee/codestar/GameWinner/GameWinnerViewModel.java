package com.example.rechee.codestar.GameWinner;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.rechee.codestar.CodeStarApplication;
import com.example.rechee.codestar.dagger.viewmodel.RepositoryModule;

/**
 * Created by Rechee on 1/1/2018.
 */

public class GameWinnerViewModel extends AndroidViewModel {

    private String usernameOne;
    private String usernameTwo;
    private int starCountUsernameOne;
    private int starCountUsernameTwo;

    private MutableLiveData<String> winningUser;

    public GameWinnerViewModel(@NonNull Application application) {
        super(application);

        ((CodeStarApplication) application)
                .getApplicationComponent()
                .newViewModelComponent(new RepositoryModule())
                .inject(this);
        winningUser = new MutableLiveData<>();
    }

    public void recordCount(String username, int starCount){
        if(usernameOne == null){
            usernameOne = username;
            starCountUsernameOne = starCount;
        }
        else{
            usernameTwo = username;
            starCountUsernameTwo = starCount;
            winningUser.postValue(starCountUsernameOne > starCountUsernameTwo ? usernameOne : usernameTwo);
        }
    }

    public LiveData<String> getWinningUser() {
        return winningUser;
    }
}
