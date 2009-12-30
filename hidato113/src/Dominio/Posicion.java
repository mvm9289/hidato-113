/*  Class Posicion:
    Descripcion: Implementa una posicion con la informacion nombre del
      jugador, su puntuacion y la fecha en la que consiguio dicha puntuacion.
      Ademas permite modificar la puntuacion y la fecha de esta.
    Autor: david.castano.gonzalez
    Revisado: 14/11/2009 21:30*/


package Dominio;

import Tiempo.Fecha;

public class Posicion {

    private String nJugador;
    private int puntuacion;
    private Fecha fecha;

    /*{PRE: puntuacion>=0, nombreUsuario solo utiliza caracteres alfanuerico}*/
    public Posicion (int puntuacion, String nombreUsuario, Fecha fecha){

        this.puntuacion = puntuacion;
        this.nJugador = nombreUsuario;
        this.fecha = fecha;
    }
    /*{POST: crea una instancia de Posicion inicializada a los datos de su record}*/

    /*{PRE: -}*/
    public  String getNjugador(){

        return nJugador;
    }
    /*{POST:  Retorna el atributo nJugador}*/

    /*{PRE: -} */
    public int getPuntuacion() {

        return puntuacion;
    }
    /*{POST: Retorna el atributo puntuacion}*/

    /*{PRE: -} */
    public  Fecha  getFecha(){
        
        return fecha;
    }
    /*{POST:  Retorna el atributo fecha}*/

}
