/*  Class CrearCuenta:
    Descripcion: Implementa el dialogo de creacion de una cuenta
    Autor: alex.catarineu
    Revisado: 21/12/2009 06:33 */

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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class CrearCuenta extends JDialog {

    private final static int MIN_CARACTERES = 6;
    private final static int MAX_CARACTERES = 20;

    private JLabel labelUsuario;
    private JTextField fieldUsuario;
    private JLabel labelPassword;
    private JPasswordField fieldPassword;
    private JLabel labelPasswordRepetido;
    private JPasswordField fieldPasswordRepetido;
    private JLabel labelMensajes;
    private JButton botonAceptar;
    private JButton botonCancelar;

    /* PRE: - */
    public CrearCuenta() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia del dialogo de creacion de cuenta */

    /* PRE: - */
    private void accionAceptar() {

        String usuario = fieldUsuario.getText().trim();
        String password = String.valueOf(fieldPassword.getPassword());
        String passwordRepetido = String.valueOf(fieldPasswordRepetido.getPassword());

        labelMensajes.setText("Min. " + Integer.toString(MIN_CARACTERES) + " Máx. " +
          Integer.toString(MAX_CARACTERES) + " caracteres.");
        labelMensajes.setForeground(Color.gray);

        if (usuario.length() < MIN_CARACTERES ||
          usuario.length() > MAX_CARACTERES) {
            labelMensajes.setText("El usuario debe tener de " +
              Integer.toString(MIN_CARACTERES) + " a " +
              Integer.toString(MAX_CARACTERES) + " caracteres.");
            labelMensajes.setForeground(Color.red);
        }
        else if (!Utiles.alfanumericos(usuario)) {
            labelMensajes.setText("El usuario solo debe tener números y/o letras.");
            labelMensajes.setForeground(Color.red);
        }
        else if (password.length() < MIN_CARACTERES ||
          password.length() > MAX_CARACTERES) {
            labelMensajes.setText("La contraseña debe tener de " +
              Integer.toString(MIN_CARACTERES) + " a " +
              Integer.toString(MAX_CARACTERES) + " caracteres.");
            labelMensajes.setForeground(Color.red);
        }
        else if (!Utiles.alfanumericos(password)) {
            labelMensajes.setText("La contraseña solo debe tener números y/o " +
              "letras.");
            labelMensajes.setForeground(Color.red);
        }
        else if (!password.equals(passwordRepetido)) {
            labelMensajes.setText("La contraseña y la repetición no coinciden.");
            labelMensajes.setForeground(Color.red);
        }
        else {
            try {
                ControladorVistas.getInstance().procesarCrearCuenta
                  (usuario.toLowerCase(), password);
                JOptionPane.showMessageDialog(null, "Cuenta creada con éxito.",
                  "Crear Cuenta", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            }
            catch (InstanceRepeatedException ex) {
                labelMensajes.setText("Usuario ya existe.");
                labelMensajes.setForeground(Color.red);
            }
        }
    }
    /* POST: Crea una nueva cuenta con el usuario y password de los campos editables de
        la ventana. En caso no ser campos correctos o que ya exista una cuenta registrada
        en el sistema se da un error */


    /* PRE: - */
    private void initComponents() {

        labelUsuario = new JLabel();
        labelUsuario.setText("Usuario:");

        fieldUsuario = new JTextField();

        labelPassword = new JLabel();
        labelPassword.setText("Contraseña:");

        fieldPassword = new JPasswordField();

        labelPasswordRepetido = new JLabel();
        labelPasswordRepetido.setText("Repita la contraseña:");

        fieldPasswordRepetido = new JPasswordField();

        labelMensajes = new JLabel();
        labelMensajes.setText("Min. 6 Máx. 20 caracteres.");
        labelMensajes.setHorizontalAlignment(JLabel.CENTER);
        labelMensajes.setForeground(Color.gray);

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

        setTitle("Crear Cuenta");
        setMinimumSize(new Dimension(295, 180));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        dialogo de creacion de cuenta */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(botonAceptar, GroupLayout.PREFERRED_SIZE, 92,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(botonCancelar, GroupLayout.DEFAULT_SIZE,
                  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(75, 75, 75))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(labelMensajes, GroupLayout.Alignment.LEADING,
                      GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING)
                            .addComponent(labelUsuario, GroupLayout.DEFAULT_SIZE, 145,
                              Short.MAX_VALUE)
                            .addComponent(labelPassword, GroupLayout.DEFAULT_SIZE, 145,
                              Short.MAX_VALUE)
                            .addComponent(labelPasswordRepetido,
                              GroupLayout.PREFERRED_SIZE, 135,
                                GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING, false)
                            .addComponent(fieldUsuario)
                            .addComponent(fieldPassword, GroupLayout.DEFAULT_SIZE, 111,
                              Short.MAX_VALUE)
                            .addComponent(fieldPasswordRepetido, GroupLayout.DEFAULT_SIZE,
                              111, Short.MAX_VALUE))))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsuario, GroupLayout.PREFERRED_SIZE, 19,
                      GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldUsuario, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPassword, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPassword, GroupLayout.PREFERRED_SIZE, 19,
                      GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPasswordRepetido, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPasswordRepetido, GroupLayout.PREFERRED_SIZE, 19,
                      GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMensajes)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33,
                  Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCancelar))
                .addContainerGap())
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        dialogo de creacion de cuenta */
}