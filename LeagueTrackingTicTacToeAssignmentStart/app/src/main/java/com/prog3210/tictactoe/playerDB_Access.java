package com.prog3210.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class playerDB_Access extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "playersManager";
    private static final String TABLE = "players";

    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String WINS = "wins";
    private static final String LOSSES = "losses";
    private static final String TIES = "ties";

    public playerDB_Access(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_PLAYER = "CREATE TABLE " + TABLE +
                " (" +
                ID + " INTEGER PRIMARY KEY NOT NULL," +
                NAME + " TEXT," +
                WINS + " INTEGER," +
                LOSSES + " INTEGER," +
                TIES + " INTEGER)";

        db.execSQL(CREATE_PLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addPlayer(playerDB player){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
//        vals.put(ID, player.get_id());
        vals.put(NAME, player.getName());
        vals.put(WINS, player.getWins());
        vals.put(LOSSES, player.getLosses());
        vals.put(TIES, player.getTies());

        db.insert(TABLE, null, vals);
        db.close();
    }

    public playerDB getPlayerByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cur = db.query(TABLE, new String[]{ID, NAME, WINS, LOSSES, TIES}, NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, "1");

        if (cur != null){
            cur.moveToFirst();
        }

        playerDB player = null;
        try {
            player = new playerDB(Integer.parseInt(cur.getString(0)), cur.getString(1), Integer.parseInt(cur.getString(2)), Integer.parseInt(cur.getString(3)), Integer.parseInt(cur.getString(4)));

        } catch (Exception e){
            return null;
        }

        return player;
    }

    public List<playerDB> getAllPlayers(){
        List<playerDB> players = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE + " ORDER BY " + WINS + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                playerDB player = new playerDB();
                player.set_id(Integer.parseInt(cursor.getString(0)));
                player.setName(cursor.getString(1));
                player.setWins(Integer.parseInt(cursor.getString(2)));
                player.setLosses(Integer.parseInt(cursor.getString(3)));
                player.setTies(Integer.parseInt(cursor.getString(4)));

                players.add(player);
            }while(cursor.moveToNext());
        }

        return players;
    }

    public void playerWin(playerDB player, playerDB player2){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, player.get_id());
        values.put(NAME, player.getName());
        values.put(WINS, player.getWins() + 1);
        values.put(LOSSES, player.getLosses());
        values.put(TIES, player.getTies());

        db.update(TABLE, values, ID + "=?", new String[]{String.valueOf(player.get_id())});


        ContentValues values2 = new ContentValues();
        values2.put(ID, player2.get_id());
        values2.put(NAME, player2.getName());
        values2.put(WINS, player2.getWins());
        values2.put(LOSSES, player2.getLosses() + 1);
        values2.put(TIES, player2.getTies());

        db.update(TABLE, values2, ID + "=?", new String[]{String.valueOf(player2.get_id())});
    }

    public void playerTies(playerDB player1, playerDB player2){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, player1.get_id());
        values.put(NAME, player1.getName());
        values.put(WINS, player1.getWins());
        values.put(LOSSES, player1.getLosses());
        values.put(TIES, player1.getTies() + 1);
        db.update(TABLE, values, ID + "=?", new String[]{String.valueOf(player1.get_id())});

        ContentValues values2 = new ContentValues();
        values2.put(ID, player2.get_id());
        values2.put(NAME, player2.getName());
        values2.put(WINS, player2.getWins());
        values2.put(LOSSES, player2.getLosses());
        values2.put(TIES, player2.getTies() + 1);
        db.update(TABLE, values2, ID + "=?", new String[]{String.valueOf(player2.get_id())});
    }

    public  void deletePlayer(playerDB player){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, ID + "=?",
                new String[]{String.valueOf(player.get_id())});
        db.close();
    }

    public int getAmountOfPlayers(){
        int k = 0;
        String amount = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(amount, null);
        k = cur.getCount();
        cur.close();

        return  k;
    }

    public ArrayList<HashMap<String, String>> getArrayOfPlayers(){
        ArrayList<HashMap<String, String>> test = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY " + WINS + " DESC",null );

        while (cursor.moveToNext()) {
            HashMap<String, String> meme = new HashMap<String, String>();
            meme.put("name", cursor.getString(1));
            meme.put("wins", cursor.getString(2));
            meme.put("losses", cursor.getString(3));
            meme.put("ties", cursor.getString(4));
            test.add(meme);
        }

        cursor.close();
        db.close();

        return test;
    }

    public boolean updatePlayerName(playerDB player, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, player.get_id());
        values.put(NAME, name);
        values.put(WINS, player.getWins());
        values.put(LOSSES, player.getLosses());
        values.put(TIES, player.getTies());

        db.update(TABLE, values, ID + "=?", new String[]{String.valueOf(player.get_id())});
        return true;
    }
}
