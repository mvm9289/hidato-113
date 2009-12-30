/*  Class BarraSuperiorEnJuego:
    Descripcion: Vista para la barra superior de acciones del panel principal del modo
        jugando.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 19:27 */

package Vistas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;

public class BarraSuperiorEnJuego extends JPanel {

    private JButton botonIniciar;
    private JLabel labelTiempo;
    private Cronometro cronometro;

    /* PRE: - */
    public BarraSuperiorEnJuego() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de BarraSuperiorEnJuego */

    /* PRE: - */
    public void iniciarTiempo() {

        cronometro.iniciar();
    }
    /* POST: Inicia/continua la contabilizacion de tiempo por parte del cronometro */

    /* PRE: - */
    public void pararTiempo() {

        cronometro.parar();
    }
    /* POST: Para la contabilizacion de tiempo por parte del cronometro */

    /* PRE: - */
    public int obtenerTiempo() {

        return cronometro.obtenerTiempo();
    }
    /* POST: Retorna el tiempo actual que marca el cronometro */

    /* PRE: - */
    public void setTiempo(int tiempo) {

        cronometro.setTiempo(tiempo);
    }
    /* POST: Establece el tiempo del cronometro en 'tiempo' segundos */

    /* PRE: - */
    private void initComponents() {

        botonIniciar = new JButton();
        botonIniciar.setText("Iniciar partida");
        botonIniciar.setToolTipText("Iniciar la partida");
        botonIniciar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().setPartidaIniciada(true);
                botonIniciar.setEnabled(false);
                iniciarTiempo();
            }
        });

        labelTiempo = new JLabel();
        labelTiempo.setText("Tiempo de partida:");

        cronometro = new Cronometro();
        cronometro.setFont(new Font("Arial", Font.BOLD, 16));

        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la barra superior */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(this);
        
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(botonIniciar)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29,
                  Short.MAX_VALUE)
                .addComponent(labelTiempo)
                .addGap(33, 33, 33)
                .addComponent(cronometro)
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cronometro)
                    .addComponent(botonIniciar)
                    .addComponent(labelTiempo))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        barra superior */
}
