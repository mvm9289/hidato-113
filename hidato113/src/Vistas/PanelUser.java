/*  Class PanelUser:
    Descripcion: Vista para el panel principal de usuario.
    Autor: miguel.angel.vico
    Revisado: 30/12/2009 06:02 */

package Vistas;

import Dominio.ControladorPartida;
import Dominio.ControladorTablero;
import Excepciones.InstanceRepeatedException;
import Tiempo.Fecha;
import Tiempo.Hora;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;

public class PanelUser extends JPanel {

    private final static int SIZE_FACIL = 5;
    private final static int PREFIJADAS_FACIL = 11;
    private final static int SIZE_MEDIO = 9;
    private final static int PREFIJADAS_MEDIO = 36;
    private final static int SIZE_DIFICIL = 13;
    private final static int PREFIJADAS_DIFICIL = 76;

    private JPanel contenedorPartidas;
    private JLabel labelUltimasPartidas;
    private TablaPartidasGuardadas tablaPartidas;
    private JButton botonCargar;

    private JPanel contenedorPropuestos;
    private JLabel labelPropuestos;
    private TablaRepositorio tablaPropuestos;
    private JButton botonJugarPropuestos;

    private JPanel contenedorPartidaRapida;
    private JLabel labelPartidaRapida;
    private JPanel contenedorNiveles;
    private JRadioButton radioFacil;
    private JRadioButton radioMedio;
    private JRadioButton radioDificil;
    private ButtonGroup grupoBotones;
    private JButton botonJugarPartidaRapida;

    public PanelUser() {

        initComponents();
        setMyLayout();
    }

    private String nuevoIdTablero() {

        String nuevoId = "gen";
        Fecha fecha = new Fecha();
        Hora hora = new Hora();
        fecha.hoy();
        hora.ahora();

        nuevoId += Integer.toString(fecha.obtenerDiaDelMes());
        nuevoId += Integer.toString(fecha.obtenerMesInt());
        nuevoId += Integer.toString(fecha.obtenerAnio());
        nuevoId += Integer.toString(hora.obtenerHora());
        nuevoId += Integer.toString(hora.obtenerMinuto());
        nuevoId += Integer.toString(hora.obtenerSegundo());

        return nuevoId;
    }

    private void accionPartidaRapida() {

        try {
            if (radioFacil.isSelected()) {
                if (ControladorVistas.getInstance().generarTablero(nuevoIdTablero(),
                  ControladorTablero.DIF_FACIL, ControladorTablero.TOP_RECTANGULO,
                  PREFIJADAS_FACIL, new int[SIZE_FACIL][SIZE_FACIL]))
                    setVisible(false);
            }
            else if (radioMedio.isSelected()) {
                if (ControladorVistas.getInstance().generarTablero(nuevoIdTablero(),
                  ControladorTablero.DIF_MEDIO, ControladorTablero.TOP_RECTANGULO,
                  PREFIJADAS_MEDIO, new int[SIZE_MEDIO][SIZE_MEDIO]))
                    setVisible(false);
            }
            else {
                if (ControladorVistas.getInstance().generarTablero(nuevoIdTablero(),
                  ControladorTablero.DIF_DIFICIL, ControladorTablero.TOP_RECTANGULO,
                  PREFIJADAS_DIFICIL, new int[SIZE_DIFICIL][SIZE_DIFICIL]))
                    setVisible(false);
            }
        }
        catch(InstanceRepeatedException e) {}
    }

    private void initComponents() {

        contenedorPartidas = new JPanel();
        contenedorPartidas.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        contenedorPartidas.setPreferredSize(new Dimension(200, 200));

        labelUltimasPartidas = new JLabel();
        labelUltimasPartidas.setText("Últimas partidas guardadas:");

        tablaPartidas = new TablaPartidasGuardadas();
        tablaPartidas.setContenido(ControladorVistas.getInstance()
          .obtenerListaPartidas(ControladorPartida.ORDENACION_POR_FECHA));
        tablaPartidas.obtenerContenedor().addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (tablaPartidas.esFilaSeleccionada()) botonCargar.setEnabled(true);
            }
        });

        botonCargar = new JButton();
        botonCargar.setText("Cargar");
        botonCargar.setToolTipText("Cargar partida");
        botonCargar.setEnabled(false);
        botonCargar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().procesarCargarPartida
                  (tablaPartidas.obtenerPartidaSeleccionada());
            }
        });

        contenedorPropuestos = new JPanel();
        contenedorPropuestos.setBorder(BorderFactory.createBevelBorder
          (BevelBorder.RAISED));
        contenedorPropuestos.setPreferredSize(new Dimension(200, 200));

        labelPropuestos = new JLabel();
        labelPropuestos.setText("Tableros propuestos:");

        tablaPropuestos = new TablaRepositorio();
        tablaPropuestos.setContenido(ControladorVistas.getInstance()
          .obtenerListaTableros(ControladorTablero.SEL_PROPUESTOS |
          ControladorTablero.ORD_ID));
        tablaPropuestos.obtenerContenedor().addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (tablaPropuestos.esFilaSeleccionada())
                    botonJugarPropuestos.setEnabled(true);
            }
        });

        botonJugarPropuestos = new JButton();
        botonJugarPropuestos.setText("Jugar");
        botonJugarPropuestos.setToolTipText("Jugar");
        botonJugarPropuestos.setEnabled(false);
        botonJugarPropuestos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String tablero;

                tablero = tablaPropuestos.obtenerTableroSeleccionado();
                ControladorVistas.getInstance().procesarNuevaPartida(tablero);
                setVisible(false);
            }
        });

        contenedorPartidaRapida = new JPanel();
        contenedorPartidaRapida.setBorder(BorderFactory.createBevelBorder
          (BevelBorder.RAISED));
        contenedorPartidaRapida.setPreferredSize(new Dimension(400, 100));

        labelPartidaRapida = new JLabel();
        labelPartidaRapida.setText("Partida rápida:");

        contenedorNiveles = new JPanel();
        contenedorNiveles.setBorder(BorderFactory.createBevelBorder
          (BevelBorder.LOWERED));

        radioFacil = new JRadioButton();
        radioFacil.setText("Fácil");
        radioFacil.setSelected(true);

        radioMedio = new JRadioButton();
        radioMedio.setText("Intermedio");

        radioDificil = new JRadioButton();
        radioDificil.setText("Díficil");

        grupoBotones = new ButtonGroup();
        grupoBotones.add(radioFacil);
        grupoBotones.add(radioMedio);
        grupoBotones.add(radioDificil);

        botonJugarPartidaRapida = new JButton();
        botonJugarPartidaRapida.setText("Jugar");
        botonJugarPartidaRapida.setToolTipText("Jugar");
        botonJugarPartidaRapida.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                accionPartidaRapida();
            }
        });
    }

    private void setMyLayout() {

        GroupLayout contenedorPartidasLayout = new GroupLayout(contenedorPartidas);
        contenedorPartidas.setLayout(contenedorPartidasLayout);
        contenedorPartidasLayout.setHorizontalGroup(
            contenedorPartidasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPartidasLayout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(botonCargar)
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(contenedorPartidasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorPartidasLayout.createParallelGroup
                  (GroupLayout.Alignment.LEADING)
                    .addComponent(tablaPartidas, GroupLayout.DEFAULT_SIZE, 256,
                      Short.MAX_VALUE)
                    .addComponent(labelUltimasPartidas))
                .addContainerGap())
        );
        contenedorPartidasLayout.setVerticalGroup(
            contenedorPartidasLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPartidasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelUltimasPartidas)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablaPartidas, GroupLayout.DEFAULT_SIZE, 179,
                  Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(botonCargar)
                .addGap(20, 20, 20))
        );

        GroupLayout contenedorPropuestosLayout = new GroupLayout(contenedorPropuestos);
        contenedorPropuestos.setLayout(contenedorPropuestosLayout);
        contenedorPropuestosLayout.setHorizontalGroup(
            contenedorPropuestosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPropuestosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorPropuestosLayout.createParallelGroup
                  (GroupLayout.Alignment.LEADING)
                    .addComponent(labelPropuestos)
                    .addComponent(tablaPropuestos, GroupLayout.Alignment.TRAILING,
                      GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(contenedorPropuestosLayout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addComponent(botonJugarPropuestos)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        contenedorPropuestosLayout.setVerticalGroup(
            contenedorPropuestosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPropuestosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPropuestos)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablaPropuestos, GroupLayout.DEFAULT_SIZE, 178,
                  Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(botonJugarPropuestos)
                .addGap(21, 21, 21))
        );

        GroupLayout contenedorNivelesLayout = new GroupLayout(contenedorNiveles);
        contenedorNiveles.setLayout(contenedorNivelesLayout);
        contenedorNivelesLayout.setHorizontalGroup(
            contenedorNivelesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contenedorNivelesLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(radioFacil)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 145,
                  Short.MAX_VALUE)
                .addComponent(radioMedio)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 139,
                  Short.MAX_VALUE)
                .addComponent(radioDificil)
                .addGap(40, 40, 40))
        );
        contenedorNivelesLayout.setVerticalGroup(
            contenedorNivelesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(contenedorNivelesLayout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(contenedorNivelesLayout.createParallelGroup
                  (GroupLayout.Alignment.BASELINE)
                    .addComponent(radioMedio)
                    .addComponent(radioDificil)
                    .addComponent(radioFacil))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        GroupLayout contenedorPartidaRapidaLayout =
          new GroupLayout(contenedorPartidaRapida);
        contenedorPartidaRapida.setLayout(contenedorPartidaRapidaLayout);
        contenedorPartidaRapidaLayout.setHorizontalGroup(
            contenedorPartidaRapidaLayout.createParallelGroup
              (GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPartidaRapidaLayout.createSequentialGroup()
                .addContainerGap(251, Short.MAX_VALUE)
                .addComponent(botonJugarPartidaRapida)
                .addContainerGap(251, Short.MAX_VALUE))
            .addGroup(contenedorPartidaRapidaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contenedorPartidaRapidaLayout.createParallelGroup
                  (GroupLayout.Alignment.LEADING)
                    .addGroup(contenedorPartidaRapidaLayout.createSequentialGroup()
                        .addComponent(contenedorNiveles, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(contenedorPartidaRapidaLayout.createSequentialGroup()
                        .addComponent(labelPartidaRapida)
                        .addGap(322, 322, 322))))
        );
        contenedorPartidaRapidaLayout.setVerticalGroup(
            contenedorPartidaRapidaLayout.createParallelGroup
              (GroupLayout.Alignment.LEADING)
            .addGroup(contenedorPartidaRapidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPartidaRapida)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedorNiveles, GroupLayout.DEFAULT_SIZE,
                  GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(botonJugarPartidaRapida)
                .addGap(20, 20, 20))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contenedorPartidas, GroupLayout.DEFAULT_SIZE, 381,
                  Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedorPropuestos, GroupLayout.DEFAULT_SIZE, 381,
                  Short.MAX_VALUE))
            .addComponent(contenedorPartidaRapida, GroupLayout.DEFAULT_SIZE, 768,
              Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(contenedorPartidas, GroupLayout.DEFAULT_SIZE, 168,
                      Short.MAX_VALUE)
                    .addComponent(contenedorPropuestos, GroupLayout.DEFAULT_SIZE, 168,
                      Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedorPartidaRapida, GroupLayout.PREFERRED_SIZE, 215,
                  GroupLayout.PREFERRED_SIZE))
        );
    }
}
