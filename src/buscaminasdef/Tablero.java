/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminasdef;

/**
 *
 * @author Santiago, Fernando y Anthony
 */

/**
 * Clase que representa el tablero del juego Buscaminas.
 */

public final class Tablero {
    private int filas, columnas, minas; // Dimensiones y numero de minas
    private Casilla[][] casillas; // Matriz de casillas
    private boolean[][] esMina; // Matriz que indica en que lugar hay minas
    private int[][] minasAdyacentes; // Matriz con la cantidad de minas adyacentes
   
    public void marcarCasilla(int x, int y){
        if (!casillas[x][y].revelada()){
            casillas[x][y].marcar();
        }
    }
    
    public boolean estaMarcada(int x, int y) {
        return casillas[x][y].marcada();
    }

    /**
     * Constructor de la clase Tablero.
     * Inicializa el tablero con el número de filas, columnas y minas especificado.
     */
    
    public Tablero(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.esMina = new boolean[filas][columnas];
        this.minasAdyacentes = new int[filas][columnas];
       
        // Crear la matriz de casillas
        casillas = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
        
        //Para que las minas se generen si la cantidad es suparior  0
        if (minas > 0){
            generarMinas();
            colocarMinasEnCasillas();
            calcularMinasAdyacentes();
        }
  }
    
    /**
     *
     * @param x
     * @param y
     * @param marcada
     */
    
    /**
     * Método que marca o desmarca una casilla con una bandera.
     */
    
    public void marcarCasilla(int x, int y, boolean marcada) {
        if (casillas[x][y].revelada()) {
            return; 
        }
        casillas[x][y].setMarcada(marcada);
    }  
    
    /**
     * Método que busca y devuelve una casilla en una posición específica.
     */
    
    public Casilla buscarCasilla(int fila, int columna) {
        if (fila >= 0 && fila < casillas.length && columna >= 0 && columna < casillas[0].length) {
            return casillas[fila][columna];
        }
        return null; // Casilla fuera de rango
    }   
    
    /**
     * Obtiene el estado de una casilla para guardar la partida.
     */
    
    public String obtenerEstadoCasilla(int fila, int columna) {
        Casilla casilla = buscarCasilla(fila, columna); // Método para encontrar la casilla en el grafo
        if (casilla == null) return "E"; // Si no encuentra la casilla, la considera vacía

        if (casilla.esMina()) return "M";
        if (casilla.marcada()) return "B";
        if (casilla.revelada()) return "R";
        return "E"; // "E" significa vacío
    }

    /**
     * Carga el estado de una casilla desde un archivo guardado.
     */
    
    public void cargarEstadoCasilla(int fila, int columna, String estado) {
        Casilla casilla = buscarCasilla(fila, columna);
        if (casilla == null) return;

        if (estado.equals("M")) casilla.colocarMina();
        if (estado.equals("B")) casilla.marcar();
        if (estado.equals("R")) casilla.revelar();
    }
    
    /**
     * Método que revela una casilla en el tablero.
     */
    
    public void descubrirCasilla(int x, int y) {
        if (x >= 0 && x < filas && y >= 0 && y < columnas) {
            casillas[x][y].revelar(); //Ahora se deberian revelar las casillas
        }
    }
    
    /**
     * Método que coloca minas en posiciones aleatorias del tablero.
     */
    
    private void colocarMinas(int cantidadMinas) {
        int filas = casillas.length;
        int columnas = casillas[0].length;
        int minasColocadas = 0;
        
        // Coloca las minas de manera aleatoria
        while (minasColocadas < cantidadMinas) {
            int fila = (int) (Math.random() * filas);
            int columna = (int) (Math.random() * columnas);

            // Verificar si ya hay una mina en la casilla
            if (!casillas[fila][columna].esMina()) {
                casillas[fila][columna].colocarMina();
                minasColocadas++;
            }
        }
    }
    
    /**
     * Método que genera minas en el tablero de manera aleatoria.
     */
    
    private void generarMinas() {
        int colocadas = 0;
        while (colocadas < minas) {
            int x = (int) (Math.random() * filas);
            int y = (int) (Math.random() * columnas);
            if (!esMina[x][y]) {
                esMina[x][y] = true;
                colocadas++;
            }
        }
    }

    /**
     * Calcula cuántas minas hay alrededor de cada casilla.
     */
    
    public void calcularMinasAdyacentes() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!esMina[i][j]) {
                    minasAdyacentes[i][j] = contarMinasAdyacentes(i, j);
                }
            }
        }
    }

    /**
     * Cuenta cuántas minas hay en las casillas adyacentes a (x, y).
     */
    public int contarMinasAdyacentes(int x, int y) {
        int contador = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < filas && ny < columnas && esMina[nx][ny]) {
                    contador++;
                }
            }
        }
        return contador;
    }
    
    /**
     * Verifica si todas las minas han sido marcadas correctamente.
     */
    
    public boolean todasMinasMarcadas() {
        int minasMarcadas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (esMina[i][j] && casillas[i][j].marcada()) {
                    minasMarcadas++;
                }
            }
        }
        return minasMarcadas == minas;
    }
    
    /**
     * Coloca las minas generadas en las casillas correspondientes.
     */

    
    public void colocarMinasEnCasillas(){
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){
                if (esMina[i][j]) {
                    casillas[i][j].colocarMina();
                }
            }
        }
    }
    
    /**
     * Verifica si el jugador ha ganado el juego.
     */
    
    public boolean verificarVictoria() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Casilla casilla = casillas[i][j];
                if (!casilla.esMina() && !casilla.revelada()) {
                    return false; // Aún hay casillas sin descubrir
                }
            }
        }
        return true; // Todas las casillas sin minas están descubiertas
    }

    // A partir de acá son métodos auxiliares para obtener información del tablero
    public boolean esMina(int x, int y) {
        return esMina[x][y];
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
    
    public Casilla getCasilla(int x, int y) {
        return casillas[x][y]; // Devuelve la casilla en la posición (x, y)
    }
    
    //Esto coloca las minas al cargarlas
    public void setEsMina(int x, int y, boolean esMina){
        this.esMina[x][y]= esMina;
    }
    
    //Esto es para tener el numero de minas 
    public int getMinas(){
        return this.minas;
    }
    
    
}



