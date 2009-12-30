/*  Class Repositorio:
    Descripcion: STUB de la clase compartida Repositorio.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:17 */

package Dominio;

import java.util.ArrayList;
import java.util.List;



public class Repositorio {

    /* PRE: - */
    public Repositorio() {

        System.out.println("Llamada: Repositorio.Repositorio()");
    }
    /* POST: - */

    /* PRE: - */
    public List<String> getTableros() {

        System.out.println("Llamada: Repositorio.getTableros()");
        
        return new ArrayList<String>();
    }
    /* POST: - */

    /* PRE: - */
    public void addTablero(String tablero) {

        System.out.println("Llamada: Repositorio.addTablero(" + tablero + ")");
    }
    /* POST: - */
}
