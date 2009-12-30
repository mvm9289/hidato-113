/*  Class Casilla:
    Descripcion: Representa una casilla del juego, ubicada en unas coordenadas
    concretas de un tablero, ocupada por un valor y que puede estar prefijada
    al generar el tablero.
    Autor: jonathan.florido
    Revisado: 04/11/2009 */

package Dominio;

public class Casilla {

    private int x;
    private int y;
    private int valor;
    private Boolean prefijada;

    /* PRE: x >= 0 , y >= 0, no existe una casilla con los mismo 'x' e 'y' en
    el tablero y/o bloque al que pertenece la casilla */
    public Casilla(int x, int y) {
        
        valor = 0;
        prefijada = false;
        this.x = x;  /*Bea: añado la asignacion de x e y */
        this.y = y;
    }
    /* POST: Crea una nueva instancia de casilla con x = 'x' e y = 'y' */

    /* PRE: - */
    public int getX() {

        return x;
    }
    /* POST: retorna la coordenada x de la casilla */

    /* PRE: - */
    public int getY() {

        return y;
    }
    /* POST: retorna la coordenada y de la casilla */

    /* PRE: - */
    public int getValor() {

        return valor;
    }
    /* POST: retorna el valor de la casilla; si no está ocupada retorna 0 */

    /* PRE: - */
    public Boolean esPrefijada() {

        return prefijada;
    }
    /* POST: retorna el valor del atributo prefijada */

    /* PRE: valor es un número natural mayor o igual que 0 */
    public void setValor(int valor) {

        this.valor = valor;
    }
    /* POST: asigna al atributo valor de la casilla el parámetro de entrada */

    /* PRE: - */
    public void setPrefijada(Boolean prefijada) {

        this.prefijada = prefijada;
    }
    /* POST: asigna al atributo prefijada de la casilla el parámetro de entrada */

}