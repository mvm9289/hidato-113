/*  Class ControladorEstadisticas:
    Descripcion: Clase encargada de controlar toda la parte de las estadisticas y ranking
        del juego.
    Autor: miguel.angel.vico
    Revisado: 20/11/2009 09:19 */

package Dominio;

import Datos.ControladorDatosEstadisticasGenerales;
import Datos.ControladorDatosEstadisticasPersonales;
import Datos.ControladorDatosRanking;
import Excepciones.InstanceNotFoundException;
import Tiempo.Fecha;
import java.util.ArrayList;
import java.util.List;

public class ControladorEstadisticas {

    /* Constantes de estadisticas generales */
    public final static int TOTAL_PARTIDAS = 1;
    public final static int PARTIDAS_ABANDONADAS = 2;
    public final static int PARTIDAS_RESUELTAS = 3;
    public final static int PARTIDAS_FACIL = 4;
    public final static int PARTIDAS_MEDIO = 5;
    public final static int PARTIDAS_DIFICIL = 6;
    public final static int TOTAL_USUARIOS = 7;
    public final static int TOTAL_TABLEROS = 8;
    public final static int TABLEROS_GENERADOS = 9;
    public final static int TABLEROS_PROPUESTOS = 10;
    /* Constantes de estadisticas personales */
    public final static int NIVEL_EXPERIENCIA = 11;
    public final static int TIEMPO_TOTAL_JUEGO = 12;
    public final static int TIEMPO_MEDIO_PARTIDA_RESUELTA = 13;
    public final static int PARTIDAS_RESUELTAS_PERSONALES = 14;
    public final static int x100_PARTIDAS_RESUELTAS = 15;
    public final static int TOTAL_PARTIDAS_PERSONALES = 16;
    public final static int POSICION_RANKING = 17;
    public final static int TABLEROS_PROPUESTOS_PERSONALES = 18;
    public final static int PUNTUACION_TOTAL = 19;
    public final static int PUNTUACION_MEDIA = 20;
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
    public void cargarEstadisticasPersonales(String usuario) {

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
            tupla[2] = posiciones.get(i).getFecha();
            retorno.add(tupla);
        }

        return retorno;
    }
    /* POST: Retorna una lista con las 'nbPosiciones' primeras posiciones del ranking,
        si las hay. */

    /* PRE: puntuacion >= 0. 'usuario' corresponde a un identificador de una instancia
        existente de Usuario. */
    public void nuevaPosicion(int puntuacion, String usuario) {

        Posicion posicion;
        Fecha fechaHoy = new Fecha();

        fechaHoy.hoy();
        posicion = new Posicion(puntuacion, usuario, fechaHoy);
        ranking.insertarPosicion(posicion);
        DATOS_RANKING.actualizar();
    }
    /* POST: Inserta una nueva posicion dentro del ranking con puntuacion = 'puntuacion',
        usuario = 'usuario' y fecha de hoy. */

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
    public void resetearEstadisticas() {

        estadisticasPersonales.inicializarEstadisticasPersonales();
        DATOS_PERSONALES.actualizar(estadisticasPersonales);
    }
    /* POST: Todas las estadisticas de estadisticasPersonales quedan en su estado inicial,
        excepto tablerosPropuestos.  */

    /* PRE: estadisticasPersonales != null. 'estadisticas' es una lista con cada una de
        las estadisticas a modificar (constantes de estadisticas personales) y sus
        valores. */
    public void actualizarEstadisticasPersonales(ArrayList<Object[]> estadisticas) {
        
        Object[] tupla;
        int estadistica;
        int valorInt;
        boolean valorBool;

        for (int i = 0; i < estadisticas.size(); ++i) {
            tupla = estadisticas.get(i);
            estadistica = ((Integer)tupla[0]).intValue();
            switch (estadistica) {
                case TIEMPO_TOTAL_JUEGO:
                    valorInt = ((Integer)tupla[1]).intValue();
                    estadisticasPersonales.incrementarTiempoTotalDeJuego(valorInt);
                    break;
                case TOTAL_PARTIDAS_PERSONALES:
                    valorBool = ((Boolean)tupla[1]).booleanValue();
                    estadisticasPersonales.incrementarTotalPartidas(valorBool);
                    break;
                case POSICION_RANKING:
                    valorInt = ((Integer)tupla[1]).intValue();
                    if(estadisticasPersonales.obtenerPosicionRanking() > valorInt ||
                      estadisticasPersonales.obtenerPosicionRanking() == -1)
                        estadisticasPersonales.introducirPosicionRanking(valorInt);
                    break;
                case TABLEROS_PROPUESTOS_PERSONALES:
                    estadisticasPersonales.incrementarTablerosPropuestos();
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
    public void actualizarEstadisticasGenerales(ArrayList<Object[]> estadisticas) {

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
                    estadisticasGenerales.incrementarTotalTableros();
                    break;
                case TABLEROS_GENERADOS:
                    estadisticasGenerales.incrementaTablerosGenerados();
                    break;
                case TABLEROS_PROPUESTOS:
                    estadisticasGenerales.incrementarTablerosPropuestos();
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
    public void eliminarEstadisticas() {

        DATOS_PERSONALES.borrar(estadisticasPersonales.obtenerUsuario());
        estadisticasPersonales = null;
    }
    /* POST: Se elimina estadisticasPersonales del sistema. */
}
