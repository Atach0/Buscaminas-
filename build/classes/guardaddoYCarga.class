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
            
            try (FileWriter escritor = new FileWriter(archivo)) {
                int filas = tablero.getFilas();
                int columnas = tablero.getColumnas();
                
                // Guardar dimensiones y número de minas
                escritor.write(filas + "," + columnas + "\n");
                
                // Guardar el estado de cada casilla
                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        escritor.write(tablero.obtenerEstadoCasilla(i, j) + ",");
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
                // Leer dimensiones del tablero
                String[] dimensiones = lector.readLine().split(",");
                int filas = Integer.parseInt(dimensiones[0]);
                int columnas = Integer.parseInt(dimensiones[1]);
                
                Tablero tablero = new Tablero(filas, columnas, 0); // Se creará sin minas
                
                // Leer el estado de cada casilla
                for (int i = 0; i < filas; i++) {
                    String[] estados = lector.readLine().split(",");
                    for (int j = 0; j < columnas; j++) {
                        tablero.cargarEstadoCasilla(i, j, estados[j]);
                    }
                }
                
                JOptionPane.showMessageDialog(null, "Partida cargada correctamente.");
                return tablero;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar la partida.");
            }
        }
        return null; // En caso de fallo o cancelación
    }
}
