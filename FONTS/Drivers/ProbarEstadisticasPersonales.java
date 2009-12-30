
package Tests;

import Dominio.EstadisticasPersonales;
import DriverTBC.inout;
import java.util.ArrayList;

public class ProbarEstadisticasPersonales {

    public static void probar() {

        EstadisticasPersonales estadisticasPersonales = null;
        boolean actuTotalPartidas = false;
        boolean actuTiempoTotal = false;
        boolean atras = false;

        while(!atras) {
            try {
                inout io = new inout();
                int option, parametro;
                char siNo;
                String usuario;
                ArrayList<Object> listaEstadisticas;

                io.write("Operacion:\n" +
                         "1. EstadisticasPersonales(String usuario)\n" +
                         "2. inicializarEstadisticasPersonales()\n" +
                         "3. incrementarTiempoTotalDeJuego(int inc)\n" +
                         "4. obtenerTiempoTotalDeJuego()\n" +
                         "5. obtenerPartidasResueltas()\n" +
                         "6. incrementarTotalPartidas(boolean resuelta)\n" +
                         "7. obtenerTotalPartidas()\n" +
                         "8. introducirPosicionRanking(int posicion)\n" +
                         "9. obtenerPosicionRanking()\n" +
                         "10. incrementarTablerosPropuestos()\n" +
                         "11. obtenerTablerosPropuestos()\n" +
                         "12. incrementarPuntuacionTotal(int inc)\n" +
                         "13. obtenerPuntuacionTotal()\n" +
                         "14. obtenerUsuario()\n" +
                         "15. obtenerNivelExperiencia()\n" +
                         "16. obtenerTiempoMedioPartidaResuelta()\n" +
                         "17. obtenerPorcentajePartidasResueltas()\n" +
                         "18. obtenerPuntuacionMedia()\n" +
                         "19. obtenerEstadisticas()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        io.write("Introduce un nombre de usuario:\n");
                        usuario = io.readword();
                        estadisticasPersonales = new EstadisticasPersonales(usuario);
                        io.write("\n");
                        break;
                    case 2:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            estadisticasPersonales.inicializarEstadisticasPersonales();
                            io.write("\n\n");
                        }
                        break;
                    case 3:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            while (parametro < 0) {
                                io.write("Parametro no valido " +
                                         "(debe ser mayor o igual que 0)\n");
                                io.write("Introduce el parametro:\n");
                                parametro = io.readint();
                            }
                            estadisticasPersonales.incrementarTiempoTotalDeJuego(parametro);
                            actuTiempoTotal = true;
                        }
                        break;
                    case 4:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("TiempoTotalDeJuego es:\n");
                            io.write(estadisticasPersonales.obtenerTiempoTotalDeJuego() +
                                     "\n\n");
                        }
                        break;
                    case 5:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("PartidasResueltas es:\n");
                            io.write(estadisticasPersonales.obtenerPartidasResueltas() +
                                     "\n\n");
                        }
                        break;
                    case 6:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else if (!actuTiempoTotal) {
                            io.write("El tiempo total de juego no esta actualizado.\n\n");
                        }
                        else {
                            io.write("La partida esta resuelta? (s/n)\n");
                            siNo = io.read();
                            while (siNo != 's' && siNo != 'n') {
                                io.write("Por favor, introduce 's' o 'n'\n");
                                siNo = io.read();
                            }
                            if (siNo == 's') estadisticasPersonales.incrementarTotalPartidas(true);
                            else estadisticasPersonales.incrementarTotalPartidas(false);
                            io.write("\n");
                            actuTotalPartidas = true;
                            actuTiempoTotal = false;
                        }
                        break;
                    case 7:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Total de partidas es:\n");
                            io.write(estadisticasPersonales.obtenerTotalPartidas() +
                                     "\n\n");
                        }
                        break;
                    case 8:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            while (parametro <= 0) {
                                io.write("Parametro no valido (debe ser mayor o igual " +
                                         "que 1)\n");
                                io.write("Introduce el parametro:\n");
                                parametro = io.readint();
                            }
                            estadisticasPersonales.introducirPosicionRanking(parametro);
                            io.write("\n");
                        }
                        break;
                    case 9:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Posicion Ranking es:\n");
                            io.write(estadisticasPersonales.obtenerPosicionRanking() +
                                     "\n\n");
                        }
                        break;
                    case 10:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            estadisticasPersonales.incrementarTablerosPropuestos();
                            io.write("\n");
                        }
                        break;
                    case 11:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Tableros propuestos es:\n");
                            io.write(estadisticasPersonales.obtenerTablerosPropuestos() +
                                     "\n\n");
                        }
                        break;
                    case 12:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else if (!actuTotalPartidas) {
                            io.write("El total de partidas no esta actualizado.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasPersonales.incrementarPuntuacionTotal(parametro);
                            io.write("\n");
                            actuTotalPartidas = false;
                        }
                        break;
                    case 13:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Puntuacion total es:\n");
                            io.write(estadisticasPersonales.obtenerPuntuacionTotal() +
                                     "\n\n");
                        }
                        break;
                    case 14:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Usuario es:\n");
                            io.write(estadisticasPersonales.obtenerUsuario() +
                                     "\n\n");
                        }
                        break;
                    case 15:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Nivel de Experiencia es:\n");
                            io.write(estadisticasPersonales.obtenerNivelExperiencia() +
                                     "\n\n");
                        }
                        break;
                    case 16:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Tiempo medio partida resuelta es:\n");
                            io.write
                              (estadisticasPersonales.obtenerTiempoMedioPartidaResuelta()
                               + "\n\n");
                        }
                        break;
                    case 17:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Porcentaje de partidas resueltas es:\n");
                            io.write
                              (estadisticasPersonales.obtenerPorcentajePartidasResueltas()
                                + "\n\n");
                        }
                        break;
                    case 18:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Puntuacion media es:\n");
                            io.write(estadisticasPersonales.obtenerPuntuacionMedia() +
                                     "\n\n");
                        }
                        break;
                    case 19:
                        if (estadisticasPersonales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasPersonales.\n\n");
                        }
                        else {
                            io.write("Todas las estadisticas personales son:\n");
                            listaEstadisticas =
                              estadisticasPersonales.obtenerEstadisticas();
                            io.write(listaEstadisticas.get(0).toString() + "\n");
                            io.write(listaEstadisticas.get(1).toString() + "\n");
                            io.write(listaEstadisticas.get(2).toString() + "\n");
                            io.write(listaEstadisticas.get(3).toString() + "\n");
                            io.write(listaEstadisticas.get(4).toString() + "\n");
                            io.write(listaEstadisticas.get(5).toString() + "\n");
                            io.write(listaEstadisticas.get(6).toString() + "\n");
                            io.write(listaEstadisticas.get(7).toString() + "\n");
                            io.write(listaEstadisticas.get(8).toString() + "\n");
                            io.write(listaEstadisticas.get(9).toString() + "\n\n");
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
