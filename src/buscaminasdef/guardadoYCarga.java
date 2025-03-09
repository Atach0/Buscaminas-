/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buscaminasdef;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class guardadoYCarga {
    // Método para guardar la partida en un archivo CSV
    public static void guardarPartida(Tablero tablero) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar partida");
        
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            try (FileWriter escritor = new FileWriter(archivo)){
                // Escribe las dimensiones del tablero y el número de minas
                escritor.write(
                tablero.getFilas() + "," + tablero.getColumnas() + "," 
                + tablero.getMinas() + "\n"
                );
                
                // Escribe la matriz de minas (1 si es mina, 0 si no lo es)
                for (int i = 0; i < tablero.getFilas() ; i++) {
                    for (int j = 0; j < tablero.getColumnas(); j ++) {
                        escritor.write(tablero.esMina(i,j) ? "1" : "0");
                        if (j < tablero.getColumnas() - 1) escritor.write(",");
                    }
                    escritor.write("\n");
                }
                
                // Guardar dimensiones y número de minas
                //escritor.write(tablero.getFilas() + "," + tablero.getColumnas() + "\n");
                
                // Guardar el estado de cada casilla
                for (int i = 0; i < tablero.getFilas(); i++) {
                    for (int j = 0; j < tablero.getColumnas(); j++) {
                        escritor.write(tablero.obtenerEstadoCasilla(i, j) + (j 
                                < tablero.getColumnas() - 1 ? "," : ""));
                    }
                    escritor.write("\n");
                }
                
                JOptionPane.showMessageDialog(null, "Partida guardada correctamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar la partida.");
            }
        }
    }

    // Método para cargar una partida desde un archivo CSV
    public static Tablero cargarPartida() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Cargar partida");
        
        int seleccion = fileChooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
                // Leer dimensiones del tablero y las minitas
                String[] datos = lector.readLine().split(",");
                int filas = Integer.parseInt(datos[0]);
                int columnas = Integer.parseInt(datos[1]);
                int minas = Integer.parseInt(datos[2]);
                
                // Crea un nuevo tablero con las dimensiones y minas leídas
                Tablero tablero = new Tablero(filas, columnas, minas);
                
                //lee la matriz esMina
                for (int i = 0; i < filas; i++) {
                    String[] minasFila = lector.readLine().split(",");
                    for (int j = 0; j < columnas; j++) {
                        tablero.setEsMina(i, j, minasFila[j].equals("1"));
                    }
                }
                
                // Lee el estado de cada casilla
                for (int i = 0; i < filas; i++) {
                    String[] estados = lector.readLine().split(",");
                    for (int j = 0; j < columnas; j++) {
                        tablero.cargarEstadoCasilla(i, j, estados[j]);
                    }
                }
                
                //Esto sincroniza las minas en Casilla
                tablero.colocarMinasEnCasillas();
                tablero.calcularMinasAdyacentes();
                
                return tablero;
               
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar la partida.");
            }
        }
        return null; // En caso de fallo o cancelación
    }
}
