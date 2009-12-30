/*  Class PartidaGuardada:
    Descripcion: Implementa una partida guardada con la fecha y hora del momento en que
        se guardo.
    Autor: daniel.camarasa
    Revisado: 20/11/2009 09:17 */

package Dominio;

import Tiempo.Fecha;
import Tiempo.Hora;

public class PartidaGuardada extends Partida {
    
    private Fecha fecha;
    private Hora hora;

    /* PRE: No existe una partida guardada con id = 'id'. 'id' unicamente contiene
        caracteres alfanumericos. 'partida' se corresponde a la partida actual a guardar.
        La fecha 'fecha' y hora 'hora' son temporalmente correctas. */
    public PartidaGuardada(String id, Partida partida, Fecha fecha, Hora hora) {

        super(partida, id);
        this.fecha = fecha;
        this.hora = hora;
    }
    /* POST: Crea una instancia de PartidaGuardada a partir de 'partida' con id = 'id',
        fecha = 'fecha' y hora = 'hora'. */

    /* PRE: - */
    public Fecha obtenerFecha() {
        
        return fecha;
    }
    /* POST: Retorna la fecha de la partida guardada. */

    /* PRE: La fecha 'fecha' es temporalmente correcta. */
    public void introducirFecha(Fecha fecha) {

        this.fecha = fecha;
    }
    /* POST: La fecha pasa a ser 'fecha'. */

    /* PRE: - */
    public Hora obtenerHora() {

        return hora;
    }
    /* POST: Retorna la hora de la partida guardada. */

    /* PRE: La hora 'h' es temporalmente correcta. */
    public void introducirHora(Hora hora) {

        this.hora = hora;
    }
    /* POST: La hora pasa a ser 'hora'. */
}
