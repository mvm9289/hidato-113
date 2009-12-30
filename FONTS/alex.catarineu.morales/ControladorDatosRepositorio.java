/*  Class ControladorDatosRepositorio:
    Descripcion: STUB del controlador de datos de Repositorio.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:16 */

package Datos;

import Dominio.Repositorio;

public class ControladorDatosRepositorio {

    private final static ControladorDatosRepositorio INSTANCIA =
      new ControladorDatosRepositorio();
    Repositorio repositorio;

    /* PRE: - */
    private ControladorDatosRepositorio() {

        repositorio = new Repositorio();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosRepositorio getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public Repositorio obtener() {

        return repositorio;
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar() {

    }
    /* POST: - */
}
