package com.artiomtaliaronak.ipr1ppo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    EditText editTextUsername;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        buttonStart = findViewById(R.id.buttonStart);
        editTextUsername = findViewById(R.id.editTextUsername);

        databaseHelper = new DatabaseHelper(this);
    }

    public void startGame(View view) {
        if (editTextUsername.getText().toString().length() > 1){
            User.activeUser.setUsername(editTextUsername.getText().toString());
            GameView gameView = new GameView(this);
            setContentView(gameView);
        } else {
            Toast.makeText(this, "username too short", Toast.LENGTH_SHORT).show();
        }
    }

    public void openLeaderboard(View view){
        Intent intent = new Intent(this, GameOver.class);
        startActivity(intent);
    }

    public boolean checkUsername(){
        String username = editTextUsername.getText().toString();
        if (username.length() == 0){
            return false;
        } else {
            return true;
        }
    }

}