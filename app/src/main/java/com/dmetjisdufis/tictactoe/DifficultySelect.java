package com.dmetjisdufis.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;

public class DifficultySelect extends AppCompatActivity {
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_select);
    }
    //method to go to single player activity
    public void goToSinglePlayer(View v){
        Intent i = new Intent(this, SinglePlayer.class);
        b = (Button) v;
        if(b.getText().toString().equals("Easy"))
            i.putExtra("difficulty", "easy");
        else
            i.putExtra("difficulty", "difficult");
        startActivity(i);
    }
}
