package com.example.androidbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

public class MetodosBaseDatos extends SQLiteOpenHelper {
    String Users = "CREATE TABLE Users (id_user INTEGER PRIMARY KEY AUTOINCREMENT, nombre_user TEXT, contra TEXT)";
    String Productos ="CREATE TABLE Productos(id_productos INTEGER PRIMARY KEY AUTOINCREMENT, nombre_producto TEXT, precio FLOAT)";

    public MetodosBaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Users);
        db.execSQL("INSERT INTO Users(nombre_user, contra) VALUES('admin','12345')");
        db.execSQL(Productos);
        db.execSQL("INSERT INTO Productos(nombre_producto, precio) VALUES ('Sabritas',10)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
