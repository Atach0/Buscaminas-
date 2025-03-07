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
    private boolean marcada;
    private boolean estaRevelada;
    private boolean descubierta;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.esMina = false;
        this.estaMarcada = false;
        this.marcada = marcada;
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
    
    public boolean Marcada() {
        return marcada;
    }

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }
    
    

    public boolean isEstaMarcada() {
        return estaMarcada;
    }

    public void setEstaMarcada(boolean estaMarcada) {
        this.estaMarcada = estaMarcada;
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

    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }
    
    public boolean isEstaRevelada() {
        return estaRevelada;
    }

    public void setEstaRevelada(boolean estaRevelada) {
        this.estaRevelada = estaRevelada;
    }

    public void revelar() {
        this.estaRevelada = true;
    }
}

