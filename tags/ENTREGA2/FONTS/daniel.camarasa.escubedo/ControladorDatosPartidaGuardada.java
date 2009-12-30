/*  Class ControladorDatosPartidaGuardada:
    Descripcion: STUB del controlador de datos de PartidaGuardada.
    Autor: daniel.camarasa
    Revisado: 20/11/2009 09:16 */

package Datos;

import Dominio.PartidaGuardada;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import java.util.ArrayList;

public class ControladorDatosPartidaGuardada {

    private final static ControladorDatosPartidaGuardada INSTANCIA =
      new ControladorDatosPartidaGuardada();
    ArrayList<PartidaGuardada> partidas;

    /* PRE: - */
    private ControladorDatosPartidaGuardada() {

        partidas = new ArrayList<PartidaGuardada>();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosPartidaGuardada getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public PartidaGuardada obtener(String id) throws InstanceNotFoundException {

        PartidaGuardada partida;

        for(int i = 0; i < partidas.size(); ++i) {
            partida = partidas.get(i);
            if (partida.getIdPartida().equals(id)) return partida;
        }

        throw new InstanceNotFoundException(id);
    }
    /* POST: - */

    /* PRE: - */
    public ArrayList<PartidaGuardada> obtenerTodos() {

        return partidas;
    }
    /* POST: - */

    /* PRE: - */
    public boolean existe(String id) {

        for(int i = 0; i < partidas.size(); ++i)
            if (partidas.get(i).getIdPartida().equals(id)) return true;

        return false;
    }
    /* POST: - */

    /* PRE: - */
    public void insertar(PartidaGuardada partida) throws InstanceRepeatedException {

        for(int i = 0; i < partidas.size(); ++i)
            if (partidas.get(i).getIdPartida().equals(partida.getIdPartida()))
                throw new InstanceRepeatedException(partida.getIdPartida());

        partidas.add(partida);
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar(PartidaGuardada partida) throws InstanceNotFoundException {

        /*boolean encontrado = false;

        for(int i = 0; i < usuarios.size() && !encontrado; ++i)
            if (usuarios.get(i).obtenerUsuario().equals(usuario.obtenerUsuario())) {
                usuarios.set(i, usuario);
                encontrado = true;
            }

        if(!encontrado) throw new InstanceNotFoundException(usuario.obtenerUsuario());*/
    }
    /* POST: - */

    /* PRE: - */
    public void borrar(String id) {

        boolean encontrado = false;

        for(int i = 0; i < partidas.size() && !encontrado; ++i)
            if (partidas.get(i).getIdPartida().equals(id)) {
                partidas.remove(i);
                encontrado = true;
            }
    }
    /* POST: - */
}
