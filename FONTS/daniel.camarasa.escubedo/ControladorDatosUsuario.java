/*  Class ControladorDatosUsuario:
    Descripcion: STUB del controlador de datos de Usuario.
    Autor: daniel.camarasa
    Revisado: 20/11/2009 09:16 */

package Datos;

import Dominio.Usuario;
import java.util.ArrayList;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import java.util.List;

public class ControladorDatosUsuario {

    private final static ControladorDatosUsuario INSTANCIA =
      new ControladorDatosUsuario();
    List<Usuario> usuarios;

    /* PRE: - */
    private ControladorDatosUsuario() {

        usuarios = new ArrayList<Usuario>();
    }
    /* POST: - */

    /* PRE: - */
    public static ControladorDatosUsuario getInstance() {

        return INSTANCIA;
    }
    /* POST: - */

    /* PRE: - */
    public Usuario obtener(String nickname) throws InstanceNotFoundException {

        Usuario usuario;

        for(int i = 0; i < usuarios.size(); ++i) {
            usuario = usuarios.get(i);
            if (usuario.obtenerUsuario().equals(nickname)) return usuario;
        }

        throw new InstanceNotFoundException(nickname);
    }
    /* POST: - */

    /* PRE: - */
    public List<Usuario> obtenerTodos() {

        return usuarios;
    }
    /* POST: - */

    /* PRE: - */
    public boolean existe(String nickname) {

        for(int i = 0; i < usuarios.size(); ++i)
            if (usuarios.get(i).obtenerUsuario().equals(nickname)) return true;

        return false;
    }
    /* POST: - */

    /* PRE: - */
    public void insertar(Usuario usuario) throws InstanceRepeatedException {

        for(int i = 0; i < usuarios.size(); ++i)
            if (usuarios.get(i).obtenerUsuario().equals(usuario.obtenerUsuario()))
                throw new InstanceRepeatedException(usuario.obtenerUsuario());

        usuarios.add(usuario);
    }
    /* POST: - */

    /* PRE: - */
    public void actualizar(Usuario usuario) throws InstanceNotFoundException {

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
    public void borrar(String nickname) {

        boolean encontrado = false;

        for(int i = 0; i < usuarios.size() && !encontrado; ++i)
            if (usuarios.get(i).obtenerUsuario().equals(nickname)) {
                usuarios.remove(i);
                encontrado = true;
            }
    }
    /* POST: - */
}