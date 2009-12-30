/*  Class PasswordIncorrectException:
    Descripcion: Excepcion para cuando se introduce un passoword de usuario erroneo.
    Autor: daniel.camarasa
    Revisado: 18/11/2009 15:16 */

package Excepciones;

public class PasswordIncorrectException extends Exception {

    /* PRE: 'nickname' unicamente contiene caracteres alfanumericos. */
    public PasswordIncorrectException(String nickname) {

        super("El password de " + nickname + " no coincide con el de su cuenta");
    }
    /* POST: Crea una instancia de PasswordIncorrectException. */
}
