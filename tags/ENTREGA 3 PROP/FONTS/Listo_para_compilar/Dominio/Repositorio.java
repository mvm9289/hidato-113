/*
    Class Repositorio
    Descripcion: Representa el conjunto de tableros solucion del sistema
    Autor: carlos.sans
    Revisado: 19/11/2009 17:00h
 */

package Dominio;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {

    private List<String> tableros;

    /* PRE: - */
    public Repositorio() {

        tableros = new ArrayList<String>();
    }
    /* POST: Se crea una instancia de Repositorio */

     /* PRE: - */
    public List<String> getTableros() {

        return tableros;
    }
    /* POST: Devuelve el conjunto de identificadores de todos los tableros solución del Repositorio */

    /* PRE: El tablero con identificador 'tablero' no existe en el Repositorio */
    public void addTablero(String tablero) {
        
        tableros.add(tablero);
    }
    /* POST: Añade el tablero con identificador 'tablero' al repositorio */
}