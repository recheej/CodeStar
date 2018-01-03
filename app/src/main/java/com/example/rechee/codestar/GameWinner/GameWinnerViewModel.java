package com.example.rechee.codestar.GameWinner;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.rechee.codestar.CodeStarApplication;
import com.example.rechee.codestar.dagger.viewmodel.RepositoryModule;

/**
 * Created by Rechee on 1/1/2018.
 */

public class GameWinnerViewModel extends ViewModel {

    private String usernameOne;
    private String usernameTwo;
    private int starCountUsernameOne;
    private int starCountUsernameTwo;

    private MutableLiveData<String> winningUser;
    private MutableLiveData<Void> tieLiveData;

    public GameWinnerViewModel() {
        super();

        winningUser = new MutableLiveData<>();
        tieLiveData = new MutableLiveData<>();
    }

    public void recordCount(String username, int starCount){
        if(usernameOne == null){
            usernameOne = username;
            starCountUsernameOne = starCount;
        }
        else{
            usernameTwo = username;
            starCountUsernameTwo = starCount;

            if(starCountUsernameOne == starCountUsernameTwo){
                tieLiveData.postValue(null);
            }
            else{
                winningUser.postValue(starCountUsernameOne > starCountUsernameTwo ? usernameOne : usernameTwo);
            }
        }
    }

    public LiveData<String> getWinningUser() {
        return winningUser;
    }

    public LiveData<Void> getTie() {
        return this.tieLiveData;
    }
}
