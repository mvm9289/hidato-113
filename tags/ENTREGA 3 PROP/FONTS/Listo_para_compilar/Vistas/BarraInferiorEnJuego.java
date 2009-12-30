/*  Class BarraInferiorEnJuego:
    Descripcion: Vista para la barra inferior de acciones del panel principal del modo
        jugando.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 19:26 */

package Vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;

public class BarraInferiorEnJuego extends JPanel {

    private int zoom;
    private JButton botonMasZoom;
    private JButton botonMenosZoom;
    private BarraCasillas barraCasillas;

    /* PRE: - */
    public BarraInferiorEnJuego(BarraCasillas barraCasillas) {

        initComponents(barraCasillas);
        setMyLayout();
    }
    /* POST: Crea una instancia de BarraInferiorEnJuego */

    /* PRE: - */
    private void initComponents(BarraCasillas barra) {

        zoom = 100;

        botonMasZoom = new JButton();
        botonMasZoom.setText("Zoom ( + )");
        botonMasZoom.setToolTipText("Ampliar");
        botonMasZoom.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                zoom += 10;
                barraCasillas.redimensionaTablero(zoom);
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
                barraCasillas.redimensionaTablero(zoom);
                if (zoom <= 30) botonMenosZoom.setEnabled(false);
                if (zoom < 300) botonMasZoom.setEnabled(true);
            }
        });

        this.barraCasillas = barra;

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
                .addComponent(barraCasillas, GroupLayout.PREFERRED_SIZE,
                  barraCasillas.obtenerAnchura(), GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(52 - barraCasillas.obtenerAltura(),
                          52 - barraCasillas.obtenerAltura())
                        .addComponent(barraCasillas, GroupLayout.PREFERRED_SIZE,
                          barraCasillas.obtenerAltura(),
                          GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.BASELINE)
                            .addComponent(botonMasZoom)
                            .addComponent(botonMenosZoom, GroupLayout.PREFERRED_SIZE,
                              GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52 - barraCasillas.obtenerAltura(),
                  52 - barraCasillas.obtenerAltura()))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        barra inferior */
}