/*  Class VentanaPrincipal:
    Descripcion: Vista para la ventana principal de la aplicacion.
    Autor: daniel.camarasa
    Revisado: 21/12/2009 06:39 */

package Vistas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {

    private BarraMenu barraMenu;
    private JPanel panel;

    /* PRE: 'panel' se corresponde con el panel principal de la ventana */
    public VentanaPrincipal(JPanel panel) {

        initComponents(panel);
        setMyLayout();
        pack();
        setLocationRelativeTo(null);
    }
    /* POST: Crea la instancia de VentanaPrincipal */

    /* PRE: - */
    public void bloqueoLogin(boolean bloquear) {

        barraMenu.bloqueoLogin(bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' las opciones de la barra de
        menu correspondientes al inicio de sesion */

    /* PRE: - */
    public void bloqueoNuevaPartida(boolean bloquear) {

        barraMenu.bloqueoNuevaPartida(bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' las opciones de la barra de
        menu correspondientes a la apertura de una partida */

    /* PRE: - */
    public void bloqueoPartidaIniciada(boolean bloquear) {

        barraMenu.bloqueoPartidaIniciada(bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' las opciones de la barra de
        menu correspondientes al inicio de partida */

    /* PRE: - */
    public void bloqueoDeshacer(boolean bloquear) {

        barraMenu.bloqueoDeshacer(bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' la opcion "Deshacer" de la
        barra de menu  */

    /* PRE: - */
    public void setPanel(JPanel panel) {

        this.panel.setVisible(false);
        if (panel !=  null) {
            this.panel = panel;
            this.panel.setVisible(true);
            setMyLayout();
            if (getExtendedState() != MAXIMIZED_BOTH) {
                pack();
                setLocationRelativeTo(null);
            }
        }
    }
    /* POST: Oculta el panel principal de la ventana y establece 'panel' como el nuevo
        panel principal. Resimensiona la ventana en el caso de que sea necesario */

    /* PRE: 'panel' se corresponde con el panel principal de la ventana */
    private void initComponents(JPanel panel) {

        barraMenu = new BarraMenu();
        setJMenuBar(barraMenu);
        barraMenu.bloqueoLogin(true);
        barraMenu.bloqueoNuevaPartida(true);
        barraMenu.bloqueoPartidaIniciada(true);
        barraMenu.bloqueoDeshacer(true);

        this.panel = panel;

        setTitle("Hidato 11.3");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                ControladorVistas.getInstance().salir();
            }
            
            public void windowIconified(WindowEvent e) {

                ControladorVistas.getInstance().pararTiempo();
            }

            public void windowDeiconified(WindowEvent e) {

                if (ControladorVistas.getInstance().esPartidaIniciada())
                    ControladorVistas.getInstance().iniciarTiempo();
            }
        });
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la ventana principal */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());
        
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel, GroupLayout.Alignment.TRAILING,
              GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel, GroupLayout.DEFAULT_SIZE,
              GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        ventana principal */
}
