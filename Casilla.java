/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminasdef;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Casilla {
    private int fila;
    private int columna;
    private boolean esMina;
    private boolean estaMarcada;
    private boolean estaRevelada;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.esMina = false;
        this.estaMarcada = false;
        this.estaRevelada = false;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public boolean esMina() {
        return esMina;
    }

    public void colocarMina() {
        this.esMina = true;
    }

    public boolean estaMarcada() {
        return estaMarcada;
    }

    public void marcar() {
        this.estaMarcada = true;
    }

    public void desmarcar() {
        this.estaMarcada = false;
    }

    public boolean estaRevelada() {
        return estaRevelada;
    }

    public void revelar() {
        this.estaRevelada = true;
    }
}
