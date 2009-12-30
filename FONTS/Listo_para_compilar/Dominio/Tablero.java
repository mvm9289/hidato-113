/*  Class Tablero
    Descripcion: La clase 'Tablero' representa el conjunto estructurado de casillas asi
        como sus caracteristicas (altura y anchura), pero no su contenido.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:17 */

package Dominio;

public abstract class Tablero {

    private String id;
    private int anchura;
    private int altura;

    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente contiene
        caracteres alfanumericos. 'anchura' y 'altura' son valores mayores que 0. */
    public Tablero(String id, int anchura, int altura) {
        
        this.id = id;
        this.anchura = anchura;
        this.altura = altura;
    }
    /* POST: Se crea una instancia de Tablero con id = 'id', anchura = 'anchura' y
        altura = 'altura' */

    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente contiene
        caracteres alfanumericos. 'tablero' existe y tiene correctamente definidos los
        atributos altura y anchura. */
    public Tablero(String id, Tablero tablero) {
        
        this.id = id;
        this.anchura = tablero.anchura;
        this.altura = tablero.altura;
    }
    /* POST: Se crea una instancia de Tablero con id = 'id' y el resto de atributos como
        copia de los atributos de 'tablero'. */

    /* PRE: - */
    public String getTablero() {

        return id;
    }
    /* POST: Retorna el atributo id. */

    /* PRE: - */
    public int getAnchura() {

        return anchura;
    }
    /* POST: Retorna el atributo anchura. */

    /* PRE: - */
    public int getAltura() {
        
        return altura;
    }
    /* POST: Retorna el atributo altura. */

    /* PRE: - */
    public abstract void resetearTablero();
    /* POST: Se reconfigura el tablero con el estado que tenía al inicio de la partida. */
}
