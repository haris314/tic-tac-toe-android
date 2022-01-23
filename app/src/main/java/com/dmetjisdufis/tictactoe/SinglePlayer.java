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

import java.util.Random;

public class SinglePlayer extends AppCompatActivity implements View.OnClickListener{

    private int i, j, player1WinCount, player2WinCount, winner;
    private int[][] state;
    private static final String TAG = "SinglePlayer";
    TextView message, player1Display, player2Display;
    private String player, player1, player2, difficulty;
    private Button resetButton, lockButton;
    private boolean gameEndedFlag;

    StatisticsDBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        db = new StatisticsDBHandler(this);
        Intent i = getIntent();
        player1 = "You";
        player2 = "Opponent";
        difficulty = i.getStringExtra("difficulty");

        player1WinCount = player2WinCount = 0;
        gameEndedFlag=false;

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

    private void displayState(){
        int r,c;
        System.out.println("Printing the state of the game");;
        for(r=1; r<=3; r++){
            for(c=1;c<=3; c++)
                System.out.print(state[r][c] + " ");

            System.out.print("\n");
        }
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
                box.setBackgroundResource(R.drawable.circle);
                message.setText("");
                setTaken(i,j);
                gameEnded();
                if(!gameEndedFlag)
                    computerMove();
                    gameEnded();
            }
        }catch(Exception e){}
        displayState();

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

    /**
     * This method sets (r,c) element and then checks for win
     * @param r Row number
     * @param c Column number
     */
    void setTaken(int r, int c){
        if(player.equals("circle"))
            state[r][c]=1;
        else
            state[r][c]=2;
        //code for checking if a player has won.
        winner = -1;
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
                return;
            }
        }

        //check for diagonals
        //diagonal 1
        if(((state[3][3]+state[1][1])==state[2][2]*2) && state[3][3]!=0 && state[1][1]!=0 && state[2][2]!=0) {
            winner = state[1][1]==1? 1 : 2;
            return;
        }
        //diagonal 2
        if(((state[1][3]+state[2][2])==state[3][1]*2) && state[1][3]!=0 && state[2][2]!=0 && state[3][1]!=0) {
            winner = state[1][3]==1? 1 : 2 ;
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
            winner = 0;
        }

    }




    private void gameEnded(){
        if(gameEndedFlag)
            return;
        if(winner==-1)
            return;
        if(winner==1){
            message.setText(player1 + " Won!");
            if(difficulty.equals("easy")){
                db.increment(getString(R.string.wonE));
            }
            else{
                db.increment(getString(R.string.wonD));
            }
            player1WinCount++;
        }
        else if(winner==2){
            message.setText(player2 + " Won!");
            if(difficulty.equals("easy")){
                db.increment(getString(R.string.lostE));
            }
            else{
                db.increment(getString(R.string.lostD));
            }
            player2WinCount++;
        }
        else{
            message.setText("Draw");
            if(difficulty.equals("easy")){
                db.increment(getString(R.string.drawE));
            }
            else{
                db.increment(getString(R.string.drawD));
            }

        }


        resetButton.setVisibility(View.VISIBLE);
        lockButton.setVisibility(View.VISIBLE);

        player1Display.setText(player1 + " : " + player1WinCount);
        player2Display.setText(player2 + " : " + player2WinCount);

        gameEndedFlag=true;

    }


    private void computerMove()  {
        player = "cross";
        int r, c;
        int drawCheck=0;
        for(r=1;r<4;r++)
            for(c=1;c<4;c++)
                if(state[r][c]==0)
                    drawCheck++;
        if(drawCheck==0)
            return;

        Button b ;
        if(difficulty.equals("easy")){
            generateRandom();
        }
        else{
            boolean moveMade = false;
            player="cross";
            for(r=1;r<4; r++){
                for(c=1; c<4; c++){
                    if(isTaken(r,c))
                        continue;
                    setTaken(r,c);
                    if(winner==2){
                        i=r;
                        j=c;
                        moveMade=true;
                        break;
                    }
                    else{
                        state[r][c]=0;
                    }
                }
                if(moveMade)
                    break;
            }
            if(!moveMade){
                player = "circle";
                for(r=1; r<=3; r++){
                    for(c=1; c<=3; c++){

                        if(!isTaken(r,c)){
                            setTaken(r,c);
                            if(winner==1){
                                player = "cross";
                                setTaken(r,c);
                                i=r;
                                j=c;
                                moveMade=true;
                                break;
                            }
                            else
                                state[r][c] = 0;
                        }
                    }
                    if(moveMade)
                        break;
                }
            }

            if(!moveMade)
                generateRandom();
        }

        player = "cross";

        if(i==1 && j==1)
            b = findViewById(R.id.one);
        else if(i==1 && j==2)
            b = findViewById(R.id.two);
        else if(i==1 && j==3)
        b = findViewById(R.id.three);
        else if(i==2 && j==1)
            b = findViewById(R.id.four);
        else if(i==2 && j==2)
        b = findViewById(R.id.five);
        else if(i==2 && j==3)
            b = findViewById(R.id.six);
        else if(i==3 && j==1)
        b = findViewById(R.id.seven);
        else if(i==3 && j==2)
            b = findViewById(R.id.eight);
        else
            b = findViewById(R.id.nine);

        b.setBackgroundResource(R.drawable.cross);
        setTaken(i,j);
        player = "circle";
    }

    private void generateRandom(){
        Random rand = new Random();
        do{
            i = rand.nextInt(3) + 1;
            j= rand.nextInt(3) + 1;
            Log.d(TAG, "computerMove: i = " + i + " j = "+j);
        }while(isTaken(i,j));
    }

    public void restart(View v) {
        for (i = 0; i < 4; i++){
            for (j = 0; j < 4; j++){
                state[i][j] = 0;
            }
        }
        player = "circle";
        message.setText("");
        gameEndedFlag=false;
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
