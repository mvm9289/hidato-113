/*  Class Hora:
    Descripcion: La clase "Hora" implementa una representacion de una hora mediante
        la hora, el minuto y el segundo exactos. Nos permite obtener la hora actual o
        seleccionar una deseada.
    Autor: miguel.angel.vico
    Revisado: 15/11/2009 22:21  */

package Tiempo;

import java.util.Calendar;

public class Hora {

    private int hora;
    private int minuto;
    private int segundo;

    /* PRE: - */
    public Hora() {

        hora = 0;
        minuto = 0;
        segundo = 0;
    }
    /* POST: Se crea una nueva instancia de Hora con todos sus atributos inicializados
        a 0. */

    /* PRE: - */
    public void ahora() {

        hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        minuto = Calendar.getInstance().get(Calendar.MINUTE);
        segundo = Calendar.getInstance().get(Calendar.SECOND);
    }
    /* POST: Todos los atributos de Hora quedan modificados de tal manera que su valor
        se corresponde con la hora actual. */

    /* PRE: 'hora', 'minuto' y 'segundo' representan una hora valida, es decir, 'hora'
        esta entre 0 y 23, 'minuto' entre 0 y 59, y 'segundo' entre 0 y 59. */
    public void ponerHora(int hora, int minuto, int segundo) {

        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }
    /* POST: Los atributos hora, minuto y segundo quedan modificados con el valor de
        'hora', 'minuto' y 'segundo' respectivamente. */

    /* PRE: - */
    public String obtenerHoraFormal() {

        String horaStr = String.valueOf(hora);
        String minutoStr = String.valueOf(minuto);
        String segundoStr = String.valueOf(segundo);

        if(hora < 10) horaStr = "0" + horaStr;
        if(minuto < 10) minutoStr = "0" + minutoStr;
        if(segundo < 10) segundoStr = "0" + segundoStr;
        
        return horaStr + ":" + minutoStr + ":" + segundoStr;
    }
    /* POST: Retorna un string en formato "HH:MM:SS" donde HH es el valor del atributo
        hora, MM es el valor del atributo minuto y SS es el valor del atributo segundo,
        convertidos a string. */

    /* PRE: - */
    public int obtenerHora() {
        
        return hora;
    }
    /* POST: Retorna el atributo hora. */

    /* PRE: - */
    public int obtenerMinuto() {

        return minuto;
    }
    /* POST: Retorna el atributo minuto. */

    /* PRE: - */
    public int obtenerSegundo() {

        return segundo;
    }
    /* POST: Retorna el atributo segundo. */
}
