package com.prog3210.tictactoe;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class game extends AppCompatActivity implements View.OnClickListener{

    Button[][] aButtons = new Button[3][3];

    private boolean player1Turn = true;
    boolean gameMode = true,
            F = false;
    Boolean[] active = { false, false, false, false, false, false, false, false, false};

    private int roundCount;

    private int player1Points;
    private int player2Points;

    public TextView textViewPlayer1;
    public TextView textViewPlayer2;
    public TextView resultText;
    Button buttonReset;

    public ConstraintLayout background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Toolbar toolbarr = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarr);

        textViewPlayer2 = findViewById(R.id.text_view_p2);
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        resultText = findViewById(R.id.text_result);

        aButtons[0][0] = findViewById(R.id.button00);
        aButtons[0][1] = findViewById(R.id.button01);
        aButtons[0][2] = findViewById(R.id.button02);
        aButtons[1][0] = findViewById(R.id.button10);
        aButtons[1][1] = findViewById(R.id.button11);
        aButtons[1][2] = findViewById(R.id.button12);
        aButtons[2][0] = findViewById(R.id.button20);
        aButtons[2][1] = findViewById(R.id.button21);
        aButtons[2][2] = findViewById(R.id.button22);
        for(int i = 0; i < 3; i++){
            for(int j =  0; j < 3; j++){
                aButtons[i][j].setOnClickListener(this);
            }
        }
        buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameMode)
                    resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v){

        if(gameMode) {
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
            if (((Button) v).getText().toString().equals("")) {
                if (player1Turn) {
                    ((Button) v).setText("X");
                } else {
                    ((Button) v).setText("O");
                }
            }
            roundCount++;

            if (checkForWin()) {
                if (F) {
                    if (player1Turn) {
                        player1Wins();
                    } else {
                        player2Wins();
                    }
                }
            } else if (roundCount == 9) {

                purpose.data.playerTies(purpose.playerTwo, purpose.playerOne);
                resultText.setText(getResources().getString(R.string.draw));
                gameMode = false;
            } else {
                player1Turn = !player1Turn;
            }
        }else {
            ((Button) buttonReset).setText(getResources().getString(R.string.newGame));
            purpose.updatePlayers();
            F = false;
        }

    }

    private boolean checkForWin(){
        String[][] field = new String[3][3];
        for (int i = 0; i < 9; i++) {
            if (!active[i]){  F = true; }
        }

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                field[i][j] = aButtons[i][j].getText().toString();

            }
        }
        for (int i = 0; i < 3; i++){
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;

            }
        }
        for (int i = 0; i < 3; i++){
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }

        return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("");
    }

private void player1Wins(){
player1Points++;
    purpose.data.playerWin(purpose.playerOne,purpose.playerTwo);
resultText.setText(getResources().getString(R.string.p1Wins));
updatePointsText();
gameMode = false;
}

private  void player2Wins(){
player2Points++;
    purpose.data.playerWin(purpose.playerTwo, purpose.playerOne);
resultText.setText(getResources().getString(R.string.p2Wins));
updatePointsText();
gameMode = false;

}

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }


    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                aButtons[i][j].setText("");
                active[i] = false;
                F = false;
            }
        }
        buttonReset.setText(getResources().getString(R.string.reset));
        gameMode = true;
        roundCount = 0;
        resultText.setText("");
        player1Turn = true;
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        purpose.changeColor(false);
    }

    @Override
    public void onBackPressed() {
        if (!gameMode) {
            message("Match is not over yet: you will lose match data", this);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.game_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals(getString(R.string.reset))){
            resetBoard();
        }else if(item.getTitle().equals(getString(R.string.re))){
            if (!gameMode) {
                message("Match is not over yet: you will lose match data", this);

            }else{
                purpose.returning = false;
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);
            }
        } else if(item.getTitle().equals(getString(R.string.checkScore))){
            purpose.updatePlayers();
            String val = purpose.playerOne.getName() + ": Wins(" + purpose.playerOne.getWins() + ") Losses: (" + purpose.playerOne.getLosses() + ")\n";
            val += purpose.playerTwo.getName() + ": Wins(" + purpose.playerTwo.getWins() + ") Losses: (" + purpose.playerTwo.getLosses() + ")";
            purpose.passiveMessage(val, this);
        }
        else{
            purpose.changeColor(true);
        }
        return super.onOptionsItemSelected(item);
    }


    public void message(String x, final Context con){
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(x);


        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in = new Intent(con, MainActivity.class);
                startActivity(in);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}