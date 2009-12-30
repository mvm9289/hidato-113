/*  Class BarraMenu:
    Descripcion: Vista para la barra de menu de la aplicacion.
    Autor: alex.catarineu
    Revisado: 21/12/2009 06:27 */

package Vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

public class BarraMenu extends JMenuBar {

    /* Menu Archivo */
    private JMenu menuArchivo;
    private JMenuItem itemNuevaPartida;
    private JSeparator separador1;
    private JMenuItem itemCargarPartida;
    private JMenuItem itemGuardarPartida;
    private JSeparator separador2;
    private JMenuItem itemAbandonarPartida;
    private JSeparator separador3;
    private JMenuItem itemImprimir;
    private JSeparator separador4;
    private JMenuItem itemSalir;

    /* Menu Editar */
    private JMenu menuEditar;
    private JMenuItem itemDeshacer;
    private JSeparator separador5;
    private JMenuItem itemResetearPartida;

    /* Menu Cuenta */
    private JMenu menuCuenta;
    private JMenuItem itemCerrarSesion;
    private JSeparator separador6;
    private JMenuItem itemModificarPassword;
    private JSeparator separador7;
    private JMenuItem itemEliminarCuenta;

    /* Menu Estadisticas */
    private JMenu menuEstadisticas;
    private JMenuItem itemConsultarEstadisticas;
    private JMenuItem itemConsultarRanking;
    private JSeparator separador8;
    private JMenuItem itemResetearEstadisticas;

    /* Menu Ayuda */
    private JMenu menuAyuda;
    private JMenuItem itemDarPista;
    private JMenuItem itemResolverHidato;
    private JSeparator separador9;
    private JMenuItem itemManualUsuario;

    /* PRE: - */
    public BarraMenu() {

        initComponents();
    }
    /* POST: Crea la instancia de BarraMenu */

    /* PRE: - */
    public void bloqueoLogin(boolean bloquear) {
        
        itemNuevaPartida.setEnabled(!bloquear);
        itemCargarPartida.setEnabled(!bloquear);
        itemCerrarSesion.setEnabled(!bloquear);
        itemModificarPassword.setEnabled(!bloquear);
        itemEliminarCuenta.setEnabled(!bloquear);
        itemConsultarRanking.setEnabled(!bloquear);
        itemConsultarEstadisticas.setEnabled(!bloquear);
        itemResetearEstadisticas.setEnabled(!bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' las opciones de la barra de
        menu correspondientes al inicio de sesion */

    /* PRE: - */
    public void bloqueoNuevaPartida(boolean bloquear) {

        itemGuardarPartida.setEnabled(!bloquear);
        itemAbandonarPartida.setEnabled(!bloquear);
        itemImprimir.setEnabled(!bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' las opciones de la barra de
        menu correspondientes a la apertura de una partida */

    /* PRE: - */
    public void bloqueoPartidaIniciada(boolean bloquear) {

        itemResetearPartida.setEnabled(!bloquear);
        itemDarPista.setEnabled(!bloquear);
        itemResolverHidato.setEnabled(!bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' las opciones de la barra de
        menu correspondientes al inicio de partida */

    /* PRE: - */
    public void bloqueoDeshacer(boolean bloquear) {

        itemDeshacer.setEnabled(!bloquear);
    }
    /* POST: Bloquea/Desbloquea segun el valor de 'bloquear' la opcion "Deshacer" de la
        barra de menu  */

    /* PRE: - */
    private void initComponents() {

        itemNuevaPartida = new JMenuItem();
        itemNuevaPartida.setText("Nueva Partida...");
        itemNuevaPartida.setToolTipText("Crear una nueva partida");
        itemNuevaPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
          InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        itemNuevaPartida.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().nuevaPartida();
            }
        });

        separador1 = new JSeparator();

        itemCargarPartida = new JMenuItem();
        itemCargarPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
          InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        itemCargarPartida.setText("Cargar Partida...");
        itemCargarPartida.setToolTipText("Abrir una partida existente");
        itemCargarPartida.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().cargarPartida();
            }
        });

        itemGuardarPartida = new JMenuItem();
        itemGuardarPartida.setText("Guardar Partida");
        itemGuardarPartida.setToolTipText("Guardar la partida actual");
        itemGuardarPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
          InputEvent.CTRL_MASK));
        itemGuardarPartida.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if(ControladorVistas.getInstance().guardarPartida()){};
            }
        });

        separador2 = new JSeparator();

        itemAbandonarPartida = new JMenuItem();
        itemAbandonarPartida.setText("Abandonar Partida");
        itemAbandonarPartida.setToolTipText("Abandonar la partida actual");
        itemAbandonarPartida.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().abandonarPartida();
            }
        });

        separador3 = new JSeparator();

        itemImprimir = new JMenuItem();
        itemImprimir.setText("Imprimir...");
        itemImprimir.setToolTipText("Imprimir el tablero");
        itemImprimir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
          InputEvent.CTRL_MASK));
        itemImprimir.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().imprimir();
            }
        });

        separador4 = new JSeparator();

        itemSalir = new JMenuItem();
        itemSalir.setText("Salir");
        itemSalir.setToolTipText("Salir de la aplicación");
        itemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
          InputEvent.CTRL_MASK));
        itemSalir.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().salir();
            }
        });

        menuArchivo = new JMenu();
        menuArchivo.setText("Archivo ");
        menuArchivo.add(itemNuevaPartida);
        menuArchivo.add(separador1);
        menuArchivo.add(itemCargarPartida);
        menuArchivo.add(itemGuardarPartida);
        menuArchivo.add(separador2);
        menuArchivo.add(itemAbandonarPartida);
        menuArchivo.add(separador3);
        menuArchivo.add(itemImprimir);
        menuArchivo.add(separador4);
        menuArchivo.add(itemSalir);

        itemDeshacer = new JMenuItem();
        itemDeshacer.setText("Deshacer");
        itemDeshacer.setToolTipText("Deshacer el último movimiento");
        itemDeshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
          InputEvent.CTRL_MASK));
        itemDeshacer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().deshacer();
            }
        });

        separador5 = new JSeparator();

        itemResetearPartida = new JMenuItem();
        itemResetearPartida.setText("Resetear Partida");
        itemResetearPartida.setToolTipText("Resetear la partida actual");
        itemResetearPartida.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().resetearPartida();
            }
        });

        menuEditar = new JMenu();
        menuEditar.setText("Editar");
        menuEditar.add(itemDeshacer);
        menuEditar.add(separador5);
        menuEditar.add(itemResetearPartida);

        itemCerrarSesion = new JMenuItem();
        itemCerrarSesion.setText("Cerrar Sesión");
        itemCerrarSesion.setToolTipText("Cerrar la sesión actual");
        itemCerrarSesion.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().cerrarSesion();
            }
        });

        separador6 = new JSeparator();

        itemModificarPassword = new JMenuItem();
        itemModificarPassword.setText("Modificar Contraseña");
        itemModificarPassword.setToolTipText("Modificar la contraseña actual");
        itemModificarPassword.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().modificarPassword();
            }
        });

        separador7 = new JSeparator();

        itemEliminarCuenta = new JMenuItem();
        itemEliminarCuenta.setText("Eliminar Cuenta");
        itemEliminarCuenta.setToolTipText("Eliminar la cuenta");
        itemEliminarCuenta.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().eliminarCuenta();
            }
        });

        menuCuenta = new JMenu();
        menuCuenta.setText("Cuenta");
        menuCuenta.add(itemCerrarSesion);
        menuCuenta.add(separador6);
        menuCuenta.add(itemModificarPassword);
        menuCuenta.add(separador7);
        menuCuenta.add(itemEliminarCuenta);

        itemConsultarEstadisticas = new JMenuItem();
        itemConsultarEstadisticas.setText("Consultar Estadísticas");
        itemConsultarEstadisticas.setToolTipText("Consultar las estadísticas");
        itemConsultarEstadisticas.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().consultarEstadisticas();
            }
        });

        itemConsultarRanking = new JMenuItem();
        itemConsultarRanking.setText("Consultar Ranking");
        itemConsultarRanking.setToolTipText("Consultar el ranking");
        itemConsultarRanking.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().consultarRanking();
            }
        });

        separador8 = new JSeparator();

        itemResetearEstadisticas = new JMenuItem();
        itemResetearEstadisticas.setText("Resetear Estadísticas");
        itemResetearEstadisticas.setToolTipText("Resetear las estadísticas personales");
        itemResetearEstadisticas.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().resetearEstadisticas();
            }
        });

        menuEstadisticas = new JMenu();
        menuEstadisticas.setText("Estadísticas");
        menuEstadisticas.add(itemConsultarEstadisticas);
        menuEstadisticas.add(itemConsultarRanking);
        menuEstadisticas.add(separador8);
        menuEstadisticas.add(itemResetearEstadisticas);

        itemDarPista = new JMenuItem();
        itemDarPista.setText("Dar Pista");
        itemDarPista.setToolTipText("Dar una pista");
        itemDarPista.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().darPista();
            }
        });

        itemResolverHidato = new JMenuItem();
        itemResolverHidato.setText("Resolver Hidato");
        itemResolverHidato.setToolTipText("Resolver el hidato");
        itemResolverHidato.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().resolverTablero();
            }
        });

        separador9 = new JSeparator();

        itemManualUsuario = new JMenuItem();
        itemManualUsuario.setText("Manual de Usuario...");
        itemManualUsuario.setToolTipText("Ver el manual de usuario");
        itemManualUsuario.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ControladorVistas.getInstance().manualUsuario();
            }
        });

        menuAyuda = new JMenu();
        menuAyuda.setText("Ayuda");
        menuAyuda.add(itemDarPista);
        menuAyuda.add(itemResolverHidato);
        menuAyuda.add(separador9);
        menuAyuda.add(itemManualUsuario);

        add(menuArchivo);
        add(menuEditar);
        add(menuCuenta);
        add(menuEstadisticas);
        add(menuAyuda);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la barra de menu */
}
