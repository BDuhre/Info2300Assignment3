package com.prog3210.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.List;


public class purpose extends Fragment  {

    public static Boolean themes;
    public static SharedPreferences store11;
    public static SharedPreferences.Editor editor;

    private OnFragmentInteractionListener mListener;

    public static TextView[] texts = new TextView[5];
    public static ConstraintLayout background = null;
    public static EditText nam = null;

    public static playerDB_Access data;
    public static List<playerDB> players;
    public static String grab;

    //players
    public static playerDB playerOne = new playerDB();
    public static playerDB playerTwo  = new playerDB();
    public static Boolean isPlayerOneSelected = false;
    public static Boolean isPlayerTwoSelected = false;
    public static Boolean selectingFor = true;
    public static Boolean returning = false;


    public static void changeColor(Boolean todo){

        try {
            String th = store11.getString("Theme", "false");
            themes = Boolean.parseBoolean(th);
            if(todo){themes = !themes;}

        }catch(Exception e){ texts[0].setText(e.toString()); }


        for (TextView text : texts) {
            if (text != null) {
                if (themes) {
                    text.setTextColor(Color.rgb(255, 255, 255));
                    background.setBackgroundColor(Color.rgb(46, 45, 45));
                    if(nam != null){nam.setTextColor(Color.rgb(255, 255, 255));}


                } else {
                    text.setTextColor(Color.rgb(0, 0, 0));
                    background.setBackgroundColor(Color.rgb(0, 128, 128));
                    if(nam != null){nam.setTextColor(Color.rgb(0, 0, 0)); } } } }

        String theme2 = String.valueOf(themes);
        editor = store11.edit();
        editor.putString("Theme", theme2);
        editor.apply();
    }

    public static String[] printPlayersNames(){
        players = data.getAllPlayers();
        grab = "";

        for (playerDB x: players){
            grab += x.getName() + ",";
        }
        if(!grab.equals("")) {
            return grab.substring(0, grab.length() - 1).split(",", 10);
        }else{
            return null;
        }
    }

    public static void passiveMessage(String x, Context con){
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setTitle(x);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) { } });
        builder.show();
    }

    public static void setUpData(Context con){
        data = new playerDB_Access(con);

        if(data.getAmountOfPlayers() == 0 || data.getAmountOfPlayers() == -1){
            data.addPlayer(new playerDB("todd", 0, 0, 0));
            data.addPlayer(new playerDB("trey", 0, 0, 0));
            data.addPlayer(new playerDB("susan", 0, 0, 0));
            data.addPlayer(new playerDB("mike", 0, 0, 0));
            data.addPlayer(new playerDB("debbie", 0, 0, 0));
            data.addPlayer(new playerDB("steve", 0, 0, 0));
            data.addPlayer(new playerDB("catherine", 0, 0, 0));
            data.addPlayer(new playerDB("ted", 0, 0, 0));
            data.addPlayer(new playerDB("amy", 0, 0, 0));
            data.addPlayer(new playerDB("raj", 0, 0, 0));
        }

        players = com.prog3210.tictactoe.purpose.data.getAllPlayers();
    }

    public static void updatePlayers(){
        players = data.getAllPlayers();
        playerOne = data.getPlayerByName(playerOne.getName());
        playerTwo = data.getPlayerByName(playerTwo.getName());
    }


    public static com.prog3210.tictactoe.purpose newInstance(String param1, String param2) {
        com.prog3210.tictactoe.purpose fragment = new com.prog3210.tictactoe.purpose();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
