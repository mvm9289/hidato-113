/*  Class UnrecognizedOSException:
    Descripcion: Excepcion para cuando no se reconoce el Sistema Operativo de la
        maquina.
    Autor: miguel.angel.vico
    Revisado: 04/11/2009 18:44 */

package Excepciones;

public class UnrecognizedOSException extends Exception {

    private final static String MENSAJE_ERROR = "No se reconoce el Sistema Operativo.";

    public UnrecognizedOSException() {

        super(MENSAJE_ERROR);
    }
}