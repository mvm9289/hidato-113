/*  Class ControladorDatosTableroPropuesto:
    Descripcion: Gestor de disco para las instancias de la clase TableroHidatoPropuesto.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 00:17 */

package Datos;

import Dominio.CasillaHidato;
import Dominio.TableroHidatoPropuesto;
import java.util.ArrayList;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import java.io.RandomAccessFile;
import java.util.List;
import Utiles.Files;
import Utiles.Utiles;
import java.io.IOException;

public class ControladorDatosTableroPropuesto {

    private final static int ID_TABLERO_SIZE = 20;
    private final static int ID_USUARIO_SIZE = 20;
    private final static int INT_SIZE = 4;
    private final static int CASILLA_SIZE = 3 * INT_SIZE + 2;
    private final static int FILA_SIZE_INDEX = ID_TABLERO_SIZE;
    private final static int CASILLAS_INDEX = ID_TABLERO_SIZE + 6 * INT_SIZE +
      ID_USUARIO_SIZE;

    private final static ControladorDatosTableroPropuesto INSTANCIA =
      new ControladorDatosTableroPropuesto();
    
    private RandomAccessFile file;

    /* PRE: - */
    private ControladorDatosTableroPropuesto() {

        try {
            file = Files.openFile(Files.getPath() + "/saves/", "tablerosPropuestos");
        }
        catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    /* POST: Se crea la instancia del ControladorDatosTableroPropuesto */

    /* PRE: - */
    public static ControladorDatosTableroPropuesto getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorDatosTableroPropuesto */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Tablero no encontrado */
    public TableroHidatoPropuesto obtener(String tablero) throws Exception {

        long fila = searchFila(tablero);

        if (fila == -1) throw new InstanceNotFoundException(tablero);

        return getFila(fila);
    }
    /* POST: Retorna la instancia de TableroHidatoPropuesto identificada por 'tablero'
        almacenada en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public List<TableroHidatoPropuesto> obtenerTodos() throws IOException {

       List<TableroHidatoPropuesto> listaTableros = new ArrayList();

       file.seek(0);
       for (long i = 0; i < file.length(); i += obtenerSize(i))
           listaTableros.add(getFila(i));

       return listaTableros;
    }
    /* POST: Retorna todas las instancias de TableroHidatoPropuesto almacenadas en el
        sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public boolean existe(String tablero) throws IOException {

        return (searchFila(tablero) != -1);
    }
    /* POST: Retorna cierto si existe la instancia de TableroHidatoPropuesto identificada
        por 'tablero' en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceRepetedException': Tablero ya existente */
    public void insertar(TableroHidatoPropuesto tablero) throws Exception {

        String idTablero = tablero.getTablero();
        long fila = searchFila(idTablero);

        if (fila != -1) throw new InstanceRepeatedException(idTablero);
        setFila(file.length(), tablero);
    }
    /* POST: Inserta y hace persistente la instancia de TableroHidatoPropuesto 'tablero'
        en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Tablero no encontrado */
    public void actualizar(TableroHidatoPropuesto tablero) throws Exception {

        String idTablero = tablero.getTablero();
        long fila = searchFila(idTablero);

        if (fila == -1) throw new InstanceNotFoundException(idTablero);
        setFila(fila, tablero);
    }
    /* POST: Hace persistentes los cambios en la instancia de TableroHidatoPropuesto
        'tablero' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Tablero no encontrado */
    public void borrar(String tablero) throws Exception {

        long fila = searchFila(tablero);
        long filaBorradaSize;
        long filaSize;

        if (fila == -1) throw new InstanceNotFoundException(tablero);
        filaSize = obtenerSize(fila);
        filaBorradaSize = filaSize;

        for (long i = fila + filaBorradaSize; i < file.length();
          i += filaSize, fila += filaSize) {

            filaSize = obtenerSize(i);
            setFila(fila, getFila(i));
        }
        file.setLength(file.length() - filaBorradaSize);
    }
    /* POST: Borra del sistema la instancia de TableroHidatoPropuesto identificada por
        'tablero' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private TableroHidatoPropuesto getFila(long fila) throws IOException {
        
        byte[] input;
        String idTablero, usuario;
        int anchura;
        int altura;
        int dificultad;
        int topologia;
        int puntuacion;
        CasillaHidato casilla;
        TableroHidatoPropuesto tablero;

        file.seek(fila);

        input = new byte[ID_TABLERO_SIZE];
        file.read(input);
        idTablero = Utiles.byteArrayToString(input);

        input = new byte[INT_SIZE];
        file.read(input);

        file.read(input);
        anchura = Utiles.byteArrayToInt(input);

        file.read(input);
        altura = Utiles.byteArrayToInt(input);

        file.read(input);
        dificultad = Utiles.byteArrayToInt(input);

        file.read(input);
        topologia = Utiles.byteArrayToInt(input);

        file.read(input);
        puntuacion = Utiles.byteArrayToInt(input);

        input = new byte[ID_USUARIO_SIZE];
        file.read(input);
        usuario = Utiles.byteArrayToString(input);

        tablero = new TableroHidatoPropuesto(idTablero, anchura, altura, dificultad,
          topologia, usuario);

        tablero.setPuntuacion(puntuacion);

        for (int i = 0; i < altura ; ++i) {
            for (int j = 0; j < anchura; ++j) {
                casilla = obtenerCasilla();
                tablero.introducirValor(j, i, casilla.getValor());
                tablero.setPrefijada(j, i, casilla.esPrefijada());
                tablero.setActiva(j, i, casilla.esActiva());
            }
        }

        return tablero;
    }
    /* POST: Retorna la instancia de TableroHidatoPropuesto que se encuentra en la
        posicion 'fila' del fichero */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private void setFila (long fila, TableroHidatoPropuesto tablero)
      throws IOException {

        byte[] output;
        int anchura;
        int altura;

        file.seek(fila);

        output = Utiles.stringToByteArray(tablero.getTablero(), ID_TABLERO_SIZE);
        file.write(output);

        output = Utiles.intToByteArray(0);
        file.write(output);

        anchura = tablero.getAnchura();
        output = Utiles.intToByteArray(anchura);
        file.write(output);

        altura = tablero.getAltura();
        output = Utiles.intToByteArray(altura);
        file.write(output);

        output = Utiles.intToByteArray(tablero.obtenerDificultad());
        file.write(output);

        output = Utiles.intToByteArray(tablero.obtenerTopologia());
        file.write(output);

        output = Utiles.intToByteArray(tablero.obtenerPuntuacion());
        file.write(output);

        output = Utiles.stringToByteArray(tablero.obtenerUsuario(), ID_USUARIO_SIZE);
        file.write(output);

        file.seek(fila + FILA_SIZE_INDEX);
        output = Utiles.intToByteArray(CASILLAS_INDEX + anchura * altura * CASILLA_SIZE);
        file.write(output);

        file.seek(fila + CASILLAS_INDEX);
        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                output = Utiles.intToByteArray(j);
                file.write(output);

                output = Utiles.intToByteArray(i);
                file.write(output);

                output = Utiles.intToByteArray(tablero.obtenerValor(j, i));
                file.write(output);

                output = Utiles.booleanToByteArray(tablero.esPrefijada(j, i));
                file.write(output);

                output = Utiles.booleanToByteArray(tablero.esActiva(j, i));
                file.write(output);
            }
        }
    }
    /* POST: Inserta y hace persistente la instancia de TableroHidatoPropuesto 'tablero'
        en la posicion 'fila' del fichero */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private long searchFila(String tablero) throws IOException {

        byte[] input = new byte[ID_TABLERO_SIZE];

        for (long i = 0; i < file.length(); i += obtenerSize(i)) {
            file.seek(i);
            file.read(input);
            if (Utiles.byteArrayToString(input).equals(tablero)) return i;
        }

        return -1;
    }
    /* POST: Retorna el desplazamiento dentro del fichero donde se encuentra la instancia
        de TableroHidatoPropuesto identificada por 'tablero' o -1 si no existe */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private CasillaHidato obtenerCasilla() throws IOException {

        byte[] input;
        int x, y, valor;
        Boolean prefijada, activa;
        CasillaHidato casilla;

        input = new byte[INT_SIZE];
        file.read(input);
        x = Utiles.byteArrayToInt(input);

        file.read(input);
        y = Utiles.byteArrayToInt(input);

        file.read(input);
        valor = Utiles.byteArrayToInt(input);

        input = new byte[1];
        file.read(input);
        prefijada = Utiles.byteArrayToBoolean(input);

        file.read(input);
        activa = Utiles.byteArrayToBoolean(input);

        casilla = new CasillaHidato(x, y);
        casilla.setValor(valor);
        casilla.setPrefijada(prefijada);
        casilla.setActiva(activa);

        return casilla;
    }
    /* POST: Retorna el contenido de la siguiente casilla del TableroHidatoPropuesto */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private long obtenerSize(long fila) throws IOException {

        byte[] input = new byte[INT_SIZE];

        file.seek(fila + ID_TABLERO_SIZE);
        file.read(input);

        return Utiles.byteArrayToInt(input);
    }
    /* POST: Retorna el tama(ny)o de la fila 'fila'. */
}