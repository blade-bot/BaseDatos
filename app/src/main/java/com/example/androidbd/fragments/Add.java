package com.example.androidbd.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidbd.MainActivity;
import com.example.androidbd.MetodosBaseDatos;
import com.example.androidbd.R;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Add extends Fragment {
    EditText et_a_nombre, et_a_precio;
    Button boton_agregar;
    float aux;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista_agregar = inflater.inflate(R.layout.add_main, container, false);

        et_a_nombre = vista_agregar.findViewById(R.id.edit_text_agregar_nombre);
        et_a_precio = vista_agregar.findViewById(R.id.edit_text_agregar_precio);
        boton_agregar = vista_agregar.findViewById(R.id.boton_agregar);
        boton_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux = Float.parseFloat(et_a_precio.getText().toString());
                MetodosBaseDatos mbd = new MetodosBaseDatos(v.getContext(), "App", null, 1);
                SQLiteDatabase db = mbd.getWritableDatabase();
                if (db != null)
                {
                    String product_name = et_a_nombre.getText().toString();
                    float product_precio = Float.parseFloat(et_a_precio.getText().toString());
                    db.execSQL("INSERT INTO Productos(nombre_producto, precio) VALUES ('"+product_name+"',"+product_precio+")");
                }
                Toast.makeText(v.getContext(), "Producto Agregado", Toast.LENGTH_SHORT).show();
                MainActivity ma = new MainActivity();
                ma.BajarTeclado();
                et_a_nombre.getText().clear();
                et_a_precio.getText().clear();
            }
        });

        return vista_agregar;
    }
}