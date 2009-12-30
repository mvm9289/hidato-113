
package Tests;

import Dominio.ControladorEstadisticas;
import DriverTBC.inout;
import Tiempo.Fecha;
import java.util.ArrayList;
import java.util.List;

public class ProbarControladorEstadisticas {

    private final static String[] constantes = {null, "TOTAL_PARTIDAS",
      "PARTIDAS_ABANDONADAS", "PARTIDAS_RESUELTAS", "PARTIDAS_FACIL", "PARTIDAS_MEDIO",
      "PARTIDAS_DIFICIL", "TOTAL_USUARIOS", "TOTAL_TABLEROS", "TABLEROS_GENERADOS",
      "TABLEROS_PROPUESTOS", "NIVEL_EXPERIENCIA", "TIEMPO_TOTAL_JUEGO",
      "TIEMPO_MEDIO_PARTIDA_RESUELTA", "PARTIDAS_RESUELTAS_PERSONALES",
      "x100_PARTIDAS_RESUELTAS", "TOTAL_PARTIDAS_PERSONALES", "POSICION_RANKING",
      "TABLEROS_PROPUESTOS_PERSONALES", "PUNTUACION_TOTAL", "PUNTUACION_MEDIA"};

    public static void probar() {

        ControladorEstadisticas ctrlEstadisticas = ControladorEstadisticas.getInstance();
        List<Object[]> listaTuplas;
        Object[] tupla;
        boolean iniPers = false;
        boolean atras = false;
        int inInt;
        ArrayList<Object[]> listaTuplas2;

        while(!atras) {
            try {
                inout io = new inout();
                int option;

                io.write("Operacion:\n" +
                         "1. cargarEstadisticasPersonales(String usuario)\n" +
                         "2. consultarRanking(int nbPosiciones)\n" +
                         "3. nuevaPosicion(int puntuacion, String usuario)\n" +
                         "4. mostrarEstadisticasPersonales()\n" +
                         "5. mostrarEstadisticasGenerales()\n" +
                         "6. resetearEstadisticas()\n" +
                         "7. actualizarEstadisticasPersonales" +
                           "(ArrayList<Object[]> estadisticas)\n" +
                         "8. actualizarEstadisticasGenerales" +
                           "(ArrayList<Object[]> estadisticas)\n" +
                         "9. eliminarEstadisticas()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        io.write("Introduce el parametro:\n");
                        ctrlEstadisticas.cargarEstadisticasPersonales(io.readword());
                        io.write("\n");
                        iniPers = true;
                        break;
                    case 2:
                        io.write("Introduce cuantas posiciones quieres que muestre:\n");
                        listaTuplas = ctrlEstadisticas.consultarRanking(io.readint());
                        for(int i = 0; i < listaTuplas.size(); ++i) {
                            tupla = listaTuplas.get(i);
                            io.write(String.valueOf(i + 1) + " " + tupla[1] + " " +
                              tupla[0] + " " + ((Fecha)tupla[2]).obtenerFechaFormal() +
                              "\n");
                        }
                        io.write("\n");
                        break;
                    case 3:
                        io.write("Introduce los parametros:\n");
                        ctrlEstadisticas.nuevaPosicion(io.readint(), io.readword());
                        io.write("\n");
                        break;
                    case 4:
                        if (!iniPers) {
                            io.write("No has cargado las estadisticas personales.\n\n");
                        }
                        else {
                            listaTuplas =
                              ctrlEstadisticas.mostrarEstadisticasPersonales();
                            for (int i = 0; i < listaTuplas.size(); ++i) {
                                tupla = listaTuplas.get(i);
                                io.write(constantes[((Integer)tupla[0]).intValue()], 31);
                                io.write(tupla[1] + "\n");
                            }
                            io.write("\n");
                        }
                        break;
                    case 5:
                        listaTuplas =
                          ctrlEstadisticas.mostrarEstadisticasGenerales();
                        for (int i = 0; i < listaTuplas.size(); ++i) {
                            tupla = listaTuplas.get(i);
                            io.write(constantes[((Integer)tupla[0]).intValue()], 31);
                            io.write(tupla[1] + "\n");
                        }
                        io.write("\n");
                        break;
                    case 6:
                        if (!iniPers) {
                            io.write("No has cargado las estadisticas personales.\n\n");
                        }
                        else {
                            ctrlEstadisticas.resetearEstadisticas();
                            io.write("\n");
                        }
                        break;
                    case 7:
                        if (!iniPers) {
                            io.write("No has cargado las estadisticas personales.\n\n");
                        }
                        else {
                            io.write("Introduce la lista de las estadisticas a " +
                                     "actualizar:\nIndica el num. de la estadistica a " +
                                     "modificar seguida del valor. Para terminar " +
                                     "introduce 0.\n(Hay que tener en cuenta:\nPara " +
                                     "incrementar el total de partidas primero " +
                                     "hay que incrementar el tiempo total de juego." +
                                     "\nPara incrementar puntuacion total antes hay que" +
                                     " incrementar total partidas)\n");
                            io.write(constantes[12], 31);
                            io.write(12 + " (parametro: int)\n");
                            io.write(constantes[16], 31);
                            io.write(16 + " (parametro: boolean)\n");
                            io.write(constantes[17], 31);
                            io.write(17 + " (parametro: int)\n");
                            io.write(constantes[18], 31);
                            io.write(18 + "\n");
                            io.write(constantes[19], 31);
                            io.write(19 + " (parametro: int)\n\n");
                            listaTuplas2 = new ArrayList<Object[]>();
                            inInt = io.readint();
                            while(inInt != 0) {
                                tupla = new Object[2];
                                tupla[0] = inInt;
                                switch (inInt) {
                                    case 12:
                                        tupla[1] = io.readint();
                                        break;
                                    case 16:
                                        tupla[1] = io.readboolean();
                                        break;
                                    case 17:
                                        tupla[1] = io.readint();
                                        break;
                                    case 18:
                                        break;
                                    case 19:
                                        tupla[1] = io.readint();
                                        break;
                                    default: break;
                                }
                                listaTuplas2.add(tupla);
                                inInt = io.readint();
                            }
                            ctrlEstadisticas
                              .actualizarEstadisticasPersonales(listaTuplas2);
                        }
                        break;
                    case 8:
                        io.write("Introduce la lista de las estadisticas a " +
                                 "actualizar:\nIndica el num. de la estadistica a " +
                                 "modificar seguida del valor. Para terminar " +
                                 "introduce 0.\n");
                        for(int i = 1; i < 11; ++i) {
                            io.write(constantes[i], 31);
                            io.write(i);
                            if (i < 8) io.write(" (parametro: int)");
                            io.write("\n");
                        }
                        listaTuplas2 = new ArrayList<Object[]>();
                        inInt = io.readint();
                        while(inInt != 0) {
                            tupla = new Object[2];
                            tupla[0] = inInt;
                            if(inInt < 8) tupla[1] = io.readint();
                            listaTuplas2.add(tupla);
                            inInt = io.readint();
                        }
                        ctrlEstadisticas
                          .actualizarEstadisticasGenerales(listaTuplas2);
                        break;
                    case 9:
                        if (!iniPers) {
                            io.write("No has cargado las estadisticas personales.\n\n");
                        }
                        else {
                            ctrlEstadisticas.eliminarEstadisticas();
                            iniPers = false;
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }
}
