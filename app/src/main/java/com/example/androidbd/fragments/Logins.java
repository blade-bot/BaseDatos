package com.example.androidbd.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidbd.ActualizaLogins;
import com.example.androidbd.MetodosBaseDatos;
import com.example.androidbd.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Logins extends Fragment {
    Button boton1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista_user = inflater.inflate(R.layout.login_main, container, false);

        MetodosBaseDatos mbd = new MetodosBaseDatos(vista_user.getContext(), "App", null, 1);
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(vista_user.getContext(), android.R.layout.simple_list_item_1, arreglo);
            ListView lista = vista_user.findViewById(R.id.lista_user);
            lista.setAdapter(adapter);
        }
        boton1 = vista_user.findViewById(R.id.boton_modificar_logins);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(v.getContext(), ActualizaLogins.class);
                startActivity(intent3);
            }
        });
        return vista_user;
    }
}
