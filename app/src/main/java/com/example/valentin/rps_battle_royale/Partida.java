package com.example.valentin.rps_battle_royale;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Partida {

    String hora;
    boolean challanged;
    String challengerID;
    String challengedID;
    String paper;
    String scissor;
    String rock;

    public Partida(String hora, boolean challanged, boolean challenger, String challengerID, String challengedID, String paper, String scissor, String rock) {
        this.hora = hora;
        this.challanged = challanged;
        this.challengerID = challengerID;
        this.challengedID = challengedID;
        this.paper = paper;
        this.scissor = scissor;
        this.rock = rock;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isChallanged() {
        return challanged;
    }

    public void setChallanged(boolean challanged) {
        this.challanged = challanged;
    }

    public String getChallengerID() {
        return challengerID;
    }

    public void setChallengerID(String challengerID) {
        this.challengerID = challengerID;
    }

    public String getChallengedID() {
        return challengedID;
    }

    public void setChallengedID(String challengedID) {
        this.challengedID = challengedID;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getScissor() {
        return scissor;
    }

    public void setScissor(String scissor) {
        this.scissor = scissor;
    }

    public String getRock() {
        return rock;
    }

    public void setRock(String rock) {
        this.rock = rock;
    }

    public Partida () {}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("hora", hora);
        result.put("challanged", challanged);
        result.put("challangedID", challengedID);
        result.put("challangerID", challengerID);
        result.put("paper", paper);
        result.put("rock", rock);
        result.put("scissors", scissor);

        return result;
    }
}
