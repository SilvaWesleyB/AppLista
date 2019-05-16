package com.main.worklist.bdhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BdHelper extends SQLiteOpenHelper {

    public static int VERSION = 2;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";


    public BdHelper(Context context, String nome, SQLiteDatabase.CursorFactory factory, int version ) {
        super(context, NOME_DB, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA_TAREFAS +
                " (id integer PRIMARY KEY AUTOINCREMENT, " +
                " nomeLista varchar (30) NOT NULL," +
                " descLista varchar (50) NOT NULL); ";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela");

        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF  EXISTS " + TABELA_TAREFAS + " ;";

        try {
            db.execSQL(sql);

            onCreate(db);

            Log.i("INFO DB", "Sucesso ao atualizar tabela");


        }catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar a tabela" + e.getMessage());
        }

    }
}
