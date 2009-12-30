
package Tests;

import DriverTBC.inout;
import Tiempo.Fecha;

public class ProbarFecha {

    public static void probar() {

        Fecha fecha = null;
        boolean atras = false;

        while(!atras) {
            try {
                inout io = new inout();
                int option;

                io.write("Operacion:\n" +
                         "1. Fecha()\n" +
                         "2. hoy()\n" +
                         "3. ponerFecha(int dia, int mes, int anio)\n" +
                         "4. obtenerFechaFormal()\n" +
                         "5. obtenerFechaCompleta()\n" +
                         "6. obtenerDiaDelMes()\n" +
                         "7. obtenerDiaDeLaSemanaInt()\n" +
                         "8. obtenerDiaDeLaSemanaStr()\n" +
                         "9. obtenerMesInt()\n" +
                         "10. obtenerMesStr()\n" +
                         "11. obtenerAnio()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        fecha = new Fecha();
                        io.writeln();
                        break;
                    case 2:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            fecha.hoy();
                            io.writeln();
                        }
                        break;
                    case 3:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("Introduce los parametros: (Deben corresponder a " +
                                       "una fecha correcta)");
                            fecha.ponerFecha(io.readint(), io.readint(), io.readint());
                            io.writeln();
                        }
                        break;
                    case 4:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("La fecha formal es:");
                            io.writeln(fecha.obtenerFechaFormal());
                            io.writeln();
                        }
                        break;
                    case 5:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("La fecha completa es:");
                            io.writeln(fecha.obtenerFechaCompleta());
                            io.writeln();
                        }
                        break;
                    case 6:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("El dia del mes:");
                            io.writeln(fecha.obtenerDiaDelMes());
                            io.writeln();
                        }
                        break;
                    case 7:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("El dia de la semana es: (int)");
                            io.writeln(fecha.obtenerDiaDeLaSemanaInt());
                            io.writeln();
                        }
                        break;
                    case 8:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("El dia de la semana es: (String)");
                            io.writeln(fecha.obtenerDiaDeLaSemanaStr());
                            io.writeln();
                        }
                        break;
                    case 9:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("El mes es: (int)");
                            io.writeln(fecha.obtenerMesInt());
                            io.writeln();
                        }
                        break;
                    case 10:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("El mes es: (String)");
                            io.writeln(fecha.obtenerMesStr());
                            io.writeln();
                        }
                        break;
                    case 11:
                        if (fecha == null) {
                            io.writeln("No has creado una instancia de Fecha.\n");
                        }
                        else {
                            io.writeln("El a(ny)o es:");
                            io.writeln(fecha.obtenerAnio());
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
