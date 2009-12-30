/*  Class ControladorDatosEstadisticasGenerales:
    Descripcion: STUB del controlador de datos de EstadisticasGenerales.
    Autor: miguel.angel.vico
    Revisado: 20/11/2009 09:16 */

package Datos;

import Dominio.EstadisticasHidato;

public class ControladorDatosEstadisticasGenerales {

    private final static ControladorDatosEstadisticasGenerales INSTANCIA =
      new ControladorDatosEstadisticasGenerales();
    EstadisticasHidato estadisticasGenerales;

    /* PRE: - */
    private ControladorDatosEstadisticasGenerales() {

        estadisticasGenerales = new EstadisticasHidato();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosEstadisticasGenerales getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public EstadisticasHidato obtener() {

        return estadisticasGenerales;
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar() {

    }
    /* POST: - */
}
