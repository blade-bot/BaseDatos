package com.example.androidbd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.androidbd.fragments.Add;
import com.example.androidbd.fragments.Delete;
import com.example.androidbd.fragments.Logins;
import com.example.androidbd.fragments.Select_Update;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar tool;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        drawer = findViewById(R.id.drawable);
        navigationView = findViewById(R.id.navview);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, tool,  R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new Select_Update());
        fragmentTransaction.commit();

        navigationView.setNavigationItemSelectedListener(this);
        //DatosIniciales(this.navigationView);
    }
    public void DatosIniciales(View view_datos)
    {
        MetodosBaseDatos mbd = new MetodosBaseDatos(this, "App", null, 1);
        SQLiteDatabase db = mbd.getWritableDatabase();
        if (db != null)
        {
            ContentValues valores = new ContentValues();
            valores.put("nombre_user", "admin");
            valores.put("contra", "admin");
            long i = db.insert("Users", null, valores);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.option_select)
        {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Select_Update());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.option_add)
        {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Add());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.option_delete)
        {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Delete());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.option_logins)
        {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Logins());
            fragmentTransaction.commit();
        }
        return false;
    }
    public void BajarTeclado(){
        View view = this.getCurrentFocus();
        if (view!=null)
        {
            InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
