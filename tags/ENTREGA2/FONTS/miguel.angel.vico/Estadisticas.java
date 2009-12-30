/*  Class Estadisticas:
    Descripcion: STUB de la clase compartida Estadisticas.
    Autor: miguel.angel.vico
    Revisado: 20/11/2009 09:18 */

package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Estadisticas {

    /* PRE: - */
    public Estadisticas() {

        System.out.println("Llamada: Estadisticas.Estadisticas()");
    }
    /* POST: - */

    /* PRE: - */
    public int obtenerTotalPartidas() {

        System.out.println("Llamada: Estadisticas.obtenerTotalPartidas()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public void incrementarTotalPartidas(int inc) {

        System.out.println("Llamada: Estadisticas.incrementarTotalPartidas(" +
          String.valueOf(inc) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public int obtenerPartidasAbandonadas() {

        System.out.println("Llamada: Estadisticas.obtenerPartidasAbandonadas()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public void incrementarPartidasAbandonadas(int inc) {

        System.out.println("Llamada: Estadisticas.incrementarPartidasAbandonadas(" +
          String.valueOf(inc) + ")");
    }
    /* POST: - */

    /* PRE:- */
    public int obtenerPartidasResueltas() {

        System.out.println("Llamada: Estadisticas.obtenerPartidasResueltas()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public void incrementarPartidasResueltas(int inc) {

        System.out.println("Llamada: Estadisticas.incrementarPartidasResueltas(" +
          String.valueOf(inc) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public int obtenerPartidasFacil() {

        System.out.println("Llamada: Estadisticas.obtenerPartidasFacil()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public void incrementarPartidasFacil(int inc) {

        System.out.println("Llamada: Estadisticas.incrementarPartidasFacil(" +
          String.valueOf(inc) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public int obtenerPartidasMedio() {

        System.out.println("Llamada: Estadisticas.obtenerPartidasMedio()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public void incrementarPartidasMedio(int inc) {

        System.out.println("Llamada: Estadisticas.incrementarPartidasMedio(" +
          String.valueOf(inc) + ")");
    }
    /* POST: - */

    /*PRE: - */
    public int obtenerPartidasDificil() {

        System.out.println("Llamada: Estadisticas.obtenerPartidasDificil()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public void incrementarPartidasDificil(int inc) {

        System.out.println("Llamada: Estadisticas.incrementarPartidasDificil(" +
          String.valueOf(inc) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public int obtenerTotalUsuarios() {

        System.out.println("Llamada: Estadisticas.obtenerTotalUsuarios()");

        return 1;
    }
    /* POST: - */


    /* PRE: - */
    public void incrementarTotalUsuarios(int inc) {

        System.out.println("Llamada: Estadisticas.incrementarTotalUsuarios(" +
          String.valueOf(inc) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public List<Integer> obtenerEstadisticas() {

        List<Integer> list = new ArrayList<Integer>();

        System.out.println("Llamada: Estadisticas.obtenerEstadisticas()");
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);

        return list;
    }
    /* POST: - */
}
