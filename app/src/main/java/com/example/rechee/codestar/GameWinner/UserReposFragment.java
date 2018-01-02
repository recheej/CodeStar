package com.example.rechee.codestar.GameWinner;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rechee.codestar.MainScreen.UserNameFormError;
import com.example.rechee.codestar.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rechee on 1/1/2018.
 */

public class UserReposFragment extends Fragment {

    public static final String USERNAME = "username";
    private String username;
    private GameWinnerViewModel viewModel;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private RecyclerView recyclerView;
    private List<Repo> repos;
    private ReposAdapter adapter;

    public static UserReposFragment newInstance(String username){
        UserReposFragment userReposFragment = new UserReposFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        userReposFragment.setArguments(args);
        return userReposFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_repos_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        errorTextView = view.findViewById(R.id.textView_error);
        recyclerView = view.findViewById(R.id.recyclerView_repos);

        repos = new ArrayList<>();
        adapter = new ReposAdapter(repos);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = getArguments().getString(USERNAME);

        viewModel = ViewModelProviders.of(this).get(GameWinnerViewModel.class);

        viewModel.getError().observe(this, new Observer<UserNameFormError>() {
            @Override
            public void onChanged(@Nullable UserNameFormError userNameFormError) {
                progressBar.setVisibility(View.GONE);

                if(userNameFormError != null){
                    switch (userNameFormError.getError()){
                        default:
                            errorTextView.setText(userNameFormError.getErrorMessage());
                            errorTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        viewModel.setUsername(username);
        viewModel.getRepos().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> newRepos) {
                if(newRepos != null){
                    repos.clear();
                    repos.addAll(newRepos);
                }
            }
        });
    }
}
