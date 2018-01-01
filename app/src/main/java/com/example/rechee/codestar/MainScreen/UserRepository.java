package com.example.rechee.codestar.MainScreen;

import android.arch.lifecycle.LiveData;

import com.example.rechee.codestar.Enumerations;

/**
 * Created by Rechee on 1/1/2018.
 */

public interface UserRepository {
    LiveData<User> getUser(String username);
    LiveData<Enumerations.Error> getError();
}
