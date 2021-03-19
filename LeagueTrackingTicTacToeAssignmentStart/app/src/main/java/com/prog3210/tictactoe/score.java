package com.prog3210.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class score  extends AppCompatActivity {


    ListView scoreList;
    TextView view5;

    String[] nameFromDatabase = new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        view5 = findViewById(R.id.view5);

        purpose.texts = new TextView[5];
        purpose.texts[0] = findViewById(R.id.view1);
        purpose.texts[1] = findViewById(R.id.view2);
        purpose.texts[2] = findViewById(R.id.view3);
        purpose.texts[3] = findViewById(R.id.view4);
        purpose.texts[4] = view5;
        purpose.background = findViewById(R.id.backgroundScore);
        scoreList = findViewById(R.id.LVScore);

        updateScoreList();
    }

    private void updateScoreList(){
        ArrayList<HashMap<String, String>> test = purpose.data.getArrayOfPlayers();

        if (purpose.data.getAmountOfPlayers() != 0 || purpose.data.getAmountOfPlayers() != -1) {

            int resource = R.layout.listview_items2;
            String[] from = {"name", "wins", "losses", "ties"};
            int[] to = {R.id.Listitems1, R.id.Listitems2, R.id.Listitems3, R.id.Listitems4};

            SimpleAdapter adapter = new SimpleAdapter(this, test, resource, from, to);
            scoreList.setAdapter(adapter);

            String value = getString(R.string.All) + " " + purpose.data.getAmountOfPlayers() + " " +  getString(R.string.Players);
            view5.setText(value);
        }else{
            view5.setText(getString(R.string.noPlayers));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.score_menu, menu);
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
    }

    @Override
    protected void onResume(){
        super.onResume();
        purpose.changeColor(false);
    }
}
