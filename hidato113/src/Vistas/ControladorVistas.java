/*  Class ControladorVistas:
    Descripcion: Gestiona todo el funcionamiento de la capa de presentacion de la
        aplicacion y se comunica con la capa de dominio.
    Autor: miguel.angel.vico
    Colaboradores: daniel.camarasa, alex.catarineu
    Revisado: 30/12/2009 05:56 */

package Vistas;

import Dominio.ControladorEstadisticas;
import Dominio.ControladorPartida;
import Dominio.ControladorTablero;
import Dominio.ControladorUsuario;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import Excepciones.PasswordIncorrectException;
import Utiles.Files;
import Utiles.Navegador;
import Utiles.Utiles;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ControladorVistas {

    /* Controladores de Dominio */
    private final static ControladorPartida CTRL_PARTIDA =
      ControladorPartida.getInstance();
    private final static ControladorTablero CTRL_TABLERO =
      ControladorTablero.getInstance();
    private final static ControladorUsuario CTRL_USUARIO =
      ControladorUsuario.getInstance();
    private final static ControladorEstadisticas CTRL_ESTADISTICAS =
      ControladorEstadisticas.getInstance();

    /* Componentes */
    private VentanaPrincipal ventana;
    private PanelLogin panelLogin;
    private PanelEnJuego panelEnJuego;
    private PanelProponerTablero panelProponerTablero;
    private PanelProponerTopologia panelProponerTopologia;

    /* Variables de control */
    private String partidaActual;
    private boolean partidaCargada;
    private boolean partidaIniciada;
    private boolean partidaGuardada;
    private String usuarioActual;
    private boolean resueltoMaquina;

    private final static ControladorVistas INSTANCIA = new ControladorVistas();

    /* PRE: - */
    private ControladorVistas() {

        initComponents();
    }
    /* POST: Crea la instancia de ControladorVistas */

    /* PRE: - */
    public static ControladorVistas getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorVistas */

    /* OPERACIONES RELACIONADAS CON EL MANEJO DE LOS USUARIOS */
    /* PRE: - */
    public void crearCuenta() {

        new CrearCuenta().setVisible(true);
    }
    /* POST: Crea y muestra un dialogo de creacion de cuenta */

    /* PRE: - */
    public void procesarCrearCuenta(String usuario, String password)
      throws InstanceRepeatedException {

        ArrayList<Object[]> lista = new ArrayList<Object[]>();
        Object[] tupla = new Object[2];
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            CTRL_USUARIO.crearUsuario(usuario, password);
            tupla[0] = Integer.valueOf(ControladorEstadisticas.TOTAL_USUARIOS);
            tupla[1] = Integer.valueOf(1);
            lista.add(tupla);
            CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);
        }
        catch (InstanceRepeatedException e) {
            throw e;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Transmite la accion de crear una nueva cuenta de usuario con identificador
        'usuario' y password 'password', al dominio de la aplicacion */

    /* PRE: - */
    public void iniciarSesion(String usuario, String password)
      throws InstanceNotFoundException, PasswordIncorrectException {

        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";
        
        try {
            CTRL_USUARIO.iniciarSesion(usuario, password);
            CTRL_ESTADISTICAS.cargarEstadisticasPersonales(usuario);
            usuarioActual = usuario;
            ventana.setPanel(new PanelUser());
            ventana.bloqueoLogin(false);
        }
        catch (InstanceNotFoundException e) {
            throw e;
        }
        catch (PasswordIncorrectException e) {
            throw e;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Inicia la sesion para el usuario 'usuario' con password 'password' */

    /* PRE: - */
    public void cerrarSesion() {

        int opcion;
        String mensaje = "¿Desea guardar sus progresos antes de cerrar sesión?";
        String titulo = "Cerrar sesión";

        if (partidaCargada) {
            opcion = JOptionPane.showConfirmDialog(ventana, mensaje, titulo,
              JOptionPane.YES_NO_CANCEL_OPTION);
            if (opcion == JOptionPane.NO_OPTION ||
              (opcion == JOptionPane.YES_OPTION && guardarPartida()))
                logout();
        }
        else logout();
    }
    /* POST: Cierra la sesion actual */

    /* PRE: - */
    public void modificarPassword() {
        
        new ModificarPassword().setVisible(true);
    }
    /* POST: Crea y muestra un dialogo de modificacion del password */

    /* PRE: - */
    public void procesarModificarPassword(String passwordActual, String passwordNuevo)
      throws PasswordIncorrectException {

        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            CTRL_USUARIO.modificarPassword(passwordActual, passwordNuevo);
        }
        catch (PasswordIncorrectException e) {
            throw e;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Transmite la accion de modificar el password del usuario actual al dominio
        de la aplicacion */

    /* PRE: - */
    public void eliminarCuenta() {

        int opcion;
        String usuario = usuarioActual;
        String[] tablero = new String[1];
        ArrayList<Object[]> lista = new ArrayList<Object[]>();
        Object[] tupla = new Object[2];
        String mensaje = "¿Está seguro de querer borrar todos los datos\n    de su " +
          "cuenta de usuario?\n\n(Únicamente se mantendran los tableros que\n    " +
          "haya propuesto)";
        String titulo = "Eliminar cuenta";
        String mensajeError = "Es posible que el sistema no funcione correctamente.";
        String tituloError = "Error";

        opcion = JOptionPane.showConfirmDialog(ventana, mensaje, titulo,
          JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            logout();
            try {
                CTRL_USUARIO.eliminarUsuario();
                tupla[0] = Integer.valueOf(ControladorEstadisticas.TOTAL_USUARIOS);
                tupla[1] = Integer.valueOf(-1);
                lista.add(tupla);
                CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);
                CTRL_ESTADISTICAS.eliminarEstadisticas();
                lista = CTRL_PARTIDA.mostrarPartidasGuardadas
                  (ControladorPartida.ORDENACION_POR_NOMBRE, usuario);
                for (int i = 0; i < lista.size(); ++i) {
                    tupla = lista.get(i);
                    CTRL_PARTIDA.eliminarPartida(tupla[0].toString(), tablero,
                      new int[1]);
                    CTRL_TABLERO.eliminarTablero(tablero[0]);
                }
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" +
                  mensajeError, tituloError, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /* POST: Elimina la cuenta del usuario actual */

    /* OPERACIONES RELACIONADAS CON EL MANEJO DE LAS PARTIDAS */
    /* PRE: - */
    public Object[][] obtenerListaPartidas(int ordenacion) {

        List<Object[]> listaPartidas = null;
        Object[][] lista = null;
        Object[] tupla;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            listaPartidas = CTRL_PARTIDA.mostrarPartidasGuardadas
              (ordenacion, usuarioActual);

            lista = new Object[listaPartidas.size()][2];
            for (int i = 0; i < listaPartidas.size(); ++i) {
                tupla = listaPartidas.get(i);
                lista[i][0] = tupla[0];
                lista[i][1] = tupla[1] + " " + tupla[2];
            }

            return lista;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
    /* POST: Retorna un array de tuplas con toda la informacion de las partidas guardads
        por el usuario actual, ordenadas segun el criterio marcado por 'ordenacion' */

    /* PRE: - */
    public void nuevaPartida() {

        int opcion;
        String mensaje = "La partida en curso se cerrará. ¿Desea guardar sus\nprogresos" +
          " antes de iniciar una nueva partida?";
        String titulo = "Nueva Partida";

        if (partidaCargada) {
            panelEnJuego.pararTiempo();
            opcion = JOptionPane.showConfirmDialog(null, mensaje, titulo,
              JOptionPane.YES_NO_CANCEL_OPTION);
            if (opcion == JOptionPane.NO_OPTION ||
              (opcion == JOptionPane.YES_OPTION && guardarPartida())) {
                if (!partidaGuardada) actualizarAbandonadas(panelEnJuego.obtenerTiempo());
                ventana.setPanel(new PanelUser());
                ventana.bloqueoNuevaPartida(true);
                ventana.bloqueoPartidaIniciada(true);
                ventana.bloqueoDeshacer(true);
                partidaCargada = false;
                partidaGuardada = false;
                partidaIniciada = false;
                partidaActual = null;
                new NuevaPartida().setVisible(true);
            }
            else panelEnJuego.iniciarTiempo();
        }
        else new NuevaPartida().setVisible(true);
    }
    /* POST: Crea y muestra un dialogo de configuracion de una nueva partida */

    /* PRE: - */
    public void procesarNuevaPartida(String tableroOriginal) {

        ArrayList<Object[]> lista = new ArrayList<Object[]>();
        Object[] tupla;
        int[] dificultad = new int[1];
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";
        String tableroEnCurso;

        try {
            tableroEnCurso = CTRL_TABLERO.crearTableroEnCurso(tableroOriginal,
              dificultad);
            partidaActual = CTRL_PARTIDA.nuevaPartida(tableroEnCurso, usuarioActual);
            tupla = new Object[2];
            tupla[0] = Integer.valueOf(ControladorEstadisticas.TOTAL_PARTIDAS);
            tupla[1] = Integer.valueOf(1);
            lista.add(tupla);
            tupla = new Object[2];
            tupla[1] = Integer.valueOf(1);
            switch (dificultad[0]) {
                case ControladorTablero.DIF_FACIL:
                    tupla[0] = Integer.valueOf(ControladorEstadisticas.PARTIDAS_FACIL);
                    lista.add(tupla);
                    break;
                case ControladorTablero.DIF_MEDIO:
                    tupla[0] = Integer.valueOf
                      (ControladorEstadisticas.PARTIDAS_MEDIO);
                    lista.add(tupla);
                    break;
                case ControladorTablero.DIF_DIFICIL:
                    tupla[0] = Integer.valueOf
                      (ControladorEstadisticas.PARTIDAS_DIFICIL);
                    lista.add(tupla);
                    break;
                default:
                    break;
            }
            CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);
            eliminarPaneles();
            panelEnJuego = new PanelEnJuego(arrayToTablero
              (CTRL_TABLERO.obtenerContenidoTablero()));
            ventana.setPanel(panelEnJuego);
            ventana.bloqueoNuevaPartida(false);
            ventana.bloqueoPartidaIniciada(true);
            ventana.bloqueoDeshacer(true);
            partidaCargada = true;
            partidaGuardada = false;
            partidaIniciada = false;
            resueltoMaquina = false;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Crea una nueva partida para jugar al tablero identificado por
        'tableroOriginal' */

    /* PRE: - */
    public boolean guardarPartida()  {

        GuardarPartida guardarPartida;
        boolean retorno = false;

        if (partidaIniciada) panelEnJuego.pararTiempo();
        if (!partidaGuardada) {
            guardarPartida = new GuardarPartida(partidaActual);
            guardarPartida.setVisible(true);
            partidaGuardada = guardarPartida.esAceptado();
            retorno = partidaGuardada;
        }
        else {
            try {
                procesarGuardarPartida(partidaActual);
                retorno = true;
            }
            catch (InstanceRepeatedException e) {}
        }
        if (partidaIniciada) panelEnJuego.iniciarTiempo();

        return retorno;
    }
    /* POST: Crea y muestra un dialogo de guardar partida. En el caso que la partida
        actual ya se hubiese guardado con anterioridad no muestra el dialogo y pasa
        directamente a procesar la accion de guardado */

    /* PRE: - */
    public void procesarGuardarPartida(String idPartida)
      throws InstanceRepeatedException {

        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            partidaActual = idPartida;
            CTRL_TABLERO.guardarTablero();
            CTRL_PARTIDA.guardarPartida(idPartida, panelEnJuego.obtenerTiempo());
        }
        catch (InstanceRepeatedException e) {
            throw e;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Guarda la partida actual en el sistema poniendole como identificador
        'idPartida' */

    /* PRE: - */
    public void cargarPartida() {

        int opcion;
        boolean cargar = true;
        CargarPartida cargarPartida;
        String mensaje = "¿Desea guardar sus progresos antes de cargar una partida?";
        String titulo = "Cargar Partida";

        if (partidaCargada) {
            opcion = JOptionPane.showConfirmDialog(null, mensaje, titulo,
              JOptionPane.YES_NO_CANCEL_OPTION);
            if (opcion == JOptionPane.NO_OPTION ||
              (opcion == JOptionPane.YES_OPTION && guardarPartida())) {
                if (!partidaGuardada)
                    actualizarAbandonadas(panelEnJuego.obtenerTiempo());
                cargar = true;
            }
            else cargar = false;
        }
        if (cargar) {
            cargarPartida = new CargarPartida();
            cargarPartida.setVisible(true);

            if (cargarPartida.esAceptado())
                procesarCargarPartida(cargarPartida.obtenerPartidaSeleccionada());
        }
    }
    /* POST: Realiza la accion de cargar partida, para ello muestra un dialogo con todas
        las partidas guardadas por el usuario actual, y en caso de seleccionar una y
        presionar aceptar se cargara dicha partida. Si hay una partida en curso pregunta
        si se desea guardar dicha partida. */

    /* PRE: - */
    public void procesarCargarPartida(String partida) {

        int[] tiempo = new int[1];
        String[] tablero = new String[1];
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            partidaActual = partida;

            CTRL_PARTIDA.cargarPartida(partidaActual, tiempo, tablero);
            CTRL_TABLERO.cargarTablero(tablero[0]);

            eliminarPaneles();
            panelEnJuego = new PanelEnJuego(arrayToTablero
              (CTRL_TABLERO.obtenerContenidoTablero()));
            panelEnJuego.setTiempo(tiempo[0]);
            ventana.setPanel(panelEnJuego);
            ventana.bloqueoNuevaPartida(false);
            ventana.bloqueoPartidaIniciada(true);
            ventana.bloqueoDeshacer(true);
            partidaCargada = true;
            partidaIniciada = false;
            partidaGuardada = true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Carga la partida con identificador 'partida' */

    /* PRE: - */
    public void eliminarPartida(String idPartida) {

        int opcion;
        String[] idTablero = new String[1];
        int[] tiempoEmpleado = new int[1];
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            if (partidaActual != null && partidaActual.equals(idPartida)) {
                JOptionPane.showMessageDialog(null, "La partida que desea eliminar está "
                  + "actualmente en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de querer " +
                  "eliminar esta partida?", "Eliminar Partida",
                  JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    if (!CTRL_PARTIDA.eliminarPartida
                      (idPartida, idTablero, tiempoEmpleado))
                        actualizarAbandonadas(tiempoEmpleado[0]);
                    CTRL_TABLERO.eliminarTablero(idTablero[0]);
                }
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Realiza la accion de eliminar la partida con identificado 'idPartida', para
        ello muestra un dialogo de confirmacion. En caso de querer eliminar la partida
        en curso muestra el error correspondiente */

    /* PRE: - */
    public void abandonarPartida() {

        int opcion;
        String mensaje = "¿Desea guardar sus progresos antes de abandonar la partida?";
        String titulo = "Abandonar Partida";

        panelEnJuego.pararTiempo();
        opcion = JOptionPane.showConfirmDialog(null, mensaje, titulo,
          JOptionPane.YES_NO_CANCEL_OPTION);
        if (opcion == JOptionPane.NO_OPTION ||
          (opcion == JOptionPane.YES_OPTION && guardarPartida())) {
            if (!partidaGuardada) actualizarAbandonadas(panelEnJuego.obtenerTiempo());
            ventana.setPanel(new PanelUser());
            ventana.bloqueoNuevaPartida(true);
            ventana.bloqueoPartidaIniciada(true);
            ventana.bloqueoDeshacer(true);
            partidaCargada = false;
            partidaGuardada = false;
            partidaIniciada = false;
            partidaActual = null;
        }
        else panelEnJuego.iniciarTiempo();
    }
    /* POST: Realiza la accion de abandonar partida, para ello muestra un dialogo de
        confirmacion, en caso afirmativo abandonara la partida actual, en caso negativo
        aborta la accion */

    /* PRE: - */
    public void resetearPartida() {

        int opcion;
        String mensaje = "¿Seguro que desea resetear todo el tablero?";
        String titulo = "Resetear Partida";

        opcion = JOptionPane.showConfirmDialog(null, mensaje, titulo,
          JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            panelEnJuego.resetearTablero();
            CTRL_TABLERO.resetearTablero();
        }
    }
    /* POST: Realiza la accion de resetear la partida, para ello muestra un dialogo de
        confirmacion, en caso afirmativo procedera con resetear todo el tablero en curso
        actual, en caso negativo aborta la accion */

    /* PRE: - */
    public void finalizarPartida() {

        int puntuacion;
        int pistas;
        int tiempoJuego;
        int posicionRanking;
        ArrayList<Object[]> lista;
        Object[] tupla;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            panelEnJuego.pararTiempo();
            if (resueltoMaquina || CTRL_TABLERO.esTableroValido()) {
                partidaIniciada = false;
                partidaCargada = false;
                ventana.bloqueoPartidaIniciada(true);
                ventana.bloqueoNuevaPartida(true);
                ventana.bloqueoDeshacer(true);

                tiempoJuego = panelEnJuego.obtenerTiempo();

                lista = new ArrayList<Object[]>();
                tupla = new Object[2];
                tupla[0] = Integer.valueOf(ControladorEstadisticas.TIEMPO_TOTAL_JUEGO);
                tupla[1] = Integer.valueOf(tiempoJuego);
                lista.add(tupla);

                if (!resueltoMaquina) {
                    pistas = CTRL_TABLERO.obtenerPistas();
                    puntuacion = CTRL_TABLERO.obtenerPuntuacionTablero();
                    puntuacion = CTRL_PARTIDA.finalizarPartida(puntuacion, tiempoJuego,
                      pistas);

                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf
                      (ControladorEstadisticas.TOTAL_PARTIDAS_PERSONALES);
                    if (puntuacion > -1) tupla[1] = Integer.valueOf(1);
                    else tupla[1] = Integer.valueOf(0);
                    lista.add(tupla);

                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf(ControladorEstadisticas.PUNTUACION_TOTAL);
                    if (puntuacion > -1) tupla[1] = Integer.valueOf(puntuacion);
                    else tupla[1] = Integer.valueOf(0);
                    lista.add(tupla);

                    posicionRanking = 1000;
                    if (puntuacion > -1) {
                        posicionRanking = CTRL_ESTADISTICAS.nuevaPosicion(puntuacion,
                          usuarioActual);
                        tupla = new Object[2];
                        tupla[0] = Integer.valueOf
                          (ControladorEstadisticas.POSICION_RANKING);
                        tupla[1] = Integer.valueOf(posicionRanking);
                        lista.add(tupla);
                    }
                    CTRL_ESTADISTICAS.actualizarEstadisticasPersonales(lista);

                    if (puntuacion > -1) {
                        lista = new ArrayList<Object[]>();
                        tupla = new Object[2];
                        tupla[0] = Integer.valueOf
                          (ControladorEstadisticas.PARTIDAS_RESUELTAS);
                        tupla[1] = Integer.valueOf(1);
                        lista.add(tupla);
                        CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);
                    }

                    if (puntuacion == -1)
                        new FinalizarPartida(0, tiempoJuego, 1000, 10,
                          true, false).mostrar();
                    else
                        new FinalizarPartida(puntuacion, tiempoJuego, posicionRanking,
                          pistas, true, true).mostrar();
                }
                else {
                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf
                      (ControladorEstadisticas.TOTAL_PARTIDAS_PERSONALES);
                    tupla[1] = Integer.valueOf(0);
                    lista.add(tupla);

                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf(ControladorEstadisticas.PUNTUACION_TOTAL);
                    tupla[1] = Integer.valueOf(0);
                    lista.add(tupla);
                    CTRL_ESTADISTICAS.actualizarEstadisticasPersonales(lista);

                    new FinalizarPartida(0, tiempoJuego, 1000, 10,
                      false, false).mostrar();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Solución incorrecta. Sigue " +
                  "intentandolo.", "Solución incorrecta",
                  JOptionPane.INFORMATION_MESSAGE);

                panelEnJuego.iniciarTiempo();
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" +
              mensaje, titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Realiza la accion de finalizar partida, para ello verifica si el tablero
        en curso actual es correcto, en caso negativo muestra un mensaje y permite seguir
        jugando, en caso afirmativo mostrara un dialogo en el que se muestra la puntuacion
        tiempo y algunos datos mas sobre la partida actual */

    /* PRE: - */
    public boolean esPartidaCargada() {

        return partidaCargada;
    }
    /* POST: Retorna cierto si se ha cargado una partida ya sea por medio de cargar
        partida o de crear una nueva */

    /* PRE: - */
    public void setPartidaCargada(boolean partidaCargada) {

        this.partidaCargada = partidaCargada;
    }
    /* POST: Establece que se ha cargado una partida ya sea por medio de cargar partida
        o de crear una nueva */

    /* PRE: - */
    public boolean esPartidaIniciada() {

        return partidaIniciada;
    }
    /* POST: Retorna cierto si la partida actual se ha iniciado */

    /* PRE: - */
    public void setPartidaIniciada(boolean partidaIniciada) {

        this.partidaIniciada = partidaIniciada;
        ventana.bloqueoPartidaIniciada(!partidaIniciada);
    }
    /* POST: Establece que se ha iniciado la partida actual y desbloquea las opciones
        pertinentes de la barra de menu */

    /* OPERACIONES RELACIONADAS CON EL MANEJO DE LOS TABLEROS */
    /* PRE: - */
    public Object[][] obtenerListaTableros(int opcion) {
        
        List<Object[]> listaTableros = null;
        Object[][] lista = null;
        Object[] tupla;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            listaTableros = CTRL_TABLERO.obtenerListaTableros(opcion, usuarioActual);

            lista = new Object[listaTableros.size()][3];
            for (int i = 0; i < listaTableros.size(); ++i) {
                tupla = listaTableros.get(i);
                lista[i][0] = tupla[0];
                switch (((Integer)tupla[1]).intValue()) {
                    case ControladorTablero.DIF_FACIL:
                        lista[i][2] = "Fácil";
                        break;
                    case ControladorTablero.DIF_MEDIO:
                        lista[i][2] = "Medio";
                        break;
                    case ControladorTablero.DIF_DIFICIL:
                        lista[i][2] = "Difícil";
                        break;
                    case ControladorTablero.DIF_PERSONALIZADA:
                        lista[i][2] = "Personalizada";
                        break;
                    default:
                        break;
                }
                switch (((Integer)tupla[2]).intValue()) {
                    case ControladorTablero.TOP_RECTANGULO:
                        lista[i][1] = "Rectángulo";
                        break;
                    case ControladorTablero.TOP_TRIANGULO:
                        lista[i][1] = "Triángulo";
                        break;
                    case ControladorTablero.TOP_ROMBO:
                        lista[i][1] = "Rombo";
                        break;
                    case ControladorTablero.TOP_ELIPSE:
                        lista[i][1] = "Elipse";
                        break;
                    case ControladorTablero.TOP_PERSONALIZADA:
                        lista[i][1] = "Personalizada";
                        break;
                    default:
                        break;
                }
            }

            return lista;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
    /* POST: Retorna un array de tuplas con toda la informacion de los tableros del
        repositorio de tableros */

    /* PRE: - */
    public boolean generarTablero(String idTablero, int dificultad, int topologia,
      int numPrefijadas, int[][] formaTopologia) throws InstanceRepeatedException {

        int estado;
        int opcion;
        int[] minimoPrefijadas = new int[1];
        VentanaProgreso progreso;
        ArrayList<Object[]> lista = new ArrayList<Object[]>();
        Object[] tupla;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            if (CTRL_TABLERO.existeTablero(idTablero))
                throw new InstanceRepeatedException(idTablero);

            progreso = new VentanaProgreso(45, "Generando");
            CTRL_TABLERO.generarTablero(numPrefijadas, formaTopologia,
              progreso.obtenerThread());
            
            progreso.setVisible(true);

            estado = CTRL_TABLERO.confirmarTableroGenerado(idTablero, dificultad,
              topologia, formaTopologia, minimoPrefijadas);

            switch (estado) {
                case ControladorTablero.ABORTADO:
                    if (minimoPrefijadas[0] != -1) {
                        opcion = JOptionPane.showConfirmDialog(null, "Procedimiento " +
                          "abortado.\n\nSe ha encontrado un tablero de las mismas\n" +
                          "características con " + Integer.toString(minimoPrefijadas[0]) +
                          " casillas prefijadas.\n\n¿Desea utilizar dicho tablero?",
                          "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                        if (opcion == JOptionPane.YES_OPTION) {
                            CTRL_TABLERO.confirmarMejorTableroGenerado(idTablero,
                              dificultad, topologia, formaTopologia);
                            tupla = new Object[2];
                            tupla[0] = Integer.valueOf
                              (ControladorEstadisticas.TOTAL_TABLEROS);
                            tupla[1] = Integer.valueOf(1);
                            lista.add(tupla);

                            tupla = new Object[2];
                            tupla[0] = Integer.valueOf
                              (ControladorEstadisticas.TABLEROS_GENERADOS);
                            tupla[1] = Integer.valueOf(1);
                            lista.add(tupla);
                            CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);
                            procesarNuevaPartida(idTablero);
                            return true;
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Procedimiento abortado.",
                          "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case ControladorTablero.CORRECTO:
                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf(ControladorEstadisticas.TOTAL_TABLEROS);
                    tupla[1] = Integer.valueOf(1);
                    lista.add(tupla);

                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf
                      (ControladorEstadisticas.TABLEROS_GENERADOS);
                    tupla[1] = Integer.valueOf(1);
                    lista.add(tupla);
                    CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);
                    procesarNuevaPartida(idTablero);
                    return true;
                default:
                    break;
            }
        }
        catch (InstanceRepeatedException e) {
            throw e;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }
    /* POST: Realiza la accion de ejecutar el algoritmo de generacion. En caso de que
        dicho algoritmo acabe correctamente creara dicho tablero y una nueva partida
        para jugar el tablero, en otro caso mostrara el error correspondiente */


    /* PRE: - */
    public void crearProponerTablero(String idTablero, int topologia,
      int[][] formaTopologia, int[] limitePrefijadas, NuevaPartida nuevaPartida)
      throws InstanceRepeatedException {

        boolean existe = false;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            existe = CTRL_TABLERO.existeTablero(idTablero);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }

        if (existe) throw new InstanceRepeatedException(idTablero);

        eliminarPaneles();
        panelProponerTablero = new PanelProponerTablero(idTablero, topologia,
          formaTopologia, limitePrefijadas, nuevaPartida);
        ventana.setPanel(panelProponerTablero);
    }
    /* POST: Crea y muestra un panel principal dedicado a proponer un tablero */

    /* PRE: - */
    public void proponerTablero(String idTablero, int topologia, int[][] contenido) {

        int estado;
        VentanaProgreso progreso = new VentanaProgreso(30, "Validando");
        ArrayList<Object[]> lista;
        Object[] tupla;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";
        
        try {
            CTRL_TABLERO.proponerTablero(contenido, progreso.obtenerThread());

            progreso.setVisible(true);

            estado = CTRL_TABLERO.confirmarTableroPropuesto(idTablero, topologia, contenido,
              usuarioActual);

            switch (estado) {
                case ControladorTablero.ABORTADO:
                    JOptionPane.showMessageDialog(null, "Procedimiento abortado.",
                      "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case ControladorTablero.NO_SOLUCION:
                    JOptionPane.showMessageDialog(null, "El tablero no tiene " +
                      "solución.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                case ControladorTablero.CORRECTO:
                    lista = new ArrayList<Object[]>();
                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf(ControladorEstadisticas.TOTAL_TABLEROS);
                    tupla[1] = Integer.valueOf(1);
                    lista.add(tupla);
                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf
                      (ControladorEstadisticas.TABLEROS_PROPUESTOS);
                    tupla[1] = Integer.valueOf(1);
                    lista.add(tupla);
                    CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);
                    lista = new ArrayList<Object[]>();
                    tupla = new Object[2];
                    tupla[0] = Integer.valueOf(ControladorEstadisticas.TABLEROS_PROPUESTOS_PERSONALES);
                    tupla[1] = Integer.valueOf(1);
                    lista.add(tupla);
                    CTRL_ESTADISTICAS.actualizarEstadisticasPersonales(lista);
                    ControladorVistas.getInstance().procesarNuevaPartida(idTablero);
                    break;
                case ControladorTablero.MAS_UNA_SOLUCION:
                    JOptionPane.showMessageDialog(null, "El tablero tiene más " +
                      "de una solución.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Realiza la accion de ejecutar el algoritmo de verificacion. En caso de que
        dicho algoritmo acabe correctamente creara dicho tablero y una nueva partida
        para jugar el tablero, en otro caso mostrara el error correspondiente */

    /* PRE: - */
    public void abortarAlgoritmo() {

        CTRL_TABLERO.abortarAlgoritmo();
    }
    /* POST: Aborta el algoritmo que se este ejecutando en este momento, ya sea el de
        generacion como el de verificacion */

    /* PRE: - */
    public void proponerTopologia(int[][] formaTopologia, NuevaPartida nPartida) {

        eliminarPaneles();
        panelProponerTopologia = new PanelProponerTopologia(formaTopologia, nPartida);
        ventana.setPanel(panelProponerTopologia);
    }
    /* POST: Crea y muestra un panel principal dedicado a proponer una topologia */

    /* PRE: - */
    public boolean repositorio() {

        Repositorio repositorio = new Repositorio();

        repositorio.setVisible(true);

        return repositorio.esAceptado();
    }
    /* POST: Crea y muestra una ventana de dialogo de repositorio. Retorna cierto si
        se ha pulsado aceptar en dicha ventana */

    /* PRE: - */
    public void darPista() {

        panelEnJuego.introducirPista(CTRL_TABLERO.darPista());
    }
    /* POST: Muestra una pista sobre el tablero en curso actual. La pista puede ser
        marcar un valor incorrecto (en el caso que los haya) o rellenar una casilla
        vacia */

    /* PRE: - */
    public void resolverTablero() {

        int opcion;
        String mensaje = "¿Seguro que desea resolver el Hidato?";
        String titulo = "Resolver Hidato";

        opcion = JOptionPane.showConfirmDialog(null, mensaje, titulo,
          JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            resueltoMaquina = true;
            panelEnJuego.resolverTablero(CTRL_TABLERO.resolverHidato());
        }
    }
    /* POST: Realiza la accion de resolver el tablero en curso actual. Para ello muestra
        un dialogo de confirmacion, en caso afirmativo muestra el tablero resuelto, en
        caso negativo aborta la accion */

    /* PRE: - */
    public void insertarValor(int x, int y, int valor) {

        CTRL_TABLERO.insertarValor(y, x, valor);
    }
    /* POST: Transmite la accion de insertar el valor 'valor' en la casilla (x, y) del
        tablero en curso actual, al dominio de la aplicacion */

    /* PRE: - */
    public void quitarValor(int x, int y) {

        CTRL_TABLERO.quitarValor(y, x);
    }
    /* POST: Transmite la accion de quitar el valor de la casilla (x, y) del tablero
        en curso actual, al dominio de la aplicacion */

    /* OPERACIONES RELACIONADAS CON EL MANEJO DE LAS ESTADISTICAS Y RANKING */
    /* PRE: - */
    public Object[][] obtenerEstadisticas(boolean generales) {

        List<Object[]> estadisticas = null;
        Object[][] resultado = null;
        Object[] tupla;
        String nombreEstadisticas[] = {
          "Total de partidas", "Partidas abandonadas", "Partidas resueltas",
          "Hidatos empezados (fácil)", "Hidatos empezados (intermedio)",
          "Hidatos empezados (difícil)", "Total de usuarios", "Total de tableros",
          "Tableros generados", "Tableros propuestos",

          "Nivel de Experiencia", "Tiempo total de juego",
          "Tiempo medio de resolución", "Hidatos resueltos",
          "Porcentaje de Hidatos resueltos", "Total de partidas jugadas",
          "Posición en el ranking", "Tableros propuestos", "Puntuación total",
          "Puntuación media"
        };
        String nivelExperiencia[] = {"Principiante", "Intermedio", "Experto", "Maestro"};
        int intAux;
        double doubleAux;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            if (generales)
                estadisticas = CTRL_ESTADISTICAS.mostrarEstadisticasGenerales();
            else estadisticas = CTRL_ESTADISTICAS.mostrarEstadisticasPersonales();

            resultado = new Object[estadisticas.size()][2];
            for (int i = 0; i < estadisticas.size(); ++i) {
                tupla = estadisticas.get(i);
                intAux = Integer.parseInt(tupla[0].toString());
                resultado[i][0] = nombreEstadisticas[intAux];
                if (intAux == ControladorEstadisticas.NIVEL_EXPERIENCIA) {
                    intAux = Integer.parseInt(tupla[1].toString());
                    resultado[i][1] = nivelExperiencia[intAux];
                }
                else if(intAux == ControladorEstadisticas.TIEMPO_TOTAL_JUEGO) {
                    intAux = Integer.parseInt(tupla[1].toString());
                    resultado[i][1] = Utiles.segundosToTiempo(intAux);
                }
                else if(intAux == ControladorEstadisticas.TIEMPO_MEDIO_PARTIDA_RESUELTA) {
                    intAux = (int)(Double.parseDouble(tupla[1].toString()));
                    resultado[i][1] = Utiles.segundosToTiempo(intAux);
                }
                else {
                    doubleAux = Double.parseDouble(tupla[1].toString());
                    if (doubleAux < 0) resultado[i][1] = " - ";
                    else resultado[i][1] = tupla[1].toString();
                }
            }

            return resultado;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
    /* POST: Retorna un array de tuplas con toda la informacion de las estadisticas
        generales de la aplicacion y las estadisticas personales del usuario actual */

    /* PRE: - */
    public void consultarEstadisticas() {

        new Estadisticas().setVisible(true);
    }
    /* POST: Crea y muestra una ventana de dialogo de estadisticas */

    /* PRE: - */
    public void resetearEstadisticas() {

        int opcion;
        String mensaje = "¿Está seguro de querer resetear sus estadísticas?\n\n" +
          "(Únicamente se mantendrán las referentes a los\n    tableros propuestos)";
        String titulo = "Resetear Estadísticas";
        String mensajeError = "Es posible que el sistema no funcione correctamente.";
        String tituloError = "Error";

        try {
            opcion = JOptionPane.showConfirmDialog(null, mensaje, titulo,
              JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION)
                CTRL_ESTADISTICAS.resetearEstadisticas();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" +
              mensajeError, tituloError, JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Realiza la accion de resetear las estadisticas personales, para ello antes
        muestra un dialogo de confirmacion, en caso de respuesta positiva se procede con
        la accion, en otro caso de aborta */

    /* PRE: 'numPosiciones' >= 1 */
    public Object[][] obtenerRanking(int numPosiciones) {

        List<Object[]> ranking = null;
        Object[][] resultado = null;
        Object[] tupla;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            ranking = CTRL_ESTADISTICAS.consultarRanking(numPosiciones);

            resultado = new Object[ranking.size()][4];
            for (int i = 0; i < ranking.size(); ++i) {
                tupla = ranking.get(i);
                resultado[i][0] = Integer.toString(i + 1);
                resultado[i][1] = tupla[0];
                resultado[i][2] = tupla[1];
                resultado[i][3] = tupla[2];
            }

            return resultado;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }

        return resultado;
    }
    /* POST: Retorna un array de tuplas con toda la informacion de las 'numPosiciones'
        primeras posiciones del ranking */

    /* PRE: - */
    public void consultarRanking() {

        new Ranking().setVisible(true);
    }
    /* POST: Crea y muestra una ventana de dialogo de ranking */

    /* PRE: - */
    public void actualizarAbandonadas(int tiempoEmpleado) {

        ArrayList<Object[]> lista;
        Object[] tupla;
        String mensaje = "Es posible que el sistema no funcione correctamente.";
        String titulo = "Error";

        try {
            lista = new ArrayList<Object[]>();
            tupla = new Object[2];
            tupla[0] = Integer.valueOf(ControladorEstadisticas.PARTIDAS_ABANDONADAS);
            tupla[1] = Integer.valueOf(1);
            lista.add(tupla);
            CTRL_ESTADISTICAS.actualizarEstadisticasGenerales(lista);

            lista = new ArrayList<Object[]>();
            tupla = new Object[2];
            tupla[0] = Integer.valueOf(ControladorEstadisticas.TIEMPO_TOTAL_JUEGO);
            tupla[1] = Integer.valueOf(tiempoEmpleado);
            lista.add(tupla);

            tupla = new Object[2];
            tupla[0] = Integer.valueOf(ControladorEstadisticas.TOTAL_PARTIDAS_PERSONALES);
            tupla[1] = Integer.valueOf(-1);
            lista.add(tupla);

            tupla = new Object[2];
            tupla[0] = Integer.valueOf(ControladorEstadisticas.PUNTUACION_TOTAL);
            tupla[1] = Integer.valueOf(0);
            lista.add(tupla);
            CTRL_ESTADISTICAS.actualizarEstadisticasPersonales(lista);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + ".\n" + mensaje,
              titulo, JOptionPane.ERROR_MESSAGE);
        }
    }

    /* OPERACIONES AUXILIARES */
    /* PRE: - */
    public void iniciarTiempo() {

        if (panelEnJuego != null) panelEnJuego.iniciarTiempo();
    }
    /* POST: Inicia/continua la contabilizacion del tiempo de partida */

    /* PRE: - */
    public void pararTiempo() {

        if (panelEnJuego != null) panelEnJuego.pararTiempo();
    }
    /* POST: Para la contabilizacion del tiempo de partida */

    /* PRE: - */
    public void imprimir() {

        new Imprimir(CTRL_TABLERO.obtenerContenidoTablero());
    }
    /* POST: Manda a imprimir el tablero actual que se esta jugando */

    /* PRE: - */
    public void deshacer() {

        if (panelEnJuego != null) panelEnJuego.deshacer();
        else if (panelProponerTablero != null) panelProponerTablero.deshacer();
        else panelProponerTopologia.deshacer();
    }
    /* POST: Deshace la ultima accion realizada sobre algun tablero, ya sea del modo
        jugar, modo proponer tablero o modo proponer topologia */

    /* PRE: - */
    public void bloqueoDeshacer(boolean bloquear) {

        ventana.bloqueoDeshacer(bloquear);
    }
    /* POST: Bloquea o no el item Deshacer de la barra de menu segun el valor de
        'bloquear' */

    /* PRE: - */
    public void manualUsuario() {

        try {
            Navegador.abrirURL(Files.getPath() + "/ManualUsuario/ManualUsuario.html");
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Error",
              JOptionPane.ERROR_MESSAGE);
        }
    }
    /* POST: Realiza la accion de abrir un navegador web y mostrar en dicho navegador el
        manual de usuario de la aplicacion */

    /* PRE: - */
    public void salir() {

        int opcion;
        String mensaje = "¿Desea guardar sus progresos antes de salir?";
        String titulo = "Salir";

        if (partidaCargada) {
            opcion = JOptionPane.showConfirmDialog(null, mensaje, titulo,
              JOptionPane.YES_NO_CANCEL_OPTION);
            if (opcion == JOptionPane.NO_OPTION ||
              (opcion == JOptionPane.YES_OPTION && guardarPartida())) {
                if (!partidaGuardada) actualizarAbandonadas(panelEnJuego.obtenerTiempo());
                System.exit(0);
            }
        }
        else System.exit(0);
    }
    /* POST: Realiza la accion de salir de la aplicacion. En el caso de haber una partida
        cargada, pregunta si se desea guardar la partida antes, en caso negativo cierra
        la aplicacion, en caso positivo manda a guardar la partida y cierra la aplicacion,
        en cualquier otro caso se cancela la accion de salir */

    /* PRE: - */
    public void ocultarPanel() {

        ventana.setPanel(new PanelUser());
    }
    /* POST: Oculta el panel principal actual */

    /* PRE: - */
    private void eliminarPaneles() {

        panelLogin = null;
        panelEnJuego = null;
        panelProponerTablero = null;
        panelProponerTopologia = null;
    }
    /* POST: Elimina todos los paneles principales que pudiera haber inicializados */

    /* PRE: - */
    public void ejecutar() {

        ventana.setVisible(true);
    }
    /* POST: Inicia la ejecucion de la aplicacion */

    /* PRE: - */
    private void logout() {

        eliminarPaneles();
        panelLogin = new PanelLogin();
        ventana.setPanel(panelLogin);
        ventana.bloqueoLogin(true);
        ventana.bloqueoNuevaPartida(true);
        ventana.bloqueoPartidaIniciada(true);
        ventana.bloqueoDeshacer(true);
        usuarioActual = null;
        partidaActual = null;
        partidaCargada = false;
        partidaIniciada = false;
        partidaGuardada = false;
        resueltoMaquina = false;
    }
    /* POST: Realiza los cambios en los componentes y variables que comporta el cerrar
        sesion */

    /* PRE: - */
    private Tablero arrayToTablero(int[][][] array) {

        int altura = array[0].length;
        int anchura = array[0][0].length;
        Tablero tablero = new Tablero(altura, anchura);

        for (int i = 0; i < altura; ++i)
            for (int j = 0; j < anchura; ++j)
                if (array[1][i][j] == ControladorTablero.CAS_INACTIVA)
                    tablero.setDesactiva(i, j);

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                if (array[0][i][j] > 0)
                    tablero.setValor(i, j, array[0][i][j]);
                if (array[1][i][j] == ControladorTablero.CAS_PREFIJADA)
                    tablero.setPrefijada(i, j);
            }
        }
        tablero.modoJugar();

        return tablero;
    }
    /* POST: Retorna una instancia de Tablero a partir del contenido de 2 matrices de
        enteros que definen dicho tablero */

    /* PRE: - */
    private void initComponents() {

        panelLogin = new PanelLogin();
        ventana = new VentanaPrincipal(panelLogin);
        panelEnJuego = null;
        panelProponerTablero = null;
        panelProponerTopologia = null;

        partidaActual = null;
        partidaCargada = false;
        partidaIniciada = false;
        partidaGuardada = false;
        usuarioActual = null;
        resueltoMaquina = false;
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el controlador de vistas */
}
