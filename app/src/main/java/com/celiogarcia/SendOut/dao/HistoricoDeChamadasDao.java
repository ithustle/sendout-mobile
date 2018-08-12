package com.celiogarcia.SendOut.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.celiogarcia.SendOut.modelo.HistoricoChamadas;

import java.util.ArrayList;

public class HistoricoDeChamadasDao {
    private final SetupDataBase database;
    private SQLiteDatabase db;

    public HistoricoDeChamadasDao(SetupDataBase sdb) {
        this.database = sdb;
    }

    public void registaChamada(HistoricoChamadas chamadas){
        db = database.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("numero", chamadas.getNumero());
        dados.put("data_chamada", chamadas.getDataChamada());
        dados.put("tempo_duracao", chamadas.getTempoDeChamada());

        db.insert("historico_chamadas", null, dados);
    }

    public ArrayList<HistoricoChamadas> todasAsChamadas(){
        String sql = "SELECT * FROM historico_chamadas ORDER BY id DESC LIMIT 50";
        db = database.getReadableDatabase();
        Cursor cursor;

        cursor = db.rawQuery(sql, null);

        ArrayList<HistoricoChamadas> alChamadas = new ArrayList<>();
        while (cursor.moveToNext()) {
            HistoricoChamadas ch = new HistoricoChamadas();
            ch.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
            ch.setDataChamada(cursor.getString(cursor.getColumnIndex("data_chamada")));
            ch.setTempoDeChamada(cursor.getString(cursor.getColumnIndex("tempo_duracao")));
            alChamadas.add(ch);
        }
        cursor.close();

        return alChamadas;

    }
}
