
package Tests;

import Dominio.PartidaGuardada;
import Dominio.Partida;
import Tiempo.Fecha;
import Tiempo.Hora;
import DriverTBC.inout;

public class ProbarPartida {

    public static void probar() {

        Partida partida = null;
        PartidaGuardada partidaGuardada = null;

        while(true) {
            try {
                inout io = new inout();
                int option;
                String idpartida, idpartidaguardada, idusuario, idtablero;
                Fecha fecha;
                Hora hora;
                int horas, minutos, segundos, anio, mes, dia;
                
                io.write("Operacion:\n" +
                         "1. Partida(String idPartida, String idUsuario, " +
                           "String idTablero)\n" +
                         "2. PartidaGuardada(String id, Partida partida, " +
                           "Fecha fecha, Hora hora)\n" +
                         "3. obtenerFecha()\n" +
                         "4. introducirFecha(Fecha fecha)\n" +
                         "5.  obtenerHora()\n" +
                         "6. introducirHora(Hora hora)\n" +
                         "7. getIdPartida()\n" +
                         "8. getIdUsuario()\n" +
                         "9. getIdTablero()\n" +
                         "10. getTiempo()\n" +
                         "11. esTerminada()\n" +
                         "12. setTiempo(int x)\n" +
                         "13. setTerminada()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        return;
                    case 1:
                        io.write("Introduce los parametros:\n");
                        idpartida = io.readword();
                        idusuario = io.readword();
                        idtablero = io.readword();
                        partida = new Partida(idpartida, idusuario, idtablero);
                        break;
                    case 2:
                        if (partida == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida\n\n");
                        }
                        else {
                            io.write("Introduce el nombre que quieres guardar:\n");
                            idpartidaguardada = io.readword();
                            fecha = new Fecha();
                            fecha.hoy();
                            hora = new Hora();
                            hora.ahora();
                            partidaGuardada = new PartidaGuardada
                              (idpartidaguardada, partida, fecha, hora);
                        }
                        break;
                    case 3:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("La fecha es:\n");
                            io.write(partidaGuardada.obtenerFecha().obtenerFechaFormal() +
                                     "\n\n");
                        }
                        break;
                    case 4:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("Introduce el dia:\n");
                            dia = io.readint();
                            if (dia < 0 || dia > 31) {
                                io.write("Dia no valido:\n\n");
                                break;
                            }
                            io.write("Introduce el mes:\n");
                            mes = io.readint();
                            if (mes < 0 || mes > 12) {
                                io.write("mes no valido\n\n");
                                break;
                            }
                            io.write("Introduce el anio:\n");
                            anio = io.readint();
                            fecha = new Fecha();
                            fecha.ponerFecha(dia, mes, anio);
                            partidaGuardada.introducirFecha(fecha);
                        }
                        break;
                    case 5:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("La hora es:\n");
                            io.write(partidaGuardada.obtenerHora().obtenerHoraFormal() +
                                     "\n\n");
                        }
                        break;
                    case 6:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("Introduce las horas:\n");
                            horas = io.readint();
                            if (horas < 0 || horas > 23) {
                                io.write("Hora no valida:\n\n");
                                break;
                            }
                            io.write("Introduce los minutos:\n");
                            minutos = io.readint();
                            if (minutos < 0 || minutos > 59) {
                                io.write("Minutos no validos\n\n");
                                break;
                            }
                            io.write("Introduce los segundos:\n");
                            segundos = io.readint();
                            if (segundos < 0 || segundos > 59) {
                                io.write("Segundos no validos:\n\n");
                                break;
                            }
                            hora = new Hora();
                            hora.ponerHora(horas, minutos, segundos);
                            partidaGuardada.introducirHora(hora);
                        }
                        break;
                    case 7:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("El id de la partida es:\n");
                            io.write(partidaGuardada.getIdPartida() + "\n\n");
                        }
                        break;
                    case 8:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("El id del usuario es:\n");
                            io.write(partidaGuardada.getIdUsuario() + "\n\n");
                        }
                        break;
                    case 9:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("El id del tablero es:\n");
                            io.write(partidaGuardada.getIdTablero() + "\n\n");
                        }
                        break;
                    case 10:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("El tiempo de la partida es:\n");
                            io.write(partidaGuardada.getTiempo() + "\n\n");
                        }
                        break;
                    case 11:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("La partida esta finalizada?\n");
                            io.write(partidaGuardada.esTerminada().toString() + "\n\n");
                        }
                        break;
                    case 12:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            io.write("Introduce los segundos:\n");
                            segundos = io.readint();
                            partidaGuardada.setTiempo(segundos);
                            io.writeln();
                        }
                        break;
                    case 13:
                        if (partidaGuardada == null) {
                            io.write("No has creado ninguna instancia " +
                                     "de partida guardada\n\n");
                        }
                        else {
                            partidaGuardada.setTerminada();
                            io.writeln("\n");
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
