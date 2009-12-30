/*  Class BarraCasillas:
    Descripcion: Vista para la barra de casillas colocadas y seleccionables de un tablero
        en modo juego.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 18:53 */

package Vistas;

import Utiles.Files;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BarraCasillas extends JScrollPane {

    private final static int CASILLAS_VISTAS = 15;
    private final static int CASILLA_SIZE = 30;

    private JPanel contenedor;
    private CasillaBarra[] casillas;
    private Tablero tablero;

    /* PRE: - */
    public BarraCasillas(Tablero tablero) {

        initComponents(tablero);
    }
    /* POST: Crea una instancia de BarraCasillas */

    /* PRE: - */
    public int obtenerAnchura() {

        if (casillas.length <= CASILLAS_VISTAS) {
            redimensionaBarra(CASILLA_SIZE - 18);
            return casillas.length * CASILLA_SIZE + 3;
        }

        return CASILLAS_VISTAS * CASILLA_SIZE + 3;
    }
    /* POST: Retorna la anchura de la barra de casillas en cuestion */

    /* PRE: - */
    public int obtenerAltura() {

        if (casillas.length <= CASILLAS_VISTAS) {
            redimensionaBarra(CASILLA_SIZE - 18);
            return CASILLA_SIZE;
        }

        return CASILLA_SIZE + 18;
    }
    /* POST: Retorna la altura de la barra de casillas en cuestion */

    /* PRE: valor >= 1 */
    public void setBloqueado(int valor, boolean bloqueado) {

        casillas[valor - 1].setBloqueada(bloqueado);
    }
    /* POST: La casilla de valor 'valor' pasa a estar bloqueada, o no, segun el valor de
        'bloqueado' */

    /* PRE: - */
    public void redimensionaTablero(int zoom) {

        tablero.redimensiona(zoom);
    }
    /* POST: Redimensiona el tablero asociado "tablero" en lo marcado por 'zoom' */

    /* PRE: - */
    private void redimensionaBarra(int size) {

        for (int i = 0; i < casillas.length; ++i) casillas[i].setSize(size);
    }
    /* POST: Redimensiona el tama(ny)o de las casillas de la barra adecuandose al
        tama(ny)o 'size' * 'size' */

    /* PRE: - */
    private void initComponents(Tablero tablero) {

        int numCasillas = tablero.obtenerNumCasillas();

        contenedor = new JPanel();
        contenedor.setLayout(new GridLayout(1, numCasillas));

        casillas = new CasillaBarra[numCasillas];
        for (int i = 0; i < numCasillas; ++i) {
            casillas[i] = new CasillaBarra(i + 1);
            contenedor.add(casillas[i]);
        }

        this.tablero = tablero;

        setViewportView(contenedor);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la barra de casillas */

    private class CasillaBarra extends JPanel {

        private int valor;
        private boolean bloqueada;
        private JLabel contenido;

        /* PRE: - */
        public CasillaBarra(int valor) {

            initComponents(valor);
        }
        /* POST: Crea una instancia de CasillaBarra */

        /* PRE: - */
        public void setBloqueada(boolean bloqueada) {
            
            this.bloqueada = bloqueada;
            repaint();
        }
        /* POST: La casilla pasa a estar bloqueada, o no, segun el valor de 'bloqueada' */

        /* PRE: - */
        public void setSize(int size) {

            Dimension dim = new Dimension(size, size);
            
            setMinimumSize(dim);
            setMaximumSize(dim);
            setPreferredSize(dim);
        }
        /* POST: Establece el tama(ny)o de la casilla en 'size' * 'size' */

        /* PRE: - */
        private void initComponents(int num) {

            Dimension size = new Dimension(CASILLA_SIZE, CASILLA_SIZE);

            valor = num;
            bloqueada = false;

            contenido = new JLabel();
            contenido.setSize(size);
            contenido.setHorizontalAlignment(JLabel.CENTER);
            contenido.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
            contenido.setText(Integer.toString(valor));

            setSize(CASILLA_SIZE);
            setLayout(new BorderLayout());
            add(contenido, BorderLayout.CENTER);
            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {

                    if (ControladorVistas.getInstance().esPartidaIniciada() && !bloqueada)
                        tablero.setSiguienteNumero(valor);
                }
            });
        }
        /* POST: Crea e inicializa correctamente todos los componentes y variables usadas
            por la casilla en cuestion */

        /* PRE: - */
        private String getFondo() {

            if (bloqueada) return "prefijada.png";
            else return "normal.png";
        }
        /* POST: Retorna en nombre de la imagen de fondo adequada al estado de la casilla
            en cuestion */

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
