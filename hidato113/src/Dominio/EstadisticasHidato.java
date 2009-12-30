/*  Class EstadisticasHidato:
    Descripcion: Recoge el conjunto de estadisticas generales del juego.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 11:31 */

package Dominio;

import java.util.List;

public class EstadisticasHidato extends Estadisticas {

    private int totalTableros;
    private int tablerosGenerados;
    private int tablerosPropuestos;

    /* PRE: - */
    public EstadisticasHidato() {

        super();
        totalTableros = 0;
        tablerosGenerados = 0;
        tablerosPropuestos = 0;
    }
    /* POST: Crea una instancia de EstadisticasHidato con todos sus atributos
        inicializados a 0. */

    /* PRE: - */
    public int obtenerTotalTableros() {

        return totalTableros;
    }
    /* POST: Retorna el valor del atributo totalTableros. */

    /* PRE: - */
    public void incrementarTotalTableros(int inc) {

        totalTableros += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo totalTableros. */

    /* PRE: - */
    public int obtenerTablerosGenerados() {

        return tablerosGenerados;
    }
    /* POST: Retorna el valor del atributo tablerosGenerados. */

    /* PRE: - */
    public void incrementarTablerosGenerados(int inc) {

        tablerosGenerados += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo tablerosGenerados. */

    /* PRE: - */
    public int obtenerTablerosPropuestos() {

        return tablerosPropuestos;
    }
    /* POST: Retorna el valor del atributo tablerosPropuestos. */

    /* PRE: - */
    public void incrementarTablerosPropuestos(int inc) {

        tablerosPropuestos += inc;
    }
    /* POST: Incrementa en 'inc' el valor del atributo tablerosPropuestos. */

    /* PRE: - */
    public List<Integer> obtenerEstadisticas() {

        List<Integer> estadisticas = super.obtenerEstadisticas();

        estadisticas.add(totalTableros);
        estadisticas.add(tablerosGenerados);
        estadisticas.add(tablerosPropuestos);

        return estadisticas;
    }
    /* POST: Retorna una lista con todos los atributos de la clase EstadisticasHidato. */
}
