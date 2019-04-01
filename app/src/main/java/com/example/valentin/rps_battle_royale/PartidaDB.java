package com.example.valentin.rps_battle_royale;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

public class PartidaDB implements Serializable {

    String user;
    String opponent;
    String userAction;
    String opponentAction;
    String result;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getOpponentAction() {
        return opponentAction;
    }

    public void setOpponentAction(String opponentAction) {
        this.opponentAction = opponentAction;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
