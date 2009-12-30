/*  Class Posicion:
    Descripcion: STUB de la clase compartida Posicion.
    Autor: miguel.angel.vico
    Revisado: 20/11/2009 09:17 */

package Dominio;

import Tiempo.Fecha;

public class Posicion {

    /* PRE: - */
    public Posicion(int puntuacion, String nombreUsuario, Fecha fecha) {

        System.out.println("Llamada: Posicion.Posicion(" + String.valueOf(puntuacion) +
          ", " + nombreUsuario + ", " + fecha.obtenerFechaFormal() + ")");
    }
    /* POST: - */

    /* PRE: - */
    public String getNjugador() {

        System.out.println("Llamada: Posicion.getNjugador()");

        return new String("string");
    }
    /* POST: - */

    /* PRE: - */
    public int getPuntuacion() {

        System.out.println("Llamada: Posicion.getPuntuacion()");

        return 1;
    }
    /* POST: - */

    /* PRE: - */
    public Fecha getFecha() {

        System.out.println("Llamada: Posicion.getFecha()");

        return new Fecha();
    }
    /* POST: - */

    /* PRE: - */
    public void modificarPuntuacion(int nuevaPuntuacion) {

        System.out.println("Llamada: Posicion.getFecha(" +
          String.valueOf(nuevaPuntuacion) + ")");
    }
    /* Post: - */

    /* PRE: - */
    public void modificarFecha(Fecha nuevaFecha) {

        System.out.println("Llamada: Posicion.getFecha(" +
          nuevaFecha.obtenerFechaFormal() + ")");
    }
    /* POST: - */
}
