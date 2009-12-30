/*  Class ControladorDatosRepositorio:
    Descripcion: Gestor de disco para la instancia de la clase Repositorio.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 00:16 */

package Datos;

import Dominio.Repositorio;
import Utiles.Files;
import Utiles.Utiles;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class ControladorDatosRepositorio {

    private final static int FIELD_SIZE = 20;

    private final static ControladorDatosRepositorio INSTANCIA =
      new ControladorDatosRepositorio();
    
    private RandomAccessFile file;
    private Repositorio repositorio;

    /* PRE: - */
    private ControladorDatosRepositorio() {

        try {
            file = Files.openFile(Files.getPath() + "/saves/", "repositorio");
            repositorio = cargarRepositorio();
        }
        catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    /* POST: Se crea la instancia del ControladorDatosRepositorio */

    /* PRE: - */
    public static ControladorDatosRepositorio getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorDatosRepositorio */

    /* PRE: - */
    public Repositorio obtener() {

        return repositorio;
    }
    /* POST: Retorna la instancia del repositorio de tableros de la aplicacion */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void actualizar() throws IOException {

        byte output[];
        List<String> tabs = repositorio.getTableros();

        file.seek(0);
        file.setLength(0);
        for (int i = 0; i < tabs.size(); ++i) {
            output = Utiles.stringToByteArray(tabs.get(i), FIELD_SIZE);
            file.write(output);
        }
    }
    /* POST: Hace persistentes los cambios en la instancia del repositorio de tableros
        de la aplicacion */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private Repositorio cargarRepositorio() throws IOException {

        Repositorio repos = new Repositorio();
        byte input[] = new byte[FIELD_SIZE];
        int numFilas = (int)(file.length() / FIELD_SIZE);

        file.seek(0);
        for (int i = 0; i < numFilas; ++i) {
            file.read(input);
            repos.addTablero(Utiles.byteArrayToString(input));
        }
        
        return repos;
    }
    /* POST: Reestablece la instancia del repositorio de tableros de la aplicacion */
}
