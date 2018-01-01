package com.example.rechee.codestar.MainScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rechee.codestar.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextUsernameOne = findViewById(R.id.username_one);
        final EditText editTextUsernameTwo = findViewById(R.id.username_two);

        Button startGameButton = findViewById(R.id.button_start_game);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameOne = editTextUsernameOne.getText().toString();
                final String usernameTwo = editTextUsernameTwo.getText().toString();

                if(usernameOne.isEmpty() || usernameTwo.isEmpty()){
                    Toast.makeText(MainActivity.this, getString(R.string.usernames_blank_error), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
