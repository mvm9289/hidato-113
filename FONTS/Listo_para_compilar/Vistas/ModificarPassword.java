/*  Class ModificarPassword:
    Descripcion: Vista para el dialogo de cambio de password del usuario actual.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 21:54 */

package Vistas;

import Excepciones.PasswordIncorrectException;
import Utiles.Utiles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle;

public class ModificarPassword extends JDialog {
    
    private final static int MIN_CARACTERES = 6;
    private final static int MAX_CARACTERES = 20;

    private JLabel labelPasswordActual;
    private JPasswordField fieldPasswordActual;
    private JLabel labelPasswordNuevo;
    private JPasswordField fieldPasswordNuevo;
    private JLabel labelPasswordRepetido;
    private JPasswordField fieldPasswordRepetido;
    private JLabel labelMensajes;
    private JButton botonAceptar;
    private JButton botonCancelar;

    /* PRE: - */
    public ModificarPassword() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de ModificarPassword */

    /* PRE: - */
    private void accionAceptar() {

        String passwordActual = String.valueOf(fieldPasswordActual.getPassword());
        String passwordNuevo = String.valueOf(fieldPasswordNuevo.getPassword());
        String passwordRepetido = String.valueOf(fieldPasswordRepetido.getPassword());

        labelMensajes.setText(" ");

        if (passwordNuevo.length() < MIN_CARACTERES ||
          passwordNuevo.length() > MAX_CARACTERES)
            labelMensajes.setText("La nueva contraseña debe tener de " +
              Integer.toString(MIN_CARACTERES) + " a " + Integer.toString(MAX_CARACTERES)
              + " caracteres.");
        else if (!Utiles.alfanumericos(passwordNuevo))
            labelMensajes.setText("La nueva contraseña solo debe tener números y/o " +
              "letras.");
        else if (!passwordNuevo.equals(passwordRepetido))
            labelMensajes.setText("La nueva contraseña y la repetición no coinciden.");
        else {
            try {
                ControladorVistas.getInstance()
                  .procesarModificarPassword(passwordActual, passwordNuevo);
                JOptionPane.showMessageDialog(null, "Contraseña modificada con éxito.",
                  "Modificar Contraseña", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            }
            catch (PasswordIncorrectException ex) {
                fieldPasswordActual.setText("");
                labelMensajes.setText("La contraseña actual es incorrecta.");
            }
        }
    }
    /* POST: Cambia el password del usuario actual o lanza un mensaje de error si no se
        cumplen las condiciones de las contrase(ny)as. */

    /* PRE: - */
    private void initComponents() {

        labelPasswordActual = new JLabel();
        labelPasswordActual.setText("Contraseña actual:");

        fieldPasswordActual = new JPasswordField();

        labelPasswordNuevo = new JLabel();
        labelPasswordNuevo.setText("Nueva contraseña:");

        fieldPasswordNuevo = new JPasswordField();

        labelPasswordRepetido = new JLabel();
        labelPasswordRepetido.setText("Repita la contraseña:");

        fieldPasswordRepetido = new JPasswordField();

        labelMensajes = new JLabel();
        labelMensajes.setText(" ");
        labelMensajes.setHorizontalAlignment(JLabel.CENTER);
        labelMensajes.setForeground(Color.red);

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

                setVisible(false);
            }
        });

        setTitle("Modificar Contraseña");
        setMinimumSize(new Dimension(400, 250));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el dialogo de cambio de password */

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
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING)
                            .addComponent(labelPasswordNuevo)
                            .addComponent(labelPasswordActual)
                            .addComponent(labelPasswordRepetido))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING)
                            .addComponent(fieldPasswordActual, GroupLayout.DEFAULT_SIZE,
                              175, Short.MAX_VALUE)
                            .addComponent(fieldPasswordNuevo, GroupLayout.DEFAULT_SIZE,
                              175, Short.MAX_VALUE)
                            .addComponent(fieldPasswordRepetido, GroupLayout.DEFAULT_SIZE,
                              175, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(botonAceptar)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 88,
                          Short.MAX_VALUE)
                        .addComponent(botonCancelar)
                        .addGap(62, 62, 62))
                    .addComponent(labelMensajes, GroupLayout.DEFAULT_SIZE, 354,
                      Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPasswordActual)
                    .addComponent(fieldPasswordActual, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPasswordNuevo)
                    .addComponent(fieldPasswordNuevo, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPasswordRepetido)
                    .addComponent(fieldPasswordRepetido, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelMensajes, GroupLayout.PREFERRED_SIZE, 17,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonCancelar))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        dialogo de cambio de password */
}
