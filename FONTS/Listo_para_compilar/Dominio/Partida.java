/*
    Class Partida:
    Descripción: Gestiona usuarios y tableros ya generados, las partidas quedan
        unívocamente identificadas por su identificador. Una partida solo será
        finalizada, si se ha explicitado su fin.
    Autor: 11.1(guillermo.muniz)
    Revisado: 14/11/2009 21:30
*/

package Dominio;

public class Partida {

    private String idPartida;
    private String idUsuario;
    private String idTablero; //Usado por Sudoku e Hidato
    private int tiempo;
    private Boolean finalizada;
    
    public Partida(){}; //Necesaria para que javac deje hacer herencias

    /* PRE: 'idPartida' tiene que ser un identificador de Partida nuevo, no usado
    anteriormente. Tanto 'idUsuario' como 'idTablero' tienen que ser ids válidos,
    es decir, todos hacen referencia a objetos existentes. Además los ids sólo
    pueden contener caracteres alfanuméricos. */
    public Partida(String idPartida, String idUsuario, String idTablero) {

        this.idPartida = idPartida;
        this.idUsuario = idUsuario;
        this.idTablero = idTablero;
        finalizada = false;
    }
    /* POST: Se ha creado un objeto de la clase Partida con idPartida = 'idPartida',
    idUsuario = 'idUsuario' e idTablero = 'idTablero'. */

    /* PRE: Tiene que existir 'original' e 'idPartidaNueva' tiene que ser un
    identificador de partida nuevo, no usado anteriormente. 'idPartidaNueva'
    sólo contiene caracteres alfanuméricos. */
    public Partida(Partida original, String idPartidaNueva) {

        idPartida = idPartidaNueva;
        idUsuario = original.getIdUsuario();
        idTablero = original.getIdTablero();
        tiempo = original.getTiempo();
        finalizada = original.esTerminada();
    }
    /* POST: Se ha creado un nuevo objeto de la clase Partida teniendo los
    mismo atributos que 'original', excepto el identificador de la partida, que es
    el indicado por 'idPartidaNueva'. */

    /* PRE: - */
    public String getIdPartida() {

        return idPartida;
    }
    /* POST: Se ha retornado el identificador de partida. */

    /* PRE: - */
    public String getIdUsuario() {

        return idUsuario;
    }
    /* POST: Se ha retornado el identificador del usuario de la partida. */

    /* PRE: - */
    public String getIdTablero() {

        return idTablero;
    }
    /* POST: Se ha retornado el identificador del tablero en el que se juega la
    partida. */

    /* PRE: - */
    public int getTiempo() {

        return tiempo;
    }
    /* POST: Retorna el atributo tiempo. */

    /* PRE: - */
    public Boolean esTerminada() {

        return finalizada;
    }
    /* POST: Retorna el atributo finalizada. */

    /* PRE: - */
    public void setTiempo(int x) {

        tiempo = x;
    }
    /* POST: El atributo tiempo pasa a valer 'tiempo'. */

    /* PRE: - */
    public void setTerminada() {

        finalizada = true;
    }
    /* POST: Se ha marcado la partida como terminada. Si ya había sido
    marcada como terminada no tiene ningún efecto. */
}
