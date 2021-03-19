package com.prog3210.tictactoe;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    Button  btStart, btView, btAdd, btSelect1, btSelect2;
    TextView errMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        btSelect1 = findViewById(R.id.BtSelectPlayer1);
        btSelect2 = findViewById(R.id.BtSelectPlayer2);
        btStart = findViewById(R.id.BtStartGame);
        btView = findViewById(R.id.BtViewScore);
        btAdd = findViewById(R.id.BtAddPlay);
        errMessage = findViewById(R.id.errMessage);

        btStart.setOnClickListener(this);
        btView.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btSelect1.setOnClickListener(this);
        btSelect2.setOnClickListener(this);

        purpose.texts = new TextView[2];
        purpose.texts[0] = errMessage;
        purpose.store11 = PreferenceManager.getDefaultSharedPreferences(this);
        purpose.background = findViewById(R.id.background1);

    }

    @Override
    public void onClick(View view){

        //score view
        if(view.getId() == btView.getId()){
            Intent in = new Intent(this, score.class);
            startActivity(in);


            //add a player
        }else if(view.getId() == btAdd.getId()){
            Intent in = new Intent(this, AddActivity.class);
            startActivity(in);


            //select One
        }else if(view.getId() == btSelect1.getId()){
            purpose.selectingFor = true;
            Intent in = new Intent(this, SelectActivity.class);
            startActivity(in);


            //select two
        }else if(view.getId() == btSelect2.getId()){
            purpose.selectingFor = false;
            Intent in = new Intent(this, SelectActivity.class);
            startActivity(in);


            //Start game
        }else if(view.getId() == btStart.getId()){

            if(purpose.playerOne.getName().equals(purpose.playerTwo.getName()) && purpose.isPlayerOneSelected && purpose.isPlayerTwoSelected){
                errMessage.setText(getString(R.string.Error7));

            } else if(purpose.isPlayerOneSelected && purpose.isPlayerTwoSelected && !purpose.playerOne.getName().equals(purpose.playerTwo.getName())){
                Intent in = new Intent(this, game.class);
                startActivity(in);

            }else{ errMessage.setText(getString(R.string.Error5)); }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals(getString(R.string.theme))){
            MainActivity m = new MainActivity();
            purpose.changeColor(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        purpose.setUpData(this);

        //setting names
        try {
            String one = getString(R.string.select_One) + ": " + purpose.playerOne.getName();
            String two = getString(R.string.select_Two) + ": " + purpose.playerTwo.getName();
            btSelect1.setText(one);
            btSelect2.setText(two);

        }catch (Exception e){
            String one = getString(R.string.select_One) + ": none_";
            String two = getString(R.string.select_Two) + ": none_";
            btSelect1.setText(one);
            btSelect2.setText(two);
        }

        errMessage.setText(getString(R.string.mainTitle));
        purpose.texts = new TextView[2];
        purpose.texts[0] = errMessage;
        purpose.store11 = PreferenceManager.getDefaultSharedPreferences(this);
        purpose.background = findViewById(R.id.background1);
        purpose.changeColor(false);
    }
}