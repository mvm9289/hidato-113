/*  Class CasillaHidato:
    Descripcion: Representa una casilla del tablero de Hidato con su contenido y estado
        (prefijada/activa).
    Autor: alex.catarineu
    Revisado:  20/12/2009 11:33 */

package Dominio;

public class CasillaHidato extends Casilla {

    private boolean activa;

    /* PRE: x >= 0. y >= 0. */
    public CasillaHidato(int x, int y) {
        
        super(x, y);
        activa = true;
    }
    /* POST: Crea una instancia de CasillaHidato activa con x = 'x' e y = 'y'. */

    /* PRE: - */
    public void setActiva(boolean activa) {
        
        this.activa = activa;
    }
    /* POST: Marca la casilla como activa o no segun el valor de 'activa'. */

    /* PRE: - */
    public boolean esActiva() {
        
        return activa;
    }
    /* POST: Retorna true si la casilla esta activa. */
}
