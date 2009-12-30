/*  Class GuardarPartida:
    Descripcion: Vista para la ventana de dialogo de guardar una partida.
    Autor: alex.catarineu
    Revisado: 21/12/2009 06:35 */

package Vistas;

import Excepciones.InstanceRepeatedException;
import Utiles.Utiles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class GuardarPartida extends JDialog {

    private final static int MIN_SIZE_NOMBRE = 6;
    private final static int MAX_SIZE_NOMBRE = 20;

    private JLabel labelNombrePartida;
    private JTextField fieldNombrePartida;
    private JLabel labelMensajes;
    private JButton botonAceptar;
    private JButton botonCancelar;

    private boolean aceptado;

    /* PRE: - */
    public GuardarPartida(String nombrePropuesto) {

        initComponents(nombrePropuesto);
        setMyLayout();
    }
    /* POST: Crea una instancia de GuardarPartida */

    /* PRE: - */
    public boolean esAceptado() {

        return aceptado;
    }
    /* POST: Devuelve cierto si el usuario ha aceptado guardar, falso en caso contrario */

    /* PRE: - */
    private void accionAceptar() {

        String nombrePartida = fieldNombrePartida.getText().trim();

        labelMensajes.setText(" ");

        if (nombrePartida.length() < MIN_SIZE_NOMBRE ||
          nombrePartida.length() > MAX_SIZE_NOMBRE)
            labelMensajes.setText("El nombre de partida debe tener de " +
              Integer.toString(MIN_SIZE_NOMBRE) + " a " +
              Integer.toString(MAX_SIZE_NOMBRE) + " caracteres.");
        else if (!Utiles.alfanumericos(nombrePartida))
            labelMensajes.setText("El nombre de partida solo debe tener " +
              "números y/o letras.");
        else {
            try {
                ControladorVistas.getInstance().procesarGuardarPartida(nombrePartida);
                aceptado = true;
                setVisible(false);
            }
            catch (InstanceRepeatedException ex) {
                labelMensajes.setText("El nombre de partida ya existe.");
            }
        }
    }
    /* POST: Realiza la accion de guardar la partida o muestra un error en caso de que
        los campos no sean correctos o ya exista el nombre de la partida */

    /* PRE: - */
    private void initComponents(String nombrePopuesto) {

        labelNombrePartida = new JLabel();
        labelNombrePartida.setText("Introduzca un nombre para la partida:");

        fieldNombrePartida = new JTextField();
        fieldNombrePartida.setText(nombrePopuesto);

        labelMensajes = new JLabel();
        labelMensajes.setForeground(Color.red);
        labelMensajes.setHorizontalAlignment(JLabel.CENTER);
        labelMensajes.setText(" ");

        botonAceptar = new JButton();
        botonAceptar.setText("Aceptar");
        botonAceptar.setToolTipText("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                accionAceptar();
            }
        });
        getRootPane().setDefaultButton(botonAceptar);

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

        setTitle("Guardar Partida");
        setMinimumSize(new Dimension(400, 190));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el dialogo de guardar una partida */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup
                  (GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelMensajes, GroupLayout.Alignment.LEADING,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelNombrePartida, GroupLayout.Alignment.LEADING,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fieldNombrePartida, GroupLayout.Alignment.LEADING,
                      GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAceptar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonCancelar)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(labelNombrePartida)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fieldNombrePartida, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(labelMensajes)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar)
                    .addComponent(botonAceptar))
                .addContainerGap(169, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        dialogo de guardar una partida */
}