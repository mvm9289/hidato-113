/*
    Class Estadisticas
    Descripcion: contiene las estadisticas de la aplicacion
    Autor: beatriz.casin
    Revisado: 14/11/2009 12:50

 */

package Dominio;

import java.util.*;

public class Estadisticas {

    private int totalPartidas;
    private int partidasAbandonadas;
    private int partidasResueltas;
    private int partidasFacil;
    private int partidasMedio;
    private int partidasDificil;
    private int totalUsuarios;

    /* PRE: - */
    public Estadisticas() {

        totalPartidas = 0;
        partidasAbandonadas = 0;
        partidasResueltas = 0;
        partidasFacil = 0;
        partidasMedio = 0;
        partidasDificil = 0;
        totalUsuarios = 0;
    }
    /* POST: Crea una instancia con todos sus atributos inicializados a 0 */

    /* PRE: - */
    public int obtenerTotalPartidas() {

        return totalPartidas;
    }
    /* POST: Obtiene el valor del atributo totalPartidas */

    /* PRE: - */
    public int obtenerPartidasAbandonadas() {

        return partidasAbandonadas;
    }
    /* POST: Obtiene el valor del atributo partidasAbandonadas */

    /* PRE: - */
    public int obtenerPartidasResueltas() {

        return partidasResueltas;
    }
    /* POST: Obtiene el valor del atributo partidasResueltas */

    /* PRE: - */
    public int obtenerPartidasFacil() {

        return partidasFacil;
    }
    /* POST: Obtiene el valor del atributo partidasFacil */

    /* PRE: -  */
    public int obtenerPartidasMedio() {

        return partidasMedio;
    }
    /* POST: Obtiene el valor del atributo partidasMedio */

    /* PRE: - */
    public int obtenerPartidasDificil() {

        return partidasDificil;
    }
    /* POST: Obtiene el valor del atributo partidasDificil */

    /* PRE: - */
    public int obtenerTotalUsuarios() {

        return totalUsuarios;
    }
    /* POST: Obtiene el valor del atributo totalUsuarios */

    /* PRE: - */
    public void incrementarTotalPartidas(int inc) {

        totalPartidas += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo totalPartidas */

    /* PRE: - */
    public void incrementarPartidasAbandonadas(int inc) {

        partidasAbandonadas += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo partidasAbandonadas */

    /* PRE: - */
    public void incrementarPartidasResueltas(int inc) {

        partidasResueltas += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo partidasResueltas */

    /* PRE: - */
    public void incrementarPartidasFacil(int inc) {

        partidasFacil += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo partidasFacil */

    /* PRE: - */
    public void incrementarPartidasMedio(int inc) {

        partidasMedio += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo partidasMedio */

    /* PRE: - */
    public void incrementarPartidasDificil(int inc) {

        partidasDificil += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo partidasDificil */

    /* PRE: - */
    public void incrementarTotalUsuarios(int inc) {

        totalUsuarios += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo totalUsuarios */

    /*PRE: - */
    public List<Integer> obtenerEstadisticas() {

        List<Integer> estadisticas = new ArrayList<Integer>();

        estadisticas.add(totalPartidas);
        estadisticas.add(partidasAbandonadas);
        estadisticas.add(partidasResueltas);
        estadisticas.add(partidasFacil);
        estadisticas.add(partidasMedio);
        estadisticas.add(partidasDificil);
        estadisticas.add(totalUsuarios);

        return estadisticas;
    }
    /* POST: Obtiene una lista con los valores de los atributos de la instancia */
}
