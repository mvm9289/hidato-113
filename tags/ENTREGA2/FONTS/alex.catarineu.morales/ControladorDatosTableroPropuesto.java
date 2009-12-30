/*  Class ControladorDatosTableroPropuesto:
    Descripcion: STUB del controlador de datos de TableroHidatoPropuesto.
    Autor: alex.catarineu
    Revisado: 18/11/2009 17:57 */

package Datos;

import Dominio.TableroHidatoPropuesto;
import java.util.ArrayList;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import java.util.List;

public class ControladorDatosTableroPropuesto {

    private final static ControladorDatosTableroPropuesto INSTANCIA =
      new ControladorDatosTableroPropuesto();
    List<TableroHidatoPropuesto> tableros;

    /* PRE: - */
    private ControladorDatosTableroPropuesto() {

        tableros = new ArrayList<TableroHidatoPropuesto>();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosTableroPropuesto getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public TableroHidatoPropuesto obtener(String tablero)
      throws InstanceNotFoundException {

        TableroHidatoPropuesto tableroHidato;

        for(int i = 0; i < tableros.size(); ++i) {
            tableroHidato = tableros.get(i);
            if (tableroHidato.getTablero().equals(tablero)) return tableroHidato;
        }

        throw new InstanceNotFoundException(tablero);
    }
    /* POST: - */

    /* PRE: - */
    public List<TableroHidatoPropuesto> obtenerTodos() {

        return tableros;
    }
    /* POST: - */

    /* PRE: - */
    public boolean existe(String tablero) {

        for(int i = 0; i < tableros.size(); ++i)
            if (tableros.get(i).getTablero().equals(tablero)) return true;

        return false;
    }
    /* POST: - */

    /* PRE: - */
    public void insertar(TableroHidatoPropuesto tablero)
      throws InstanceRepeatedException{

        for(int i = 0; i < tableros.size(); ++i)
            if (tableros.get(i).getTablero().equals(tablero.getTablero()))
                throw new InstanceRepeatedException(tablero.getTablero());

        tableros.add(tablero);
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar(TableroHidatoPropuesto tablero)
      throws InstanceNotFoundException {

    }
    /* POST: - */

    /* PRE: - */
    public void borrar(String tablero) {

        boolean encontrado = false;

        for(int i = 0; i < tableros.size() && !encontrado; ++i)
            if (tableros.get(i).getTablero().equals(tablero)) {
                tableros.remove(i);
                encontrado = true;
            }
    }
    /* POST: - */
}