
package Tests;

import Dominio.TableroHidato;
import Dominio.TableroHidatoEnCurso;
import Dominio.TableroHidatoPropuesto;
import DriverTBC.inout;

public class ProbarTableroHidato {

    public static void probar() {

        TableroHidatoPropuesto tabPropuesto = null;
        TableroHidatoEnCurso tabEnCurso = null;
        TableroHidato tablero = null;
        boolean atras = false;
        int posX;
        int posY;
        int valor;
        boolean bool;

        while(!atras) {
            try {
                inout io = new inout();
                int option;

                io.write("Operacion:\n" +
                         "1. TableroHidatoPropuesto(String id, int anchura, int altura," +
                           " int dificultad, int topologia, String usuario)\n" +
                         "2. TableroHidatoEnCurso(String id, TableroHidatoOriginal " +
                           "tablero)\n" +
                         "3. getTablero()\n" +
                         "4. getAnchura()\n" +
                         "5. getAltura()\n" +
                         "6. resetearTablero()\n" +
                         "7. introducirValor(int posicionX, int posicionY, int valor)\n" +
                         "8. quitarValor(int posicionX, int posicionY)\n" +
                         "9. esVacia(int posicionX, int posicionY)\n" +
                         "10. obtenerValor(int posicionX, int posicionY)\n" +
                         "11. setPrefijada(int posicionX, int posicionY, boolean prefijada)\n" +
                         "12. esPrefijada(int posicionX, int posicionY)\n" +
                         "13. setActiva(int posicionX, int posicionY, boolean activa)\n" +
                         "14. esActiva(int posicionX, int posicionY)\n" +
                         "15. obtenerDificultad()\n" +
                         "16. obtenerTopologia()\n" +
                         "17. obtenerUsuario()\n" +
                         "18. obtenerPistas()\n" +
                         "19. incrementarPistas()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        io.write("Introduce los parametros:\n");
                        io.write("Posibles valores de dificultad y topologia:\n");
                        io.write("DIF_FACIL", 20);
                        io.write("0\n");
                        io.write("DIF_MEDIO", 20);
                        io.write("1\n");
                        io.write("DIF_DIFICIL", 20);
                        io.write("2\n");
                        io.write("DIF_PERSONALIZADA", 20);
                        io.write("3\n");
                        io.write("TOP_RECTANGULO", 20);
                        io.write("0\n");
                        io.write("TOP_TRIANGULO", 20);
                        io.write("1\n");
                        io.write("TOP_ROMBO", 20);
                        io.write("2\n");
                        io.write("TOP_ELIPSE", 20);
                        io.write("3\n");
                        io.write("TOP_PERSONALIZADA", 20);
                        io.write("4\n");
                        tabPropuesto = new TableroHidatoPropuesto(io.readword(),
                          io.readint(), io.readint(), io.readint(), io.readint(),
                          io.readword());
                        io.writeln();
                        break;
                    case 2:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                                     "TableroHidatoPropuesto.\n\n");
                        }
                        else {
                            io.write("Introduce un ID:\n");
                            tabEnCurso = new TableroHidatoEnCurso(io.readword(),
                              tabPropuesto);
                            io.writeln();
                        }
                        break;
                    case 3:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("El id del tablero es:\n");
                            io.write(tablero.getTablero() + "\n\n");
                        }
                        break;
                    case 4:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("La anchura del tablero es:\n");
                            io.write(tablero.getAnchura() + "\n\n");
                        }
                        break;
                    case 5:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("La altura del tablero es:\n");
                            io.write(tablero.getAltura() + "\n\n");
                        }
                        break;
                    case 6:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            tablero.resetearTablero();
                            io.writeln();
                        }
                        break;
                    case 7:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            io.write("Introduce valor:\n");
                            valor = io.readint();
                            while (valor < 1) {
                                io.write("valor debe ser mayor que 0. Introduce valor:\n");
                                valor = io.readint();
                            }
                            tablero.introducirValor(posX, posY, valor);
                            io.writeln();
                        }
                        break;
                    case 8:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            tablero.quitarValor(posX, posY);
                            io.writeln();
                        }
                        break;
                    case 9:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            io.write("esVacia = ");
                            io.writeln(tablero.esVacia(posX, posY));
                            io.writeln();
                        }
                        break;
                    case 10:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            io.writeln("El valor es:");
                            io.writeln(tablero.obtenerValor(posX, posY));
                            io.writeln();
                        }
                        break;
                    case 11:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            io.writeln("Prefijada? (true/false)");
                            tablero.setPrefijada(posX, posY, io.readboolean());
                            io.writeln();
                        }
                        break;
                    case 12:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            io.write("esPrefijada = ");
                            io.writeln(tablero.esPrefijada(posX, posY));
                            io.writeln();
                        }
                        break;
                    case 13:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            io.writeln("Activa? (true/false)");
                            tablero.setActiva(posX, posY, io.readboolean());
                            io.writeln();
                        }
                        break;
                    case 14:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto ni de TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            if(tabEnCurso == null) tablero = tabPropuesto;
                            else {
                                io.write("Indica sobre que tablero quieres aplicar la " +
                                  "operacion:\n1. TableroHidatoPropuesto\n" +
                                  "2. TableroHidatoEnCurso\n");
                                option = io.readint();
                                while (option != 1 && option != 2) {
                                    io.write("Opcion incorrecta. Indica una opcion correcta.\n");
                                    option = io.readint();
                                }
                                if (option == 1) tablero = tabPropuesto;
                                else tablero = tabEnCurso;
                            }
                            io.write("Introduce posicionX:\n");
                            posX = io.readint();
                            while (posX < 0 || posX >= tablero.getAnchura()) {
                                io.write("posicionX incorrecta. " +
                                         "Introduce posicionX:\n");
                                posX = io.readint();
                            }
                            io.write("Introduce posicionY:\n");
                            posY = io.readint();
                            while (posY < 0 || posY >= tablero.getAltura()) {
                                io.write("posicionY incorrecta. " +
                                         "Introduce posicionY:\n");
                                posY = io.readint();
                            }
                            io.write("esActiva = ");
                            io.writeln(tablero.esActiva(posX, posY));
                            io.writeln();
                        }
                        break;
                    case 15:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto.\n\n");
                        }
                        else {
                            io.writeln("La dificultad es:");
                            io.writeln(tabPropuesto.obtenerDificultad());
                            io.writeln();
                        }
                        break;
                    case 16:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto.\n\n");
                        }
                        else {
                            io.writeln("La topologia es:");
                            io.writeln(tabPropuesto.obtenerTopologia());
                            io.writeln();
                        }
                        break;
                    case 17:
                        if (tabPropuesto == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoPropuesto.\n\n");
                        }
                        else {
                            io.writeln("El usuario es:");
                            io.writeln(tabPropuesto.obtenerUsuario());
                            io.writeln();
                        }
                        break;
                    case 18:
                        if (tabEnCurso == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            io.writeln("Las pistas son:");
                            io.writeln(tabEnCurso.obtenerPistas());
                            io.writeln();
                        }
                        break;
                    case 19:
                        if (tabEnCurso == null) {
                            io.write("No has creado una instancia de " +
                              "TableroHidatoEnCurso.\n\n");
                        }
                        else {
                            io.write("Introduce el numero de pistas:\n");
                            tabEnCurso.incrementarPistas(io.readint());
                            io.writeln();
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
                System.out.println();
            }
        }
    }
}
