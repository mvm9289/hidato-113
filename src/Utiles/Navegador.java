/*  Class Navegador:
    Descripcion: Clase que facilita la apertura de paginas web en un navegador.
    Autor: miguel.angel.vico
    Revisado: 10/11/2009 18:30 */

package Utiles;

import Excepciones.BrowserNotFoundException;
import Excepciones.UnrecognizedOSException;
import java.lang.reflect.Method;

public class Navegador {

    private final static String MAC_OS = "Mac OS";
    private final static String WINDOWS = "Windows";
    private final static String LINUX = "Linux";
    private final static String NAVEGADORES[] = {"firefox", "mozilla", "opera", 
      "konqueror", "epiphany", "netscape"};

    public static void abrirURL(String url) throws Exception {

        String sistemaOperativo = System.getProperty("os.name");

        if (sistemaOperativo.startsWith(MAC_OS)) {
            Class<?> manager = Class.forName("com.apple.eio.FileManager");
            Method openURL = manager.getDeclaredMethod("openURL",
              new Class[] {String.class});
            openURL.invoke(null, new Object[] {url});
        }
        else if (sistemaOperativo.startsWith(WINDOWS))
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        else if (sistemaOperativo.startsWith(LINUX)) {
            int i;
            Boolean navegadorEncontrado = false;

            for (i = 0; i < NAVEGADORES.length && !navegadorEncontrado;){
                if (Runtime.getRuntime().exec(new String[] {"which",
                  NAVEGADORES[i]}).waitFor() == 0) navegadorEncontrado = true;
                else i++;
            }
            if (!navegadorEncontrado) throw new BrowserNotFoundException();
            else Runtime.getRuntime().exec(new String[] {NAVEGADORES[i], url});
        }
        else throw new UnrecognizedOSException();
    }
}
