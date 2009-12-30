/*  Class ControladorUsuario:
    Descripcion: Gestiona toda la parte de cuentas de los usuarios. Es una clase
        singleton.
    Autor: daniel.camarasa
    Revisado: 20/11/2009 09:18 */

package Dominio;

import Excepciones.InstanceRepeatedException;
import Excepciones.PasswordIncorrectException;
import Datos.ControladorDatosUsuario;

public class ControladorUsuario {

    private final static ControladorDatosUsuario DATOS_USUARIO =
      ControladorDatosUsuario.getInstance();
    private final static ControladorUsuario INSTANCIA = new ControladorUsuario();
    private Usuario usuarioActual;

    /* PRE: - */
    private ControladorUsuario() {
        
        usuarioActual = null;
    }
    /* POST: Inicializa los datos de gestion de los usuarios y el usuario actual. */

    /* PRE: - */
    public static ControladorUsuario getInstance() {
        
        return INSTANCIA;
    }
    /* POST: Retorna la instancia de la clase */
    

    /* PRE: 'nickname' y 'password' unicamente contienen caracteres alfanumericos. */
    /* EXC: InstanceNotFoundException: El usuario con nickname = 'nickname' no existe. */
    /* EXC: PasswordIncorrectException: El password del usuario con nickname = 'nickname'
        no coincide con 'password'. */
    public void iniciarSesion(String nickname, String password) throws Exception {
        
        usuarioActual = DATOS_USUARIO.obtener(nickname);
        if (!password.equals(usuarioActual.obtenerPassword()))
            throw new PasswordIncorrectException(nickname);
    }
    /* POST: El usuario actual pasa a ser el identificado por 'nickname' con el password
        que tenia. */

    /* PRE: 'passwordActual' y 'passwordNuevo' unicamente contienen caracteres
        alfanumericos. usuarioActual != null. */
    public void modificarPassword(String passwordActual, String passwordNuevo)
      throws Exception {
        
        if (passwordActual.equals(usuarioActual.obtenerPassword())) {
            usuarioActual.modificarPassword(passwordNuevo);
            DATOS_USUARIO.actualizar(usuarioActual);
        }
        else throw new PasswordIncorrectException(usuarioActual.obtenerUsuario());
    }
    /* POST: El password del usuario actual pasa a valer 'passwordNuevo'. */

    /* PRE: 'nickname' y 'password' unicamente contienen caracteres alfanumericos. */
    /* EXC: InstanceRepeatedException: El usuario con nickname = 'nickname' ya existe. */
    public void crearUsuario(String nickname, String password)
      throws InstanceRepeatedException {
        
        DATOS_USUARIO.insertar(new Usuario(nickname, password));
    }
    /* POST: Se da de alta una nueva instancia de Usuario. */

    /* PRE: El usuario actual existe y es el que debe eliminarse. */
    public void eliminarUsuario() {

        DATOS_USUARIO.borrar(usuarioActual.obtenerUsuario());
        usuarioActual = null;
    }
    /* POST: Se elimina el usuario actual de la lista de usuarios. */
}
