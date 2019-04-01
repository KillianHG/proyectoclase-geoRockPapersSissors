package com.example.valentin.rps_battle_royale;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Partida {


    String userid1;
    String userid2;
    String actionid1;
    String actionid2;
    boolean checked;

    public Partida(String userid1, String userid2, String actionid1, String actionid2, boolean checked) {
        this.userid1 = userid1;
        this.userid2 = userid2;
        this.actionid1 = actionid1;
        this.actionid2 = actionid2;
        this.checked = checked;
    }

    public String getUserid1() {
        return userid1;
    }

    public void setUserid1(String userid1) {
        this.userid1 = userid1;
    }

    public String getUserid2() {
        return userid2;
    }

    public void setUserid2(String userid2) {
        this.userid2 = userid2;
    }

    public String getActionid1() {
        return actionid1;
    }

    public void setActionid1(String actionid1) {
        this.actionid1 = actionid1;
    }

    public String getActionid2() {
        return actionid2;
    }

    public void setActionid2(String actionid2) {
        this.actionid2 = actionid2;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Partida() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userid1", userid1);
        result.put("userid2", userid2);
        result.put("actionid1", actionid1);
        result.put("actionid2", actionid2);
        result.put("checked", checked);

        return result;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "userid1='" + userid1 + '\'' +
                ", userid2='" + userid2 + '\'' +
                ", actionid1='" + actionid1 + '\'' +
                ", actionid2='" + actionid2 + '\'' +
                ", checked=" + checked +
                '}';
    }

    //si jugador1 gana - 1 | si jugador2 gana - 2 | si hay empate - 0
    public int checkWin() {
        if ( (actionid1.equals( "r" )) && (this.actionid2.equals( "s" )) ) {
            return 1;
        }
        if ( (actionid1.equals( "r" )) && (this.actionid2.equals( "r" )) ) {
            return 0;
        }
        if ( (actionid1.equals( "s")) && (actionid2.equals( "p" )) ) {
            return 1;
        }
        if ( (actionid1.equals( "s")) && (actionid2.equals( "s" )) ) {
            return 0;
        }
        if ( (actionid1.equals( "p" )) && (actionid2.equals( "r" )) ) {
            return 1;
        }
        if ( (actionid1.equals( "p" )) && (actionid2.equals( "p" )) ) {
            return 0;
        }

        return 2;
    }
}
