/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buscaminasdef;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel labelMinasRestantes;
    private JLabel labelTiempo;
    private Timer timer;
    private int segundos; // Contador de tiempo restante
    private int minasRestantes; // Contador de minas restantes
    private boolean juegoEnCurso;

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
        labelMinasRestantes = new JLabel("Minas restantes: 0");
        labelTiempo = new JLabel("Tiempo: 0s");

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
        panelConfiguracion.add(labelMinasRestantes);
        panelConfiguracion.add(labelTiempo);
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
        minasRestantes = minas;
        labelMinasRestantes.setText("Minas restantes: " + minasRestantes);
        segundos = 0;
        labelTiempo.setText("Tiempo: 0s");
        juegoEnCurso = true;

        if (timer != null) {
            timer.stop();
        }
        iniciarTemporizador();

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
        if (!juegoEnCurso) return;
        
        if (esClickDerecho) {
            // Marcar o desmarcar casilla
            tablero.marcarCasilla(x, y);
            botones[x][y].setText(tablero.estaMarcada(x, y) ? "🚩" : "");
            
        } else {
            // No permitir barrer casilla si está marcada
            if (tablero.estaMarcada(x, y)) return;

            if (tablero.esMina(x, y)) {
                // Mostrar todas las minas al perder
                mostrarMinas();
                botones[x][y].setText("💣");
                botones[x][y].setForeground(Color.RED); // Color rojo para la mina
                timer.stop();
                juegoEnCurso = false;
                JOptionPane.showMessageDialog(frame, "¡Has perdido! Inicia una nueva partida");
                
                // Deshabilitar todas las casillas
                bloquearTablero();
                return;
            } else {
                int minasAdyacentes = tablero.contarMinasAdyacentes(x, y);
                botones[x][y].setText(minasAdyacentes > 0 ? String.valueOf(minasAdyacentes) : "");
                
                if (minasAdyacentes > 0) {
                    botones[x][y].setText(String.valueOf(minasAdyacentes));
                    botones[x][y].setForeground(obtenerColorNumero(minasAdyacentes));
                } else {
                    botones[x][y].setText("");
                    Busqueda.barrer(tablero, x, y, usarBFS);
                }
                botones[x][y].setEnabled(false);
                tablero.descubrirCasilla(x, y);
                
                if (minasAdyacentes == 0) {
                    Busqueda.barrer(tablero, x, y, usarBFS);
                }
            }

            verificarVictoria(); // Llamamos a la verificación de victoria 
                                 // si el usuario selecciono todas las casillas sin minas
            
            // Verificar si el jugador ha ganado marcando todas las minas
            if (tablero.todasMinasMarcadas()) {
                JOptionPane.showMessageDialog(frame, "¡Felicidades, has ganado!");
            }
            
            // Verificar victoria
            if (tablero.verificarVictoria()) {
                timer.stop();
                juegoEnCurso = false;
                JOptionPane.showMessageDialog(frame, "¡Has ganado en " + segundos + " segundos!");
            }
        }
    }
    
    private void iniciarTemporizador() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundos++;
                labelTiempo.setText("Tiempo: " + segundos + "s");
            }
        });
        timer.start();
    }
    
    private void manejarBandera(int x, int y) {
        if (!juegoEnCurso) return;

        if (tablero.estaMarcada(x, y)) {
            tablero.marcarCasilla(x, y, false);
            botones[x][y].setText("");
            minasRestantes++;
        } else {
            tablero.marcarCasilla(x, y, true);
            botones[x][y].setText("🚩");
            minasRestantes--;
        }
        labelMinasRestantes.setText("Minas restantes: " + minasRestantes);
    }
    
    // Método para obtener el color según el número
    private Color obtenerColorNumero(int numero) {
        switch (numero) {
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.RED;
            case 4: return Color.MAGENTA;
            case 5: return Color.ORANGE;
            case 6: return Color.CYAN;
            case 7: return Color.BLACK;
            case 8: return Color.GRAY;
            default: return Color.BLACK;
        }
    }
    
    private void mostrarMinas() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (tablero.esMina(i, j)) {
                    botones[i][j].setText("💣");
                }
            }
        }
    }
    
    private void verificarVictoria() {
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (!tablero.esMina(i, j) && !tablero.estaMarcada(i, j)) {
                    return; // Si hay casillas sin descubrir, aún no has ganado
                }
            }
        }

        // Si todas las casillas no minadas están descubiertas, has ganado
        JOptionPane.showMessageDialog(frame, "¡Felicidades, has ganado!");
        bloquearTablero(); // Bloquea el tablero para evitar más acciones
    }
    
    private void bloquearTablero() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                botones[i][j].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Buscaminas(10, 10, 10));
    }
    
}
