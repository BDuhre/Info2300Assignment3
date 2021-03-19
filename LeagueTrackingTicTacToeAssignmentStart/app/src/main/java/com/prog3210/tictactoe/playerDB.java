package com.prog3210.tictactoe;

public class playerDB {
    public int _id;
    public String name;
    public int wins;
    public int losses;
    public int ties;

    public playerDB(){
        _id = -1;
        name = "none";
        wins = -1;
        losses = -1;
        ties = -1;
    }

    public playerDB(int _id, String name, int wins, int losses, int ties){
        this._id = _id;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public playerDB(String name, int wins, int losses, int ties){
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }
}
