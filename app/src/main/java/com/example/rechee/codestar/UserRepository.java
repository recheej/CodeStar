package com.example.rechee.codestar;

import android.arch.lifecycle.LiveData;

/**
 * Created by Rechee on 1/1/2018.
 */

public interface UserRepository {
    LiveData<User> getUser(String username);
    LiveData<Enumerations.Error> getError();
}
