package com.example.trab3bimandroidstudio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    public Banco(Context context){
        // contexto,nomedobanco,cursor, versão
        super(context, "banco.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlP = "CREATE TABLE produto(codigo integer primary key autoincrement, descr text, unidade int, caloria real);";
        String sqlC = "CREATE TABLE consumo(codigo integer primary key autoincrement, codproduto integer not null, dia integer, mes integer, ano integer, hora integer, minuto integer, qtde real);";
        db.execSQL(sqlP);
        db.execSQL(sqlC);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS produto");
        db.execSQL("DROP TABLE IF EXISTS consumo");
        onCreate(db);
    }
}

