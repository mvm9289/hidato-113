/*  Class ControladorEstadisticas:
    Descripcion: Clase encargada de controlar toda la parte de las estadisticas y ranking
        del juego.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 14:04 */

package Dominio;

import Datos.ControladorDatosEstadisticasGenerales;
import Datos.ControladorDatosEstadisticasPersonales;
import Datos.ControladorDatosRanking;
import Excepciones.InstanceNotFoundException;
import Tiempo.Fecha;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorEstadisticas {

    /* Constantes de estadisticas generales */
    public final static int TOTAL_PARTIDAS = 0;
    public final static int PARTIDAS_ABANDONADAS = 1;
    public final static int PARTIDAS_RESUELTAS = 2;
    public final static int PARTIDAS_FACIL = 3;
    public final static int PARTIDAS_MEDIO = 4;
    public final static int PARTIDAS_DIFICIL = 5;
    public final static int TOTAL_USUARIOS = 6;
    public final static int TOTAL_TABLEROS = 7;
    public final static int TABLEROS_GENERADOS = 8;
    public final static int TABLEROS_PROPUESTOS = 9;

    /* Constantes de estadisticas personales */
    public final static int NIVEL_EXPERIENCIA = 10;
    public final static int TIEMPO_TOTAL_JUEGO = 11;
    public final static int TIEMPO_MEDIO_PARTIDA_RESUELTA = 12;
    public final static int PARTIDAS_RESUELTAS_PERSONALES = 13;
    public final static int x100_PARTIDAS_RESUELTAS = 14;
    public final static int TOTAL_PARTIDAS_PERSONALES = 15;
    public final static int POSICION_RANKING = 16;
    public final static int TABLEROS_PROPUESTOS_PERSONALES = 17;
    public final static int PUNTUACION_TOTAL = 18;
    public final static int PUNTUACION_MEDIA = 19;

    /* Constantes de controlador */
    private final static ControladorDatosRanking DATOS_RANKING =
     ControladorDatosRanking.getInstance();
    private final static ControladorDatosEstadisticasGenerales DATOS_GENERALES =
     ControladorDatosEstadisticasGenerales.getInstance();
    private final static ControladorDatosEstadisticasPersonales DATOS_PERSONALES =
     ControladorDatosEstadisticasPersonales.getInstance();

    private final static ControladorEstadisticas INSTANCIA =
      new ControladorEstadisticas();
    
    /* Atributos */
    private Ranking ranking;
    private EstadisticasHidato estadisticasGenerales;
    private EstadisticasPersonales estadisticasPersonales;

    /* PRE: - */
    private ControladorEstadisticas() {

        ranking = DATOS_RANKING.obtener();
        estadisticasGenerales = DATOS_GENERALES.obtener();
        estadisticasPersonales = null;
    }
    /* POST: Se crea una instancia de ControladorEstadisticas. */

    /* PRE: - */
    public static ControladorEstadisticas getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia del ControladorEstadisticas. */

    /* PRE: 'usuario' unicamente contiene caracteres alfanumericos. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void cargarEstadisticasPersonales(String usuario) throws Exception {

        try {
            estadisticasPersonales = DATOS_PERSONALES.obtener(usuario);
        }
        catch (InstanceNotFoundException ex) {
            estadisticasPersonales = new EstadisticasPersonales(usuario);
            DATOS_PERSONALES.insertar(estadisticasPersonales);
        }
    }
    /* POST: Carga las estadisticas personales del usuario 'usuario'. */

    /* PRE: nbPosiciones >= 1 */
    public List<Object[]> consultarRanking(int nbPosiciones) {
        
        List<Object[]> retorno = new ArrayList<Object[]>();
        List<Posicion> posiciones = ranking.obtenerPosiciones();

        for (int i = 0; i < posiciones.size() && i < nbPosiciones; ++i) {
            Object[] tupla = new Object[3];
            tupla[0] = posiciones.get(i).getNjugador();
            tupla[1] = posiciones.get(i).getPuntuacion();
            tupla[2] = posiciones.get(i).getFecha().obtenerFechaFormal();
            retorno.add(tupla);
        }

        return retorno;
    }
    /* POST: Retorna una lista con las 'nbPosiciones' primeras posiciones del ranking,
        si las hay. */

    /* PRE: puntuacion >= 0. 'usuario' corresponde a un identificador de una instancia
        existente de Usuario. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public int nuevaPosicion(int puntuacion, String usuario) throws IOException {

        Posicion posicion;
        Fecha fechaHoy = new Fecha();
        int posicionRanking;

        fechaHoy.hoy();
        posicion = new Posicion(puntuacion, usuario, fechaHoy);
        posicionRanking = ranking.insertarPosicion(posicion);
        DATOS_RANKING.actualizar();

        return posicionRanking;
    }
    /* POST: Inserta una nueva posicion dentro del ranking con puntuacion = 'puntuacion',
        usuario = 'usuario' y fecha de hoy, y retorna el numero de posicion
        correspondiente dentro del ranking. */

    /* PRE: estadisticasPersonales != null. */
    public List<Object[]> mostrarEstadisticasPersonales() {

        List<Object[]> retorno = new ArrayList<Object[]>();
        List<Object> estadisticas = estadisticasPersonales.obtenerEstadisticas();

        for (int i = 0; i < estadisticas.size(); ++i) {
            Object[] tupla = new Object[2];
            tupla[0] = NIVEL_EXPERIENCIA+i;
            tupla[1] = estadisticas.get(i);
            retorno.add(tupla);
        }

        return retorno;
    }
    /* POST: Retorna una lista con todas las estadisticas personales y sus valores. */

    /* PRE: - */
    public List<Object[]> mostrarEstadisticasGenerales() {

        List<Object[]> retorno = new ArrayList<Object[]>();
        List<Integer> estadisticas = estadisticasGenerales.obtenerEstadisticas();

        for (int i = 0; i < estadisticas.size(); ++i) {
            Object[] tupla = new Object[2];
            tupla[0] = TOTAL_PARTIDAS+i;
            tupla[1] = estadisticas.get(i).intValue();
            retorno.add(tupla);
        }

        return retorno;
    }
    /* POST: Retorna una lista con todas las estadisticas generales y sus valores. */

    /* PRE: estadisticasPersonales != null */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void resetearEstadisticas() throws Exception {

        ranking.eliminaPosiciones(estadisticasPersonales.obtenerUsuario());
        DATOS_RANKING.actualizar();
        estadisticasPersonales.inicializarEstadisticasPersonales();
        DATOS_PERSONALES.actualizar(estadisticasPersonales);
    }
    /* POST: Todas las estadisticas de estadisticasPersonales quedan en su estado inicial,
        excepto tablerosPropuestos.  */

    /* PRE: estadisticasPersonales != null. 'estadisticas' es una lista con cada una de
        las estadisticas a modificar (constantes de estadisticas personales) y sus
        valores. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void actualizarEstadisticasPersonales(ArrayList<Object[]> estadisticas)
      throws Exception {
        
        Object[] tupla;
        int estadistica;
        int valorInt;

        for (int i = 0; i < estadisticas.size(); ++i) {
            tupla = estadisticas.get(i);
            estadistica = ((Integer)tupla[0]).intValue();
            switch (estadistica) {
                case TIEMPO_TOTAL_JUEGO:
                    valorInt = ((Integer)tupla[1]).intValue();
                    estadisticasPersonales.incrementarTiempoTotalDeJuego(valorInt);
                    break;
                case TOTAL_PARTIDAS_PERSONALES:
                    valorInt = ((Integer)tupla[1]).intValue();
                    if (valorInt == -1) {
                        if (estadisticasPersonales.obtenerPartidasResueltas() > 0)
                            estadisticasPersonales.incrementarTotalPartidas(0, true);
                        estadisticasPersonales.incrementarTotalPartidas(1, false);
                    }
                    else
                        estadisticasPersonales.incrementarTotalPartidas(1, valorInt == 1);
                    break;
                case POSICION_RANKING:
                    valorInt = ((Integer)tupla[1]).intValue();
                    if(estadisticasPersonales.obtenerPosicionRanking() > valorInt ||
                      estadisticasPersonales.obtenerPosicionRanking() == -1)
                        estadisticasPersonales.introducirPosicionRanking(valorInt);
                    break;
                case TABLEROS_PROPUESTOS_PERSONALES:
                    estadisticasPersonales.incrementarTablerosPropuestos(1);
                    break;
                case PUNTUACION_TOTAL:
                    valorInt = ((Integer)tupla[1]).intValue();
                    estadisticasPersonales.incrementarPuntuacionTotal(valorInt);
                    break;
                default:
                    break;
            }
        }
        DATOS_PERSONALES.actualizar(estadisticasPersonales);
    }
    /* POST: Todas las estadisticas de estadisticasPersonales quedan modificadas segun
        'estadisticas'. */
    
    /* PRE: 'estadisticas' es una lista con cada una de las estadisticas a modificar
        (constantes de estadisticas generales) y sus valores. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void actualizarEstadisticasGenerales(ArrayList<Object[]> estadisticas)
      throws IOException {

        Object[] tupla;
        int estadistica;
        int valor;

        for (int i = 0; i < estadisticas.size(); ++i) {
            tupla = estadisticas.get(i);
            estadistica = ((Integer)tupla[0]).intValue();
            switch (estadistica) {
                case TOTAL_PARTIDAS:
                    valor = ((Integer)tupla[1]).intValue();
                    estadisticasGenerales.incrementarTotalPartidas(valor);
                    break;
                case PARTIDAS_ABANDONADAS:
                    valor = ((Integer)tupla[1]).intValue();
                    estadisticasGenerales.incrementarPartidasAbandonadas(valor);
                    break;
                case PARTIDAS_RESUELTAS:
                    valor = ((Integer)tupla[1]).intValue();
                    estadisticasGenerales.incrementarPartidasResueltas(valor);
                    break;
                case PARTIDAS_FACIL:
                    valor = ((Integer)tupla[1]).intValue();
                    estadisticasGenerales.incrementarPartidasFacil(valor);
                    break;
                case PARTIDAS_MEDIO:
                    valor = ((Integer)tupla[1]).intValue();
                    estadisticasGenerales.incrementarPartidasMedio(valor);
                    break;
                case PARTIDAS_DIFICIL:
                    valor = ((Integer)tupla[1]).intValue();
                    estadisticasGenerales.incrementarPartidasDificil(valor);
                    break;
                case TOTAL_USUARIOS:
                    valor = ((Integer)tupla[1]).intValue();
                    estadisticasGenerales.incrementarTotalUsuarios(valor);
                    break;
                case TOTAL_TABLEROS:
                    estadisticasGenerales.incrementarTotalTableros(1);
                    break;
                case TABLEROS_GENERADOS:
                    estadisticasGenerales.incrementarTablerosGenerados(1);
                    break;
                case TABLEROS_PROPUESTOS:
                    estadisticasGenerales.incrementarTablerosPropuestos(1);
                    break;
                default:
                    break;
            }
        }
        DATOS_GENERALES.actualizar();
    }
    /* POST: Todas las estadisticas de estadisticasGenerales quedan modificadas segun
        'estadisticas'. */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void eliminarEstadisticas() throws Exception {

        ranking.eliminaPosiciones(estadisticasPersonales.obtenerUsuario());
        DATOS_RANKING.actualizar();
        DATOS_PERSONALES.borrar(estadisticasPersonales.obtenerUsuario());
        estadisticasPersonales = null;
    }
    /* POST: Se elimina del sistema las estadisticasPersonales y las apariciones en el
        ranking del usuario actual. */
}
