/*  Class FinalizarPartida;
    Descripcion: Vista para la ventana de dialogo mostrada al finalizar una partida.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 20:28 */

package Vistas;

import Utiles.Files;
import Utiles.Utiles;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class FinalizarPartida extends JDialog {

    private final static String RESUELTO = "winner.png";
    private final static String NO_RESUELTO = "gameover.png";
    private final static int ANCHO_FONDO = 544;
    private final static int ALTO_FONDO = 434;

    private final static String RESUELTO_TITULO1 = "FELICIDADES! HAS RESUELTO";
    private final static String RESUELTO_TITULO2 = "EL HIDATO!";
    private final static String YA_RESUELTO_TITULO = "YA HABIAS RESUELTO EL HIDATO!";
    private final static String NO_RESUELTO_TITULO1 = "NO HAS RESUELTO EL HIDATO";
    private final static String NO_RESUELTO_TITULO2 = "NO TE DES POR VENCIDO!";

    private JLabel labelTitulo;
    private JLabel labelTitulo2;
    private JLabel labelPuntuacion;
    private JLabel labelValorPuntuacion;
    private JLabel labelTiempo;
    private JLabel labelValorTiempo;
    private JLabel labelPosicion;
    private JLabel labelValorPosicion;
    private JLabel labelNumeroPistas;
    private JLabel labelValorNumeroPistas;
    private JButton botonCerrar;
    private Fondo fondo;

    private String titulo;
    private String titulo2;
    private int puntuacion;
    private int tiempo;
    private int numPistas;
    private int posicionRanking;
    private boolean resueltoUsuario; /* indica si lo ha resuelto el usuario */
    private boolean resueltoPrimeraVez; /* indica si se ha resuelto por primera vez */

    /* PRE: - */
    public FinalizarPartida(int puntuacion, int tiempo, int posicion, int num_pistas,
      boolean resueltoUsuario, boolean resueltoPrimero) {

        initComponents(puntuacion, tiempo, posicion, num_pistas, resueltoUsuario,
          resueltoPrimero);
        setMyLayout();
    }
    /* POST: Crea una instancia de FinalizarPartida */

    /* PRE: - */
    public void mostrar() {

        if (!resueltoUsuario || !resueltoPrimeraVez) {
            labelValorPuntuacion.setText("0");
            muestraLabels(true, true, false, false, !resueltoUsuario);
        }
        else muestraLabels(true, true, true, true, true);

        setVisible(true);
    }
    /* POST: Muestra las etiquetas que correspondan dependiendo de la situacion por las
        que se ha finalizado la partida */

    /* PRE: - */
    private void colorear(Color color) {

        labelPuntuacion.setForeground(color);
        labelValorPuntuacion.setForeground(color);
        labelTiempo.setForeground(color);
        labelValorTiempo.setForeground(color);
        labelPosicion.setForeground(color);
        labelValorPosicion.setForeground(color);
        labelNumeroPistas.setForeground(color);
        labelValorNumeroPistas.setForeground(color);
    }
    /* POST: Pinta las etiquetas del panel de la ventana de color 'color' */

    /* PRE: - */
    private String configura() {

        if (!resueltoUsuario) {
            titulo = NO_RESUELTO_TITULO1;
            titulo2 = NO_RESUELTO_TITULO2;
            colorear(new Color(204, 0, 0));
            return NO_RESUELTO;
        }
        else if (!resueltoPrimeraVez) {
            titulo = YA_RESUELTO_TITULO;
            colorear(new Color(204, 0, 0));
            return NO_RESUELTO;
        }
        else {
            titulo = RESUELTO_TITULO1;
            titulo2 = RESUELTO_TITULO2;
            colorear(new Color(204, 0, 0));
            return RESUELTO;
        }
    }
    /* POST: Configura los datos de los componentes para adaptarlo a las diferentes
        situacones por las que se ha finalizado la partida: partida resuelta por el
        usuario, partida resuelta por la maquina o si el usuario ya habia resuelto la
        partida */

    /* PRE: - */
    private void muestraLabels(boolean puntuacion, boolean tiempo, boolean posicion,
      boolean num_pistas, boolean mostrarSegundoTitulo) {

        labelPuntuacion.setVisible(puntuacion);
        labelValorPuntuacion.setVisible(puntuacion);
        labelTiempo.setVisible(tiempo);
        labelValorTiempo.setVisible(tiempo);
        labelPosicion.setVisible(posicion);
        labelValorPosicion.setVisible(posicion);
        labelNumeroPistas.setVisible(num_pistas);
        labelValorNumeroPistas.setVisible(num_pistas);
        labelTitulo2.setVisible(mostrarSegundoTitulo);
    }
    /* POST: Muestra las etiquetas del panel segun el booleano que le corresponda */

    /* PRE: - */
    private void setValors() {

        labelTitulo.setText(titulo);
        labelTitulo2.setText(titulo2);
        labelValorPuntuacion.setText(Integer.toString(puntuacion));
        labelValorTiempo.setText(Utiles.segundosToTiempo(tiempo));
        labelValorPosicion.setText(Integer.toString(posicionRanking));
        labelValorNumeroPistas.setText(Integer.toString(numPistas));
    }
    /* POST: Establece los valores en las etiquetas del panel de la ventana */

    /* PRE: - */
    private void initComponents(int puntuacion, int tiempo, int posicion, int num_pistas,
      boolean resueltoUsuario, boolean resueltoPrimero) {

        labelTitulo = new JLabel();
        labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        labelTitulo.setHorizontalAlignment(JLabel.CENTER);

        labelTitulo2 = new JLabel();
        labelTitulo2.setFont(new Font("Tahoma", Font.BOLD, 20));
        labelTitulo2.setHorizontalAlignment(JLabel.CENTER);

        labelPuntuacion = new JLabel();
        labelPuntuacion.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelPuntuacion.setText("PUNTUACION");

        labelValorPuntuacion = new JLabel();
        labelValorPuntuacion.setFont(new Font("Tahoma", Font.BOLD, 15));

        labelTiempo = new JLabel();
        labelTiempo.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelTiempo.setText("TIEMPO PARTIDA");

        labelValorTiempo = new JLabel();
        labelValorTiempo.setFont(new Font("Tahoma", Font.BOLD, 15));

        labelPosicion = new JLabel();
        labelPosicion.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelPosicion.setText("POSICION EN EL RANKING");

        labelValorPosicion = new JLabel();
        labelValorPosicion.setFont(new Font("Tahoma", Font.BOLD, 15));
        
        labelNumeroPistas = new JLabel();
        labelNumeroPistas.setFont(new Font("Tahoma", Font.BOLD, 15));
        labelNumeroPistas.setText("NUMERO DE PISTAS");

        labelValorNumeroPistas = new JLabel();
        labelValorNumeroPistas.setFont(new Font("Tahoma", Font.BOLD, 15));

        botonCerrar = new JButton();
        botonCerrar.setText("Cerrar");
        botonCerrar.setToolTipText("Cerrar");
        botonCerrar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                setVisible(false);
            }
        });


        this.puntuacion = puntuacion;
        this.tiempo = tiempo;
        this.numPistas = num_pistas;
        this.posicionRanking = posicion;
        this.resueltoUsuario = resueltoUsuario;
        this.resueltoPrimeraVez = resueltoPrimero;

        fondo = new Fondo(configura(), ANCHO_FONDO, ALTO_FONDO);

        setValors();

        setTitle("Finalizar Partida");
        setMinimumSize(new Dimension(544, 434));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el dialogo de finalizar partida */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(fondo);
        
        fondo.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(botonCerrar, GroupLayout.PREFERRED_SIZE, 75,
                      GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup
                              (GroupLayout.Alignment.LEADING)
                                .addComponent(labelPuntuacion)
                                .addComponent(labelTiempo))
                            .addGap(67, 67, 67))
                        .addComponent(labelPosicion)
                        .addGroup(GroupLayout.Alignment.TRAILING,
                          layout.createSequentialGroup()
                            .addComponent(labelNumeroPistas)
                            .addGap(50, 50, 50))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 80,
                  Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(labelValorTiempo)
                    .addComponent(labelValorPuntuacion)
                    .addComponent(labelValorPosicion)
                    .addComponent(labelValorNumeroPistas))
                .addGap(103, 103, 103))
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitulo, GroupLayout.PREFERRED_SIZE, 375,
                          GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitulo2, GroupLayout.DEFAULT_SIZE, 375,
                          Short.MAX_VALUE)
                        .addGap(36, 36, 36))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addComponent(labelTitulo, GroupLayout.PREFERRED_SIZE, 29,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelTitulo2, GroupLayout.PREFERRED_SIZE, 25,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelValorPuntuacion)
                        .addGap(18, 18, 18)
                        .addComponent(labelValorTiempo)
                        .addGap(18, 18, 18)
                        .addComponent(labelValorPosicion)
                        .addGap(18, 18, 18)
                        .addComponent(labelValorNumeroPistas))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelPuntuacion)
                        .addGap(18, 18, 18)
                        .addComponent(labelTiempo)
                        .addGap(18, 18, 18)
                        .addComponent(labelPosicion)
                        .addGap(18, 18, 18)
                        .addComponent(labelNumeroPistas)))
                .addGap(28, 28, 28)
                .addComponent(botonCerrar)
                .addGap(74, 74, 74))
        );

        add(fondo, BorderLayout.CENTER);
        pack();
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        dialogo de finalizar partida */

    /* Representa el panel de fondo donde se pintara la imagen de finalizar partida */
    private class Fondo extends JPanel {

        private String imagenFondo;

        /* PRE: - */
        public Fondo(String imagen, int anchura, int altura) {

            initComponents(imagen, anchura, altura);
        }
        /* POST: Crea una instancia de Fondo con la imagen 'imagen' de dimensiones
            'anchura' x 'altura' */

        /* PRE: - */
        private void initComponents(String imagen, int anchura, int altura) {

            Dimension size = new Dimension(anchura, altura);

            this.imagenFondo = imagen;

            setSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setPreferredSize(size);       
        }
        /* POST: Crea e inicializa correctamente todos los componentes y variables usadas
            por el fondo del dialogo de finalizar partida */

        /* PRE: - */
        public void paintComponent(Graphics g) {

            Dimension size = getSize();
            ImageIcon fondo = new ImageIcon(Files.getPath() + "/Vistas/Imagenes/" +
              imagenFondo);

            g.drawImage(fondo.getImage(), 0, 0, size.width, size.height, null);
            setOpaque(false);
            super.paintComponent(g);
        }
        /* POST: Pinta el panel con la imagen 'imagenFondo' */
    }

}
