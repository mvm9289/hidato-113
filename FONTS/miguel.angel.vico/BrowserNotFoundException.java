/*  Class BrowserNotFoundException:
    Descripcion: Excepcion para cuando no se encuentra ningun navegador instalado en
        la maquina.
    Autor: miguel.angel.vico
    Revisado: 14/11/2009 17:16 */

package Excepciones;

public class BrowserNotFoundException extends Exception {

    private final static String MENSAJE_ERROR = "No se ha encontrado ningun navegador.";

    /* PRE: - */
    public BrowserNotFoundException() {

        super(MENSAJE_ERROR);
    }
    /* POST: Crea una instancia de BrowserNotFoundException. */
}
