/*  Class Cronometro:
    Descripcion: Implementa un cronometro con su respectiva vista.
    Autor: daniel.camarasa
    Revisado: 21/12/2009 06:33 */

package Vistas;

import Utiles.Utiles;
import javax.swing.JLabel;

public class Cronometro extends JLabel implements Runnable {

    private int segundos;
    private Thread miThread;
    private boolean parado;

    /* PRE: - */
    public Cronometro() {

        initComponents();
        actualizaTiempo();
        miThread.start();
    }
    /* POST: Crea una instancia de Cronometro y lanza el thread */

    /* POST: - */
    public void parar() {

        parado = true;
    }
    /* POST: Para la contabilizacion de tiempo */

    /* PRE: - */
    public void iniciar() {

        parado = false;
    }
    /* POST: Inicia/continua la contabilizacion de tiempo */

    /* PRE: - */
    public int obtenerTiempo() {

        return segundos;
    }
    /* POST: Devuelve los segundos contabilizados */

    /* PRE: tiempo >= 0 */
    public void setTiempo(int tiempo) {

        segundos = tiempo;
        actualizaTiempo();
    }
    /* POST: segundos pasa a valer 'tiempo' */

    /* PRE: - */
    private void actualizaTiempo() {

        setText(Utiles.segundosToTiempo(segundos));
    }
    /* POST: Etablece el tiempo transcurrido en el texto de la etiqueta de la vista */

    /* PRE: - */
    private void initComponents() {
        
        segundos = 0;
        miThread = new Thread(this);
        parado = true;
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el cronometro */

    /* PRE: - */
    public void run() {
        
        try {
            while (true) {
                Thread.sleep(1000);
                if (!parado) {
                    ++segundos;
                    actualizaTiempo();
                }
            }
        }
        catch (InterruptedException ex) {
            miThread.stop();
        }
    }
    /* POST: Ejecucion del thread: incrementa cada segundo transcurrido al tiempo
        contabilizado. A cada segundo se actualiza la etiqueta de la vista. */
}
