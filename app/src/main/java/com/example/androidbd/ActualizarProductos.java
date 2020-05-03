package com.example.androidbd;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActualizarProductos extends AppCompatActivity {

    Toolbar barra;
    TextView tv1, tv2;
    EditText et1, et2;
    Button boton;
    String id_dato;
    MetodosBaseDatos mbd;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizar_productos);
        barra = findViewById(R.id.toolbar_simple);
        setSupportActionBar(barra);
        tv1 = findViewById(R.id.text_view_nombre);
        tv2 = findViewById(R.id.text_view_precio);
        et1 = findViewById(R.id.edit_text_productos_actualizar_nombre);
        et2 = findViewById(R.id.edit_text_productos_actualizar_precio);
        boton = findViewById(R.id.boton_actualizar_producto);
        ObtenerDatos();
        mbd = new MetodosBaseDatos(this, "App", null, 1);
        db = mbd.getReadableDatabase();
        if (db != null)
        {
            Log.i("valor idatos",String.valueOf(id_dato));
            Cursor c = db.rawQuery("SELECT id_productos, nombre_producto, precio FROM Productos WHERE id_productos=?", new String[]{id_dato});
            if (c.moveToFirst())
            {
                do {
                    tv1.setText(c.getString(1));
                    tv2.setText(c.getString(2));
                    et1.setText(c.getString(1));
                    et2.setText(c.getString(2));
                }while (c.moveToNext());
            }
        }
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = mbd.getWritableDatabase();
                db.execSQL("UPDATE Productos SET nombre_producto='"+et1.getText().toString()+"', precio='"+et2.getText().toString()+"' WHERE nombre_producto='"+tv1.getText().toString()+"'");
                Intent intent5 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent5);
            }
        });
    }
    private void ObtenerDatos(){
        Bundle extras = getIntent().getExtras();
        id_dato = extras.getString("id_actualizar");

    }
}
