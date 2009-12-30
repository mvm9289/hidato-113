/*  Class Main:
    Descripcion: DRIVER para la prueba de las clases del dominio.
    Revisado: 18/11/2009 18:15 */

import DriverTBC.inout;
import Tests.ProbarControladorEstadisticas;
import Tests.ProbarControladorPartida;
import Tests.ProbarControladorUsuario;
import Tests.ProbarControladorTablero;
import Tests.ProbarEstadisticasGenerales;
import Tests.ProbarEstadisticasPersonales;
import Tests.ProbarFecha;
import Tests.ProbarHora;
import Tests.ProbarPartida;
import Tests.ProbarRanking;
import Tests.ProbarTableroHidato;
import Tests.ProbarUsuario;

public class Main {

    public static void main(String[] args) {

        while(true) {
            try {
                inout io = new inout();
                int option;

                io.write("Introduce un numero para probar una clase o salir:\n" +
                         "1. Usuario\n" +
                         "2. ControladorUsuario\n" +
                         "3. EstadisticasPersonales\n" +
                         "4. EstadisticasGenerales\n" +
                         "5. Ranking\n" +
                         "6. ControladorEstadisticas\n" +
                         "7. PartidaGuardada\n" +
                         "8. ControladorPartida\n" +
                         "9. TableroHidato + Casilla\n" +
                         "10. ControladorTablero\n" +
                         "11. Fecha\n" +
                         "12. Hora\n" +
                         "0. Salir\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        ProbarUsuario.probar();
                        break;
                    case 2:
                        ProbarControladorUsuario.probar();
                        break;
                    case 3:
                        ProbarEstadisticasPersonales.probar();
                        break;
                    case 4:
                        ProbarEstadisticasGenerales.probar();
                        break;
                    case 5:
                        ProbarRanking.probar();
                        break;
                    case 6:
                        ProbarControladorEstadisticas.probar();
                        break;
                    case 7:
                        ProbarPartida.probar();
                        break;
                    case 8:
                        ProbarControladorPartida.probar();
                        break;
                    case 9:
                        ProbarTableroHidato.probar();
                        break;
                    case 10:
                        ProbarControladorTablero.probar();
                        break;
                    case 11:
                        ProbarFecha.probar();
                        break;
                    case 12:
                        ProbarHora.probar();
                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage() + "\n");
            }
        }
    }
}
