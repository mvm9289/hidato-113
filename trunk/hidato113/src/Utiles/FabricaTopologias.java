/*  Class FabricaTopologias:
    Descripcion: Clase encargada de fabricar ciertas topologias (triangulo, rombo, elipse)
        dentro de una matriz de enteros.
    Autor: miguel.angel.vico
    Colaboradores: alex.catarineu
    Revisado: 20/12/2009 14:40 */

package Utiles;

public class FabricaTopologias {

    /* Crea un triangulo isosceles de base 'base' y altura 'altura' y lo retorna en una
        matriz de enteros */
    public static int[][] triangulo(int base, int altura) {

        int[][] triangulo = new int[altura][base];
        int mitadX = (base - 1) / 2;

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j <= mitadX; ++j) {
                if (estaDentroTriangulo(j, i, mitadX, 0, 0, altura - 1, mitadX,
                  altura - 1)) {
                    triangulo[i][j] = 0;
                    triangulo[i][base - j - 1] = 0;
                }
                else {
                    triangulo[i][j] = -1;
                    triangulo[i][base - j - 1] = -1;
                }
            }
        }

        return triangulo;
    }

    /* Crea un rombo cuyas dimensiones son 'ejeX' para el eje horizontal y 'ejeY' para
        el eje vertical y lo retorna en una matriz de enteros */
    public static int[][] rombo(int ejeX, int ejeY) {

        int[][] rombo = new int[ejeY][ejeX];
        int mitadX = (ejeX - 1) / 2;
        int mitadY = (ejeY - 1) / 2;

        for (int i = 0; i <= mitadY; ++i) {
            for (int j = 0; j <= mitadX; ++j) {
                if (estaDentroTriangulo(j, i, mitadX, 0, 0, mitadY, mitadX, mitadY)) {
                    rombo[i][j] = 0;
                    rombo[ejeY - i - 1][j] = 0;
                    rombo[i][ejeX - j - 1] = 0;
                    rombo[ejeY - i - 1][ejeX - j - 1] = 0;
                }
                else {
                    rombo[i][j] = -1;
                    rombo[ejeY - i - 1][j] = -1;
                    rombo[i][ejeX - j - 1] = -1;
                    rombo[ejeY - i - 1][ejeX - j - 1] = -1;
                }
            }
        }
        return rombo;
    }

    /* Crea una elipse cuyas dimensiones son 'ejeX' para el eje horizontal y 'ejeY' para
        el eje vertical y la retorna en una matriz de enteros */
    public static int[][] elipse(int ejeX, int ejeY) {

        int[][] elipse = new int[ejeY][ejeX];
        double centroX = (double)(ejeX - 1) / 2.0;
        double centroY = (double)(ejeY - 1) / 2.0;
        double radioX = (double)ejeX / 2.0;
        double radioY = (double)ejeY / 2.0;

        for (int i = 0; i < ejeY; ++i)
            for (int j = 0; j < ejeX; ++j) {
                if (estaDentroElipse(j , i, centroX, centroY, radioX, radioY))
                    elipse[i][j] = 0;
                else elipse[i][j] = -1;
            }

        return elipse;
    }

    /* Dados tres puntos (aX, aY), (bX, bY) y (cX, cY) que forman un triangulo, retorna
        cierto si el punto (x, y) se encuentra en el interior de dicho triangulo */
    private static boolean estaDentroTriangulo(int x, int y, int aX, int aY,
     int bX, int bY, int cX, int cY) {

        int fAB = (y - aY) * (bX - aX) - (x - aX) * (bY - aY);
        int fCA = (y - cY) * (aX - cX) - (x - cX) * (aY - cY);
        int fBC = (y - bY) * (cX - bX) - (x - bX) * (cY - bY);

        return (fAB * fBC >= 0) && (fBC * fCA >= 0);
    }

    /* Dados los dos radios radioX y radioY, y el punto central (centroX, centroY) que
        forman una elipse, retorna cierto si el punto (x, y) se encuentra en el interior
        de dicha elipse */
    private static boolean estaDentroElipse(double x, double y, double centroX,
      double centroY, double radioX, double radioY) {

        double dx = (x - centroX) / radioX;
        double dy = (y - centroY) / radioY;

        return (dx * dx + dy * dy) < 1.0;
    }
}