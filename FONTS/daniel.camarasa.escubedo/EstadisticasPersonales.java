/*  Class EstadisticasPersonales:
    Descripcion: Representa el conjunto de los datos estadisticos personales de cada
        usuario.
    Autor: daniel.camarasa
    Revisado: 20/11/2009 09:18 */

package Dominio;

import java.util.Arrays;
import java.util.ArrayList;

public class EstadisticasPersonales {

    /* Constantes para Nivel de Experiencia */
    private final static int PUNT_FACIL = 30000;
    private final static int PUNT_MEDIO = 100000;
    private final static int PUNT_EXPERTO = 300000;
    /* Atributos */
    private String usuario;
    private String nivelExperiencia;
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

        nivelExperiencia = "Principiante";
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
    public String obtenerNivelExperiencia() {

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
    public void incrementarTotalPartidas(boolean resuelta) {

        if (resuelta) {
            ++partidasResueltas;
            tiempoMedioPartidaResuelta =
              (double)tiempoTotalDeJuego / (double)partidasResueltas;
        }
        ++totalPartidas;
        porcentajePartidasResueltas =
          (100.0 * (double)partidasResueltas) / (double)totalPartidas;
    }
    /* POST: Se incrementa el numero total de partidas jugadas y se actualiza el
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
    public void incrementarTablerosPropuestos() {

        ++tablerosPropuestos;
    }
    /* POST: Se incrementa en 1 el numero de tableros propuestos. */

    /* PRE: - */
    public int obtenerPuntuacionTotal() {

        return puntuacionTotal;
    }
    /* POST: Retorna la puntuacion total conseguida por el usuario. */

    /* PRE: El total de partidas esta actualizado. */
    public void incrementarPuntuacionTotal(int inc) {

        puntuacionTotal += inc;
        puntuacionMedia = (double)puntuacionTotal / (double)totalPartidas;
        if (puntuacionMedia >= PUNT_EXPERTO) nivelExperiencia = "Maestro";
        else if (puntuacionMedia >= PUNT_MEDIO) nivelExperiencia = "Experto";
        else if (puntuacionMedia >= PUNT_FACIL) nivelExperiencia = "Intermedio";
        else nivelExperiencia = "Principiante";
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
