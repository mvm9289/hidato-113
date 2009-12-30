/*  Class TableroHidatoEnCurso:
    Descripcion: Representa un tablero de Hidato sobre el cual juega un usuario.
    Autor: alex.catarineu
    Revisado: 20/12/2009 11:22 */

package Dominio;

public class TableroHidatoEnCurso extends TableroHidato {

    private String tableroOriginal;
    private int numeroPistas;

    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente contiene
        caracteres alfanumericos.  'anchura' y 'altura' son valores mayores que 0.
        'tableroOriginal' corresponde al id de una instancia de TableroHidatoOriginal. */
    public TableroHidatoEnCurso(String id, int anchura, int altura,
      String tableroOriginal) {

        super(id, anchura, altura);
        this.tableroOriginal = tableroOriginal;
        this.numeroPistas = 0;
    }
    /* POST: Se crea una instancia de TableroHidatoEnCurso a partir de 'id', 'anchura',
        'altura' y 'tableroOriginal'. */

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
    public void incrementarPistas(int inc) {

        numeroPistas += inc;
    }
    /* POST: Se incrementa en 'inc' el numero de pistas utilizadas. */
}
