package com.celiogarcia.SendOut.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.celiogarcia.SendOut.modelo.Contacto;

import java.util.ArrayList;

public class MensagemDao {

    private final SetupDataBase database;
    private SQLiteDatabase db;

    public MensagemDao(SetupDataBase sdb) {
        this.database = sdb;
    }

    public void registaMensagem(Contacto contacto, int status, int flag){
        db = database.getWritableDatabase();

        ContentValues dados = new ContentValues();
        dados.put("para", contacto.getNumeroDeTelefone());
        dados.put("sms", contacto.getMensagem());
        dados.put("estado", status);
        dados.put("flag", flag);

        db.insert("mensagens", null, dados);
    }

    public ArrayList<Contacto> mensagens(String numero){
        String sql = "SELECT * FROM mensagens WHERE para = ?";
        db = database.getReadableDatabase();
        Cursor cursor;

        if (numero != null) {
            cursor = db.rawQuery(sql, new String[]{numero});

            ArrayList<Contacto> mensagemDoContacto = new ArrayList<>();
            while (cursor.moveToNext()) {
                Contacto contacto = new Contacto();
                contacto.setMensagem(cursor.getString(cursor.getColumnIndex("sms")));
                contacto.setEstado(cursor.getString(cursor.getColumnIndex("estado")));
                mensagemDoContacto.add(contacto);
            }
            cursor.close();

            return mensagemDoContacto;
        }

        return null;
    }

    public ArrayList<Contacto> historicoDeSms(){
        String sql = "SELECT * FROM mensagens WHERE flag = 0 GROUP BY para ORDER BY id DESC";
        db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Contacto> mensagemDoContacto = new ArrayList<>();
        while (cursor.moveToNext()){
            Contacto contacto = new Contacto();
            contacto.setNumeroDeTelefone(cursor.getString(cursor.getColumnIndex("para")));
            contacto.setMensagem(cursor.getString(cursor.getColumnIndex("sms")));
            mensagemDoContacto.add(contacto);
        }
        cursor.close();

        return mensagemDoContacto;
    }
}
