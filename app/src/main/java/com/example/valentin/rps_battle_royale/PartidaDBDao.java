package com.example.valentin.rps_battle_royale;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PartidaDBDao {
    @Query("select * from PartidaDB")
    LiveData<List<PartidaDB>> getPartida();

    @Insert
    void addHistory(PartidaDB partida);

    @Insert
    void addHistorys(List<PartidaDB> partida);

    @Delete
    void deleteHistory(PartidaDB partida);

    @Query("DELETE FROM PartidaDB")
    void deleteHistory();
}

