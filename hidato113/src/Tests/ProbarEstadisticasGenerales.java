
package Tests;

import Dominio.EstadisticasHidato;
import DriverTBC.inout;
import java.util.List;

public class ProbarEstadisticasGenerales {

    public static void probar() {

        EstadisticasHidato estadisticasGenerales = null;
        boolean atras = false;

        while(!atras) {
            try {
                inout io = new inout();
                int option, parametro;
                List<Integer> listaEstadisticas;

                io.write("Operacion:\n" +
                         "1. EstadisticasGenerales()\n" +
                         "2. obtenerTotalPartidas()\n" +
                         "3. incrementarTotalPartidas(int inc)\n" +
                         "4. obtenerPartidasAbandonadas()\n" +
                         "5. incrementarPartidasAbandonadas(int inc)\n" +
                         "6. obtenerPartidasResueltas()\n" +
                         "7. incrementarPartidasResueltas(int inc)\n" +
                         "8. obtenerPartidasFacil()\n" +
                         "9. incrementarPartidasFacil(int inc)\n" +
                         "10. obtenerPartidasMedio()\n" +
                         "11. incrementarPartidasMedio(int inc)\n" +
                         "12. obtenerPartidasDificil()\n" +
                         "13. incrementarPartidasDificil(int inc)\n" +
                         "14. obtenerTotalUsuarios()\n" +
                         "15. incrementarTotalUsuarios(int inc)\n" +
                         "16. obtenerTotalTableros()\n" +
                         "17. incrementarTotalTableros()\n" +
                         "18. obtenerTablerosGenerados()\n" +
                         "19. incrementaTablerosGenerados()\n" +
                         "20. obtenerTablerosPropuestos()\n" +
                         "21. incrementarTablerosPropuestos()\n" +
                         "22. obtenerEstadisticas()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        estadisticasGenerales = new EstadisticasHidato();
                        io.write("\n");
                        break;
                    case 2:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Total Partidas es:\n");
                            io.write(estadisticasGenerales.obtenerTotalPartidas() +
                                     "\n\n");
                        }
                        break;
                    case 3:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasGenerales.incrementarTotalPartidas(parametro);
                            io.write("\n");
                        }
                        break;
                    case 4:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Partidas Abandonadas es:\n");
                            io.write(estadisticasGenerales.obtenerPartidasAbandonadas() +
                                     "\n\n");
                        }
                        break;
                    case 5:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasGenerales
                              .incrementarPartidasAbandonadas(parametro);
                            io.write("\n");
                        }
                        break;
                    case 6:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Partidas Resueltas es:\n");
                            io.write(estadisticasGenerales.obtenerPartidasResueltas() +
                                     "\n\n");
                        }
                        break;
                    case 7:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasGenerales.incrementarPartidasResueltas(parametro);
                            io.write("\n");
                        }
                        break;
                    case 8:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Partidas Facil es:\n");
                            io.write(estadisticasGenerales.obtenerPartidasFacil() +
                                     "\n\n");
                        }
                        break;
                    case 9:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasGenerales.incrementarPartidasFacil(parametro);
                            io.write("\n");
                        }
                        break;
                    case 10:
                       if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Partidas Medio es:\n");
                            io.write(estadisticasGenerales.obtenerPartidasMedio() +
                                     "\n\n");
                        }
                        break;
                    case 11:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasGenerales.incrementarPartidasMedio(parametro);
                            io.write("\n");
                        }
                        break;
                    case 12:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Partidas Dificil es:\n");
                            io.write(estadisticasGenerales.obtenerPartidasDificil() +
                                     "\n\n");
                        }
                        break;
                    case 13:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasGenerales.incrementarPartidasDificil(parametro);
                            io.write("\n");
                        }
                        break;
                    case 14:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Total Usuarios es:\n");
                            io.write(estadisticasGenerales.obtenerTotalUsuarios() +
                                     "\n\n");
                        }
                        break;
                    case 15:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Introduce el parametro:\n");
                            parametro = io.readint();
                            estadisticasGenerales.incrementarTotalUsuarios(parametro);
                            io.write("\n");
                        }
                        break;
                    case 16:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Total Tableros es:\n");
                            io.write(estadisticasGenerales.obtenerTotalTableros() +
                                     "\n\n");
                        }
                        break;
                    case 17:
                       if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            estadisticasGenerales.incrementarTotalTableros(1);
                            io.write("\n");
                        }
                        break;
                    case 18:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Tableros Generados es:\n");
                            io.write(estadisticasGenerales.obtenerTablerosGenerados() +
                                     "\n\n");
                        }
                        break;
                    case 19:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            estadisticasGenerales.incrementarTablerosGenerados(1);
                            io.write("\n");
                        }
                        break;
                    case 20:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Tableros Propuestos es:\n");
                            io.write(estadisticasGenerales.obtenerTablerosPropuestos() +
                                     "\n\n");
                        }
                        break;
                    case 21:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            estadisticasGenerales.incrementarTablerosPropuestos(1);
                            io.write("\n");
                        }
                        break;
                    case 22:
                        if (estadisticasGenerales == null) {
                            io.write("No has creado una instancia de " +
                                     "EstadisticasGenerales.\n\n");
                        }
                        else {
                            io.write("Todas las estadisticas generales son:\n");
                            listaEstadisticas =
                              estadisticasGenerales.obtenerEstadisticas();
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
