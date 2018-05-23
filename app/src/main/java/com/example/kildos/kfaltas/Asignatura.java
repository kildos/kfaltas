package com.example.kildos.kfaltas;

/**
 * Created by Kildos on 13/11/2017.
 */

public class Asignatura {
    //variables de la asignatura
    //int _id;
    String _nombre;
    int  _faltasMax;
    int _faltasAct;

    // Constructor vacio
    public Asignatura(){
    }
    // constructor completo con nombre de asignatura, faltas maximas de la misma y faltas actuales en esa asig
    public Asignatura(String nombre, int faltasMax,int faltasAct){
        //this._id = id;
        this._nombre = nombre;
        this._faltasMax = faltasMax;
        this._faltasAct= faltasAct;
    }
    public Asignatura(String nombre){
        this._nombre=nombre;

    }

    // constructor
    /*public Asignatura(String nombre, int faltasMax){
        this._nombre = nombre;
        this._faltasMax = faltasMax;
    }*/
    // get ID
   /* public int getID(){
        return this._id;
    }*/

    // set id
    /*public void setID(int id){
        this._id = id;
    }*/

    // get nombre
    public String getNombre(){
        return this._nombre;
    }

    // set nombre
    public void setNombre(String nombre){
        this._nombre = nombre;
    }

    // get faltas max
    public int getFaltasMax(){
        return this._faltasMax;
    }

    // set faltasMax
    public void setFaltasMax(int faltasMax){
        this._faltasMax = faltasMax;
    }

    // set faltasActuales
    public void setFaltasAct(int faltasAct){
        this._faltasAct = faltasAct;
    }

    // get faltas actuales
    public int getFaltasAct(){
        return this._faltasAct;
    }

}
