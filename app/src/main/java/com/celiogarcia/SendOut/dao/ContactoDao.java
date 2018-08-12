package com.celiogarcia.SendOut.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.celiogarcia.SendOut.modelo.Agenda;

import java.util.ArrayList;
import java.util.List;

public class ContactoDao {

    private Context context;
    private SQLiteDatabase db;
    private SetupDataBase dataBase;

    public ContactoDao(Context context, SetupDataBase sdb){
        this.context = context;
        this.dataBase = sdb;
    }

    private List<Agenda> carregaContactosDoTelefone(){

        ArrayList<Agenda> contactos = new ArrayList<>();

        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC");

        if (cur.getCount() > 0) {

            while (cur.moveToNext()) {

                Agenda agenda = new Agenda();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                agenda.setNome(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {

                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        agenda.setNumero(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    }
                    pCur.close();
                }
                contactos.add(agenda);
            }
        }
        cur.close();

        return contactos;
    }

    public void grava(){
        db = dataBase.getWritableDatabase();
        List<Agenda> agendas = carregaContactosDoTelefone();

        for (int i=0; i < agendas.size(); i++){

            ContentValues dados = new ContentValues();
            Agenda agenda = agendas.get(i);

            if (agenda.getNumero() != null) {
                dados.put("nome", agenda.getNome());
                dados.put("numero", agenda.getNumero());
                db.insert("agenda", null, dados);
            }
        }
    }

    public void onActualizaContactos(){

        db = dataBase.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS agenda;";
        String sq2 = "CREATE TABLE agenda (id INTEGER PRIMARY KEY, nome TEXT, numero TEXT);";
        db.execSQL(sql);
        db.execSQL(sq2);
        grava();
    }

    public ArrayList<Agenda> mostraContactos(){
        String sql = "SELECT * FROM agenda";
        db = dataBase.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<Agenda> agendaDeContactos = new ArrayList<>();

        while (cursor.moveToNext()) {
            Agenda agenda = new Agenda();
            agenda.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            agenda.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
            agendaDeContactos.add(agenda);
        }
        cursor.close();

        return agendaDeContactos;
    }

    public Boolean temContactos(){
        String sql = "SELECT COUNT(*) FROM agenda";
        db = dataBase.getReadableDatabase();
        Cursor cursor;

        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        int i = cursor.getInt(0);
        cursor.close();
        if (i > 0){
            return true;
        }else{
            return false;
        }
    }
}
