package com.example.kildos.kfaltas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;

import static com.example.kildos.kfaltas.R.styleable.View;

/**
 * Created by Kildos on 15/11/2017.
 */

public class Inicio extends Activity implements View.OnClickListener {
    Button btnanadir,btnvisualizar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        btnanadir = (Button) findViewById(R.id.btnanadir);
        btnanadir.setId(1);
        btnvisualizar=(Button) findViewById(R.id.btnvisualizar);
        btnvisualizar.setId(2);

    }
    public void onClick(View v){
        if (v.getId()==1) {
            Intent agregar = new Intent(getApplicationContext(), AndroidBaseDatos.class);
            startActivity(agregar);
        }
        else{
            Intent visualizacion = new Intent(getApplicationContext(), Visualizacion.class);
            startActivity(visualizacion);
        }
    }


}
