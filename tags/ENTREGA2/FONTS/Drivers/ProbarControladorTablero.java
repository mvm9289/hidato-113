
package Tests;

import Dominio.ControladorTablero;
import DriverTBC.inout;
import java.util.List;

public class ProbarControladorTablero {

    private static inout io = new inout();
    private static ControladorTablero ctrlTablero = ControladorTablero.getInstance();
    
    private static void imprimirTableroEnCurso() throws Exception {
        int res[][][] = ctrlTablero.obtenerContenidoTablero();
        int anchura = res[0].length;
        int altura = res[0][0].length;
        int aux = anchura*altura;
        int maxLength = 0;
        while (aux > 0) {
            aux /= 10;
            ++maxLength;
        }
        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                switch (res[1][j][i]) {
                    case ControladorTablero.CAS_INACTIVA:
                        io.write("", maxLength);
                        break;
                    default:
                        io.write(res[0][j][i], maxLength);
                        break;
                }
                io.write(" ");
            }
            io.write('\n');
        }
    }

    private static void imprimirTablero(int tablero[][]) throws Exception {
        int anchura = tablero.length;
        int altura = tablero[0].length;

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                if (tablero[j][i] == -1) io.write(" ", 4);
                else io.write(tablero[j][i], 4);
            }
            io.write('\n');
        }
    }
    
    public static void probar() {
        boolean tableroCargado = false;
        while(true) {
            try {
                int option, parametro;
                
                //List<Integer> listaEstadisticas;

                io.write("Operacion:\n" +
                         "1. generarTablero(String nombre, int anchura, int altura, " +
                           "int dificultad, int topologia, int numPrefijadas)\n" +
                         "2. proponerTablero(String nombre, int anchura, int altura, " +
                           "int dificultad, int topologia, int contenido[][], String " +
                           "usuario)\n" +
                         "3. darPista()\n" +
                         "4. resolverHidato()\n" +
                         "5. obtenerListaTableros(int opciones, String usuario)\n" +
                         "6. crearTableroEnCurso(String tableroOriginal)\n" +
                         "7. cargarTablero(String tableroEnCurso)\n" +
                         "8. insertarValor(x, y, valor)\n" +
                         "9. quitarValor(x, y)\n" +
                         "10. obtenerPuntuacion()\n" +
                         "11. obtenerPistas()\n" +
                         "12. eliminarTablero(String tablero)\n" +
                         "13. resetearTablero()\n" +
                         "14. guardarTablero()\n" +
                         "15. obtenerContenidoTablero()\n" +
                         "16. esTableroValido()\n" +
                         "17. probar algoritmo de resolucion \n" +
                         "0. Atras\n\n");
                option = io.readint();
                String nombre, usuario, tableroOriginal, tableroEnCurso, idTableroEnCurso;
                int anchura, altura, dificultad, topologia, contenido[][], opciones,
                  ordenacion, filtro, x, y, contenido2[][][];
                
                switch (option) {
                    case 0:
                        return;
                    case 1:
                        io.writeln("No implementado");
                        break;
                    case 2:
                        io.writeln("Introduce el nombre del tablero:");
                        nombre = io.readword();
                        io.writeln("Introduce la anchura del tablero:");
                        anchura = io.readint();
                        io.writeln("Introduce la altura del tablero:");
                        altura = io.readint();
                        io.writeln("Introduce la dificultad del tablero:");
                        dificultad = io.readint();
                        io.writeln("Introduce la topologia del tablero:");
                        topologia = io.readint();

                        io.writeln("Introduce el contenido del tablero, -1 para casillas"+
                          " inactivas, 0 para las vacias y el valor para prefijadas:");

                        contenido = new int[anchura][altura];
                        for (int i = 0; i < altura; ++i)
                            for (int j = 0; j < anchura; ++j)
                                contenido[j][i] = io.readint();

                        io.writeln("Introduce el usuario que ha propuesto el tablero:");
                        usuario = io.readword();

                        boolean res = ctrlTablero.proponerTablero(nombre, anchura, altura,
                          dificultad, topologia, contenido, usuario);
                        if (res) {
                            tableroCargado = true;
                            idTableroEnCurso = ctrlTablero.crearTableroEnCurso(nombre);
                            imprimirTableroEnCurso();
                        }
                        else io.writeln("Hidato sin solucion o con varias soluciones " +
                                        "(no valido)");
                        io.write("\n");
                        break;
                    case 3:
                        if (tableroCargado) {
                            contenido = ctrlTablero.darPista();
                            imprimirTablero(contenido);
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 4:
                        if (tableroCargado) {
                            contenido = ctrlTablero.resolverHidato();
                            imprimirTablero(contenido);
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 5:
                        io.writeln("Introduce la ordenacion deseada (id = 1, " +
                                   "topologia = 2, dificultad = 3");
                        ordenacion = io.readint();
                        io.writeln("Introduce el filtro deseado (todos = 1, propuestos " +
                                   "por un usuario = 2");
                        filtro = io.readint();
                        if (filtro == 2) {
                            io.writeln("Introduce el usuario");
                            usuario = io.readword();
                        }
                        else usuario = null;
                        opciones = 0;
                        if (ordenacion == 1) opciones |= ControladorTablero.ORD_ID;
                        else if (ordenacion == 2)
                            opciones |= ControladorTablero.ORD_TOPOLOGIA;
                        else opciones |= ControladorTablero.ORD_DIFICULTAD;
                        if (filtro == 1) opciones |= ControladorTablero.SEL_TODOS;
                        else opciones |= ControladorTablero.SEL_PROPUESTOS;
                        
                        List<Object[]> list =
                          ctrlTablero.obtenerListaTableros(opciones, usuario);
                        for (int i = 0; i < list.size(); ++i) {
                            io.writeln("Posicion " + ((Integer)i).toString());
                            Object[] obj = list.get(i);
                            io.writeln((String)obj[0]);
                            dificultad = ((Integer)obj[1]).intValue();
                            topologia = ((Integer)obj[2]).intValue();
                            if (dificultad == ControladorTablero.DIF_FACIL)
                                io.writeln("Dificultad facil");
                            else if (dificultad == ControladorTablero.DIF_MEDIO)
                                io.writeln("Dificultad media");
                            else if (dificultad == ControladorTablero.DIF_DIFICIL)
                                io.writeln("Dificultad dificil");
                            else io.writeln("Dificultad facil");
                        }
                        break;
                    case 6:
                        io.writeln("Introduce el id del tableroOriginal");
                        tableroOriginal = io.readword();
                        idTableroEnCurso = ctrlTablero.crearTableroEnCurso(tableroOriginal);
                        tableroCargado = true;
                        break;
                    case 7:
                        io.writeln("Introduce el id del tableroEnCurso");
                        tableroEnCurso = io.readword();
                        tableroCargado = true;
                        ctrlTablero.cargarTablero(tableroEnCurso);
                        
                        break;
                    case 8:
                        if (tableroCargado) {
                            io.writeln("Introduce las coordenadas (x, y) y el valor de " +
                                       "la casilla");
                            x = io.readint();
                            y = io.readint();
                            opciones = io.readint();
                            ctrlTablero.insertarValor(x, y, opciones);
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 9:
                        if (tableroCargado) {
                            io.writeln("Introduce las coordenadas (x, y)");
                            x = io.readint();
                            y = io.readint();
                            ctrlTablero.quitarValor(x, y);
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 10:
                        if (tableroCargado) {
                            io.writeln("La puntacion del tableroEnCurso es " +
                            ((Integer)ctrlTablero.obtenerPuntuacionTablero()).toString());
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 11:
                        if (tableroCargado) {
                            io.writeln("El numero de pistas de tableroEnCurso es " +
                              ((Integer)ctrlTablero.obtenerPistas()).toString());
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 12:
                        io.writeln("Introduce el tablero a eliminar");
                        tableroEnCurso = io.readword();
                        ctrlTablero.eliminarTablero(tableroEnCurso);
                        break;
                    case 13:
                        if (tableroCargado) ctrlTablero.resetearTablero();
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 14:
                        if (tableroCargado) ctrlTablero.guardarTablero();
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 15:
                        if (tableroCargado) {
                            contenido2 = ctrlTablero.obtenerContenidoTablero();
                            for (int i = 0; i < contenido2[0][0].length; ++i) {
                                for (int j = 0; j < contenido2[0].length; ++j) {
                                    switch (contenido2[1][j][i]) {
                                        case ControladorTablero.CAS_INACTIVA:
                                            io.write("", 4);
                                            break;
                                        default:
                                            io.write(contenido2[0][j][i], 4);
                                    }
                                }
                                io.write('\n');
                            }
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 16:
                        if (tableroCargado) {
                            if (ctrlTablero.esTableroValido())
                                io.writeln("El tablero está solucionado");
                            else
                                io.writeln("El tablero no está solucionado");
                        }
                        else io.writeln("No hay ningun tablero cargado");
                        break;
                    case 17:
                        io.writeln("Introduce la anchura del tablero:");
                        anchura = io.readint();
                        io.writeln("Introduce la altura del tablero:");
                        altura = io.readint();
                        io.writeln("Introduce el contenido del tablero, -1 para casillas"+
                          " inactivas, 0 para las vacias y el valor para prefijadas:");

                        contenido = new int[anchura][altura];
                        for (int i = 0; i < altura; ++i)
                            for (int j = 0; j < anchura; ++j)
                                contenido[j][i] = io.readint();

                        io.writeln("Resolviendo... (dependiendo del Hidato, " +
                                   "puede tardar)");
                        contenido =(new BuscarSolucion(anchura, altura, contenido))
                                        .obtenerSolucion();
                        if (contenido == null)
                            io.writeln("Hidato sin solucion o con varias soluciones " +
                                       "(no valido)");
                        else imprimirTablero(contenido);
                        break;
                    default:
                        break;
                }
                io.writeln();
            }
            catch (Exception ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }
}

