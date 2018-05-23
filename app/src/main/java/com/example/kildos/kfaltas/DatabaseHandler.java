package com.example.kildos.kfaltas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kildos on 13/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // nombre de la base de datos
    private static final String DATABASE_NAME = "asignaturasManager";

    // nombre de la tabla de asignaturas
    private static final String TABLA_ASIGNATURAS = "asignaturas";

    // Contacts Table Columns names
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_FALTASMAX = "faltasMax";
    private static final String KEY_FALTASACT = "faltasAct";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREAR_TABLA_ASIGNATURAS = "CREATE TABLE " + TABLA_ASIGNATURAS + "("
                + KEY_NOMBRE + " TEXT PRIMARY KEY," + KEY_FALTASMAX + " INTEGER,"
                + KEY_FALTASACT + " INTEGER" + ")";
        db.execSQL(CREAR_TABLA_ASIGNATURAS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ASIGNATURAS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Anadir nueva asignatura
    void anadirAsignatura(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();

        /*ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, asignatura.getNombre()); // Nombre asignatura
        values.put(KEY_FALTASACT, asignatura.getFaltasAct()); //Faltas act en esa asignatura
        values.put(KEY_FALTASMAX, asignatura.getFaltasMax()); // Faltas max de la asignatura
        // Inserting Row
        db.insert(TABLA_ASIGNATURAS, null, values);*/
        if(!comprobarDuplicado(asignatura.getNombre())) {
            db.execSQL("INSERT INTO " + TABLA_ASIGNATURAS + " ( " + KEY_NOMBRE + " , " + KEY_FALTASMAX + " , " + KEY_FALTASACT + " ) VALUES (\"" + asignatura.getNombre() + "\","
                    + asignatura.getFaltasMax() + "," + asignatura.getFaltasAct() + ")");
            db.close(); // Closing database connection
        }

    }

    // Getting una sola asignatura
    Asignatura getNombre(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLA_ASIGNATURAS, new String[] {KEY_NOMBRE, KEY_FALTASMAX,
                KEY_FALTASACT}, KEY_NOMBRE + "=?",
                new String[] { nombre }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Asignatura asignatura = new Asignatura(cursor.getString(0),
                Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)));
        // return asignatura
        return asignatura;
    }

    // Getting All asignaturas
    public List<Asignatura> getAllasignaturas() {
        List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLA_ASIGNATURAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Asignatura asignatura = new Asignatura();
                asignatura.setNombre((cursor.getString(0)));
                asignatura.setFaltasMax(Integer.parseInt(cursor.getString(1)));
                asignatura.setFaltasAct(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                listaAsignaturas.add(asignatura);
            } while (cursor.moveToNext());
        }

        // return contact list
        return listaAsignaturas;
    }
    public boolean comprobarDuplicado(String nombre){
        List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();
        boolean duplicado=false;
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLA_ASIGNATURAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Asignatura asignatura = new Asignatura();
                asignatura.setNombre((cursor.getString(0)));
                asignatura.setFaltasMax(Integer.parseInt(cursor.getString(1)));
                asignatura.setFaltasAct(Integer.parseInt(cursor.getString(2)));
                // Adding contact to list
                listaAsignaturas.add(asignatura);
                if (asignatura.getNombre().equals(nombre)){
                    duplicado = true;
                    break;
                }
            } while (cursor.moveToNext());
        }

        return duplicado;
    }

    // Updating single contact
    public int cambiarAsignatura(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, asignatura.getNombre());
        values.put(KEY_FALTASMAX, asignatura.getFaltasMax());
        values.put(KEY_FALTASACT, asignatura.getFaltasAct());

        // updating row
        return db.update(TABLA_ASIGNATURAS, values, KEY_NOMBRE + " = ?",
                new String[] { String.valueOf(asignatura.getNombre()) });
    }
    public void sumarFaltas(String nombre,int nuevasFaltas) {
        List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLA_ASIGNATURAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Asignatura asignatura = new Asignatura();
                asignatura.setNombre((cursor.getString(0)));
                asignatura.setFaltasMax(Integer.parseInt(cursor.getString(1)));
                asignatura.setFaltasAct(Integer.parseInt(cursor.getString(2)));
                if (asignatura.getNombre().equals(nombre)){
                    String lineaSQL="UPDATE asignaturas set faltasAct = "+nuevasFaltas+" where nombre="+"\""+asignatura.getNombre()+"\"";
                    db.execSQL(lineaSQL);
                    break;
                }
            } while (cursor.moveToNext());
        }
    }
    public int devolverFaltasActuales(String nombre){
        List<Asignatura> listaAsignaturas = new ArrayList<Asignatura>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLA_ASIGNATURAS;
        int numeroFaltasActuales=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Asignatura asignatura = new Asignatura();
                asignatura.setNombre((cursor.getString(0)));
                asignatura.setFaltasMax(Integer.parseInt(cursor.getString(1)));
                asignatura.setFaltasAct(Integer.parseInt(cursor.getString(2)));
                if (asignatura.getNombre().equals(nombre)){
                        numeroFaltasActuales= asignatura.getFaltasAct();
                    break;
                }
            } while (cursor.moveToNext());
        }
        return numeroFaltasActuales;
    }

    // Deleting single asignatura
    public void borrarAsignatura(Asignatura asignatura) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_ASIGNATURAS, KEY_NOMBRE + " = ?", new String[] { String.valueOf(asignatura.getNombre())});
        db.close();
    }

    // devuelve contador de asignaturas
    public int getAsignatuasCount() {
        String countQuery = "SELECT  * FROM " + TABLA_ASIGNATURAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}