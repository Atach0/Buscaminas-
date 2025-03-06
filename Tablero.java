/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminasdef;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Tablero {
    private int filas, columnas, minas;
    private Casilla[][] casillas; // Matriz de casillas
    private boolean[][] esMina;
    private int[][] minasAdyacentes;
    private boolean[][] descubiertas;
    private boolean[][] marcadas; // Nueva matriz para casillas marcadas

    public Tablero(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.esMina = new boolean[filas][columnas];
        this.minasAdyacentes = new int[filas][columnas];
        this.descubiertas = new boolean[filas][columnas];
        this.marcadas = new boolean[filas][columnas]; // Inicializar matriz
        generarMinas();
        calcularMinasAdyacentes();
        
        casillas = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
        colocarMinas(minas);
    }
    
    public Casilla buscarCasilla(int fila, int columna) {
        if (fila >= 0 && fila < casillas.length && columna >= 0 && columna < casillas[0].length) {
            return casillas[fila][columna];
        }
        return null; // Casilla fuera de rango
    }   
    
    public String obtenerEstadoCasilla(int fila, int columna) {
        Casilla casilla = buscarCasilla(fila, columna); // Método para encontrar la casilla en el grafo
        if (casilla == null) return "E"; // Si no encuentra la casilla, la considera vacía

        if (casilla.esMina()) return "M";
        if (casilla.estaMarcada()) return "B";
        if (casilla.estaRevelada()) return "R";
        return "E"; // "E" significa vacío
    }

    public void cargarEstadoCasilla(int fila, int columna, String estado) {
        Casilla casilla = buscarCasilla(fila, columna);
        if (casilla == null) return;

        if (estado.equals("M")) casilla.colocarMina();
        if (estado.equals("B")) casilla.marcar();
        if (estado.equals("R")) casilla.revelar();
    }
    
    private void colocarMinas(int cantidadMinas) {
        int filas = casillas.length;
        int columnas = casillas[0].length;
        int minasColocadas = 0;

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

    private void calcularMinasAdyacentes() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!esMina[i][j]) {
                    minasAdyacentes[i][j] = contarMinasAdyacentes(i, j);
                }
            }
        }
    }

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
    
    public boolean estaMarcada(int x, int y) {
        return marcadas[x][y];
    }

    public void marcarCasilla(int x, int y) {
        if (!descubiertas[x][y]) { // Solo se puede marcar si no está descubierta
            marcadas[x][y] = !marcadas[x][y]; // Alternar marcado
        }
    }

    public boolean todasMinasMarcadas() {
        int minasMarcadas = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (esMina[i][j] && marcadas[i][j]) {
                    minasMarcadas++;
                }
            }
        }
        return minasMarcadas == minas;
    }

    public boolean esMina(int x, int y) {
        return esMina[x][y];
    }
    
    

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
}


