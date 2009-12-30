/*  Class CargarPartida:
    Descripcion: Vista para la ventana de partidas guardadas.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 19:41 */

package Vistas;

import Dominio.ControladorPartida;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;

public class CargarPartida extends JDialog {

    private JLabel labelOrdenacion;
    private JComboBox cajaOrdenacion;
    private TablaPartidasGuardadas tabla;
    private JButton botonEliminar;
    private JButton botonCancelar;
    private JButton botonAceptar;

    private boolean aceptado;

    /* PRE: - */
    public CargarPartida() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de CargarPartida */

    /* PRE: - */
    public String obtenerPartidaSeleccionada() {

        return tabla.obtenerPartidaSeleccionada();
    }
    /* POST: Retorna el identificador de la partida correspondiente a la partida
        seleccionada en la tabla */

    /* PRE: - */
    public boolean esAceptado() {

        return aceptado;
    }
    /* POST: Retorna cierto si se ha pulsado el boton aceptar en la ventana de cargar
        partida */

    /* PRE: - */
    private void actualizarTabla() {

        int ordenacion = ControladorPartida.ORDENACION_POR_NOMBRE;
        Object[][] listaPartidas;

        if (cajaOrdenacion.getSelectedIndex() == 1)
            ordenacion = ControladorPartida.ORDENACION_POR_FECHA;

        listaPartidas = ControladorVistas.getInstance().obtenerListaPartidas(ordenacion);
        tabla.setContenido(listaPartidas);
        botonEliminar.setEnabled(false);
        botonAceptar.setEnabled(false);

        setVisible(true);
    }
    /* POST: Actualiza el contenido de la tabla de partidas guardadas segun los criterios
        seleccionados en los componentes de la ventana de cargar partida */

    /* PRE: - */
    private void initComponents() {

        labelOrdenacion = new JLabel();
        labelOrdenacion.setText("Ordenar por:");

        cajaOrdenacion = new JComboBox();
        cajaOrdenacion.setModel(new DefaultComboBoxModel(
          new String[] {"Nombre de partida", "Fecha y Hora" }));
        cajaOrdenacion.setSelectedIndex(1);
        cajaOrdenacion.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) actualizarTabla();
            }
        });

        tabla = new TablaPartidasGuardadas();
        tabla.setContenido(ControladorVistas.getInstance()
          .obtenerListaPartidas(ControladorPartida.ORDENACION_POR_FECHA));
        tabla.obtenerContenedor().addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (tabla.esFilaSeleccionada()) {
                    botonAceptar.setEnabled(true);
                    botonEliminar.setEnabled(true);
                }
            }
        });

        botonEliminar = new JButton();
        botonEliminar.setText("Eliminar Partida");
        botonEliminar.setToolTipText("Eliminar la partida");
        botonEliminar.setEnabled(false);
        botonEliminar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance()
                  .eliminarPartida(tabla.obtenerPartidaSeleccionada());
                actualizarTabla();
            }
        });

        botonAceptar = new JButton();
        botonAceptar.setText("Aceptar");
        botonAceptar.setToolTipText("Aceptar");
        botonAceptar.setEnabled(false);
        botonAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                aceptado = true;
                setVisible(false);
            }
        });

        botonCancelar = new JButton();
        botonCancelar.setText("Cancelar");
        botonCancelar.setToolTipText("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                aceptado = false;
                setVisible(false);
            }
        });

        aceptado = false;

        setTitle("Cargar partida");
        setMinimumSize(new Dimension(400, 340));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la ventana de cargar partida */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());
        
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING,
                              layout.createSequentialGroup()
                                .addComponent(botonEliminar)
                                .addGap(65, 65, 65)
                                .addComponent(botonAceptar)
                                .addGap(18, 18, 18)
                                .addComponent(botonCancelar))
                            .addComponent(tabla, GroupLayout.Alignment.TRAILING,
                              GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)))
                    .addGroup(GroupLayout.Alignment.TRAILING,
                      layout.createSequentialGroup()
                        .addContainerGap(10, Short.MAX_VALUE)
                        .addComponent(labelOrdenacion)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 105,
                          Short.MAX_VALUE)
                        .addComponent(cajaOrdenacion, GroupLayout.PREFERRED_SIZE, 212,
                          GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaOrdenacion, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelOrdenacion))
                .addGap(18, 18, 18)
                .addComponent(tabla, GroupLayout.PREFERRED_SIZE, 184,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonEliminar)
                    .addComponent(botonCancelar)
                    .addComponent(botonAceptar))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        ventana de cargar partida */
}
