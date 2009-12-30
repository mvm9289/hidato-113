/*  Class EstadisticasPersonales:
    Descripcion: Representa el conjunto de los datos estadisticos personales de cada
        usuario.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 19:35 */

package Dominio;

import Utiles.Utiles;
import java.util.Arrays;
import java.util.ArrayList;

public class EstadisticasPersonales {

    /* Constantes para Nivel de Experiencia */
    public final static int EXP_PRINCIPIANTE = 0;
    public final static int EXP_INTERMEDIO = 1;
    public final static int EXP_EXPERTO = 2;
    public final static int EXP_MAESTRO = 3;
    
    private final static int PUNT_FACIL = 10000;
    private final static int PUNT_MEDIO = 40000;
    private final static int PUNT_EXPERTO = 70000;

    /* Atributos */
    private String usuario;
    private int nivelExperiencia;
    private int tiempoTotalDeJuego;
    private double tiempoMedioPartidaResuelta;
    private int partidasResueltas;
    private double porcentajePartidasResueltas;
    private int totalPartidas;
    private int posicionRanking;
    private int tablerosPropuestos;
    private int puntuacionTotal;
    private double puntuacionMedia;

    /* PRE: Existe una cuenta de usuario 'usuario'. */
    public EstadisticasPersonales(String usuario) {

        inicializarEstadisticasPersonales();
        this.usuario = usuario;
        tablerosPropuestos = 0;
    }
    /* POST: Crea una instancia de EstadisticasPersonales con usuario = 'usuario'. */

    /* PRE: - */
    public void inicializarEstadisticasPersonales() {

        nivelExperiencia = EXP_PRINCIPIANTE;
        tiempoTotalDeJuego = 0;
        tiempoMedioPartidaResuelta = 0;
        partidasResueltas = 0;
        totalPartidas = 0;
        porcentajePartidasResueltas = 0;
        posicionRanking = -1; // -1: no tiene posicion en el ranking
        puntuacionTotal = 0;
        puntuacionMedia = 0;
    }
    /* POST: Inicializa todos los datos estadisticos del usuario 'usuario', a excepcion
        del numero de tableros propuestos, que se mantiene. */

    /* PRE: - */
    public String obtenerUsuario() {

        return usuario;
    }
    /* POST: Retorna el usuario de las estadisticas personales. */

    /* PRE: - */
    public int obtenerNivelExperiencia() {

        return nivelExperiencia;
    }
    /* POST: Retorna el nivel de experiencia del usuario. */

    /* PRE: - */
    public int obtenerTiempoTotalDeJuego() {

        return tiempoTotalDeJuego;
    }
    /* POST: Retorna el tiempo total que ha jugado el usuario. */

    /* PRE: inc >= 0 */
    public void incrementarTiempoTotalDeJuego(int inc) {

        tiempoTotalDeJuego += inc;
    }
    /* POST: Incrementa en 'inc' el tiempo total de juego. */

    /* PRE: - */
    public double obtenerTiempoMedioPartidaResuelta() {

        return tiempoMedioPartidaResuelta;
    }
    /* POST: Retorna el tiempo medio por partida resuelta del usuario. */

    /* PRE: - */
    public int obtenerPartidasResueltas() {

        return partidasResueltas;
    }
    /* POST: Retorna el numero de partidas resueltas por el usuario. */

    /* PRE: - */
    public double obtenerPorcentajePartidasResueltas() {

        return porcentajePartidasResueltas;
    }
    /* POST: Retorna el porcentaje de partidas resueltas por el usuario. */

    /* PRE: - */
    public int obtenerTotalPartidas() {

        return totalPartidas;
    }
    /* POST: Retorna el numero total de partidas jugadas. */

    /* PRE: El tiempoTotalDeJuego esta actualizado. */
    public void incrementarTotalPartidas(int inc, boolean resueltas) {

        if (resueltas) {
            partidasResueltas += inc;
            tiempoMedioPartidaResuelta = Utiles.redondearDouble(
              (double)tiempoTotalDeJuego / (double)partidasResueltas, 2);
        }
        totalPartidas += inc;
        porcentajePartidasResueltas = Utiles.redondearDouble(
          (100.0 * (double)partidasResueltas) / (double)totalPartidas, 2);
    }
    /* POST: Se incrementa el numero total de partidas jugadas en 'inc' y se actualiza el
        porcentaje de partidas resueltas. Si ha sido resuelta se actualiza el tiempo medio
        de una partida resuelta. */

    /* PRE: - */
    public int obtenerPosicionRanking() {

        return posicionRanking;
    }
    /* POST: Retorna la posicion del usuario en el ranking del sistema. */

    /* PRE: posicion >= 1 */
    public void introducirPosicionRanking(int posicion) {

        posicionRanking = posicion;
    }
    /* POST: La nueva posicion en el ranking del usuario pasa a ser 'posicion'. */

    /* PRE: - */
    public int obtenerTablerosPropuestos() {

        return tablerosPropuestos;
    }
    /* POST: Retorna el numero de tableros propuestos por el usuario. */

    /* PRE: - */
    public void incrementarTablerosPropuestos(int inc) {

        tablerosPropuestos += inc;
    }
    /* POST: Se incrementa en 'inc' el numero de tableros propuestos. */

    /* PRE: - */
    public int obtenerPuntuacionTotal() {

        return puntuacionTotal;
    }
    /* POST: Retorna la puntuacion total conseguida por el usuario. */

    /* PRE: El total de partidas esta actualizado. */
    public void incrementarPuntuacionTotal(int inc) {

        puntuacionTotal += inc;
        puntuacionMedia = Utiles.redondearDouble(
          (double)puntuacionTotal / (double)totalPartidas, 2);
        if (puntuacionMedia >= PUNT_EXPERTO) nivelExperiencia = EXP_MAESTRO;
        else if (puntuacionMedia >= PUNT_MEDIO) nivelExperiencia = EXP_EXPERTO;
        else if (puntuacionMedia >= PUNT_FACIL) nivelExperiencia = EXP_INTERMEDIO;
        else nivelExperiencia = EXP_PRINCIPIANTE;
    }
    /* POST: Se incrementa en 'inc' la puntuacion total del usuario y se actualiza la
        puntuacion media. En funcion del valor de este ultimo dato estadistico
        clasificamos el jugador en un nivel de experiencia determinado. */

    /* PRE: - */
    public double obtenerPuntuacionMedia() {
        
        return puntuacionMedia;
    }
    /* POST: Retorna la puntuacion media conseguida por el usuario. */

    /*PRE: - */
    public ArrayList<Object> obtenerEstadisticas() {
        return new ArrayList<Object>(Arrays.asList(nivelExperiencia,
                                     tiempoTotalDeJuego,
                                     tiempoMedioPartidaResuelta,
                                     partidasResueltas,
                                     porcentajePartidasResueltas,
                                     totalPartidas,
                                     posicionRanking,
                                     tablerosPropuestos,
                                     puntuacionTotal,
                                     puntuacionMedia));
    }
    /* POST: Retorna una lista de objetos con los valores de los datos estadisticos. */
}
