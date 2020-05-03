package com.example.androidbd;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActualizaLogins extends AppCompatActivity {
    Toolbar barra;
    Button boton1, boton2;
    EditText et1, et2;
    String nam, pass;
    MetodosBaseDatos mbd;
    SQLiteDatabase  db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_logins);
        //barra.findViewById(R.id.toolbar_simple2);
        //setSupportActionBar(barra);
        et1 = findViewById(R.id.edit_text_nombre_login_agregar);
        et2 = findViewById(R.id.edit_text_contra_login_agregar);
        boton1 = findViewById(R.id.boton_agregar_login);
        boton2 = findViewById(R.id.boton_eliminar_login);
        mbd = new MetodosBaseDatos(this, "App", null, 1);
        db = mbd.getWritableDatabase();

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nam = et1.getText().toString();
                pass = et2.getText().toString();
                db.execSQL("INSERT INTO Users (nombre_user, contra) VALUES('"+nam+"','"+pass+"')");
                et1.getText().clear();
                et2.getText().clear();
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nam = et1.getText().toString();
                db.execSQL("DELETE FROM Users WHERE nombre_user='"+nam+"'");
                et1.getText().clear();
                et2.getText().clear();
            }
        });
    }
    public void Verificar(View v) {
        MetodosBaseDatos mbd = new MetodosBaseDatos(v.getContext(), "App", null, 1);
        SQLiteDatabase db = mbd.getReadableDatabase();
        Log.i("base de datos Users",String.valueOf(db));
        if (db != null)
        {
            Cursor c = db.rawQuery("SELECT nombre_user, contra FROM Users",null);
            int cantidad = c.getCount();
            int i=0;
            String[] arreglo = new String[cantidad];
            Log.i("entro en el if ",String.valueOf(cantidad));
            if (c.moveToFirst())
            {
                do {
                    String linea = "Usuario: "+c.getString(0)+"\n Contraseña: " + c.getString(1);
                    arreglo[i]=linea;
                    i++;
                }while (c.moveToNext());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, arreglo);
            ListView lista = v.findViewById(R.id.Lista_productos_eliminar);
            lista.setAdapter(adapter);
        }
    }
}
