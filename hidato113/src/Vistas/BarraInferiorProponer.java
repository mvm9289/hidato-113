/*  Class BarraInferiorProponer:
    Descripcion: Vista para la barra inferior de acciones del panel principal del modo
        proponiendo topologia o proponiendo tablero.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 19:26 */

package Vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;

public class BarraInferiorProponer extends JPanel {

    private int zoom;
    private Tablero tablero;
    private String idTablero;
    private int topologia;
    private int[] limiteCasillasPrefijadas;

    private NuevaPartida nuevaPartida;
    private JButton botonMasZoom;
    private JButton botonMenosZoom;
    private JLabel labelMensaje;
    private JButton botonAceptar;
    private JButton botonCancelar;

    /* PRE: - */
    public BarraInferiorProponer(Tablero tabler, int topolog, String id,
      int[] limitePrefijadas, NuevaPartida nPartida) {

        initComponents(tabler, nPartida);
        setMyLayout();
        idTablero = id;
        topologia = topolog;
        this.limiteCasillasPrefijadas = limitePrefijadas;
        labelMensaje.setText("Primera casilla: 1    Última casilla: " +
          Integer.toString(tablero.obtenerNumCasillas()));
        botonAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int numeroPrefijadas = tablero.obtenerNumPrefijadas();

                if (!tablero.primeroUltimoPuestos())
                    JOptionPane.showMessageDialog(null, "No ha colocado la primera y " +
                      "la última casilla.", "Error", JOptionPane.ERROR_MESSAGE);
                else if (numeroPrefijadas < limiteCasillasPrefijadas[0] ||
                  numeroPrefijadas > limiteCasillasPrefijadas[1])
                    JOptionPane.showMessageDialog(null, "El número de casillas " +
                      "prefijadas debe estar entre " +
                      Integer.toString(limiteCasillasPrefijadas[0]) + " y " +
                      Integer.toString(limiteCasillasPrefijadas[1]) + ".", "Error",
                      JOptionPane.ERROR_MESSAGE);
                else
                    ControladorVistas.getInstance()
                      .proponerTablero(idTablero, topologia, tablero.toArray());
            }
        });
        botonCancelar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().ocultarPanel();
                nuevaPartida.setVisible(true);
            }
        });
    }
    /* POST: Crea una instancia de BarraInferiorProponer para el modo proponer tablero */

    /* PRE: - */
    public BarraInferiorProponer(Tablero tabler, NuevaPartida nPartida) {

        initComponents(tabler, nPartida);
        setMyLayout();
        nuevaPartida = nPartida;
        botonAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (nuevaPartida.numeroCasillasTopologia(tablero.toArray()) < 9) {
                    JOptionPane.showMessageDialog(null, "Topología no válida.", "Error",
                      JOptionPane.ERROR_MESSAGE);
                }
                else {
                    nuevaPartida.setFormaTopologia(tablero.toArray());
                    nuevaPartida.setVisible(true);
                }
            }
        });
        botonCancelar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                nuevaPartida.cancelarTopologia();
                nuevaPartida.setVisible(true);
            }
        });
    }
    /* POST: Crea una instancia de BarraInferiorProponer para el modo proponer
        topologia */

    /* PRE: - */
    private void initComponents(Tablero tablr, NuevaPartida nPartida) {

        zoom = 100;
        this.tablero = tablr;

        botonMasZoom = new JButton();
        botonMasZoom.setText("Zoom ( + )");
        botonMasZoom.setToolTipText("Ampliar");
        botonMasZoom.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                zoom += 10;
                tablero.redimensiona(zoom);
                if (zoom >= 300) botonMasZoom.setEnabled(false);
                if (zoom > 30) botonMenosZoom.setEnabled(true);
            }
        });

        botonMenosZoom = new JButton();
        botonMenosZoom.setText("Zoom ( - )");
        botonMenosZoom.setToolTipText("Reducir");
        botonMenosZoom.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                zoom -= 10;
                tablero.redimensiona(zoom);
                if (zoom <= 30) botonMenosZoom.setEnabled(false);
                if (zoom < 300) botonMasZoom.setEnabled(true);
            }
        });

        labelMensaje = new JLabel();
        labelMensaje.setText("");

        botonAceptar = new JButton();
        botonAceptar.setText("Aceptar");
        botonAceptar.setToolTipText("Aceptar");

        botonCancelar = new JButton();
        botonCancelar.setText("Cancelar");
        botonCancelar.setToolTipText("Cancelar");

        nuevaPartida = nPartida;

        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la barra inferior */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(botonMasZoom)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonMenosZoom, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 99,
                  Short.MAX_VALUE)
                .addComponent(labelMensaje, GroupLayout.PREFERRED_SIZE, 250,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(botonAceptar, GroupLayout.PREFERRED_SIZE, 100,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(botonCancelar, GroupLayout.PREFERRED_SIZE, 100,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar)
                    .addComponent(botonAceptar)
                    .addComponent(labelMensaje)
                    .addComponent(botonMasZoom)
                    .addComponent(botonMenosZoom))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        barra inferior */
}