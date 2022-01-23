package com.dmetjisdufis.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NameSelect extends AppCompatActivity {

    private EditText firstPlayer;
    private EditText secondPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_select);
        firstPlayer = findViewById(R.id.firstPlayer);
        secondPlayer = findViewById(R.id.secondPlayer);

    }

    //method to go to the gameplay activity
    public void goToGameplay(View v){
        Intent i = new Intent(this, Gameplay.class);



        i.putExtra("player1", firstPlayer.getText().toString());
        i.putExtra("player2", secondPlayer.getText().toString());
        startActivity(i);
    }
}
