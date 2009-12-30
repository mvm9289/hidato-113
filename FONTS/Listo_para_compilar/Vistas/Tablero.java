/*  Class Tablero:
    Descripcion: Vista para los tableros, tanto en modo jugar, modo proponer tablero o
        modo proponer topologia.
    Autor: miguel.angel.vico
    Revisado: 21/12/2009 01:28 */

package Vistas;

import Utiles.Files;
import Utiles.Utiles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel {

    private final static int CASILLA_SIZE = 40;
    private final static int FONT_SIZE = 18;
    private int altura;
    private int anchura;
    private int casilla_size;
    private int font_size;
    private Casilla casillas[][];
    private int casillasActivas;
    private int casillasPuestas;
    private int siguienteNumero;
    private boolean[] numerosPuestos;
    private int direccionBusqueda;
    private BarraCasillas barraCasillas;
    private Deshacer colaDeshacer;

    /* PRE: altura > 0, anchura > 0 */
    public Tablero(int altura, int anchura) {

        initComponents(altura, anchura);
    }
    /* POST: Crea una instancia de Tablero de altura = 'altura' y anchura = 'anchura' */

    /* PRE: - */
    public Tablero(int[][] topologia) {

        initComponents(topologia.length, topologia[0].length);
        for (int i = 0; i < topologia.length; ++i)
            for (int j = 0; j < topologia[i].length; ++j)
                if (topologia[i][j] == -1) casillas[i][j].desactiva();
    }
    /* POST: Crea una instancia de Tablero a partir de la topologia 'topologia' */

    /* PRE: - */
    public int obtenerNumCasillas() {

        return casillasActivas;
    }
    /* POST: Retorna el numero de casillas activas del tablero en cuestion */

    /* PRE: - */
    public int obtenerNumPrefijadas() {

        int numeroPrefijadas = 0;

        for (int i = 0; i < casillas.length; ++i)
            for (int j = 0; j < casillas[i].length; ++j)
                if (casillas[i][j].prefijada) ++numeroPrefijadas;

        return numeroPrefijadas;
    }
    /* POST: Retorna el numero de casillas prefijadas del tablero en cuestion */

    /* PRE: - */
    public BarraCasillas obtenerBarraCasillas() {
        
        return barraCasillas;
    }
    /* POST: Retorna la barra de casillas asociada al tablero en cuestion */

    /* PRE: - */
    public void redimensiona(int zoom) {

        casilla_size = (zoom * CASILLA_SIZE) / 100;
        font_size = (zoom * FONT_SIZE) / 100;

        for (int i = 0; i < altura; ++i)
            for (int j = 0; j < anchura; ++j)
                casillas[i][j].redimensiona();
    }
    /* POST: Redimensiona el tablero en cuestion degun el valor de 'zoom' */

    /* PRE: x >= 0, y >= 0 */
    public void setValor(int x, int y, int valor) {

        if (casillas[x][y].activa) {
            casillas[x][y].valor = valor;
            casillas[x][y].contenido.setForeground(Color.BLACK);
            casillas[x][y].contenido.setFont(new Font("Trebuchet MS", Font.BOLD,
              font_size));
            casillas[x][y].contenido.setText(Integer.toString(valor));
            if (barraCasillas != null) barraCasillas.setBloqueado(valor, true);
            if (valor == 1 || valor == casillasActivas)
                casillas[x][y].inicialFinal = true;
            if (casillas[x][y].vacia) {
                ++casillasPuestas;
                if (numerosPuestos != null) {
                    numerosPuestos[valor] = true;
                    buscarSiguienteNumero();
                }
            }
            casillas[x][y].vacia = false;
        }
    }
    /* POST: Establece el valor de la casilla (x, y) como 'valor' */

    /* PRE: x >= 0, y >= 0 */
    public void setValorIncorrecto(int x, int y) {

        casillas[x][y].contenido.setForeground(Color.red);
    }
    /* POST: Marca el contenido de la casilla (x, y) como incorrecto */

    /* PRE: x >= 0, y >= 0 */
    public void setPrefijada(int x, int y) {

        casillas[x][y].prefijada = true;
    }
    /* POST: Marca la casilla (x, y) como prefijada */

    /* PRE: x >= 0, y >= 0 */
    public void setDesactiva(int x, int y) {

        casillas[x][y].desactiva();
        casillas[x][y].setVisible(false);
    }
    /* POST: Desactiva la casilla (x, y) */

    /* PRE: - */
    public void setSiguienteNumero(int numero) {

        siguienteNumero = numero;
        direccionBusqueda = 1;
    }
    /* POST: Establece el que el siguiente numero no colocado es 'numero' (modo jugar) */

    /* PRE: - */
    public boolean primeroUltimoPuestos() {

        return numerosPuestos[1] && numerosPuestos[casillasActivas];
    }
    /* POST: Retorna cierto si el primero y el ultimo numero de un tablero estan
        colocados (modo proponer tablero) */

    /* PRE: - */
    public void deshacer() {

        colaDeshacer.undo();
    }
    /* POST: Deshace la ultima accion realizada sobre el tablero */

    /* PRE: - */
    public void resetear() {

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                if (!casillas[i][j].vacia && !casillas[i][j].prefijada) {
                    numerosPuestos[casillas[i][j].valor] = false;
                    barraCasillas.setBloqueado(casillas[i][j].valor, false);
                    casillas[i][j].setVacia(true);
                }
            }
        }
        direccionBusqueda = 1;
        buscarSiguienteNumero();
    }
    /* POST: Vacia todas las casillas no prefijadas del tablero */

    /* PRE: - */
    public void modoJugar() {

        int i;

        barraCasillas = new BarraCasillas(this);

        numerosPuestos = new boolean[casillasActivas + 1];
        for (i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                if (casillas[i][j].activa) {
                    if (!casillas[i][j].vacia) {
                        numerosPuestos[casillas[i][j].valor] = true;
                        barraCasillas.setBloqueado(casillas[i][j].valor, true);
                    }
                    else numerosPuestos[casillas[i][j].valor] = false;
                }
                casillas[i][j].modoJugar();
            }
        }

        i = 2;
        while (i < casillasActivas && numerosPuestos[i]) ++i;
        siguienteNumero = i;
        direccionBusqueda = 1;

        colaDeshacer.cambiaModo(Deshacer.JUEGO);
    }
    /* POST: Establece que el tablero en cuestion esta en modo jugar */

    /* PRE: x >= 0, y >= 0 */
    public void introducirValorJugando(int x, int y, int valor) {
        
        casillas[x][y].introducirValorJugando(valor);
    }
    /* POST: Introduce el valor 'valor' en la casilla (x, y) (modo jugar) */

    /* PRE: x >= 0, y >= 0 */
    public void quitarValorJugando(int x, int y) {
        
        casillas[x][y].quitarValorJugando();
    }
    /* POST: Quita el valor de la casilla (x, y) (modo jugar) */

    /* PRE: - */
    public void modoProponerTablero() {

        numerosPuestos = new boolean[casillasActivas + 1];
        for (int i = 1; i <= casillasActivas; ++i) numerosPuestos[i] = false;

        for (int i = 0; i < altura; ++i)
            for (int j = 0; j < anchura; ++j)
                casillas[i][j].modoProponerTablero();

        colaDeshacer.cambiaModo(Deshacer.PROPONER_TAB);
    }
    /* POST: Establece que el tablero en cuestion esta en modo proponer tablero */

    /* PRE: x >= 0, y >= 0 */
    public void introducirValorProponiendo(int x, int y, int valor) {

        casillas[x][y].introducirValorProponiendo(valor);
    }
    /* POST: Introduce el valor 'valor' en la casilla (x, y) (modo proponer tablero) */

    /* PRE: x >= 0, y >= 0 */
    public void quitarValorProponiendo(int x, int y) {

        casillas[x][y].quitarValorProponiendo();
    }
    /* POST: Quita el valor de la casilla (x, y) (modo proponer tablero) */

    /* PRE: - */
    public void modoProponerTopologia() {

        for (int i = 0; i < altura; ++i)
            for (int j = 0; j < anchura; ++j)
                casillas[i][j].modoProponerTopologia();

        colaDeshacer.cambiaModo(Deshacer.PROPONER_TOP);
    }
    /* POST: Establece que el tablero en cuestion esta en modo proponer topologia */

    /* PRE: - */
    public void activarDesactivar(int x, int y) {

        casillas[x][y].activarDesactivar();
    }
    /* POST: Cambia el estado de la casilla (x, y) de activa a desactiva y viceversa
        (modo proponer topologia) */

    /* PRE: - */
    public int[][] toArray() {

        int[][] array = new int[altura][anchura];

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                if (!casillas[i][j].activa) array[i][j] = -1;
                else array[i][j] = casillas[i][j].valor;
            }
        }

        return array;
    }
    /* POST: Convierte el tablero en cuestion en una matriz de enteros */

    /* PRE: - */
    private void buscarSiguienteNumero() {

        if (casillasPuestas < casillasActivas) {
            while(casillasPuestas < casillasActivas &&
              numerosPuestos[siguienteNumero]) {
                siguienteNumero += direccionBusqueda;
                if (siguienteNumero > casillasActivas) siguienteNumero = 1;
                else if (siguienteNumero < 2) siguienteNumero = casillasActivas - 1;
            }
        }
        else if (casillasPuestas == casillasActivas)
            ControladorVistas.getInstance().finalizarPartida();
    }
    /* POST: Encuentra el siguiente numero no colocado. Si todos estan colocados intenta
        finalizar la partida (modo jugar) */

    /* PRE: - */
    private void resetearClics() {

        for (int i = 0; i < altura; ++i)
            for (int j = 0; j < anchura; ++j)
                casillas[i][j].clicada = false;
    }
    /* POST: Marca todas las casillas como no clicadas */

    /* PRE: - */
    private void initComponents(int altura, int anchura) {

        this.altura = altura;
        this.anchura = anchura;

        casilla_size = 40;
        font_size = 18;

        setLayout(new GridLayout(altura, anchura));

        casillas = new Casilla[altura][anchura];
        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                casillas[i][j] = new Casilla(i, j);
                add(casillas[i][j]);
            }
        }

        casillasActivas = altura*anchura;
        casillasPuestas = 0;

        colaDeshacer = new Deshacer(this);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el tablero */

    private class Casilla extends JPanel {

        private int x;
        private int y;
        private boolean inicialFinal;
        private boolean prefijada;
        private boolean vacia;
        private boolean activa;
        private boolean clicada;
        private int valor;
        private JLabel contenido;

        /* PRE: x >= 0, y >= 0 */
        public Casilla(int x, int y) {

            initComponents(x, y);
        }
        /* POST: Crea una instancia de Casilla */

        /* PRE: - */
        public void setSize(int size) {

            Dimension dim = new Dimension(size, size);

            setMinimumSize(dim);
            setMaximumSize(dim);
            setPreferredSize(dim);
        }
        /* POST: Establece el tama(ny)o de la casilla en cuestion en 'size' * 'size' */

        /* PRE: - */
        private void redimensiona() {

            contenido.setSize(new Dimension(casilla_size, casilla_size));
            contenido.setFont(new Font("Trebuchet MS", Font.BOLD, font_size));
            setSize(casilla_size);
        }
        /* POST: Establece el tama(ny)o de la casilla en cuestion y de su contenido
            segun los valores actuales de casilla_size y font_size */

        /* PRE: - */
        private void setVacia(boolean vacia) {

            if (vacia && !this.vacia) {
                prefijada = false;
                inicialFinal = false;
                numerosPuestos[valor] = false;
                valor = 0;
                contenido.setText("");
                this.vacia = true;
                --casillasPuestas;
            }
            else if (!vacia) {
                numerosPuestos[valor] = true;
                this.vacia = false;
                ++casillasPuestas;
            }
        }
        /* POST: Establece la casilla en cuestion como vacia o no segun el valor de
            'vacia' */

        /* PRE: - */
        private void desactiva() {

            this.activa = false;
            --casillasActivas;
        }
        /* POST: Configura la casilla en cuestion como desactiva */

        /* PRE: - */
        private void modoJugar() {

            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {

                    if (ControladorVistas.getInstance().esPartidaIniciada()) {
                        if (!prefijada && !inicialFinal) {
                            if(vacia) {
                                colaDeshacer.insertarAccion(Deshacer.INSERTAR_VAL_JUEGO,
                                  x, y, siguienteNumero);
                                introducirValorJugando(siguienteNumero);
                            }
                            else {
                                colaDeshacer.insertarAccion(Deshacer.QUITAR_VAL_JUEGO, x,
                                  y, valor);
                                quitarValorJugando();
                                contenido.setForeground(Color.GRAY);
                                contenido.setFont(new Font("Trebuchet MS", Font.ITALIC,
                                  font_size));
                                contenido.setText(String.valueOf(siguienteNumero));
                            }
                            resetearClics();
                        }
                        else if (casillasPuestas < casillasActivas) {
                            if (clicada) direccionBusqueda = -direccionBusqueda;
                            else direccionBusqueda = 1;
                            resetearClics();
                            clicada = true;
                            siguienteNumero = valor;
                            buscarSiguienteNumero();
                        }
                    }
                }

                public void mouseEntered(MouseEvent e) {

                    if (vacia && ControladorVistas.getInstance().esPartidaIniciada()) {
                        contenido.setForeground(Color.GRAY);
                        contenido.setFont(new Font("Trebuchet MS", Font.ITALIC,
                          font_size));
                        contenido.setText(String.valueOf(siguienteNumero));
                    }
                }

                public void mouseExited(MouseEvent e) {

                    if (vacia && ControladorVistas.getInstance().esPartidaIniciada()) {
                        contenido.setForeground(Color.BLACK);
                        contenido.setFont(new Font("Trebuchet MS", Font.BOLD, font_size));
                        contenido.setText("");
                    }
                }
            });
        }
        /* POST: Establece las acciones propias del modo jugar a los eventos a captar en
            dicho modo */

        /* PRE: - */
        private void introducirValorJugando(int valor) {

            this.valor = valor;
            contenido.setForeground(Color.BLACK);
            contenido.setFont(new Font("Trebuchet MS", Font.BOLD, font_size));
            contenido.setText(Integer.toString(valor));
            barraCasillas.setBloqueado(valor, true);
            setVacia(false);
            ControladorVistas.getInstance().insertarValor(x, y, valor);
            siguienteNumero = valor;
            buscarSiguienteNumero();
        }
        /* POST: Introduce el valor 'valor' en la casilla en cuestion (modo jugar) */

        /* PRE: - */
        private void quitarValorJugando() {

            siguienteNumero = valor;
            barraCasillas.setBloqueado(valor, false);
            direccionBusqueda = 1;
            setVacia(true);
            ControladorVistas.getInstance().quitarValor(x, y);
        }
        /* POST: Quita el valor de la casilla en cuestion (modo jugar) */

        /* PRE: - */
        private void modoProponerTablero() {

            if (!activa) setVisible(false);
            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {

                    int valorAux = 0;
                    boolean correcto = false;
                    String opcion;
                    String mensaje = "Introduzca un valor:\n(Para vaciar la casilla " +
                      "deje el campo vacío)";
                    String titulo = "Introducir valor";

                    if (activa) {
                        opcion = JOptionPane.showInputDialog(null, mensaje, titulo,
                          JOptionPane.QUESTION_MESSAGE);

                        while (!correcto) {
                            if (opcion == null) correcto = true;
                            else if (opcion.equals("")) correcto = true;
                            else if (!Utiles.esInt(opcion.trim())) {
                                opcion = JOptionPane.showInputDialog(null,
                                  "El valor debe ser un número.\n\n" + mensaje, titulo,
                                  JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                valorAux = Integer.parseInt(opcion.trim());
                                if (valorAux <= 0 || valorAux > casillasActivas) {
                                    opcion = JOptionPane.showInputDialog(null,
                                      "Valor fuera de rango.\n\n" + mensaje, titulo,
                                      JOptionPane.ERROR_MESSAGE);
                                }
                                else if (numerosPuestos[valorAux]) {
                                    opcion = JOptionPane.showInputDialog(null,
                                      "Valor ya introducido.\n\n" + mensaje, titulo,
                                      JOptionPane.ERROR_MESSAGE);
                                }
                                else correcto = true;
                            }
                        }

                        if (opcion != null) {
                            if (opcion.equals("")) {
                                valorAux = valor;
                                colaDeshacer.insertarAccion(Deshacer.QUITAR_VAL_PROP, x,
                                  y, valorAux);
                                quitarValorProponiendo();
                            }
                            else {
                                colaDeshacer.insertarAccion(Deshacer.INSERTAR_VAL_PROP, x,
                                  y, valorAux);
                                introducirValorProponiendo(valorAux);
                            }
                        }
                    }
                }
            });
        }
        /* POST: Establece las acciones propias del modo proponer tablero a los eventos
            a captar en dicho modo */

        /* PRE: - */
        private void introducirValorProponiendo(int valor) {

            if (valor > 0) {
                setVacia(true);
                prefijada = true;
                this.valor = valor;
                contenido.setText(Integer.toString(valor));
                setVacia(false);
                if (valor == 1 || valor == casillasActivas) inicialFinal = true;
            }
        }
        /* POST: Introduce el valor 'valor' en la casilla en cuestion (modo proponer
            tablero) */

        /* PRE: - */
        private void quitarValorProponiendo() {

            setVacia(true);
        }
        /* POST: Quita el valor de la casilla en cuestion (modo proponer tablero) */

        /* PRE: - */
        private void modoProponerTopologia() {

            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {

                    colaDeshacer.insertarAccion(Deshacer.DES_ACTIVAR_CASILLA, x, y, 0);
                    activarDesactivar();
                }
            });
        }
        /* POST: Establece las acciones propias del modo proponer topologia a los eventos
            a captar en dicho modo */

        /* PRE: - */
        private void activarDesactivar() {

            if (activa) activa = false;
            else activa = true;
            repaint();
        }
        /* POST: Cambia el estado de la casilla en cuestion de activa a desactiva y
            viceversa (modo proponer topologia) */

        /* PRE: - */
        private void initComponents(int x, int y) {

            this.x = x;
            this.y = y;
            inicialFinal = false;
            prefijada = false;
            vacia = true;
            activa = true;
            clicada = false;

            contenido = new JLabel();
            contenido.setSize(new Dimension(casilla_size, casilla_size));
            contenido.setHorizontalAlignment(JLabel.CENTER);
            contenido.setFont(new Font("Trebuchet MS", Font.BOLD, font_size));

            setSize(casilla_size);
            setLayout(new BorderLayout());
            add(contenido, BorderLayout.CENTER);
        }
        /* POST: Crea e inicializa correctamente todos los componentes y variables usadas
            por la casilla en cuestion */

        /* PRE: - */
        private String getFondo() {

            if (inicialFinal) return "inicialFinal.png";
            else if (prefijada) return "prefijada.png";
            else if (!activa) return "desactivada.png";
            else return "normal.png";
        }
        /* POST: Retorna el archivo de imagen para el fondo adequado al estado de la
            casilla en cuestion */

        /* PRE: - */
        public void paintComponent(Graphics g) {

            Dimension size = getSize();
            ImageIcon fondo = new ImageIcon(Files.getPath() + "/Vistas/Imagenes/" +
              getFondo());

            g.drawImage(fondo.getImage(), 0, 0, size.width, size.height, null);
            setOpaque(false);
            super.paintComponent(g);
        }
        /* POST: Pinta la imagen de fondo oportuna a la casilla en cuestion */
    }
}