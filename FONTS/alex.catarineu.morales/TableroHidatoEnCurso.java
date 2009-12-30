/*  Class TableroHidatoEnCurso:
    Descripcion: Representa un tablero de Hidato sobre el cual juega un usuario.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:17 */

package Dominio;

public class TableroHidatoEnCurso extends TableroHidato {

    private String tableroOriginal;
    private int numeroPistas;

    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente contiene
        caracteres alfanumericos. 'tablero' es un tableroHidatoOriginal perfectamente
        inicializado.  */
    public TableroHidatoEnCurso(String id, TableroHidatoOriginal tablero) {

        super(id, tablero);
        resetearTablero();
        tableroOriginal = tablero.getTablero();
        numeroPistas = 0;
    }
    /* POST: Se crea una instancia de TableroHidatoEnCurso a partir de 'id' y 'tablero'. */

    /* PRE: - */
    public String obtenerTableroOriginal() {
        
        return tableroOriginal;
    }
    /* POST: Retorna el identificador del tablero original al que se corresponde la
        instancia de TableroHidatoEnCurso. */

    /* PRE: - */
    public int obtenerPistas() {

        return numeroPistas;
    }
    /* POST: Retorna el numero de pistas que se han utilizado para resolver el tablero. */

    /* PRE: - */
    public void incrementarPistas() {

        ++numeroPistas;
    }
    /* POST: Se incrementa en 1 el numero de pistas utilizadas. */
}
