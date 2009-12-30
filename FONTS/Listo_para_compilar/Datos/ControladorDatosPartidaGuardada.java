/*  Class ControladorDatosPartidaGuardada:
    Descripcion: Gestor de disco para las instancias de la clase PartidaGuardada.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 00:16 */

package Datos;

import Dominio.Partida;
import Dominio.PartidaGuardada;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import Tiempo.Fecha;
import Tiempo.Hora;
import Utiles.Files;
import Utiles.Utiles;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ControladorDatosPartidaGuardada {

    private final static int ID_PARTIDA_SIZE = 20;
    private final static int ID_USUARIO_SIZE = 20;
    private final static int ID_TABLERO_SIZE = 36;
    private final static int TIEMPO_SIZE = 4;
    private final static int FINALIZADA_SIZE = 1;
    private final static int FECHA_SIZE = 12;
    private final static int HORA_SIZE = 12;
    private final static int FILA_SIZE = ID_PARTIDA_SIZE + ID_USUARIO_SIZE +
      ID_TABLERO_SIZE + TIEMPO_SIZE + FINALIZADA_SIZE + FECHA_SIZE + HORA_SIZE;

    private final static ControladorDatosPartidaGuardada INSTANCIA =
      new ControladorDatosPartidaGuardada();
    
    private RandomAccessFile file;

    /* PRE: - */
    private ControladorDatosPartidaGuardada() {

        try {
            file = Files.openFile(Files.getPath() + "/saves/", "partidas");
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    /* POST: Se crea la instancia del ControladorDatosPartidaGuardada */

    /* PRE: - */
    public static ControladorDatosPartidaGuardada getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorDatosPartidaGuardada */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Partida no encontrada */
    public PartidaGuardada obtener(String idPartida) throws Exception {

        int fila = searchFila(idPartida);
        
        if (fila == -1) throw new InstanceNotFoundException(idPartida);

        return getFila(fila);
    }
    /* POST: Retorna la instancia de PartidaGuardada identificada por 'idPartida'
        almacenada en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public List<PartidaGuardada> obtenerTodos() throws IOException {

        List<PartidaGuardada> partidas = new ArrayList<PartidaGuardada>();
        int numFilas = (int)(file.length() / FILA_SIZE);

        for (int i = 0; i < numFilas; ++i) partidas.add(getFila(i));

        return partidas;
    }
    /* POST: Retorna todas las instancias de PartidaGuardada almacenadas en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public boolean existe(String idPartida) throws IOException {

        return (searchFila(idPartida) != -1);
    }
    /* POST: Retorna cierto si existe la instancia de PartidaGuardada identificada
        por 'idPartida' en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceRepetedException': Partida ya existente */
    public void insertar(PartidaGuardada partida) throws Exception {

        String idPartida = partida.getIdPartida();
        int fila = searchFila(idPartida);

        if (fila != -1) throw new InstanceRepeatedException(idPartida);
        setFila((int)(file.length() / FILA_SIZE), partida);
    }
    /* POST: Inserta y hace persistente la instancia de PartidaGuardada 'partida' en el
        sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Partida no encontrada */
    public void actualizar(PartidaGuardada partida) throws Exception {

        String idPartida = partida.getIdPartida();
        int fila = searchFila(idPartida);

        if (fila == -1) throw new InstanceNotFoundException(idPartida);
        setFila(fila, partida);
    }
    /* POST: Hace persistentes los cambios en la instancia de PartidaGuardada
        'partida' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Partida no encontrada */
    public void borrar(String idPartida) throws Exception {

        int numFilas;
        int fila = searchFila(idPartida);

        if (fila == -1) throw new InstanceNotFoundException(idPartida);
        numFilas = (int)(file.length() / FILA_SIZE);
        for(int i = fila + 1; i < numFilas; ++i) setFila(i - 1, getFila(i));
        file.setLength(file.length() - FILA_SIZE);
    }
    /* POST: Borra del sistema la instancia de PartidaGuardada identificada por
        'idPartida' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private PartidaGuardada getFila(int fila) throws IOException {

        byte[] input;
        String idPartida;
        String idUsuario;
        String idTablero;
        int tiempo;
        boolean finalizada;
        Fecha fecha;
        Hora hora;
        Partida partida;

        file.seek(fila * FILA_SIZE);

        input = new byte[ID_PARTIDA_SIZE];
        file.read(input);
        idPartida = Utiles.byteArrayToString(input);

        input = new byte[ID_USUARIO_SIZE];
        file.read(input);
        idUsuario = Utiles.byteArrayToString(input);

        input = new byte[ID_TABLERO_SIZE];
        file.read(input);
        idTablero = Utiles.byteArrayToString(input);

        input = new byte[TIEMPO_SIZE];
        file.read(input);
        tiempo = Utiles.byteArrayToInt(input);

        input = new byte[FINALIZADA_SIZE];
        file.read(input);
        finalizada = Utiles.byteArrayToBoolean(input);

        input = new byte[FECHA_SIZE];
        file.read(input);
        fecha = Utiles.byteArrayToFecha(input);

        input = new byte[HORA_SIZE];
        file.read(input);
        hora = Utiles.byteArrayToHora(input);
        
        partida = new Partida(idPartida, idUsuario, idTablero);
        partida.setTiempo(tiempo);
        if (finalizada) partida.setTerminada();

        return new PartidaGuardada(idPartida, partida, fecha, hora);
    }
    /* POST: Retorna la instancia de PartidaGuardada que se encuentra en la fila 'fila'
        del fichero */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private void setFila(int fila, PartidaGuardada partida) throws IOException {

        byte[] output;

        file.seek(fila * FILA_SIZE);

        output = Utiles.stringToByteArray(partida.getIdPartida(), ID_PARTIDA_SIZE);
        file.write(output);

        output = Utiles.stringToByteArray(partida.getIdUsuario(), ID_USUARIO_SIZE);
        file.write(output);

        output = Utiles.stringToByteArray(partida.getIdTablero(), ID_TABLERO_SIZE);
        file.write(output);

        output = Utiles.intToByteArray(partida.getTiempo());
        file.write(output);

        output = Utiles.booleanToByteArray(partida.esTerminada().booleanValue());
        file.write(output);

        output = Utiles.fechaToByteArray(partida.obtenerFecha());
        file.write(output);
        
        output = Utiles.horaToByteArray(partida.obtenerHora());
        file.write(output);
    }
    /* POST: Inserta y hace persistente la instancia de PartidaGuardada 'partida' en
        la fila 'fila' del fichero */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private int searchFila(String idPartida) throws IOException {

        byte[] input = new byte[ID_PARTIDA_SIZE];
        int numFilas = (int)(file.length() / FILA_SIZE);

        for (int i = 0; i < numFilas; ++i) {
            file.seek(i * FILA_SIZE);
            file.read(input);
            if (Utiles.byteArrayToString(input).equals(idPartida)) return i;
        }

        return -1;
    }
    /* POST: Retorna la fila dentro del fichero donde se encuentra la instancia de
        PartidaGuardada identificada por 'idPartida' o -1 si no existe */
}
