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
    private boolean marcada;
    private boolean revelada;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.esMina = false;
        this.marcada = false;
        this.revelada = false;
    }

   public boolean revelada(){
       return this.revelada;
   }
   
   public void revelar(){
       this.revelada = true;
   }
   
   public boolean marcada(){
       return this.marcada;
   }
   public void setMarcada(boolean marcada){
       this.marcada = marcada;
   }
   
   public void marcar(){
       this.marcada = !this.marcada;
   }
   
   public boolean esMina(){
       return esMina;
   }
   
   public void colocarMina(){
       this.esMina = true;
   }

}
