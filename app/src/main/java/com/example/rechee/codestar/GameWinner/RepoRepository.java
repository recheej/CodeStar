package com.example.rechee.codestar.GameWinner;

import android.arch.lifecycle.LiveData;

import com.example.rechee.codestar.Enumerations;

import java.util.List;

/**
 * Created by Rechee on 1/1/2018.
 */

public interface RepoRepository {
    List<Repo> getRepos(String username);
    LiveData<Enumerations.Error> getError();
}
