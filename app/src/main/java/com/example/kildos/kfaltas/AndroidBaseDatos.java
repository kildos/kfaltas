package com.example.kildos.kfaltas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * Created by Kildos on 10/11/2017.
 */

public class AndroidBaseDatos extends Activity implements View.OnClickListener{
    DatabaseHandler db = new DatabaseHandler(this);
    TextView t;
    Button btnAgregar,btnBorrar;
    EditText txtAsignatura,txtFaltasMax,txtFaltasAct;
    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.mipruebaTexto);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);
        btnBorrar=(Button) findViewById(R.id.btnBorrar);
        txtAsignatura=(EditText) findViewById(R.id.txtAsignatura);
        txtFaltasMax=(EditText) findViewById(R.id.txtFaltasMax);
        txtFaltasAct=(EditText) findViewById(R.id.txtFaltasAct);
        if (savedInstanceState == null){
            nuevaAsignatura("Acceso a datos",19,0);
            nuevaAsignatura("Intefaces",17,0);
            nuevaAsignatura("Multimedia",15,0);
            nuevaAsignatura("Servicios",8,0);
            nuevaAsignatura("Gestion",10,0);
            nuevaAsignatura("EIE",6,0);
        }
        //btnAgregar.setOnClickListener((View.OnClickListener)this);


        /**
         * CRUD Operations
         * */
        // Inserting Contacts // AQUI EMPIEZA LA ESCRITURA
        /*Log.d("Insert: ", "Inserting ..");

        db.anadirAsignatura(new Asignatura("Acceso_a_datos", 14,19));
        db.anadirAsignatura(new Asignatura("Intefaces", 12,16));
        db.anadirAsignatura(new Asignatura("EIE",4,6));
        db.anadirAsignatura(new Asignatura("Multimedia",8,13));*/
        // Reading all contacts
        //Log.d("Reading: ", "Reading all contacts.."); // AQUI EMPIEZA LA LECTURA

    }
    public void nuevaAsignatura(String nombre,int faltasMax,int faltasAct){
        db.anadirAsignatura(new Asignatura(nombre,faltasMax,faltasAct));
        //Toast.makeText(this, "Asignatura "+nombre+" agregada correctamente a la base de datos :)", Toast.LENGTH_SHORT).show();
        //mostrarTodas();
    }
    public void onClick(View v){
        if(v.getId()==R.id.btnBorrar){
            if (!txtFaltasMax.getText().toString().trim().equals(""))
                Toast.makeText(this, "Para borrar una asignatura, deben estar vacíos los campos de faltas", Toast.LENGTH_SHORT).show();
            else if(!txtFaltasAct.getText().toString().trim().equals(""))
                Toast.makeText(this, "Para borrar una asignatura, deben estar vacíos los campos de faltas", Toast.LENGTH_SHORT).show();
            else{
                borrarAsignatura(txtAsignatura.getText().toString());
            }
        }
    }
    public void mostrarTodas(View v) {
        Intent visualizacion = new Intent(getApplicationContext(), Visualizacion.class);
        startActivity(visualizacion);
        /*List<Asignatura> asignaturas = db.getAllasignaturas();
        String resultados = "";
        for (Asignatura asig : asignaturas) {
            String log = "Nombre: " + asig.getNombre() + " ,Faltas max: " + asig.getFaltasMax() + " ,Faltas act: " + asig.getFaltasAct();
            // Writing Contacts to log
            resultados += asig.getNombre() + "\t\t" + asig.getFaltasMax() + "\t\t" + asig.getFaltasAct() + "\n";
            //t.setText(asig.getNombre()+"....."+asig.getFaltasMax()+"....."+asig.getFaltasAct());
            Log.d("Name: ", log);
        }
        t.setText(resultados);*/
    }
    public void agregarAsignatura(View v){
        if (txtAsignatura.getText().toString().trim().equals(""))
            Toast.makeText(this, "Debes introducir una asignatura", Toast.LENGTH_SHORT).show();
        else if (txtFaltasMax.getText().toString().trim().equals(""))
            Toast.makeText(this, "Debes introducir las faltas maximas", Toast.LENGTH_SHORT).show();
        else if(txtFaltasAct.getText().toString().trim().equals(""))
            Toast.makeText(this, "Debes introducir las faltas actuales", Toast.LENGTH_SHORT).show();
        else if(db.comprobarDuplicado(txtAsignatura.getText().toString())){
            Toast.makeText(this, "ERROR! Ya existe esta asignatura", Toast.LENGTH_SHORT).show();
        }
        else{
            int num1 = Integer.parseInt(txtFaltasMax.getText().toString());
            int num2 = Integer.parseInt(txtFaltasAct.getText().toString());
            nuevaAsignatura(txtAsignatura.getText().toString(), num1, num2);
            Toast.makeText(this, txtAsignatura.getText().toString()+" agregada correctamente a la BBDD :)", Toast.LENGTH_SHORT).show();
        }
    }
    public void borrarAsignatura(String nombre){
        db.borrarAsignatura(new Asignatura(nombre));
    }


}

