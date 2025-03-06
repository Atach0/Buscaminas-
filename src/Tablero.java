/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public final class Tablero {
    Casilla [][] casillas;
    
    int numFilas;
    int numColumnas;
    int numMinas;
    
    public Tablero(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        iniciarCasillas();
    }
    
    public void iniciarCasillas(){
        casillas=new Casilla[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j]=new Casilla(i,j);
                
            }   
        }
    
        generarMinas();
    }
    
    private void generarMinas(){
       int minasGeneradas=0;
       while(minasGeneradas!=numMinas) {
           int posTmpFila=(int) (Math.random()* casillas.length);
           int posTmpColumna=(int) (Math.random()* casillas[0].length);
           if (!casillas[posTmpFila][posTmpColumna].isMina()){
               casillas[posTmpFila][posTmpColumna].setMina(true);
               minasGeneradas++;
           }
       }
    }
    
    private void pruebitaTablero(){
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                System.out.print(casillas[i][j].isMina()? "*":"0");
           }
            System.out.println("");
        }  
    }
    
    public static void main(String[] args){
        Tablero tablero=new Tablero(6,6,5);
        tablero.pruebitaTablero();
    }
    
}


