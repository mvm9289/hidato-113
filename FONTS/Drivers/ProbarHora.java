
package Tests;

import DriverTBC.inout;
import Tiempo.Hora;

public class ProbarHora {

    public static void probar() {

        Hora hora = null;
        boolean atras = false;

        while(!atras) {
            try {
                inout io = new inout();
                int option;

                io.write("Operacion:\n" +
                         "1. Hora()\n" +
                         "2. ahora()\n" +
                         "3. ponerHora(int hora, int minuto, int segundo)\n" +
                         "4. obtenerHoraFormal()\n" +
                         "5. obtenerHora()\n" +
                         "6. obtenerMinuto()\n" +
                         "7. obtenerSegundo()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        hora = new Hora();
                        io.writeln();
                        break;
                    case 2:
                        if (hora == null) {
                            io.writeln("No has creado una instancia de Hora.\n");
                        }
                        else {
                            hora.ahora();
                            io.writeln();
                        }
                        break;
                    case 3:
                        if (hora == null) {
                            io.writeln("No has creado una instancia de Hora.\n");
                        }
                        else {
                            io.writeln("Introduce los parametros: (Deben corresponder a " +
                                       "una hora correcta)");
                            hora.ponerHora(io.readint(), io.readint(), io.readint());
                            io.writeln();
                        }
                        break;
                    case 4:
                        if (hora == null) {
                            io.writeln("No has creado una instancia de Hora.\n");
                        }
                        else {
                            io.writeln("La hora formal es:");
                            io.writeln(hora.obtenerHoraFormal());
                            io.writeln();
                        }
                        break;
                    case 5:
                        if (hora == null) {
                            io.writeln("No has creado una instancia de Hora.\n");
                        }
                        else {
                            io.writeln("La hora es:");
                            io.writeln(hora.obtenerHora());
                            io.writeln();
                        }
                        break;
                    case 6:
                        if (hora == null) {
                            io.writeln("No has creado una instancia de Hora.\n");
                        }
                        else {
                            io.writeln("El minuto es:");
                            io.writeln(hora.obtenerMinuto());
                            io.writeln();
                        }
                        break;
                    case 7:
                        if (hora == null) {
                            io.writeln("No has creado una instancia de Hora.\n");
                        }
                        else {
                            io.writeln("El segundo es:");
                            io.writeln(hora.obtenerSegundo());
                            io.writeln();
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
