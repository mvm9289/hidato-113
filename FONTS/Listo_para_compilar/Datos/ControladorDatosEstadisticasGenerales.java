/*  Class ControladorDatosEstadisticasGenerales:
    Descripcion: Gestor de disco para las instancias de la clase EstadisticasGenerales
    Autor: alex.catarineu
    Revisado: 20/12/2009 00:16 */

package Datos;

import Dominio.EstadisticasHidato;
import Utiles.Files;
import Utiles.Utiles;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ControladorDatosEstadisticasGenerales {
    
    private final static int FIELD_SIZE = 4;
    
    private final static ControladorDatosEstadisticasGenerales INSTANCIA =
      new ControladorDatosEstadisticasGenerales();
    
    private RandomAccessFile file;
    private EstadisticasHidato estadisticasGenerales;

    /* PRE: - */
    private ControladorDatosEstadisticasGenerales() {
        
        try {
            file = Files.openFile(Files.getPath() + "/saves/", "estadisticasGenerales");
            estadisticasGenerales = cargarEstadisticasGenerales();
        }
        catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    /* POST: Se crea la instancia del ControladorDatosEstadisticasGenerales */

    /* PRE: - */
    public static ControladorDatosEstadisticasGenerales getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorDatosEstadisticasGenerales */

    /* PRE: - */
    public EstadisticasHidato obtener() {

        return estadisticasGenerales;
    }
    /* POST: Retorna la instancia de las estadisticas generales de la aplicacion */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void actualizar() throws IOException {
        
        byte[] output;
        int campo;

        file.seek(0);
        file.setLength(0);
        
        campo = estadisticasGenerales.obtenerTotalPartidas();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerPartidasAbandonadas();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerPartidasResueltas();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerPartidasFacil();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerPartidasMedio();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerPartidasDificil();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerTotalUsuarios();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerTotalTableros();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerTablerosGenerados();
        output = Utiles.intToByteArray(campo);
        file.write(output);

        campo = estadisticasGenerales.obtenerTablerosPropuestos();
        output = Utiles.intToByteArray(campo);
        file.write(output);
    }
    /* POST: Hace persistentes los cambios en la instancia de las estadisticas generales
        de la aplicacion */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private EstadisticasHidato cargarEstadisticasGenerales()
      throws IOException {

        byte input[] = new byte[FIELD_SIZE];
        int campo;
        estadisticasGenerales = new EstadisticasHidato();

        file.seek(0);
        if (file.length() > 0) {
            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarTotalPartidas(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarPartidasAbandonadas(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarPartidasResueltas(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarPartidasFacil(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarPartidasMedio(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarPartidasDificil(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarTotalUsuarios(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarTotalTableros(campo);

            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarTablerosGenerados(campo);
            
            file.read(input);
            campo = Utiles.byteArrayToInt(input);
            if (campo > 0) estadisticasGenerales.incrementarTablerosPropuestos(campo);
        }

        return estadisticasGenerales;
    }
    /* POST: Reestablece la instancia de las estadisticas generales de la aplicacion */
}
