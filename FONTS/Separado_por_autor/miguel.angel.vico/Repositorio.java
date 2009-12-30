/*  Class Repositorio:
    Descripcion: Vista para la ventana de seleccion de los tableros del repositorio.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 16:56 */

package Vistas;

import Dominio.ControladorTablero;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;

public class Repositorio extends JDialog {

    private JLabel labelOrdenacion;
    private JComboBox cajaOrdenacion;
    private TablaRepositorio tabla;
    private JLabel labelVer;
    private JRadioButton radioTodos;
    private JRadioButton radioPropuestos;
    private ButtonGroup grupoBotones;
    private JButton botonAceptar;
    private JButton botonCancelar;

    private boolean aceptado;

    /* PRE: - */
    public Repositorio() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de Repositorio */

    /* PRE: - */
    public boolean esAceptado() {

        return aceptado;
    }
    /* POST: Retorna cierto si se ha pulsado el boton aceptar en la ventana de
        repositorio */

    /* PRE: - */
    private void actualizarTabla() {

        int seleccion = 0;
        int ordenacion = 0;

        if (radioTodos.isSelected()) seleccion = ControladorTablero.SEL_TODOS;
        else seleccion = ControladorTablero.SEL_PROPUESTOS;

        switch (cajaOrdenacion.getSelectedIndex()) {
            case 0:
                ordenacion = ControladorTablero.ORD_ID;
                break;
            case 1:
                ordenacion = ControladorTablero.ORD_TOPOLOGIA;
                break;
            case 2:
                ordenacion = ControladorTablero.ORD_DIFICULTAD;
                break;
            default:
                break;
        }

        tabla.setContenido(ControladorVistas.getInstance()
          .obtenerListaTableros(seleccion | ordenacion));
        setVisible(true);
    }
    /* POST: Actualiza el contenido de la tabla de tableros segun los criterios
        seleccionados en los componentes de la ventana de repositorio */

    /* PRE: - */
    private void initComponents() {

        labelOrdenacion = new JLabel();
        labelOrdenacion.setText("Ordenar por:");

        cajaOrdenacion = new JComboBox();
        cajaOrdenacion.setModel(new DefaultComboBoxModel(
          new String[] {"Nombre del tablero", "Topología", "Dificultad"}));
        cajaOrdenacion.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) actualizarTabla();
            }
        });

        tabla = new TablaRepositorio();
        tabla.setContenido(ControladorVistas.getInstance()
          .obtenerListaTableros(ControladorTablero.SEL_TODOS |
          ControladorTablero.ORD_ID));
        tabla.obtenerContenedor().addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (tabla.esFilaSeleccionada()) botonAceptar.setEnabled(true);
            }
        });

        labelVer = new JLabel();
        labelVer.setText("Ver:");

        radioTodos = new JRadioButton();
        radioTodos.setText("Todos");
        radioTodos.setSelected(true);
        radioTodos.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) actualizarTabla();
            }
        });

        radioPropuestos = new JRadioButton();
        radioPropuestos.setText("Propuestos");
        radioPropuestos.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) actualizarTabla();
            }
        });

        grupoBotones = new ButtonGroup();
        grupoBotones.add(radioTodos);
        grupoBotones.add(radioPropuestos);

        botonAceptar = new JButton();
        botonAceptar.setText("Aceptar");
        botonAceptar.setToolTipText("Aceptar");
        botonAceptar.setEnabled(false);
        botonAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String tablero;

                tablero = tabla.obtenerTableroSeleccionado();
                ControladorVistas.getInstance().procesarNuevaPartida(tablero);
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

        setTitle("Repositorio");
        setMinimumSize(new Dimension(340, 280));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la ventana de repositorio */

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
                          (GroupLayout.Alignment.TRAILING)
                            .addComponent(tabla, GroupLayout.Alignment.LEADING,
                              GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                            .addGroup(GroupLayout.Alignment.LEADING,
                              layout.createSequentialGroup()
                                .addComponent(labelOrdenacion)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                  93, Short.MAX_VALUE)
                                .addComponent(cajaOrdenacion, GroupLayout.PREFERRED_SIZE,
                                  166, GroupLayout.PREFERRED_SIZE))
                            .addGroup(GroupLayout.Alignment.LEADING,
                              layout.createSequentialGroup()
                                .addComponent(labelVer)
                                .addGap(69, 69, 69)
                                .addComponent(radioTodos)
                                .addGap(18, 18, 18)
                                .addComponent(radioPropuestos))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(botonAceptar)
                        .addGap(18, 18, 18)
                        .addComponent(botonCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaOrdenacion, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelOrdenacion))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabla, GroupLayout.PREFERRED_SIZE, 123,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelVer)
                    .addComponent(radioTodos)
                    .addComponent(radioPropuestos))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addContainerGap(63, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        ventana de repositorio */
}
