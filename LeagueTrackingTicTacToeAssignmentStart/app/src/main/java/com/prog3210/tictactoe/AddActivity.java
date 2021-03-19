package com.prog3210.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AddActivity extends AppCompatActivity implements  View.OnClickListener {

    EditText playerName;
    Button btnAdd;
    TextView errMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);

        playerName = findViewById(R.id.playerName);
        btnAdd = findViewById(R.id.BtnAdding);
        errMessage = findViewById(R.id.AddErrMess);

        //Theme
        purpose.texts = new TextView[2];
        purpose.texts[0] = errMessage;
        purpose.background = findViewById(R.id.backgroundAdd);
        purpose.nam = playerName;

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view.getId() == btnAdd.getId() && String.valueOf(playerName.getText()).equals("")){
            errMessage.setText(getString(R.string.Error1));

        }else if(String.valueOf(playerName.getText()).length() <= 3 && String.valueOf(playerName.getText()).length() > 0) {
            errMessage.setText(getString(R.string.Error3));

        }else if(String.valueOf(playerName.getText()).length() >= 10) {
            errMessage.setText(getString(R.string.Error4));

        } else {

            playerDB player = purpose.data.getPlayerByName(String.valueOf(playerName.getText()));

            if(player == null) {
                playerDB newPlayer = new playerDB(String.valueOf(playerName.getText()), 0, 0, 0);
                purpose.data.addPlayer(newPlayer);
                String x = "\'" + playerName.getText() + "\' Successfully Added";
                errMessage.setText(x);
                playerName.setText("");

            }else{  errMessage.setText(getString(R.string.Error2));  }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals(getString(R.string.re))){
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
        }
        else{
            purpose.changeColor(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause(){
        super.onPause();
        purpose.nam = null;
    }

    @Override
    protected void onResume(){
        super.onResume();
        purpose.changeColor(false);
    }
}