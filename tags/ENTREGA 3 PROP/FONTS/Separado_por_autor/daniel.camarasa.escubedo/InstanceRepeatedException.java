/*  Class InstanceRepeatedException:
    Descripcion: Al crear una instancia de una clase, el identificador de dicha
        instancia ya existe.
    Autor: daniel.camarasa
    Revisado: 18/11/2009 15:13 */

package Excepciones;

public class InstanceRepeatedException extends Exception {

    private final static String MENSAJE_ERROR = 
            "Ya existe una instancia con el identificador ";

    /* PRE: 'identificador' unicamente contiene caracteres alfanumericos. */
    public InstanceRepeatedException(String identificador) {
        
        super(MENSAJE_ERROR+identificador);
    }
    /* POST: Crea una instancia de InstanceRepeatedException. */
}