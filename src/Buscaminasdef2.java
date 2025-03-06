/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscaminasdef;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Santiago, Fernando y Anthony
 */
public class Buscaminas {
    
    /**
     * @param args the command line arguments
     */
    private JFrame frame;
    private JPanel panelTablero;
    private JPanel panelConfiguracion;
    private JButton[][] botones;
    private Tablero tablero;
    private boolean usarBFS;

    public Buscaminas(int filas, int columnas, int minas) {
        this.tablero = new Tablero(filas, columnas, minas);
        this.usarBFS = true; // Por defecto BFS
        inicializar();
    }
    
    private void actualizarTablero() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                Casilla casilla = tablero.buscarCasilla(i, j);

                if (casilla.estaRevelada()) {
                    if (casilla.esMina()) {
                        botones[i][j].setText("💣");
                    } else {
                        int minasAdyacentes = tablero.contarMinasAdyacentes(i, j);
                        botones[i][j].setText(minasAdyacentes > 0 ? String.valueOf(minasAdyacentes) : "");
                    }
                } else if (casilla.estaMarcada()) {
                    botones[i][j].setText("🚩"); // Bandera
                } else {
                    botones[i][j].setText(""); // Casilla sin revelar
                }
            }
        }
    }

    private void inicializar() {
        frame = new JFrame("Buscaminas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCargar = new JButton("Cargar");  

        panelConfiguracion = new JPanel();
        String[] opciones = {"10x10 (10 minas)", "18x18 (40 minas)", "24x24 (100 minas)"};
        JComboBox<String> selectorTamaño = new JComboBox<>(opciones);
        JButton btnIniciar = new JButton("Iniciar Juego");
        JRadioButton btnBFS = new JRadioButton("BFS", true);
        JRadioButton btnDFS = new JRadioButton("DFS");
        ButtonGroup grupoBusqueda = new ButtonGroup();
        grupoBusqueda.add(btnBFS);
        grupoBusqueda.add(btnDFS);
        
        btnBFS.addActionListener(e -> usarBFS = true);
        btnDFS.addActionListener(e -> usarBFS = false);

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
        
        btnGuardar.addActionListener(e -> guardadoYCarga.guardarPartida(tablero));
        btnCargar.addActionListener(e -> {
            Tablero tableroCargado = guardadoYCarga.cargarPartida();
            if (tableroCargado != null) {
                tablero = tableroCargado;
                actualizarTablero();
            }
        });

        panelConfiguracion.add(btnGuardar);
        panelConfiguracion.add(btnCargar);

        panelConfiguracion.add(new JLabel("Seleccione tamaño: "));
        panelConfiguracion.add(selectorTamaño);
        panelConfiguracion.add(btnBFS);
        panelConfiguracion.add(btnDFS);
        panelConfiguracion.add(btnIniciar);
        frame.add(panelConfiguracion, BorderLayout.NORTH);

        panelTablero = new JPanel();
        frame.add(panelTablero, BorderLayout.CENTER);
        
        frame.setVisible(true);
    }

    private void iniciarJuego(int filas, int columnas, int minas) {
        tablero = new Tablero(filas, columnas, minas);
        panelTablero.removeAll();
        panelTablero.setLayout(new GridLayout(filas, columnas));
        botones = new JButton[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(30, 30));
                final int x = i;
                final int y = j;

                // Captura de clic izquierdo y derecho
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            manejarClick(x, y, true); // Clic derecho para marcar/desmarcar
                        } else {
                            manejarClick(x, y, false); // Clic izquierdo para revelar
                        }
                    }
                });

                botones[i][j] = boton;
                panelTablero.add(boton);
            }
    }

    panelTablero.revalidate();
    panelTablero.repaint();
    }
    
    private void manejarClick(int x, int y, boolean esClickDerecho) {
        if (esClickDerecho) {
            // Marcar o desmarcar casilla
            tablero.marcarCasilla(x, y);
            botones[x][y].setText(tablero.estaMarcada(x, y) ? "🚩" : "");
        } else {
            // No permitir barrer casilla si está marcada
            if (tablero.estaMarcada(x, y)) return;

            if (tablero.esMina(x, y)) {
                botones[x][y].setText("💣");
                JOptionPane.showMessageDialog(frame, "¡Has perdido!");
            } else {
                int minasAdyacentes = tablero.contarMinasAdyacentes(x, y);
                botones[x][y].setText(minasAdyacentes > 0 ? String.valueOf(minasAdyacentes) : "");

                if (minasAdyacentes == 0) {
                    Busqueda.barrer(tablero, x, y, usarBFS);
                }
            }

            // Verificar si el jugador ha ganado
            if (tablero.todasMinasMarcadas()) {
                JOptionPane.showMessageDialog(frame, "¡Felicidades, has ganado!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Buscaminas(10, 10, 10));
    }
    
}
