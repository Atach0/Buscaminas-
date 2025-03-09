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
 * Clase que representa el barrido usando BFS y DFS
 */
public class Busqueda {
    
    /**
     * Método principal para realizar el barrido de casillas.
     * @param tablero - Objeto del tablero de juego.
     * @param x - Coordenada X de la casilla inicial.
     * @param y - Coordenada Y de la casilla inicial.
     * @param usarBFS - Booleano para determinar si se usa BFS (true) o DFS (false).
     */
    
    public static void barrer(Tablero tablero, int x, int y, boolean usarBFS) {
        // Matriz para marcar las casillas ya visitadas
        boolean[][] visitado = new boolean[tablero.getFilas()][tablero.getColumnas()];
        
        // Llamar al método correspondiente según el algoritmo seleccionado
        if (usarBFS) {
            barrerBFS(tablero, x, y, visitado);
        } else {
            barrerDFS(tablero, x, y, visitado);
        }
    }

    /**
     * Método para realizar el barrido utilizando BFS (Búsqueda en Anchura).
     * Usa una cola para explorar las casillas de manera ordenada.
     */
    
    private static void barrerBFS(Tablero tablero, int x, int y, boolean[][] visitado) {
        Cola cola = new Cola();
        cola.encolar(new Nodo(x, y));
        visitado[x][y] = true;
        tablero.descubrirCasilla(x,y); 

        while (!cola.estaVacia()) {
            Nodo nodo = cola.desencolar();
            int i = nodo.x, j = nodo.y;
            
            // Si la casilla no tiene minas adyacentes, expande la búsqueda
            if (tablero.contarMinasAdyacentes(i, j) == 0) {
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue;
                        int nx = i + dx, ny = j + dy;
                        
                        // Verifica que la nueva casilla esté dentro del tablero y no haya sido visitada
                        if (nx >= 0 && ny >= 0 && nx < tablero.getFilas() && ny < tablero.getColumnas()
                                && !visitado[nx][ny]) {
                            visitado[nx][ny] = true;
                            tablero.descubrirCasilla(nx, ny);
                            cola.encolar(new Nodo(nx, ny)); // Agrega la casilla a la cola para seguir explorando
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Método para realizar el barrido utilizando DFS (Búsqueda en Profundidad).
     * Usa una pila para explorar primero las casillas más profundas.
     */
    
    private static void barrerDFS(Tablero tablero, int x, int y, boolean[][] visitado) {
        Pila pila = new Pila();
        pila.apilar(new Nodo(x, y));
        visitado[x][y] = true;
        tablero.descubrirCasilla(x,y);

        while (!pila.estaVacia()) {
            Nodo nodo = pila.desapilar();
            int i = nodo.x, j = nodo.y;
            tablero.descubrirCasilla(i,j);

            // Si la casilla no tiene minas adyacentes, expande la búsqueda
            if (tablero.contarMinasAdyacentes(i, j) == 0) {
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = i + dx, ny = j + dy;
                        
                        // Verifica que la nueva casilla esté dentro del tablero y no haya sido visitada
                        if (nx >= 0 && ny >= 0 && nx < tablero.getFilas() && ny < tablero.getColumnas()
                                && !visitado[nx][ny]) {
                            visitado[nx][ny] = true;
                            tablero.descubrirCasilla(nx, ny);
                            pila.apilar(new Nodo(nx, ny)); // Agrega la casilla a la pila para seguir explorando
                        }
                    }
                }
            }
        }
    }
}
