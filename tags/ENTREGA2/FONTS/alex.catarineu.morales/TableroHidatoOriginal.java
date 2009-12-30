/*  Class TableroHidatoOriginal:
    Descripcion: Representa una deficion de un tablero de Hidato.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:17 */

package Dominio;

public class TableroHidatoOriginal extends TableroHidato {
  
    private int dificultad;
    private int topologia;

    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente
        contiene caracteres alfanumericos. 'anchura' y 'altura' son valores mayores
        que 0. 'dificultad' es un valor del conjunto de constantes DIF_FACIL, DIF_MEDIO,
        DIF_DIFICIL, DIF_PERSONALIZADA. 'topologia' es un valor del conjunto de
        constantes TOP_RECTANGULO, TOP_TRIANGULO, TOP_ROMBO, TOP_ELIPSE,
        TOP_PERSONALIZADA. */
    public TableroHidatoOriginal(String id, int anchura, int altura, int dificultad,
      int topologia) {

        super(id, anchura, altura);
        this.dificultad = dificultad;
        this.topologia = topologia;
    }
    /* POST: Se crea una instancia de TableroHidatoPropuesto completamente
        inicializada. */

    /* PRE: - */
    public int obtenerDificultad() {

        return dificultad;
    }
    /* POST: Retorna el valor de la dificultad del tablero. */

    /* PRE: - */
    public int obtenerTopologia() {
        
        return topologia;
    }
    /* POST: Retorna el valor de la topologia del tablero. */
}
