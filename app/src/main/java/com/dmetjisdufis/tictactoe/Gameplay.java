package com.dmetjisdufis.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class Gameplay extends AppCompatActivity implements View.OnClickListener{

    private int i, j, player1WinCount, player2WinCount;
    private int[][] state;
    private static final String TAG = "Gameplay";
    TextView message, player1Display, player2Display;
    private String player, player1, player2;
    private Button resetButton, lockButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        player1 = i.getStringExtra("player1");
        player2 = i.getStringExtra("player2");

        player1WinCount = player2WinCount = 0;

        player1Display = findViewById(R.id.firstPlayer);
        player2Display = findViewById(R.id.secondPlayer);
        player1Display.setText(player1 + " : 0");
        player2Display.setText(player2 + " : 0");


        message = findViewById(R.id.textView3);
        message.setText(player1 + "'s turn");
        resetButton = findViewById(R.id.resetButton);
        resetButton.setVisibility(View.INVISIBLE);

        lockButton = findViewById(R.id.lock);
        lockButton.setVisibility(View.INVISIBLE);

        state = new int[4][4];
        player = "circle";
    }



    @Override
    public void onClick(View v) {
        try{
            Button box = (Button) v;
            getIndices(box.getText().toString());

            if(isTaken(i, j))
            {
                message.setText("Can't play an already played move");
            }
            else {
                if(player.equals("circle")){
                    box.setBackgroundResource(R.drawable.circle);
                }
                else{
                    box.setBackgroundResource(R.drawable.cross);
                }

                swapPlayer();
                setTaken(i,j);
            }
        }catch(Exception e){}


    }

    void getIndices(String s){
        int indices = Integer.parseInt(s);
        i = indices/10;
        j = indices%10;
    }

    boolean isTaken(int i, int j){
        if(state[i][j]==0){
            return false;
        }
        else
            return true;
    }

    void setTaken(int r, int c){
        if(player.equals("circle"))
            state[i][j]=2;
        else
            state[i][j]=1;
        //code for checking if a player has won.
        int winner = -1;
        //check for horizontal matches
        for(i=1; i<=3; i++) {
            winner = -1;
            for (j = 1; j <= 3; j++) {
                if (state[i][j] == 1)
                    winner = 1;
                else {
                    winner = -1;
                    break;
                }
            }
            if (winner == 1) {
                message.setText(player1 + " Won!");
                gameEnded();
                return;

            }

            for (j = 1; j <= 3; j++) {
                if (state[i][j] == 2)
                    winner = 2;
                else {
                    winner = -1;
                    break;
                }
            }
            if (winner == 2) {
                message.setText(player2 + " Won!");
                gameEnded();
                return;

            }
        }

        //chekc for vertical matches
        for(i=1; i<=3; i++) {
            winner = -1;
            for(j=1;j<=3; j++) {
                if(state[j][i]==1)
                    winner = 1;
                else
                {
                    winner =  -1;
                    break;
                }
            }
            if(winner==1) {
                message.setText(player1 + " Won!");
                gameEnded();
                return;

            }

            for(j=1;j<=3; j++) {
                if(state[j][i]==2)
                    winner = 2;
                else
                {
                    winner =  -1;
                    break;
                }
            }
            if(winner==2) {
                message.setText(player2 + " Won!");
                gameEnded();
                return;

            }
        }

        //check for diagonals
        //diagonal 1
        if(((state[3][3]+state[1][1])==state[2][2]*2) && state[3][3]!=0 && state[1][1]!=0 && state[2][2]!=0) {
            message.setText((state[1][1]==1? player1:player2) + " Won!");
            gameEnded();
            return;
        }
        //diagonal 2
        if(((state[1][3]+state[2][2])==state[3][1]*2) && state[1][3]!=0 && state[2][2]!=0 && state[3][1]!=0) {
            message.setText((state[1][3]==1? player1:player2) + " Won!");
            gameEnded();
            return;
        }

        //check if all places are filled
        boolean flag = true;
        for(i=1; i<=3; i++)
        {
            for(j=1;  j<=3; j++) {
                if(state[i][j]==0)
                    flag = false;

            }
        }
        if(flag) {
            message.setText("Draw!");
            resetButton.setVisibility(View.VISIBLE);
            lockButton.setVisibility(View.VISIBLE);
        }


        Log.d(TAG, "setTaken: Sat " + i + j);
    }




    private void gameEnded(){
        resetButton.setVisibility(View.VISIBLE);
        lockButton.setVisibility(View.VISIBLE);
        if(player.equals("circle"))
            player2WinCount++;
        else
            player1WinCount++;
        player1Display.setText(player1 + " : " + player1WinCount);
        player2Display.setText(player2 + " : " + player2WinCount);

    }


    private void swapPlayer() {
        if(player.equals("circle")){
            player = "cross";
            message.setText(player2 + "'s turn" );
        }

        else{
            player = "circle";
            message.setText(player1 + "'s turn" );

        }

    }

    public void restart(View v) {
        for (i = 0; i < 4; i++){
            for (j = 0; j < 4; j++){
                state[i][j] = 0;
            }
        }
        player = "circle";
        message.setText("");
        resetButton.setVisibility(View.INVISIBLE);
        lockButton.setVisibility(View.INVISIBLE);


        Button b;
        b = findViewById(R.id.one);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.two);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.three);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.four);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.five);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.six);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.seven);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.eight);
        b.setBackgroundResource(R.color.colorPrimaryDark);

        b = findViewById(R.id.nine);
        b.setBackgroundResource(R.color.colorPrimaryDark);
    }

}
