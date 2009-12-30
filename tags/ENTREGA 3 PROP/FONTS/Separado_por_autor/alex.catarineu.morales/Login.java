/*  Class Login:
    Descripcion: Vista para el panel que contiene los campos y botones para realizar el
        inicio de sesion.
    Autor: alex.catarineu
    Revisado: 21/12/2009 06:35 */

package Vistas;

import Excepciones.InstanceNotFoundException;
import Excepciones.PasswordIncorrectException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class Login extends JRootPane {

    private JLabel labelUsuario;
    private JTextField fieldUsuario;
    private JLabel labelPassword;
    private JPasswordField fieldPassword;
    private JLabel labelMensajes;
    private JButton botonIniciar;
    private JLabel linkCrearCuenta;

    /* PRE: - */
    public Login() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de Login */

    /* PRE: - */
    private void accionAceptar() {

        labelMensajes.setText("");

        try {
            ControladorVistas.getInstance().iniciarSesion
              (fieldUsuario.getText().trim(),
              String.valueOf(fieldPassword.getPassword()));
        }
        catch (InstanceNotFoundException ex) {
            fieldPassword.setText("");
            labelMensajes.setText("Usuario incorrecto.");
        }
        catch (PasswordIncorrectException ex) {
            fieldPassword.setText("");
            labelMensajes.setText("Contraseña incorrecta.");
        }
    }
    /* POST: Realiza la accion de iniciar sesion con los datos obtenidos de los campos o
        saca el error correspondiente si algun campo es incorrecto o simplemente no
        existe esa cuenta */

    /* PRE: - */
    private void initComponents() {

        labelUsuario = new JLabel();
        labelUsuario.setText("Usuario:");

        fieldUsuario = new JTextField();

        labelPassword = new JLabel();
        labelPassword.setText("Contraseña:");

        fieldPassword = new JPasswordField();

        labelMensajes = new JLabel();
        labelMensajes.setText(" ");
        labelMensajes.setHorizontalAlignment(JLabel.CENTER);
        labelMensajes.setForeground(Color.red);

        botonIniciar = new JButton();
        botonIniciar.setText("Iniciar Sesión");
        botonIniciar.setToolTipText("Iniciar Sesión");
        botonIniciar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                accionAceptar();
            }
        });
        setDefaultButton(botonIniciar);

        linkCrearCuenta = new JLabel();
        linkCrearCuenta.setText("<html><u>Crear una nueva cuenta</u>");
        linkCrearCuenta.setToolTipText("Crear una nueva cuenta");
        linkCrearCuenta.setHorizontalAlignment(JLabel.CENTER);
        linkCrearCuenta.setForeground(new Color(0, 0, 204));
        linkCrearCuenta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkCrearCuenta.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                ControladorVistas.getInstance().crearCuenta();
            }
        });
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el panel de login */
    
    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup
                  (GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING)
                            .addComponent(labelMensajes, GroupLayout.Alignment.TRAILING,
                              GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup
                                  (GroupLayout.Alignment.LEADING)
                                    .addComponent(labelUsuario)
                                    .addComponent(labelPassword))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                  17, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup
                                  (GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fieldUsuario)
                                    .addComponent(fieldPassword, GroupLayout.DEFAULT_SIZE,
                                      100, Short.MAX_VALUE))))
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(linkCrearCuenta, GroupLayout.PREFERRED_SIZE,
                                  167, GroupLayout.PREFERRED_SIZE))
                            .addGroup(GroupLayout.Alignment.LEADING,
                              layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(botonIniciar, GroupLayout.PREFERRED_SIZE,
                                  167, GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUsuario)
                    .addComponent(fieldUsuario, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPassword)
                    .addComponent(fieldPassword, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMensajes)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonIniciar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(linkCrearCuenta)
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        panel de login */
}
