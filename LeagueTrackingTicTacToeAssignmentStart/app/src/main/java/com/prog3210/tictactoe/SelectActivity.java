package com.prog3210.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SelectActivity extends AppCompatActivity {

    ListView numPlayers;
    TextView amount, CurrentPlayer;
    String[] nameFromDatabase;
    String playerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //toolbar
        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        //number of players
        amount = findViewById(R.id.numOfPlayers);
        amount.append(purpose.data.getAmountOfPlayers() + " " + getString(R.string.Players));
        CurrentPlayer = findViewById(R.id.currPlayer);

        //add to list
        numPlayers = findViewById(R.id.listOfPlayers);
        nameFromDatabase = purpose.printPlayersNames();
        if(nameFromDatabase != null) {
            final List<String> namesArray = new ArrayList<String>(Arrays.asList(nameFromDatabase));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_items, R.id.Listitems, namesArray);
            numPlayers.setAdapter(arrayAdapter);
            numPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    CurrentPlayer.setText(nameFromDatabase[i]);
                    playerName = nameFromDatabase[i];
                    if (purpose.selectingFor) {
                        purpose.playerOne = purpose.data.getPlayerByName(nameFromDatabase[i]);
                        purpose.isPlayerOneSelected = true;
                    } else {
                        purpose.playerTwo = purpose.data.getPlayerByName(nameFromDatabase[i]);
                        purpose.isPlayerTwoSelected = true;
                    } } });

        }else{ amount.setText(getString(R.string.noPlayers)); }



        //print player name
        if(purpose.selectingFor){
            playerName = purpose.playerOne.getName();
            CurrentPlayer.setText(playerName);

        } else {
            playerName = purpose.playerTwo.getName();
            CurrentPlayer.setText(playerName); }

        //Theme
        purpose.texts = new TextView[2];
        purpose.texts[0] = findViewById(R.id.selectView1);
        purpose.texts[1] = findViewById(R.id.selectView2);
        purpose.background = findViewById(R.id.backgroundSelector);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //return
        if(item.getTitle().equals(getString(R.string.re))){
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);



            //add player
        }else if(item.getTitle().equals(getString(R.string.addplay))){
            Intent in = new Intent(this, AddActivity.class);
            startActivity(in);



            //delete player
        }else if (item.getTitle().equals(getString(R.string.selectDelete))) {
            if (!playerName.equals("")) {
                playerDB as = purpose.data.getPlayerByName(playerName);
                purpose.data.deletePlayer(as);

                if(purpose.playerOne.getName().equals(playerName)){ purpose.playerOne = new playerDB(); }
                if(purpose.playerTwo.getName().equals(playerName)){ purpose.playerTwo = new playerDB(); }

                if(purpose.selectingFor) { playerName = purpose.playerOne.getName();
                }else{ playerName = purpose.playerTwo.getName(); }

                populate();
            }else{ purpose.passiveMessage(getString(R.string.Error6), this); }



            //update player name
        }else if(item.getTitle().equals(getString(R.string.selectUpdate))){

            if(!playerName.equals("none")) {
                updateName();
            }else{ purpose.passiveMessage(getString(R.string.Error6), this); }



            //change theme
        } else{ purpose.changeColor(true);  }

        return super.onOptionsItemSelected(item);
    }

    public void updateName(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Player name:");

        final EditText text = new EditText(this);
        builder.setView(text);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(String.valueOf(text.getText()).equals("")){
                    Toast.makeText(SelectActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();

                }else if(String.valueOf(text.getText()).length() <= 3 && String.valueOf(text.getText()).length() > 0) {  Toast.makeText(SelectActivity.this, "Name Too Short", Toast.LENGTH_SHORT).show();

                }else if(String.valueOf(text.getText()).length() >= 10) {  Toast.makeText(SelectActivity.this, "Name Too Long", Toast.LENGTH_SHORT).show();

                }else {


                    String[] names = purpose.printPlayersNames();
                    boolean found = false;
                    for (String x: names){
                        if (x.equals(String.valueOf(text.getText()))){
                            found = true;
                            break;
                        }
                    }

                    if(!found) {
                        if(purpose.selectingFor){
                            purpose.data.updatePlayerName(purpose.playerOne, text.getText().toString());
                            purpose.playerOne = purpose.data.getPlayerByName(String.valueOf(text.getText()));

                        }else{
                            purpose.data.updatePlayerName(purpose.playerTwo, text.getText().toString());
                            purpose.playerTwo = purpose.data.getPlayerByName(String.valueOf(text.getText())); }


                        populate();
                        CurrentPlayer.setText(text.getText().toString());
                        purpose.players = purpose.data.getAllPlayers();

                    }else{  Toast.makeText(SelectActivity.this, "Name Already Exists", Toast.LENGTH_SHORT).show();  }}}});

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) {  dialog.cancel();  } });
        builder.show();
    }

    public void populate(){

        if(purpose.selectingFor) { CurrentPlayer.setText(purpose.playerOne.getName());
        }else{ CurrentPlayer.setText(purpose.playerTwo.getName()); }

        nameFromDatabase = purpose.printPlayersNames();
        String amt = purpose.data.getAmountOfPlayers() + " " + getString(R.string.Players);
        amount.setText(amt);

        if(nameFromDatabase != null) {
            final List<String> namesArray = new ArrayList<String>(Arrays.asList(nameFromDatabase));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.listview_items, R.id.Listitems, namesArray);
            numPlayers.setAdapter(arrayAdapter);
        }else{
            final ArrayAdapter<String> arrayAdapter = null;
            numPlayers.setAdapter(arrayAdapter);
            numPlayers.invalidateViews();  }

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
}