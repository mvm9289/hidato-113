
package Tests;

import Dominio.Posicion;
import Dominio.Ranking;
import DriverTBC.inout;
import Tiempo.Fecha;
import java.util.ArrayList;

public class ProbarRanking {

    public static void probar() {

        Ranking ranking = null;
        boolean atras = false;

        while(!atras) {
            try {
                inout io = new inout();
                int option;
                ArrayList<Posicion> listaPosiciones;
                Fecha fecha;

                io.write("Operacion:\n" +
                         "1. Ranking()\n" +
                         "2. obtenerPosiciones()\n" +
                         "3. insertarPosicion(Posicion nuevaPosicion)\n" +
                         "4. eliminaPosiciones(String usuario)\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        ranking = new Ranking();
                        break;
                    case 2:
                        if (ranking == null) {
                            io.write("No has creado una instancia de Ranking.\n\n");
                        }
                        else {
                            listaPosiciones = ranking.obtenerPosiciones();
                            for(int i = 0; i < listaPosiciones.size(); ++i) {
                                io.write((i+1) + " " +
                                  listaPosiciones.get(i).getPuntuacion() + " " +
                                  listaPosiciones.get(i).getNjugador() + " " +
                                  listaPosiciones.get(i).getFecha().obtenerFechaFormal() +
                                  "\n");
                            }
                            io.write("\n");
                        }
                        break;
                    case 3:
                        if (ranking == null) {
                            io.write("No has creado una instancia de Ranking.\n\n");
                        }
                        else {
                            io.write("Introduce una Posicion (Posicion(int puntuacion, " +
                                     "String nombreUsuario, Fecha fecha)):\n");
                            io.write("Introduce puntuacion y nombreUsuario:\n");
                            fecha = new Fecha();
                            fecha.hoy();
                            ranking.insertarPosicion(new Posicion(io.readint(),
                                                                  io.readword(),
                                                                  fecha));
                            io.write("\n");
                        }
                        break;
                    case 4:
                        if (ranking == null) {
                            io.write("No has creado una instancia de Ranking.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            ranking.eliminaPosiciones(io.readword());
                            io.write("\n");
                        }
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
