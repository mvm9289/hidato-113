/*  Class ControladorDatosRanking:
    Descripcion: Gestor de disco para la instancia de la clase Ranking.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 00:16 */

package Datos;

import Dominio.Posicion;
import Dominio.Ranking;
import Tiempo.Fecha;
import Utiles.Files;
import Utiles.Utiles;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class ControladorDatosRanking {

    private final static int FECHA_SIZE = 12;
    private final static int PUNTUACION_SIZE = 4;
    private final static int JUGADOR_SIZE = 20;
    private final static int FILA_SIZE = FECHA_SIZE + PUNTUACION_SIZE + JUGADOR_SIZE;

    private final static ControladorDatosRanking INSTANCIA =
      new ControladorDatosRanking();
    
    private RandomAccessFile file;
    Ranking ranking;

    /* PRE: - */
    private ControladorDatosRanking() {

        try {
            file = Files.openFile(Files.getPath() + "/saves/", "ranking");
            ranking = cargarRanking();
        }
        catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    /* POST: Se crea la instancia del ControladorDatosRanking */

    /* PRE: - */
    public static ControladorDatosRanking getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorDatosRanking */

    /* PRE: - */
    public Ranking obtener() {

        return ranking;
    }
    /* POST: Retorna la instancia del ranking de la aplicacion */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void actualizar() throws IOException {

        byte[] output;
        List<Posicion> posiciones = ranking.obtenerPosiciones();

        file.seek(0);
        file.setLength(0);
        for (int i = 0; i < posiciones.size(); ++i) {
            output = Utiles.fechaToByteArray(posiciones.get(i).getFecha());
            file.write(output);

            output = Utiles.intToByteArray(posiciones.get(i).getPuntuacion());
            file.write(output);

            output = Utiles.stringToByteArray(posiciones.get(i).getNjugador(),
              JUGADOR_SIZE);
            file.write(output);
        }
    }
    /* POST: Hace persistentes los cambios en la instancia del ranking de la aplicacion */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private Ranking cargarRanking() throws IOException {

        Ranking rank = new Ranking();
        Fecha fecha;
        int puntuacion;
        String jugador;
        byte[] input;
        int numFilas = (int)(file.length() / FILA_SIZE);

        file.seek(0);
        for (long i = 0; i < numFilas; ++i) {
            input = new byte[FECHA_SIZE];
            file.read(input);
            fecha = Utiles.byteArrayToFecha(input);

            input = new byte[PUNTUACION_SIZE];
            file.read(input);
            puntuacion = Utiles.byteArrayToInt(input);

            input = new byte[JUGADOR_SIZE];
            file.read(input);
            jugador = Utiles.byteArrayToString(input);
            
            rank.insertarPosicion(new Posicion(puntuacion, jugador, fecha));
        }

        return rank;
    }
    /* POST: Reestablece la instancia del ranking de la aplicacion */
}
