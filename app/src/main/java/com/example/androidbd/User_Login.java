package com.example.androidbd;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class User_Login extends AppCompatActivity {
    EditText n,p;
    Button entrar,r;
    Boolean login_verificar;
    Toolbar barra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicial_login);
        n=findViewById(R.id.edit_text_nombre_login);
        p=findViewById(R.id.edit_text_contra_login);
        r=findViewById(R.id.registro_login_principal);
        entrar=(Button) findViewById(R.id.boton_iniciar_login);
        barra = findViewById(R.id.toolbar_simple);
        setSupportActionBar(barra);
    }

    public void Verificar(View v) {
        if (v.getId() == R.id.boton_iniciar_login)
        {
            login_verificar=false;
            String nombre = n.getText().toString();
            String contra = p.getText().toString();
            MetodosBaseDatos mbd = new MetodosBaseDatos(this, "App", null, 1);
            SQLiteDatabase db = mbd.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT nombre_user, contra FROM Users",null);
            int cantidad = c.getCount();
            int i=0;
            String[] arreglo = new String[cantidad];
            if (c.moveToFirst())
            {
                do {
                    if ((nombre.equals(c.getString(0).toString())) && (contra.equals(c.getString(1).toString())))
                    {
                        login_verificar=true;
                    }
                }while (c.moveToNext());
            }
            if (login_verificar){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            } else{
                Toast.makeText(this,"Error en los datos", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void ModificarLogins(View view_logins)
    {
        Intent intentMod = new Intent(view_logins.getContext(), ActualizaLogins.class);
        startActivity(intentMod);
    }
}
