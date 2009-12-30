/*  Class ControladorDatosEstadisticasPersonales:
    Descripcion: STUB del controlador de datos de EstadisticasPersonales.
    Autor: miguel.angel.vico
    Revisado: 20/11/2009 09:16 */

package Datos;

import Dominio.EstadisticasPersonales;
import Excepciones.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ControladorDatosEstadisticasPersonales {

    private final static ControladorDatosEstadisticasPersonales INSTANCIA =
      new ControladorDatosEstadisticasPersonales();
    List<EstadisticasPersonales> estadisticasPersonales;

    /* PRE: - */
    private ControladorDatosEstadisticasPersonales() {

        estadisticasPersonales = new ArrayList<EstadisticasPersonales>();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosEstadisticasPersonales getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public EstadisticasPersonales obtener(String usuario)
      throws InstanceNotFoundException {

        EstadisticasPersonales estadisticas;

        for(int i = 0; i < estadisticasPersonales.size(); ++i) {
            estadisticas = estadisticasPersonales.get(i);
            if (estadisticas.obtenerUsuario().equals(usuario)) return estadisticas;
        }

        throw new InstanceNotFoundException(usuario);
    }
    /* POST: - */

    /* PRE: - */
    public List<EstadisticasPersonales> obtenerTodos() {

        return estadisticasPersonales;
    }
    /* POST: - */

    /* PRE: - */
    public boolean existe(String usuario) {

        for(int i = 0; i < estadisticasPersonales.size(); ++i)
            if (estadisticasPersonales.get(i).obtenerUsuario().equals(usuario))
                return true;

        return false;
    }
    /* POST: - */

    /* PRE: - */
    public void insertar(EstadisticasPersonales estadisticas) {

        estadisticasPersonales.add(estadisticas);
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar(EstadisticasPersonales estadisticas) {

        /*boolean encontrado = false;

        for(int i = 0; i < estadisticasPersonales.size() && !encontrado; ++i)
            if (estadisticasPersonales.get(i).obtenerUsuario()
              .equals(estadisticas.obtenerUsuario())) {
                estadisticasPersonales.set(i, estadisticas);
                encontrado = true;
            }*/
    }
    /* POST: - */

    /* PRE: - */
    public void borrar(String usuario) {

        boolean encontrado = false;

        for(int i = 0; i < estadisticasPersonales.size() && !encontrado; ++i)
            if (estadisticasPersonales.get(i).obtenerUsuario().equals(usuario)) {
                estadisticasPersonales.remove(i);
                encontrado = true;
            }
    }
    /* POST: - */
}
