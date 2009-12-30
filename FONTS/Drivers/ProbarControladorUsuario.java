
package Tests;

import Dominio.ControladorUsuario;
import DriverTBC.inout;

public class ProbarControladorUsuario {

    public static void probar() {

        ControladorUsuario ctrlUsuario = ControladorUsuario.getInstance();
        boolean initSes = false;
        boolean atras = false;

        while(!atras) {
            try {
                inout io = new inout();
                int option;

                io.write("Operacion:\n" +
                         "1. iniciarSesion(String nickname, String password)\n" +
                         "2. modificarPassword(String passwordActual, " +
                           "String passwordNuevo)\n" +
                         "3. crearUsuario(String nickname, String password)\n" +
                         "4. eliminarUsuario()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        io.write("Introduce los parametros:\n");
                        ctrlUsuario.iniciarSesion(io.readword(), io.readword());
                        initSes = true;
                        io.write("\n");
                        break;
                    case 2:
                        if (!initSes) io.write("No has iniciado sesion.\n\n");
                        else {
                            io.write("Introduce los parametros:\n");
                            ctrlUsuario.modificarPassword(io.readword(), io.readword());
                            io.write("\n");
                        }
                        break;
                    case 3:
                        io.write("Introduce los parametros:\n");
                        ctrlUsuario.crearUsuario(io.readword(), io.readword());
                        io.write("\n");
                        break;
                    case 4:
                        if (!initSes) io.write("No has iniciado sesion.\n\n");
                        else {
                            ctrlUsuario.eliminarUsuario();
                            initSes = false;
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
