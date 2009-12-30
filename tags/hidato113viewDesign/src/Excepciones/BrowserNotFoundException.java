/*  Class BrowserNotFoundException:
    Descripcion: Excepcion para cuando no se encuentra ningun navegador instalado en
        la maquina.
    Autor: miguel.angel.vico
    Revisado: 04/11/2009 18:44 */

package Excepciones;

public class BrowserNotFoundException extends Exception {

    private final static String MENSAJE_ERROR = "No se ha encontrado ningun navegador.";

    public BrowserNotFoundException() {

        super(MENSAJE_ERROR);
    }
}
