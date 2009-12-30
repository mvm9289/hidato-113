/*  Class Ranking:
    Descripcion: Representa la lista de las mejores puntuaciones conseguidas en el juego.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 11:28 */

package Dominio;

import java.util.ArrayList;

public class Ranking {

    private ArrayList<Posicion> posiciones;

    /* PRE: - */
    public Ranking () {
        
        posiciones = new ArrayList<Posicion>();
    }
    /* POST: Se crea una instancia de ranking. */

    /* PRE: - */
    public ArrayList<Posicion> obtenerPosiciones() {
        
        return posiciones;
    }
    /* POST: Devuelve el conjunto de posiciones del ranking en el sistema. */

    /* PRE: - */
    public int insertarPosicion(Posicion nuevaPosicion) {
        
        int i;
        
        for (i = 0; i < posiciones.size(); ++i) {
            /* insertamos si nuevaPosicion tiene mas puntuacion que la que estamos
                mirando o si tiene la misma pero el nombre de jugador es anterior
                lexicograficamente */
            if (nuevaPosicion.getPuntuacion() > posiciones.get(i).getPuntuacion() ||
              (nuevaPosicion.getPuntuacion() == posiciones.get(i).getPuntuacion() &&
              nuevaPosicion.getNjugador().compareTo(posiciones.get(i).getNjugador()) < 0))
              {
                posiciones.add(i, nuevaPosicion);
                
                return i + 1;
            }
        }
        posiciones.add(nuevaPosicion);
        
        return posiciones.size();
    }
    /* POST: Se inserta la nueva posicion en el conjunto de posiciones del ranking y
        se devuelve la posicion en la lista (indice + 1). */

    /* PRE: - */
    public void eliminaPosiciones(String usuario) {
        
        for (int i = posiciones.size() - 1; i >= 0; --i)
            if (usuario.equals(posiciones.get(i).getNjugador()))
                posiciones.remove(i);
    }
    /* POST: Se eliminan las posiciones cuyo usuario = 'usuario' */
}
