/*  Class TableroHidatoPropuesto:
    Descripcion: Representa un tablero de Hidato propuesto por un usuario.
    Autor: miguel.angel.vico
    Revisado: 20/11/2009 09:17 */

package Dominio;

public class TableroHidatoPropuesto extends TableroHidatoOriginal {

    private String usuario;

    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente
        contiene caracteres alfanumericos. 'anchura' y 'altura' son valores mayores
        que 0. 'dificultad' es un valor del conjunto de constantes DIF_FACIL, DIF_MEDIO,
        DIF_DIFICIL, DIF_PERSONALIZADA. 'topologia' es un valor del conjunto de
        constantes TOP_RECTANGULO, TOP_TRIANGULO, TOP_ROMBO, TOP_ELIPSE,
        TOP_PERSONALIZADA. 'usuario' corresponde a un identificador de una instancia
        existente de Usuario. */
    public TableroHidatoPropuesto(String id, int anchura, int altura,
      int dificultad, int topologia, String usuario) {

        super(id, anchura, altura, dificultad, topologia);
        this.usuario = usuario;
    }
    /* POST: Se crea una instancia de TableroHidatoPropuesto completamente
        inicializada. */

    /* PRE: - */
    public String obtenerUsuario() {

        return usuario;
    }
    /* POST: Retorna el identificador del usuario que propuso el tablero. */
}
