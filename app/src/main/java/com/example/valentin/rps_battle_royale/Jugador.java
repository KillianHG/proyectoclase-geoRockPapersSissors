package com.example.valentin.rps_battle_royale;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Jugador {
    String latitud;
    String longitud;
    String direccion;

    public Jugador(String latitud, String longitud, String direccion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Jugador() {}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("latitud", latitud);
        result.put("longitud", longitud);
        result.put("direccion", direccion);

        return result;
    }
}
