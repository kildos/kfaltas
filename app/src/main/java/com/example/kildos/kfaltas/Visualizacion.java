package com.example.kildos.kfaltas;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Kildos on 15/11/2017.
 */

public class Visualizacion extends Activity implements View.OnClickListener,View.OnLongClickListener{
    DatabaseHandler db = new DatabaseHandler(this);
    Asignatura asignatura = new Asignatura();
    int sumador=0;
    TextView txtCambios,txtinformativo;
    ConstraintLayout miLayoutPrincipal;
    TableLayout tabla;
    Button b;
    Button btnAceptar;
    String asignaturaSeleccionada;
    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.visualizacion);
        miLayoutPrincipal = (ConstraintLayout) findViewById(R.id.miConstraint);
        tabla = (TableLayout) findViewById(R.id.miTabla);
        txtCambios=(TextView) findViewById(R.id.txtCambios);
        txtinformativo=(TextView) findViewById(R.id.txtInformativo);
        b=(Button) findViewById(R.id.botonPrueba);
        b.setOnClickListener(this);
        btnAceptar=(Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);
        btnAceptar.setOnLongClickListener(this);
        //mostrarDatos();
    }
    public void onStart(){
        super.onStart();
        mostrarDatos();
    }
    public void onClick(View v){
        if(v.getId()==R.id.botonPrueba) {
            Intent principal = new Intent(getApplicationContext(), AndroidBaseDatos.class);
            startActivity(principal);
        }
        else if(v.getId()==R.id.btnAceptar){
            Toast.makeText(this, "Haga una pulsación larga si desea añadir las faltas", Toast.LENGTH_SHORT).show();
        }
        else{
            asignaturaSeleccionada= ((Button) v).getText().toString();
            txtinformativo.setText("Teclee las faltas a modificar en "+asignaturaSeleccionada);
        }
    }
    public boolean onLongClick(View v){
        if(v.getId()==R.id.btnAceptar) {
            sumarFaltas(v);
        }
        return true;
    }
    public void mostrarDatos(){
        Button botonPrueba = new Button(this);
        TextView cuadroTexto = new TextView(this);
        List<Asignatura> asignaturas = db.getAllasignaturas();
        String resultados = "";

        /*tabla.setStretchAllColumns(true);
        tabla.setShrinkAllColumns(true);*/
        tabla.setStretchAllColumns(true);
        tabla.setShrinkAllColumns(true);
       int contadorDeIDs=0;
        TableRow trCabecera = new TableRow(this);
        TextView nombre=new TextView(this);
        nombre.setText("Asignatura");
        nombre.setGravity(Gravity.CENTER);
        TextView faltasactuales=new TextView(this);
        faltasactuales.setText("Faltas actuales");
        faltasactuales.setGravity(Gravity.CENTER);
        TextView faltasmaximas=new TextView(this);
        faltasmaximas.setText("  Faltas máximas");
        faltasmaximas.setGravity(Gravity.CENTER);
        //tabla.removeAllViews();
        trCabecera.addView(nombre);
        trCabecera.addView(faltasactuales);
        trCabecera.addView(faltasmaximas);

        tabla.addView(trCabecera);
        for (Asignatura asig : asignaturas) {
            //resultados = "\t\t" + asig.getFaltasAct() + "\t\t\t\t" + asig.getFaltasMax() + "\n";
            //t.setText(asig.getNombre()+"....."+asig.getFaltasMax()+"....."+asig.getFaltasAct());
            TextView numfaltasactuales=new TextView(this);
            numfaltasactuales.setText(Integer.toString(asig.getFaltasAct()));
            numfaltasactuales.setGravity(Gravity.CENTER);
            TextView numfaltasmaximas=new TextView(this);
            numfaltasmaximas.setText(Integer.toString(asig.getFaltasMax()));
            numfaltasmaximas.setGravity(Gravity.CENTER);
            TableRow tr = new TableRow(this);
            Button btn=new Button(this);
            btn.setText(asig.getNombre());
            btn.setId(contadorDeIDs);
            btn.setOnClickListener(this);
            tr.addView(btn);
            tr.addView(numfaltasactuales);
            tr.addView(numfaltasmaximas);

            tabla.addView(tr);
            contadorDeIDs++;
        }
       // miLayoutPrincipal.addView(tabla);


    }
    public void volverAInicio(View v){

    }
    public void sumarAlBoton(View v){
        //int numeroActual=Integer.parseInt(txtCambios.getText().toString());
        if(v.getId()==R.id.btnSumar){
            sumador++;
            txtCambios.setText(Integer.toString(sumador));

        }
        if(v.getId()==R.id.btnRestar){
            sumador--;
            txtCambios.setText(Integer.toString(sumador));
        }
    }
    public void sumarFaltas(View v){
        int numeroActual=db.devolverFaltasActuales(asignaturaSeleccionada);
        //numeroActual=numeroActual+(sumador);
        if(asignaturaSeleccionada==null){
            Toast.makeText(this, "Debe seleccionar una asignatura!", Toast.LENGTH_SHORT).show();
        }
        else if(sumador==0){
            Toast.makeText(this, "No ha seleccionado faltas!", Toast.LENGTH_SHORT).show();
        }
        else{
            db.sumarFaltas(asignaturaSeleccionada, numeroActual + (sumador));
            finish();
            startActivity(getIntent());
            Toast.makeText(this, "Se han añadido " + sumador + " faltas a " + asignaturaSeleccionada, Toast.LENGTH_SHORT).show();
        }
    }
}
