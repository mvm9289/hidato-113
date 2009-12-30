/*  Class Ranking:
    Descripcion: Vista para la ventana de ranking.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 17:08 */

package Vistas;

import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Ranking extends JDialog {

    private final static int POSICIONES_DEFECTO = 10;
    private final static int MAX_POSICIONES = 1000;
    
    private JLabel labelNumPosiciones;
    private JSpinner spinnerNumPosiciones;
    private TablaRanking tabla;
    private JButton botonAceptar;

    /* PRE: - */
    public Ranking () {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de Ranking */

    /* PRE: - */
    private void actualizarTabla() {

        int numPosiciones = Integer.parseInt(spinnerNumPosiciones.getValue().toString());

        tabla.setContenido(ControladorVistas.getInstance().obtenerRanking(numPosiciones));
    }
    /* POST: Actualiza el contenido de la tabla de posiciones segun el numero de
        posiciones seleccionado en el spinner de la ventana de ranking */

    /* PRE: - */
    private void initComponents() {

        SpinnerModel spinnerModel;

        labelNumPosiciones = new JLabel();
        labelNumPosiciones.setText("Número de posiciones a mostrar:");

        spinnerModel = new SpinnerNumberModel(POSICIONES_DEFECTO, 1, MAX_POSICIONES, 1);
        spinnerNumPosiciones = new JSpinner(spinnerModel);
        spinnerModel.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

                actualizarTabla();
            }
        });

        tabla = new TablaRanking();
        actualizarTabla();
        
        botonAceptar = new JButton();
        botonAceptar.setText("Aceptar");
        botonAceptar.setToolTipText("Aceptar");
        botonAceptar.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {

                setVisible(false);
            }
        });

        setTitle("Ranking");
        setMinimumSize(new Dimension(405, 400));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la ventana de ranking */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelNumPosiciones)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                          106, Short.MAX_VALUE)
                        .addComponent(spinnerNumPosiciones, GroupLayout.PREFERRED_SIZE,
                          58, GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabla, GroupLayout.PREFERRED_SIZE, 375,
                      GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAceptar, GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumPosiciones)
                    .addComponent(spinnerNumPosiciones, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tabla, GroupLayout.PREFERRED_SIZE, 263,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonAceptar)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        ventana de ranking */
}
