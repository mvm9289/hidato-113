/*  Class ControladorUsuario:
    Descripcion: Gestiona toda la parte de cuentas de los usuarios. Es una clase
        singleton.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 11:40 */

package Dominio;

import Excepciones.PasswordIncorrectException;
import Datos.ControladorDatosUsuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': El usuario con nickname = 'nickname' no existe. */
    /* EXC 'PasswordIncorrectException': El password del usuario con nickname = 'nickname'
        no coincide con 'password'. */
    /* EXC 'NoSuchAlgorithmException': La JVM no tiene los algoritmos criptograficos
        necesarios. */
    public void iniciarSesion(String nickname, String password) throws Exception {
        
        usuarioActual = DATOS_USUARIO.obtener(nickname);
        if (!hash(password).equals(usuarioActual.obtenerPassword()))
            throw new PasswordIncorrectException(nickname);
    }
    /* POST: El usuario actual pasa a ser el identificado por 'nickname' con el password
        que tenia. */

    /* PRE: 'passwordActual' y 'passwordNuevo' unicamente contienen caracteres
        alfanumericos. usuarioActual != null. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'NoSuchAlgorithmException': La JVM no tiene los algoritmos criptograficos
        necesarios. */
    public void modificarPassword(String passwordActual, String passwordNuevo)
      throws Exception {
        
        if (hash(passwordActual).equals(usuarioActual.obtenerPassword())) {
            usuarioActual.modificarPassword(hash(passwordNuevo));
            DATOS_USUARIO.actualizar(usuarioActual);
        }
        else throw new PasswordIncorrectException(usuarioActual.obtenerUsuario());
    }
    /* POST: El password del usuario actual pasa a valer 'passwordNuevo'. */

    /* PRE: 'nickname' y 'password' unicamente contienen caracteres alfanumericos. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceRepeatedException': El usuario con nickname = 'nickname' ya existe. */
    /* EXC 'NoSuchAlgorithmException': La JVM no tiene los algoritmos criptograficos
        necesarios. */
    public void crearUsuario(String nickname, String password)
      throws Exception {
        
        DATOS_USUARIO.insertar(new Usuario(nickname, hash(password)));
    }
    /* POST: Se da de alta una nueva instancia de Usuario. */

    /* PRE: El usuario actual existe y es el que debe eliminarse. */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': El usuario con nickname = 'nickname' no existe. */
    public void eliminarUsuario() throws Exception {

        DATOS_USUARIO.borrar(usuarioActual.obtenerUsuario());
        usuarioActual = null;
    }
    /* POST: Se elimina el usuario actual de la lista de usuarios. */

    /* PRE: - */
    /* EXC 'NoSuchAlgorithmException': La JVM no tiene los algoritmos criptograficos
        necesarios. */
    private String hash(String string) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] byteArray = md5.digest(string.getBytes());
        StringBuffer buffer = new StringBuffer(byteArray.length);

        for (int i = 0; i < byteArray.length; ++i) {
            int intAux = byteArray[i] & 255;
            if (intAux < 16) buffer.append("0" + Integer.toHexString(intAux));
            else buffer.append(Integer.toHexString(intAux));
        }
        
        return buffer.toString();
    }
    /* POST: Retorna el String correspondiente al hash criptografico md5 de 'string'. */
}
