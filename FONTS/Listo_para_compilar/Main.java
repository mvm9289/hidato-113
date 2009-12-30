/*  Class Main:
    Revisado: 21/12/2009 06:15 */

import Vistas.ControladorVistas;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ControladorVistas.getInstance().ejecutar();
            }
        });
    }
}
