/*  Class ControladorPartida
    Descripcion: Gestiona usuarios y tableros ya generados, las partidas quedan
        univocamente identificadas por su identificador. Una partida solo sera
        finalizada, si se ha explicitado su fin.
    Autor: daniel.camarasa
    Revisado: 20/11/2009 09:19 */

package Dominio;

import Datos.ControladorDatosPartidaGuardada;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import Tiempo.Fecha;
import Tiempo.Hora;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class ControladorPartida {

    private final static String NOMBRE_PARTIDA = "PARTIDA";
    public final static int ORDENACION_POR_NOMBRE = 1;
    public final static int ORDENACION_POR_FECHA = 2;
    private final static int PORCENTAJE_POR_PISTA = 7;
    private final static ControladorDatosPartidaGuardada DATOS_PARTIDA =
      ControladorDatosPartidaGuardada.getInstance();
    private final static ControladorPartida INSTANCIA = new ControladorPartida();
    /* Atributos */
    private Partida partidaActual;
    private boolean esPartidaNueva;

    /* PRE: - */
    private ControladorPartida() {
        
        partidaActual = null;
    }
    /* POST: Se crea el controlador de partidas */

    /* PRE: - */
    public static ControladorPartida getInstance() {

        return INSTANCIA;
    }
    /* POST: - */
    
    /* PRE: - */
    public void nuevaPartida(String tablero, String usuario) {

        /* para evitar que haya identificadores de partida repetidos nos 
        apoyamos en la fecha y hora actuales */

        Fecha fecha = new Fecha();
        Hora hora = new Hora();
        fecha.hoy();
        hora.ahora();

        String nuevoId =  NOMBRE_PARTIDA;
        nuevoId += Integer.toString(fecha.obtenerDiaDelMes());
        nuevoId += fecha.obtenerMesStr();
        nuevoId += Integer.toString(fecha.obtenerAnio());
        nuevoId += Integer.toString(hora.obtenerHora());
        nuevoId += Integer.toString(hora.obtenerMinuto());
        nuevoId += Integer.toString(hora.obtenerSegundo());

        partidaActual = new Partida(nuevoId, usuario, tablero);
        esPartidaNueva = true;
    }
    /* POST: Creamos una nueva instancia de Partida */

    /* PRE: - */
    /* EXC: InstanceRepeatedException: Ya existe una partida guardada con 
    id = 'id' (se genera la excepcion).*/
    public void guardarPartida(String id, int tiempo) throws Exception {
        
        PartidaGuardada partida;
        Fecha fecha = new Fecha();
        Hora hora = new Hora();

        if (esPartidaNueva) {
            if (DATOS_PARTIDA.existe(id)) throw new InstanceRepeatedException(id);
            else {
                fecha.hoy();
                hora.ahora();
                partida = new PartidaGuardada(id, partidaActual, fecha, hora);
                partida.setTiempo(tiempo);
                DATOS_PARTIDA.insertar(partida);
                partidaActual = partida;
                esPartidaNueva = false;
            }
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
    public void cargarPartida(String id, Integer[] tiempo, String[] tablero)
      throws InstanceNotFoundException {
        
        partidaActual = DATOS_PARTIDA.obtener(id);
        tiempo[0] = Integer.valueOf(partidaActual.getTiempo());
        tablero[0] = partidaActual.getIdTablero();
        esPartidaNueva = false;
    }
    /* POST: La partida actual pasa a ser la partida con id = 'id'. Devuelve el tiempo de
    esa partida y el identificador del tablero. */

    /* PRE: 'puntuacion' >= 0; 'tiempo' >= 0; 'pistas' >= 0 */
    public int finalizarPartida(int puntuacion, int tiempo, int pistas)
      throws InstanceNotFoundException {

        PartidaGuardada partida;

        if (partidaActual.esTerminada().booleanValue()) puntuacion = 0;
        else puntuacion = penalizaPuntuacion(puntuacion, tiempo, pistas);

        if(!esPartidaNueva) {
            partida = DATOS_PARTIDA.obtener(partidaActual.getIdPartida());
            partida.setTerminada();
            DATOS_PARTIDA.actualizar(partida);
        }
        else partidaActual.setTerminada();
        
        partidaActual = null;
        return puntuacion;
    }
    /* POST: La partida actual pasa a estar finalizada y se devuelve una puntuacion
    resultado de aplicar penalizaciones. */

    /* PRE: - */
    private int penalizaPuntuacion (int puntuacion, int tiempo, int pistas) {

        int puntuacionTotal, penalizacionTotal;

        puntuacionTotal = puntuacion / tiempo;
        penalizacionTotal = Math.min(100, PORCENTAJE_POR_PISTA * pistas);

        return (int)(puntuacionTotal*((double)(100 - penalizacionTotal) / (double)100.0));
    }
    /* POST: Devuelve la puntuacion rebajada conforme a una penalizacion en funcion de
    tiempo y pistas */

    /* PRE: La partida actual es una partida guardada. */
    public String eliminarPartida() throws Exception {

        String tablero = partidaActual.getIdTablero();
        DATOS_PARTIDA.borrar(partidaActual.getIdPartida());
        partidaActual = null;
        
        return tablero;
    }
    /* POST: Se elimina la instancia de la partida actual devolviendo el identificador
    del tablero de esta partida. */

    /* PRE: - */
    public ArrayList<Object[]> mostrarPartidasGuardadas(int opcion, String usuario) {

        ArrayList<PartidaGuardada> listaPartidas = DATOS_PARTIDA.obtenerTodos();
        for (int i = listaPartidas.size() - 1; i >= 0; --i) {
            System.out.println(listaPartidas.get(i).getIdUsuario());
            System.out.println(i);
            if (!usuario.equals(listaPartidas.get(i).getIdUsuario()))
                listaPartidas.remove(i);
        }
        
        ArrayList<Object[]> resultado = new ArrayList<Object[]>();

        Comparator<PartidaGuardada> comparador;

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
    /* POST: Devuelve una lista de tuplas en las que cada una se corresponde a
    una partida guardada. */

    private class comparadorNombre implements Comparator<PartidaGuardada> {

        public int compare(PartidaGuardada pg1, PartidaGuardada pg2) {

            return (pg1.getIdPartida()).compareTo(pg2.getIdPartida());
        }
    }

    private class comparadorFecha implements Comparator<PartidaGuardada> {

        public int compare(PartidaGuardada pg1, PartidaGuardada pg2) {

            int  dias1, meses1, anios1, dias2, meses2, anios2;
            int horas1, minutos1, segundos1, horas2, minutos2, segundos2;
            dias1 = pg1.obtenerFecha().obtenerDiaDelMes();
            meses1 = pg1.obtenerFecha().obtenerMesInt();
            anios1 = pg1.obtenerFecha().obtenerAnio();
            horas1 = pg1.obtenerHora().obtenerHora();
            minutos1 = pg1.obtenerHora().obtenerMinuto();
            segundos1 = pg1.obtenerHora().obtenerSegundo();

            dias2 = pg2.obtenerFecha().obtenerDiaDelMes();
            meses2 = pg2.obtenerFecha().obtenerMesInt();
            anios2 = pg2.obtenerFecha().obtenerAnio();
            horas2 = pg2.obtenerHora().obtenerHora();
            minutos2 = pg2.obtenerHora().obtenerMinuto();
            segundos2 = pg2.obtenerHora().obtenerSegundo();

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
            else return (pg1.getIdPartida()).compareTo(pg2.getIdPartida());
        }
    }
}

