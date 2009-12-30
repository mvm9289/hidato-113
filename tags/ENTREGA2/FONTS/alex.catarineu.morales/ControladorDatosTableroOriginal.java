/*  Class ControladorDatosTableroOriginal:
    Descripcion: STUB del controlador de datos de TableroHidatoOriginal.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:16 */

package Datos;

import Dominio.TableroHidatoOriginal;
import java.util.ArrayList;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import java.util.List;

public class ControladorDatosTableroOriginal {

    private final static ControladorDatosTableroOriginal INSTANCIA =
      new ControladorDatosTableroOriginal();
    List<TableroHidatoOriginal> tableros;

    /* PRE: - */
    private ControladorDatosTableroOriginal() {

        tableros = new ArrayList<TableroHidatoOriginal>();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosTableroOriginal getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public TableroHidatoOriginal obtener(String tablero)
      throws InstanceNotFoundException {

        TableroHidatoOriginal tableroHidato;

        for(int i = 0; i < tableros.size(); ++i) {
            tableroHidato = tableros.get(i);
            if (tableroHidato.getTablero().equals(tablero)) return tableroHidato;
        }

        throw new InstanceNotFoundException(tablero);
    }
    /* POST: - */

    /* PRE: - */
    public List<TableroHidatoOriginal> obtenerTodos() {

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
    public void insertar(TableroHidatoOriginal tablero)
      throws InstanceRepeatedException{

        for(int i = 0; i < tableros.size(); ++i)
            if (tableros.get(i).getTablero().equals(tablero.getTablero()))
                throw new InstanceRepeatedException(tablero.getTablero());

        tableros.add(tablero);
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar(TableroHidatoOriginal tablero)
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