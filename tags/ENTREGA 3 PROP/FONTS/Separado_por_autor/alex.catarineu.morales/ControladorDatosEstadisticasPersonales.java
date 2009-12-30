/*  Class ControladorDatosEstadisticasPersonales:
    Descripcion: Gestor de disco para las instancias de la clase EstadisticasPersonales
    Autor: alex.catarineu
    Revisado: 20/12/2009 00:16 */

package Datos;

import Dominio.EstadisticasPersonales;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import Utiles.Files;
import Utiles.Utiles;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ControladorDatosEstadisticasPersonales {

    private final static int INT_SIZE = 4;
    private final static int NICK_SIZE = 20;
    private final static int FILA_SIZE = 6*INT_SIZE + NICK_SIZE;

    private final static ControladorDatosEstadisticasPersonales INSTANCIA =
      new ControladorDatosEstadisticasPersonales();
    
    private RandomAccessFile file;

    /* PRE: - */
    private ControladorDatosEstadisticasPersonales() {

        try {
            file = Files.openFile(Files.getPath() + "/saves/", "estadisticasPersonales");
        }
        catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    /* POST: Se crea la instancia del ControladorDatosEstadisticasPersonales */

    /* PRE: - */
    public static ControladorDatosEstadisticasPersonales getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorDatosEstadisticasPersonales */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Usuario no encontrado */
    public EstadisticasPersonales obtener(String usuario) throws Exception {

        int fila = searchFila(usuario);

        if (fila == -1) throw new InstanceNotFoundException(usuario);
        
        return getFila(fila);
    }
    /* POST: Retorna la instancia de EstadisticasPersonales identificada por 'usuario'
        almacenada en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public List<EstadisticasPersonales> obtenerTodos() throws IOException {

        List<EstadisticasPersonales> listEst = new ArrayList();
        int numFilas = (int)(file.length() / FILA_SIZE);

        for (int i = 0; i < numFilas; ++i) listEst.add(getFila(i));

        return listEst;
    }
    /* POST: Retorna todas las instancias de EstadisticasPersonales almacenadas en el
        sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public boolean existe(String usuario) throws IOException {

        return (searchFila(usuario) != -1);
    }
    /* POST: Retorna cierto si existe la instancia de EstadisticasPersonales identificada
        por 'usuario' en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceRepetedException': Ya existe una fila con el mismo identificador */
    public void insertar(EstadisticasPersonales estadisticas) throws Exception {

        String nickname = estadisticas.obtenerUsuario();
        int fila = searchFila(nickname);

        if (fila != -1) throw new InstanceRepeatedException(nickname);
        setFila((int)(file.length() / FILA_SIZE), estadisticas);
    }
    /* POST: Inserta y hace persistente la instancia de EstadisticasPersonales
        'estadisticas' en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': estadisticas no encontradas */
    public void actualizar(EstadisticasPersonales estadisticas) throws Exception {

        String nickname = estadisticas.obtenerUsuario();
        int fila = searchFila(nickname);

        if (fila == -1) throw new InstanceNotFoundException(nickname);
        setFila(fila, estadisticas);
    }
    /* POST: Hace persistentes los cambios en la instancia de EstadisticasPersonales
        'estadisticas' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Usuario no encontrado */
    public void borrar(String usuario) throws Exception {

        int fila = searchFila(usuario);
        int numFilas = (int)(file.length() / FILA_SIZE);

        if (fila == -1) throw new InstanceNotFoundException(usuario);
        for (int i = fila + 1; i < numFilas; ++i) setFila(i - 1, getFila(i));
        file.setLength(file.length() - FILA_SIZE);
    }
    /* POST: Borra del sistema la instancia de EstadisticasPersonales identificada por
        'usuario' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private EstadisticasPersonales getFila(int fila) throws IOException {

        byte[] input;
        
        file.seek(fila * FILA_SIZE);

        input = new byte[NICK_SIZE];
        file.read(input);
        String nickname = Utiles.byteArrayToString(input);
        EstadisticasPersonales est = new EstadisticasPersonales(nickname);

        input = new byte[INT_SIZE];
        file.read(input);
        int campo = Utiles.byteArrayToInt(input);
        if (campo > 0) est.incrementarTiempoTotalDeJuego(campo);

        file.read(input);
        campo = Utiles.byteArrayToInt(input);
        if (campo > 0) est.incrementarTotalPartidas(campo, true);

        file.read(input);
        campo = Utiles.byteArrayToInt(input) - campo;
        if (campo > 0) est.incrementarTotalPartidas(campo, false);

        file.read(input);
        campo = Utiles.byteArrayToInt(input);
        est.introducirPosicionRanking(campo);

        file.read(input);
        campo = Utiles.byteArrayToInt(input);
        if (campo > 0) est.incrementarTablerosPropuestos(campo);

        file.read(input);
        campo = Utiles.byteArrayToInt(input);
        if (campo > 0) est.incrementarPuntuacionTotal(campo);

        return est;
    }
    /* POST: Retorna la instancia de EstadisticasPersonales que se encuentra en la fila
        'fila' del fichero */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private void setFila(int fila, EstadisticasPersonales est) throws IOException {

        byte[] output;
        
        file.seek(fila * FILA_SIZE);

        output = Utiles.stringToByteArray(est.obtenerUsuario(), NICK_SIZE);
        file.write(output);

        output = Utiles.intToByteArray(est.obtenerTiempoTotalDeJuego());
        file.write(output);

        output = Utiles.intToByteArray(est.obtenerPartidasResueltas());
        file.write(output);

        output = Utiles.intToByteArray(est.obtenerTotalPartidas());
        file.write(output);

        output = Utiles.intToByteArray(est.obtenerPosicionRanking());
        file.write(output);

        output = Utiles.intToByteArray(est.obtenerTablerosPropuestos());
        file.write(output);

        output = Utiles.intToByteArray(est.obtenerPuntuacionTotal());
        file.write(output);
    }
    /* POST: Inserta y hace persistente la instancia de EstadisticasPersonales 'est' en
        la fila 'fila' del fichero */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private int searchFila(String usuario) throws IOException {

        byte[] input = new byte[NICK_SIZE];
        int numFilas = (int)(file.length() / FILA_SIZE);

        for (int i = 0; i < numFilas; ++i) {
            file.seek(i * FILA_SIZE);
            file.read(input);
            if (Utiles.byteArrayToString(input).equals(usuario)) return i;
        }

        return -1;
    }
    /* POST: Retorna la fila dentro del fichero donde se encuentra la instancia de
        EstadisticasPersonales identificada por 'usuario' o -1 si no existe */
}
