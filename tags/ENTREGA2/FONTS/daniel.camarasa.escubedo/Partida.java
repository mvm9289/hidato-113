/*  Class Partida:
    Descripcion: STUB de la clase compartida Partida.
    Autor: daniel.camarasa
    Revisado: 20/11/2009 10:40 */

package Dominio;

public class Partida {

    /* PRE: - */
    public Partida(String idPartida, String idUsuario, String idTablero) {

        System.out.println("Llamada: Partida.Partida(" + idPartida + ", " + idUsuario +
          ", " + idTablero + ")");
    }
    /* POST: - */

    /* PRE: - */
    public Partida(Partida original, String idPartidaNueva) {

        System.out.println("Llamada: Partida.Partida(" + original.getIdPartida() +
          ", " + idPartidaNueva + ")");
    }
    /* POST: - */

    /* PRE: - */
    public String getIdPartida() {

        System.out.println("Llamada: Partida.getIdPartida()");

        return new String("Partida");
    }
    /* POST: - */

    /* PRE: - */
    public String getIdUsuario() {

        System.out.println("Llamada: Partida.getIdUsuario()");

        return new String("Usuario");
    }
    /* POST: - */

    /* PRE: - */
    public String getIdTablero() {

        System.out.println("Llamada: Partida.getIdUTablero()");

        return new String("Tablero");
    }
    /* POST: - */

    /* PRE: - */
    public int getTiempo() {

        System.out.println("Llamada: Partida.getTiempo()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public void setTiempo(int tiempo) {

        System.out.println("Llamada: Partida.setTiempo(" + String.valueOf(tiempo) + ")");
    }
    /* POST: - */

    /* PRE: - */
    public Boolean esTerminada() {

        System.out.println("Llamada: Partida.esTerminada()");

        return new Boolean(true);
    }
    /* POST: - */

    /* PRE: - */
    public void setTerminada() {

        System.out.println("Llamada: Partida.setTerminada()");
    }
    /* POST: - */
}