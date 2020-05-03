package com.example.androidbd.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidbd.ActualizaLogins;
import com.example.androidbd.ActualizarProductos;
import com.example.androidbd.MetodosBaseDatos;
import com.example.androidbd.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Select_Update extends Fragment {
    ListView lista_select_edit;
    ListView lista_select;
    ArrayAdapter<String> adapter_select;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista_select = inflater.inflate(R.layout.select_main,container, false);

        lista_select_edit = vista_select.findViewById(R.id.Lista_productos);

        MetodosBaseDatos mbd = new MetodosBaseDatos(vista_select.getContext(), "App", null, 1);
        SQLiteDatabase db = mbd.getReadableDatabase();
        if (db != null)
        {
            Cursor c = db.rawQuery("SELECT id_productos, nombre_producto, precio FROM Productos",null);
            int cantidad = c.getCount();
            int i=0;
            String[] arreglo = new String[cantidad];
            if (c.moveToFirst())
            {
                do {
                    String linea = "ID: "+c.getInt(0)+"\nProducto: "+c.getString(1)+"\n Precio: " + c.getString(2);
                    arreglo[i]=linea;
                    i++;
                }while (c.moveToNext());
            }
            adapter_select = new ArrayAdapter<String>(vista_select.getContext(), android.R.layout.simple_list_item_1, arreglo);
            lista_select = vista_select.findViewById(R.id.Lista_productos);
            lista_select.setAdapter(adapter_select);
        }

        lista_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent4 = new Intent(getContext(), ActualizarProductos.class);
                Log.i("dato null ?", String.valueOf(SepararIdUpdate(adapter_select.getItem(position).toString())));
                intent4.putExtra("id_actualizar", ""+SepararIdUpdate(adapter_select.getItem(position).toString()));
                startActivity(intent4);
            }
        });

        return vista_select;
    }
    public int SepararIdUpdate(String cadena)
    {
        //id final al que eliminamos
        int idfinal=0;
        //cadenas de ayuda
        String cadena2="";
        //inicio del id
        int inicio = cadena.indexOf(" ");
        inicio= inicio+1;
        int fin = cadena.indexOf("P");
        Log.i("inicio del indice",String.valueOf(inicio));
        Log.i("fin del indice",String.valueOf(fin));
        //transforma la cadena en array de chars
        char[] aux = cadena.toCharArray();
        //traer el id
        for (int j=inicio; j<fin; j++)
        {
            //asignar caracter por caracter
            cadena2 += aux[j];
        }
        //quitar los espacios en blanco
        String aux2=cadena2.trim();
        //intentar castear a entero
        try {
            idfinal = Integer.parseInt(aux2);
            return idfinal;
        }catch (NumberFormatException e){
            return 0;
        }
    }
    public void Refresh()
    {
        lista_select.setAdapter(adapter_select);
    }
}
