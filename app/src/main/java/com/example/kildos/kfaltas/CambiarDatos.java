package com.example.kildos.kfaltas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Kildos on 22/11/2017.
 */

public class CambiarDatos extends Activity implements View.OnClickListener{
    DatabaseHandler db = new DatabaseHandler(this);
    Asignatura asignatura = new Asignatura();
    String nombreAsignatura="";
    TextView txtNombre;
    Button sumar;
    int faltasActuales;

    public CambiarDatos(){}
    public CambiarDatos(String nombre){
        this.nombreAsignatura=nombre;

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiardatos);
        txtNombre= (TextView) findViewById(R.id.txtAsignatura);

        sumar=(Button) findViewById(R.id.btnSumar);
        sumar.setOnClickListener(this);

    }
    public void onClick(View v){
        txtNombre.setText(asignatura.getNombre());
    }
    /*public void onStart(){
        super.onStart();
        txtNombre.setText(nombreAsignatura);
    }*/
    /*public void setNombreAsignatura(String nombre){
        nombreAsignatura=nombre;
        //txtNombre.setText('1');
    }*/

}
