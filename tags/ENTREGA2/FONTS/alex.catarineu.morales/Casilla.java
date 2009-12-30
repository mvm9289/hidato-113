/*  Class Casilla:
    Descripcion: STUB de la clase compartida Casilla.
    Autor: alex.catarineu
    Revisado: 20/11/2009 09:19 */

package Dominio;

public class Casilla {

    /* PRE: - */
    public Casilla(int x, int y) {

        System.out.println("Llamada: Casilla.Casilla(" + String.valueOf(x) + ", " +
          String.valueOf(y) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public int getX() {

        System.out.println("Llamada: Casilla.getX()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public int getY() {

        System.out.println("Llamada: Casilla.getY()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public int getValor() {

        System.out.println("Llamada: Casilla.getValor()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public Boolean esPrefijada() {

        System.out.println("Llamada: Casilla.esPrefijada()");

        return true;
    }
    /* POST: - */

    /* PRE: - */
    public void setValor(int valor) {

        System.out.println("Llamada: Casilla.setValor(" + String.valueOf(valor) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public void setPrefijada(Boolean prefijada) {

        System.out.println("Llamada: Casilla.setPrefijada(" + prefijada.toString() + ")");
    }
    /* POST: - */
}
