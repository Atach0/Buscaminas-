/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminasdef;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Pila {
    private Nodo tope;

    public void apilar(Nodo nodo) {
        nodo.siguiente = tope;
        tope = nodo;
    }

    public Nodo desapilar() {
        if (estaVacia()) return null;
        Nodo nodo = tope;
        tope = tope.siguiente;
        return nodo;
    }

    public boolean estaVacia() {
        return tope == null;
    }
}
