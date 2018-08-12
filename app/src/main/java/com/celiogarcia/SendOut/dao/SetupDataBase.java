package com.celiogarcia.SendOut.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.celiogarcia.SendOut.helper.DatabaseHelper;

public class SetupDataBase extends SQLiteOpenHelper {

    public static final String DATABASE = "Mensagens";
    public static final int DB_VERSION = 2;

    private static final String SQL_CRIA_TABELA_AGENDA = "CREATE TABLE " + DatabaseHelper.TabelaAgenda.TABELA_AGENDA +
            "(id INTEGER PRIMARY KEY, " + DatabaseHelper.TabelaAgenda.COLUNA_NOME +
            ", " + DatabaseHelper.TabelaAgenda.COLUNA_NUMERO_AGENDA + ");";

    private static final String SQL_CRIA_TABELA_MENSAGENS = "CREATE TABLE " + DatabaseHelper.TabelaMensagens.TABELA_MENSAGENS +
            "(id INTEGER PRIMARY KEY, " + DatabaseHelper.TabelaMensagens.COLUNA_PARA +
            ", " + DatabaseHelper.TabelaMensagens.COLUNA_SMS +
            ", " + DatabaseHelper.TabelaMensagens.COLUNA_DATASMS +
            ", " + DatabaseHelper.TabelaMensagens.COLUNA_ESTADO +
            ", " + DatabaseHelper.TabelaMensagens.COLUNA_FLAG + ");";

    private static final String SQL_CRIA_TABELA_HISTORICO_CHAMADAS = "CREATE TABLE " + DatabaseHelper.TabelaHistoricoChamada.TABELA_HISTORICO_CHAMADAS +
            "(id INTEGER PRIMARY KEY, " + DatabaseHelper.TabelaHistoricoChamada.COLUNA_NUMERO_HISTORICO +
            ", " + DatabaseHelper.TabelaHistoricoChamada.COLUNA_DATA_CHAMADAS +
            ", " + DatabaseHelper.TabelaHistoricoChamada.COLUNA_TEMPO_DURACAO + ");";

    public SetupDataBase(Context context) {
        super(context, DATABASE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CRIA_TABELA_AGENDA);
        db.execSQL(SQL_CRIA_TABELA_MENSAGENS);
        db.execSQL(SQL_CRIA_TABELA_HISTORICO_CHAMADAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_CRIA_TABELA_HISTORICO_CHAMADAS);
    }
}
