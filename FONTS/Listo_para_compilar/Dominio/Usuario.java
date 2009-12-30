/*  Class Usuario:
    Descripcion: La clase 'Usuario' representa cada una de las cuentas de usuario
        de la aplicacion, de manera que cada usuario debera tener una cuenta en el
        sistema para poder utilizar la aplicacion y/o acceder a zonas exclusivas
        de cada usuario.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 11:22 */

package Dominio;

public class Usuario {

    private String nickname;
    private String password;

    /* PRE: No existe ninguna instancia de Usuario con nickname = 'nickname'.
        'password' y 'nickname' unicamente contienen caracteres alfanumericos. */
    public Usuario(String nickname, String password) {
        
        this.nickname = nickname;
        this.password = password;
    }
    /* POST: Se crea una nueva instancia de Usuario en el sistema con nickname =
        'nickname' y password = 'password'. */

    /* PRE: 'nuevoPassword' unicamente contiene caracteres alfanumericos. */
    public void modificarPassword(String nuevoPassword) {
        
        this.password = nuevoPassword;
    }
    /* POST: El atributo password pasa a valer 'nuevoPassword'. */

    /* PRE: - */
    public String obtenerUsuario() {

        return this.nickname;
    }
    /* POST: Retorna el atributo nickname. */

    /* PRE: - */
    public String obtenerPassword() {
        
        return this.password;
    }
    /* POST: Retorna el atributo password. */
}
