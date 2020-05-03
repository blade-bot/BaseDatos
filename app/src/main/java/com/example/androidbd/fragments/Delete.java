package com.example.androidbd.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidbd.MetodosBaseDatos;
import com.example.androidbd.R;

import java.io.IOError;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Delete extends Fragment {
    ListView lista_eliminar;
    ArrayAdapter<String> adapter_delete;
    ListView lista_delete;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View vista_eliminar = inflater.inflate(R.layout.delete_main,container,false);
        lista_eliminar = vista_eliminar.findViewById(R.id.Lista_productos_eliminar);

        final MetodosBaseDatos mbd = new MetodosBaseDatos(vista_eliminar.getContext(), "App", null, 1);
        final SQLiteDatabase db = mbd.getReadableDatabase();
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
            adapter_delete = new ArrayAdapter<String>(vista_eliminar.getContext(), android.R.layout.simple_list_item_1, arreglo);
            lista_delete = vista_eliminar.findViewById(R.id.Lista_productos_eliminar);
            lista_delete.setAdapter(adapter_delete);
        }

        lista_delete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int eliminar_id = SepararId(adapter_delete.getItem(position).toString());
                db.execSQL("DELETE FROM Productos WHERE id_productos="+eliminar_id);
                Refresh();
                Toast.makeText(view.getContext(),"Producto Eliminado",Toast.LENGTH_SHORT).show();
            }});

        return vista_eliminar;
    }
    public int SepararId(String cadena)
    {
        //id final al que eliminamos
        int idfinal=0;
        //cadenas de ayuda
        String cadena2="";
        //inicio del id
        int inicio = cadena.indexOf(" ");
        inicio= inicio+1;
        int fin = cadena.indexOf("P");
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
        lista_delete.setAdapter(adapter_delete);
    }
}
