/*  Class ControladorDatosRanking:
    Descripcion: STUB del controlador de datos de Ranking.
    Autor: miguel.angel.vico
    Revisado: 20/11/2009 09:16 */

package Datos;

import Dominio.Ranking;

public class ControladorDatosRanking {

    private final static ControladorDatosRanking INSTANCIA =
      new ControladorDatosRanking();
    Ranking ranking;

    /* PRE: - */
    private ControladorDatosRanking() {

        ranking = new Ranking();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosRanking getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public Ranking obtener() {

        return ranking;
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar() {

    }
    /* POST: - */
}
