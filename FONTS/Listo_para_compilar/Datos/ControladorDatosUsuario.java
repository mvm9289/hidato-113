/*  Class ControladorDatosUsuario:
    Descripcion: Gestor de disco para las instancias de la clase Usuario.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 00:17 */

package Datos;

import Dominio.Usuario;
import Excepciones.InstanceNotFoundException;
import Excepciones.InstanceRepeatedException;
import Utiles.Files;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import Utiles.Utiles;

public class ControladorDatosUsuario {

    private final static int NICK_SIZE = 20;
    private final static int PASS_SIZE = 32;
    private final static int FILA_SIZE = NICK_SIZE + PASS_SIZE;

    private final static ControladorDatosUsuario INSTANCIA =
      new ControladorDatosUsuario();
    
    private RandomAccessFile file;

    /* PRE: - */
    private ControladorDatosUsuario() {

        try {
            file = Files.openFile(Files.getPath() + "/saves/", "usuarios");
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    /* POST: Se crea la instancia del ControladorDatosUsuario */

    /* PRE: - */
    public static ControladorDatosUsuario getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia de ControladorDatosUsuario */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Nickaname no encontrado */
    public Usuario obtener(String nickname) throws Exception {

        int fila = searchFila(nickname);

        if(fila == -1) throw new InstanceNotFoundException(nickname);
        
        return getFila(fila);
    }
    /* POST: Retorna la instancia de Usuario identificada por 'nickname' almacenada en el
        sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public List<Usuario> obtenerTodos() throws IOException {

        List<Usuario> usuarios = new ArrayList<Usuario>();
        int numFilas = (int)(file.length() / FILA_SIZE);

        for (int i = 0; i < numFilas; ++i) usuarios.add(getFila(i));

        return usuarios;
    }
    /* POST: Retorna todas las instancias de Usuario almacenadas en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public boolean existe(String nickname) throws IOException {

        return (searchFila(nickname) != -1);
    }
    /* POST: Retorna cierto si existe la instancia de Usuario identificada
        por 'nickname' en el sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceRepetedException': Usuario ya existente */
    public void insertar(Usuario usuario) throws Exception {

        String nickname = usuario.obtenerUsuario();
        int fila = searchFila(nickname);

        if (fila != -1) throw new InstanceRepeatedException(nickname);
        setFila((int)(file.length() / FILA_SIZE), usuario);
    }
    /* POST: Inserta y hace persistente la instancia de Usuario 'usuario' en el
        sistema */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Usuario no encontrado */
    public void actualizar(Usuario usuario) throws Exception {

        String nickname = usuario.obtenerUsuario();
        int fila = searchFila(nickname);

        if (fila == -1) throw new InstanceNotFoundException(nickname);
        setFila(fila, usuario);
    }
    /* POST: Hace persistentes los cambios en la instancia de Usuario 'usuario' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    /* EXC 'InstanceNotFoundException': Usuario no encontrado */
    public void borrar(String nickname) throws Exception {

        int numFilas;
        int fila = searchFila(nickname);

        if (fila == -1) throw new InstanceNotFoundException(nickname);
        numFilas = (int)(file.length() / FILA_SIZE);
        for (int i = fila + 1; i < numFilas; ++i) setFila(i - 1, getFila(i));
        file.setLength(file.length() - FILA_SIZE);
    }
    /* POST: Borra del sistema la instancia de Usuario identificada por 'nickname' */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private Usuario getFila(int fila) throws IOException {

        byte[] input;
        String nickname;
        String password;

        file.seek(fila * FILA_SIZE);

        input = new byte[NICK_SIZE];
        file.read(input);
        nickname = Utiles.byteArrayToString(input);

        input = new byte[PASS_SIZE];
        file.read(input);
        password = Utiles.byteArrayToString(input);

        return new Usuario(nickname, password);
    }
    /* POST: Retorna la instancia de Usuario que se encuentra en la fila 'fila' del
        fichero */

    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private void setFila(int fila, Usuario usuario) throws IOException {

        byte[] output;
        
        file.seek(fila * FILA_SIZE);

        output = Utiles.stringToByteArray(usuario.obtenerUsuario(), NICK_SIZE);
        file.write(output);
        
        output = Utiles.stringToByteArray(usuario.obtenerPassword(), PASS_SIZE);
        file.write(output);
    }
    /* POST: Inserta y hace persistente la instancia de Usuario 'usuario' en la fila
        'fila' del fichero */

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
        Usuario identificada por 'usuario' o -1 si no existe */
}