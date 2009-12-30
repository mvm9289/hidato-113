/*  Class ControladorPartida
    Descripcion: Gestiona usuarios y tableros ya generados, las partidas quedan
        univocamente identificadas por su identificador. Una partida solo sera
        finalizada, si se ha explicitado su fin.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 19:21 */

package Dominio;

import Datos.ControladorDatosPartidaGuardada;
import Tiempo.Fecha;
import Tiempo.Hora;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class ControladorPartida {

    private final static String NOMBRE_PARTIDA = "save";
    public final static int ORDENACION_POR_NOMBRE = 1;
    public final static int ORDENACION_POR_FECHA = 2;
    private final static int x100_POR_PISTA = 10;

    private final static ControladorDatosPartidaGuardada DATOS_PARTIDA =
      ControladorDatosPartidaGuardada.getInstance();
    
    private final static ControladorPartida INSTANCIA = new ControladorPartida();

    private Partida partidaActual;
    private boolean esPartidaNueva;

    /* PRE: - */
    private ControladorPartida() {
        
        partidaActual = null;
    }
    /* POST: Se crea la intancia del ControladorPartidas */

    /* PRE: - */
    public static ControladorPartida getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia del ControladorPartida */
    
    /* PRE: - */
    public String nuevaPartida(String tablero, String usuario) {

        /* para evitar que haya identificadores de partida repetidos nos 
        apoyamos en la fecha y hora actuales */

        Fecha fecha = new Fecha();
        Hora hora = new Hora();
        fecha.hoy();
        hora.ahora();

        String nuevoId = NOMBRE_PARTIDA;
        nuevoId += Integer.toString(fecha.obtenerDiaDelMes());
        nuevoId += Integer.toString(fecha.obtenerMesInt());
        nuevoId += Integer.toString(fecha.obtenerAnio());
        nuevoId += Integer.toString(hora.obtenerHora());
        nuevoId += Integer.toString(hora.obtenerMinuto());
        nuevoId += Integer.toString(hora.obtenerSegundo());

        partidaActual = new Partida(nuevoId, usuario, tablero);
        esPartidaNueva = true;

        return nuevoId;
    }
    /* POST: Creamos una nueva instancia de Partida devolviendo el nuevo identificador */

    /* PRE: - */
    /* EXC 'InstanceRepeatedException': Ya existe una partida guardada con
        id = 'id' (se genera la excepcion).*/
    /* EXC 'InstanceNotFoundException': Partida no encontrada al actualizar una partida
        que anteriormente ya habia sido guardada */
    public void guardarPartida(String id, int tiempo) throws Exception {
        
        PartidaGuardada partida;
        Fecha fecha = new Fecha();
        Hora hora = new Hora();

        if (esPartidaNueva) {
            fecha.hoy();
            hora.ahora();
            partida = new PartidaGuardada(id, partidaActual, fecha, hora);
            partida.setTiempo(tiempo);
            DATOS_PARTIDA.insertar(partida);
            partidaActual = partida;
            esPartidaNueva = false;
        }
        else {
            partida = DATOS_PARTIDA.obtener(id);
            fecha.hoy();
            hora.ahora();
            partida.introducirFecha(fecha);
            partida.introducirHora(hora);
            partida.setTiempo(tiempo);
            DATOS_PARTIDA.actualizar(partida);
        }    
    }
    /* POST: Se guarda la partida actual. Si ya se habia guardado se actualizan la fecha
        y la hora. Si no, se crea una instancia de PartidaGuardada. */

    /* PRE: La partida con id = 'id' existe */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void cargarPartida(String id, int[] tiempo, String[] tablero)
      throws Exception {
        
        partidaActual = DATOS_PARTIDA.obtener(id);
        tiempo[0] = partidaActual.getTiempo();
        tablero[0] = partidaActual.getIdTablero();
        esPartidaNueva = false;
    }
    /* POST: La partida actual pasa a ser la partida con id = 'id'. Devuelve el tiempo de
        esa partida y el identificador del tablero. */

    /* PRE: 'puntuacion' >= 0; 'tiempo' >= 0; 'pistas' >= 0 */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': identificador de partida actual no encontrado */
    public int finalizarPartida(int puntuacion, int tiempo, int pistas)
      throws Exception {

        PartidaGuardada partida;

        if (partidaActual.esTerminada().booleanValue()) puntuacion = -1;
        else puntuacion = penalizaPuntuacion(puntuacion, tiempo, pistas);

        if(!esPartidaNueva) {
            partida = DATOS_PARTIDA.obtener(partidaActual.getIdPartida());
            partida.setTerminada();
            DATOS_PARTIDA.actualizar(partida);
        }
        partidaActual = null;
        
        return puntuacion;
    }
    /* POST: La partida actual pasa a estar finalizada y se devuelve una puntuacion
        resultado de aplicar penalizaciones, -1 indica que la partida ya habia sido
        finalizada. */

    /* PRE: 'idPartida' corresponde al identificador de una partida guardada. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public boolean eliminarPartida(String idPartida, String[] tablero,
      int[] tiempoEmpleado) throws Exception {

        PartidaGuardada partida = DATOS_PARTIDA.obtener(idPartida);
        
        DATOS_PARTIDA.borrar(idPartida);
        tablero[0] = partida.getIdTablero();
        tiempoEmpleado[0] = partida.getTiempo();

        return partida.esTerminada();
    }
    /* POST: Se elimina la instancia de la partida con id 'idPartida' devolviendo el
        identificador del tablero y el tiempo empleado de esta partida (parametros de
        salida) y cierto si era una partida finalizada. */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public ArrayList<Object[]> mostrarPartidasGuardadas(int opcion, String usuario)
      throws IOException {

        List<PartidaGuardada> listaPartidas = DATOS_PARTIDA.obtenerTodos();
        ArrayList<Object[]> resultado = new ArrayList<Object[]>();
        Comparator<PartidaGuardada> comparador;

        for (int i = listaPartidas.size() - 1; i >= 0; --i) {
            if (!usuario.equals(listaPartidas.get(i).getIdUsuario()))
                listaPartidas.remove(i);
        }

        if (opcion == ORDENACION_POR_NOMBRE) comparador = new comparadorNombre();
        else comparador = new comparadorFecha();

        Collections.sort(listaPartidas, comparador);
        for (int i = 0; i < listaPartidas.size(); ++i) {
            Object[] tupla = new Object[3];
            tupla[0] = listaPartidas.get(i).getIdPartida();
            tupla[1] = listaPartidas.get(i).obtenerFecha().obtenerFechaFormal();
            tupla[2] = listaPartidas.get(i).obtenerHora().obtenerHoraFormal();
            resultado.add(tupla);
        }
        
        return resultado;
    }
    /* POST: Devuelve una lista de tuplas ordenada segun el criterio marcado por 'opcion'
        en las que cada una se corresponde a una partida guardada. */

    /* PRE: - */
    private int penalizaPuntuacion (int puntuacion, int tiempo, int pistas) {

        double puntos = (double)puntuacion;
        double puntuacionTotal = (100 * puntos * puntos) / Math.sqrt((double)tiempo);
        double penalizacionTotal = Math.min(100.0,
          (double)x100_POR_PISTA * (double)pistas);

        return (int)((puntuacionTotal * (100.0 - penalizacionTotal)) / 100.0);
    }
    /* POST: Devuelve la puntuacion rebajada conforme a una penalizacion en funcion de
        tiempo y pistas */

    /* FUNCIONES PARA ORDENAR */
    /* Compara dos partidas guardadas lexicograficamente mediante el identificador de
        partida */
    private class comparadorNombre implements Comparator<PartidaGuardada> {

        public int compare(PartidaGuardada guardada1, PartidaGuardada guardada2) {

            return (guardada1.getIdPartida()).compareTo(guardada2.getIdPartida());
        }
    }

    /* Compara dos partidas guardadas mediante la cronologia del momento en que cada una
        de ellas fue guardada. En un remoto caso que que fueran en el mismo segundo por el
        motivo que sea, se ordenaria por orden lexicografico del identificador de
        partida */
    private class comparadorFecha implements Comparator<PartidaGuardada> {

        public int compare(PartidaGuardada guardada1, PartidaGuardada guardada2) {

            int dias1 = guardada1.obtenerFecha().obtenerDiaDelMes();
            int meses1 = guardada1.obtenerFecha().obtenerMesInt();
            int anios1 = guardada1.obtenerFecha().obtenerAnio();
            int horas1 = guardada1.obtenerHora().obtenerHora();
            int minutos1 = guardada1.obtenerHora().obtenerMinuto();
            int segundos1 = guardada1.obtenerHora().obtenerSegundo();

            int dias2 = guardada2.obtenerFecha().obtenerDiaDelMes();
            int meses2 = guardada2.obtenerFecha().obtenerMesInt();
            int anios2 = guardada2.obtenerFecha().obtenerAnio();
            int horas2 = guardada2.obtenerHora().obtenerHora();
            int minutos2 = guardada2.obtenerHora().obtenerMinuto();
            int segundos2 = guardada2.obtenerHora().obtenerSegundo();

            if (anios1 > anios2) return -1;
            else if (anios1 < anios2) return 1;

            if (meses1 > meses2) return -1;
            else if (meses1 < meses2) return 1;

            if (dias1 > dias2) return -1;
            else if (dias1 < dias2) return 1;

            if (horas1 > horas2) return -1;
            else if  (horas1 < horas2) return 1;

            if (minutos1 > minutos2) return -1;
            else if (minutos1 < minutos2) return 1;

            if (segundos1 > segundos2) return -1;
            else if (segundos1 < segundos2) return 1;
            else return (guardada1.getIdPartida()).compareTo(guardada2.getIdPartida());
        }
    }
}

