/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscaminasdef2;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Buscaminasdef2 {

    /**
     * @param args the command line arguments
     */
    private JFrame frame;
    private JPanel panelTablero;
    private JPanel panelConfiguracion;
    private JButton[][] botones;
    private int filas;
    private int columnas;
    private int minas;
    private boolean[][] tableroMinas;
    private int[][] listaMinas;
    private int indiceMinas;
    private boolean useBFS = true;

    public Buscaminasdef2(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.listaMinas = new int[minas][2];
        this.indiceMinas = 0;
        inicializar();
    }

    private void inicializar() {
        // Toda esta funcion es para inicializar la interfaz, usando el 
        // java.swings
        frame = new JFrame("Buscaminas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        panelConfiguracion = new JPanel();
        String[] opciones = {"10x10 (10 minas)", "18x18 (40 minas)", "24x24 (100 minas)"};
        JComboBox<String> selectorTamaño = new JComboBox<>(opciones);
        JButton btnIniciar = new JButton("Iniciar Juego");
        JRadioButton bfsButton = new JRadioButton("BFS", true);
        JRadioButton dfsButton = new JRadioButton("DFS");
        ButtonGroup group = new ButtonGroup();
        group.add(bfsButton);
        group.add(dfsButton);
        
        bfsButton.addActionListener(e -> useBFS = true);
        dfsButton.addActionListener(e -> useBFS = false);
        
        // Aca es donde sale el boton donde hay 3 opciones para elegir 
        // cualquiera de las 3 dificultades
        btnIniciar.addActionListener(e -> {
            String seleccion = (String) selectorTamaño.getSelectedItem();
            if (seleccion != null) {
                switch (seleccion) {
                    case "10x10 (10 minas)":
                        iniciarJuego(10, 10, 10);
                        break;
                    case "18x18 (40 minas)":
                        iniciarJuego(18, 18, 40);
                        break;
                    case "24x24 (100 minas)":
                        iniciarJuego(24, 24, 100);
                        break;
                }
            }
        });
        
        panelConfiguracion.add(new JLabel("Seleccione tamaño: "));
        panelConfiguracion.add(selectorTamaño);
        panelConfiguracion.add(bfsButton);
        panelConfiguracion.add(dfsButton);
        panelConfiguracion.add(btnIniciar);
        frame.add(panelConfiguracion, BorderLayout.NORTH);
        
        // Dependiendo de cual elegiste, aca te crea el nuevo tablero
        panelTablero = new JPanel();
        frame.add(panelTablero, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }

    private void iniciarJuego(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.tableroMinas = new boolean[filas][columnas];
        this.listaMinas = new int[minas][2];
        this.indiceMinas = 0;
        
        panelTablero.removeAll();
        panelTablero.setLayout(new GridLayout(filas, columnas));
        botones = new JButton[filas][columnas];

        colocarMinas();

        // En este for se evaluara las casillas donde el usuario de click
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(30, 30));
                final int x = i;
                final int y = j;
                boton.addActionListener(e -> manejarClick(x, y));
                botones[i][j] = boton;
                panelTablero.add(boton);
            }
        }
        
        panelTablero.revalidate();
        panelTablero.repaint();
    }

    private void colocarMinas() {
        // En esta funcion se colocara las minas de forma aleatoria
        while (indiceMinas < minas) {
            int x = (int) (Math.random() * filas);
            int y = (int) (Math.random() * columnas);
            
            if (!existeMina(x, y)) {
                tableroMinas[x][y] = true;
                listaMinas[indiceMinas][0] = x;
                listaMinas[indiceMinas][1] = y;
                indiceMinas++;
            }
        }
    }

    private boolean existeMina(int x, int y) {
        for (int i = 0; i < indiceMinas; i++) {
            if (listaMinas[i][0] == x && listaMinas[i][1] == y) {
                return true;
            }
        }
        return false;
    }
    
    private void manejarClick(int x, int y) {
        // En este if evaluamos si hay una bomba en la mina que se da click
        if (tableroMinas[x][y]) {
            botones[x][y].setText("💣"); // Mina encontrada
            JOptionPane.showMessageDialog(frame, "¡Has perdido!");
        } else {
            // Esta parte falta terminarla con el BFS o DFS
            int minasAdyacentes = contarMinasAdyacentes(x, y);
            botones[x][y].setText(minasAdyacentes > 0 ? String.valueOf(minasAdyacentes) : "");
            if (minasAdyacentes == 0) {
                if (useBFS) {
                    realizarBarridoBFS(x, y);
                } else {
                    realizarBarridoDFS(x, y);
                }
            }
        }
    }
    
    private int contarMinasAdyacentes(int x, int y) {
        // Esta funcion simplemente en caso que no exista mina, te dira cuantas tiene cerca
        int contador = 0;
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        // En este caso al rededor de una casilla solo hay 8 casillas
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas && tableroMinas[nx][ny]) {
                contador++;
            }
        }
        return contador;
    }

    private void realizarBarridoBFS(int x, int y) {
        // Acá implementaremos el de BFS
    }

    private void realizarBarridoDFS(int x, int y) {
        // Acá implementaremos el de DFS 
    }    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Buscaminasdef2(10, 10, 10));
    }
    
}
