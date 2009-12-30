/*  Class Estadisticas:
    Descripcion: Vista para el dialogo de estadisticas generales y personales
    Autor: daniel.camarasa
    Revisado: 20/12/2009 21:40 */

package Vistas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class Estadisticas extends JDialog {

    private JLabel labelGenerales;
    private TablaEstadisticas tablaGenerales;
    private JLabel labelPersonales;
    private TablaEstadisticas tablaPersonales;
    private JButton botonAceptar;

    /* PRE: - */
    public Estadisticas() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de Estadisticas */

    /* PRE: - */
    private void initComponents() {

        labelGenerales = new JLabel();
        labelGenerales.setText("Estadísticas generales");

        tablaGenerales = new TablaEstadisticas();
        tablaGenerales.setContenido(ControladorVistas.getInstance()
          .obtenerEstadisticas(true));

        labelPersonales = new JLabel();
        labelPersonales.setText("Estadísticas personales");

        tablaPersonales = new TablaEstadisticas();
        tablaPersonales.setContenido(ControladorVistas.getInstance()
          .obtenerEstadisticas(false));

        botonAceptar = new JButton();
        botonAceptar.setText("Aceptar");
        botonAceptar.setToolTipText("Aceptar");
        botonAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setVisible(false);
            }
        });

        setTitle("Estadísticas Hidato");
        setMinimumSize(new Dimension(375, 556));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el dialogo de estadisticas */

    /* PRE: - */
    public void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());
        
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(botonAceptar, GroupLayout.Alignment.TRAILING)
                        .addComponent(tablaPersonales, GroupLayout.Alignment.TRAILING,
                          GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelPersonales)
                    .addComponent(labelGenerales)
                    .addComponent(tablaGenerales, GroupLayout.PREFERRED_SIZE, 350,
                      GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(labelGenerales)
                .addGap(18, 18, 18)
                .addComponent(tablaGenerales, GroupLayout.PREFERRED_SIZE, 180,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelPersonales, GroupLayout.PREFERRED_SIZE, 14,
                  GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tablaPersonales, GroupLayout.DEFAULT_SIZE, 180,
                  Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(botonAceptar)
                .addContainerGap())
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        dialogo de estadisticas */
}