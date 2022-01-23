package com.dmetjisdufis.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Launcher extends AppCompatActivity {

    private Button startGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        startGame = findViewById(R.id.startGame);

    }

    //method to go to name select activity  for offline Multiplayer
    public void goToNameSelect(View v){
        Intent i = new Intent(this, NameSelect.class);
        startActivity(i);
    }

    //method to go to difficulty select activity for Single Player
    public void goToDifficultySelect(View v){
        Intent i = new Intent(this, DifficultySelect.class);
        startActivity(i);
    }

    //Method to quit the game
    public void quit(View v){
        finish();
        System.exit(0);
    }

    //Method to go to the statistics activity
    public void goToStatistics(View v){
        Intent i = new Intent(this, Statistics.class);
        startActivity(i);
    }
}
