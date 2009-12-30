
package Tests;

import Dominio.ControladorPartida;
import DriverTBC.inout;
import java.util.ArrayList;

public class ProbarControladorPartida {

    public static void probar() {

        ControladorPartida ctrlPartida = ControladorPartida.getInstance();
        boolean partidaEnCurso = false;
        String partidaActual = null;
        
        while(true) {
            try {
                inout io = new inout();
                int option, puntuacion, tiempo, pistas, ordenacion;
                String id, tablero, usuario;
                int[] tiempoAnterior;
                String[] tableroAnterior;

                io.write("Operacion:\n" +
                         "1. nuevaPartida(String tablero, String usuario)\n" +
                         "2. guardarPartida(String id, int tiempo)\n" +
                         "3. cargarPartida(String id, Integer tiempo, String tablero)\n" +
                         "4. finalizarPartida(int puntuacion, int tiempo, int pistas)\n" +
                         "5. eliminarPartida()\n" +
                         "6. mostrarPartidasGuardadas(int opcion)\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        return;
                    case 1:
                        if (partidaEnCurso) {
                            io.write("Hay una partida en curso, antes debes antes debes " +
                                     "guardar, finalizar o eliminar.\n\n");
                        }
                        else {
                            io.write("Introduce los parametros:\n");
                            tablero = io.readword();
                            usuario = io.readword();
                            io.writeln();
                            io.write("El nuevo identificador es:\n");
                            partidaActual = ctrlPartida.nuevaPartida(tablero, usuario);
                            io.write(partidaActual);
                            io.write("\n\n");
                            partidaEnCurso = true;
                        }
                        break;
                    case 2:
                        if (!partidaEnCurso) {
                            io.write("No hay ninguna partida en curso\n\n");
                        }
                        else {
                            io.write("Introduce los parametros:\n");
                            id = io.readword();
                            tiempo = io.readint();
                            ctrlPartida.guardarPartida(id, tiempo);
                            partidaEnCurso = false;
                        }
                        break;
                    case 3:
                        if (partidaEnCurso) {
                            io.write("Hay una partida en curso, antes debes guardar, " +
                                     "finalizar o eliminar\n\n");
                        }
                        else {
                            io.write("Introduce el identificador:\n");
                            id = io.readword();
                            tiempoAnterior = new int[1];
                            tableroAnterior = new String[1];
                            ctrlPartida.cargarPartida(id, tiempoAnterior, tableroAnterior);
                            io.write("El tiempo de la partida guardada es:\n");
                            io.write(tiempoAnterior[0]);
                            io.write("\n");
                            io.write("El identificador de tablero de la" +
                                     " partida guardada es:\n");
                            io.write(tableroAnterior[0]);
                            io.write("\n");
                            partidaEnCurso = true;
                        }
                        break;
                    case 4:
                        if (!partidaEnCurso) {
                            io.write("No hay ninguna partida en curso\n\n");
                        }
                        else {
                            io.write("Introduce los parametros:\n");
                            puntuacion = io.readint();
                            tiempo = io.readint();
                            pistas = io.readint();
                            puntuacion = ctrlPartida.finalizarPartida(puntuacion, tiempo, pistas);
                            io.write("La puntuacion de la partida ha sido: ");
                            io.writeln(puntuacion);
                            partidaEnCurso = false;
                            io.writeln();
                        }
                        break;
                    case 5:
                        if (!partidaEnCurso) {
                            io.write("No hay ninguna partida en curso\n\n");
                        }
                        else {
                            ctrlPartida.eliminarPartida(partidaActual, new String[1], new int[1]);
                            partidaEnCurso = false;
                        }
                        break;
                    case 6:
                        io.write("Introduce el criterio por el que quieres ordenar" +
                                 "la lista de partidas guardadas:\n");
                        io.write("ORDENACION_POR_NOMBRE", 24);
                        io.writeln(1);
                        io.write("ORDENACION_POR_FECHA", 24);
                        io.writeln(2);

                        ordenacion = io.readint();
                        if (ordenacion != ControladorPartida.ORDENACION_POR_NOMBRE &&
                          ordenacion != ControladorPartida.ORDENACION_POR_FECHA) {
                            io.write("No has introducido un criterio valido.\n\n");
                            break;
                        }
                        io.write("Introduce el usuario del que quieres que se muestren " +
                                 "las partidas:\n");
                        usuario = io.readword();
                        ArrayList<Object[]> lista =
                          ctrlPartida.mostrarPartidasGuardadas(ordenacion, usuario);

                        for (int i = 0; i < lista.size(); ++i) {
                            Object[] tupla = lista.get(i);
                            io.write((String)tupla[0] + '\t');
                            io.write((String)tupla[1] + '\t');
                            io.write((String)tupla[2] + '\n');
                        }
                        io.write("\n");
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
