/*  Class UnrecognizedOSException:
    Descripcion: Excepcion para cuando no se reconoce el Sistema Operativo de la
        maquina.
    Autor: miguel.angel.vico
    Revisado: 14/11/2009 17:17 */

package Excepciones;

public class UnrecognizedOSException extends Exception {

    private final static String MENSAJE_ERROR = "No se reconoce el Sistema Operativo.";

    /* PRE: - */
    public UnrecognizedOSException() {

        super(MENSAJE_ERROR);
    }
    /* POST: Crea una instancia de UnrecognizedOSException. */
}