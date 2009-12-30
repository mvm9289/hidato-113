
package Tests;

import Dominio.Usuario;
import DriverTBC.inout;

public class ProbarUsuario {

    public static void probar() {

        Usuario usuario = null;
        boolean atras = false;

        while(!atras) {
            try {
                inout io = new inout();
                int option;

                io.write("Operacion:\n" +
                         "1. Usuario(String nickname, String password)\n" +
                         "2. modificarPassword(String nuevoPassword)\n" +
                         "3. obtenerUsuario()\n" +
                         "4. obtenerPassword()\n" +
                         "0. Atras\n\n");
                option = io.readint();

                switch (option) {
                    case 0:
                        atras = true;
                        break;
                    case 1:
                        io.write("Introduce los parametros:\n");
                        usuario = new Usuario(io.readword(), io.readword());
                        io.write("\n");
                        break;
                    case 2:
                        if (usuario == null) {
                            io.write("No has creado una instancia de Usuario.\n\n");
                        }
                        else {
                            io.write("Introduce los parametros:\n");
                            usuario.modificarPassword(io.readword());
                            io.write("\n");
                        }
                        break;
                    case 3:
                        if (usuario == null) {
                            io.write("No has creado una instancia de Usuario.\n\n");
                        }
                        else {
                            io.write("Usuario es:\n");
                            io.write(usuario.obtenerUsuario() + "\n\n");
                        }
                        break;
                    case 4:
                        if (usuario == null) {
                            io.write("No has creado una instancia de Usuario.\n\n");
                        }
                        else {
                            io.write("Password es:\n");
                            io.write(usuario.obtenerPassword() + "\n\n");
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
