/*  Class InstanceNotFoundException:
    Descripcion: Al buscar una instancia con un identificador, esta no existe.
    Autor: daniel.camarasa
    Revisado: 18/11/2009 15:14 */

package Excepciones;

public class InstanceNotFoundException extends Exception {

    private final static String MENSAJE_ERROR =
      "No existe una instancia con el identificador ";

    /* PRE: 'identificador' unicamente contiene caracteres alfanumericos. */
    public InstanceNotFoundException(String identificador) {
        
        super(MENSAJE_ERROR + identificador);
    }
    /* POST: Crea una instancia de InstanceNotFoundException. */
}
