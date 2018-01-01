package com.example.rechee.codestar.MainScreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rechee.codestar.R;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextUsernameOne = findViewById(R.id.username_one);
        final EditText editTextUsernameTwo = findViewById(R.id.username_two);

        Button startGameButton = findViewById(R.id.button_start_game);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        final TextView errorTextView = findViewById(R.id.textView_error);

        final TextInputLayout userNameOneInputLayout = findViewById(R.id.text_layout_username_one);
        final TextInputLayout userNameTwoInputLayout = findViewById(R.id.text_layout_username_two);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        viewModel.getError().observe(this, new Observer<UserNameFormError>() {
            @Override
            public void onChanged(@Nullable UserNameFormError userNameFormError) {
                progressBar.setVisibility(View.GONE);

                if(userNameFormError != null){
                    switch (userNameFormError.getError()){
                        case INVALID_USERNAME:
                            if(userNameFormError.getTarget() == UserNameFormError.ErrorTarget.USERNAME_ONE){
                                userNameOneInputLayout.setError(userNameFormError.getErrorMessage());
                            }
                            else{
                                userNameTwoInputLayout.setError(userNameFormError.getErrorMessage());
                            }
                            break;
                        default:
                            errorTextView.setText(userNameFormError.getErrorMessage());
                            errorTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameOne = editTextUsernameOne.getText().toString();
                final String usernameTwo = editTextUsernameTwo.getText().toString();

                errorTextView.setVisibility(View.GONE);

                if(usernameOne.isEmpty() || usernameTwo.isEmpty()){
                    Toast.makeText(MainActivity.this, getString(R.string.usernames_blank_error), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                if(Objects.equals(usernameOne, usernameTwo)){
                    Toast.makeText(MainActivity.this, getString(R.string.error_usernames_equal), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                viewModel.setUserName(usernameOne, UserNameFormError.ErrorTarget.USERNAME_ONE);
                viewModel.getUserID().observe(MainActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        viewModel.setUserName(usernameTwo, UserNameFormError.ErrorTarget.USERNAME_TWO);
                        viewModel.getUserID().observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(@Nullable Integer integer) {

                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                });
            }
        });
    }
}
