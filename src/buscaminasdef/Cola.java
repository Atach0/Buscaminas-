/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminasdef;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Cola {
    private Nodo primero, ultimo;

    public void encolar(Nodo nodo) {
        if (ultimo != null) {
            ultimo.siguiente = nodo;
        }
        ultimo = nodo;
        if (primero == null) {
            primero = nodo;
        }
    }

    public Nodo desencolar() {
        if (estaVacia()) return null;
        Nodo nodo = primero;
        primero = primero.siguiente;
        if (primero == null) {
            ultimo = null;
        }
        return nodo;
    }

    public boolean estaVacia() {
        return primero == null;
    }
}
