package com.example.rechee.codestar;

import com.example.rechee.codestar.MainScreen.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Rechee on 1/1/2018.
 */

public interface GithubService {
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);
}
