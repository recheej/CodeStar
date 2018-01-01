package com.example.rechee.codestar.GameWinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rechee.codestar.R;

public class GameWinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_winner);

        Intent intent = getIntent();
        String userIdOne = intent.getStringExtra("usernameOne");
        String userIdOTwo = intent.getStringExtra("usernameTwo");
    }
}
