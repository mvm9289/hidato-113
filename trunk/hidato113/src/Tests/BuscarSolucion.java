
package Tests;

import DriverTBC.inout;
import Utiles.Utiles;
import java.util.LinkedList;
import java.util.Queue;

public class BuscarSolucion {
    
     private static inout io = new inout();
     
     /* PRE: - */
     private void imprimirTablero(int tablero[][]) throws Exception {
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
    /* POST: Se escribe por salida estandard el contenido del tablero 'tablero' */

    private int tablero[][]; /* tablero sobre el que buscaremos la solucion */
    private int solucion[][]; /* tablero donde guardaremos la solucion */
    private int nSols; /* numero de soluciones encontradas (como mucho dos) */
    private int nRest; /* numero de casillas que me quedan por poner */
    private boolean usado[]; /* usado[i] nos dice si el numero i + 1 esta colocado */
    private int n; /* anchura del tablero */
    private int m; /* altura del tablero */
    private int maxCas; /* numero de la casilla mas grande */

    /* PRE: - */
    public BuscarSolucion(int anchura, int altura, int contenido[][]) {
        n = anchura;
        m = altura;
        tablero = Utiles.copiarMatriz(contenido);
        solucion = null;
        nSols = nRest = maxCas = 0;
        usado = new boolean[altura*anchura];

        for (int i = 0; i < altura*anchura; ++i)
            usado[i] = false;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (tablero[i][j] > 0) usado[tablero[i][j] - 1] = true;
                else if (tablero[i][j] == 0) ++nRest;
                maxCas = Math.max(maxCas, tablero[i][j]);
            }
        }

        backtrack();
    }
    /* POST: busca las soluciones (como mucho 2) para el tablero cuyo contenido es
     * 'contenido */

    /* PRE: - */
    public int[][] obtenerSolucion() {
        if (nSols < 2) return solucion;
        return null;
    }
    /* POST: Devuelve la matriz solucion del Hidato */

    /* La idea es que en cada llamada al backtracking seleccionamos la casilla que
       tiene menos casillas vacias a su alrededor, y probamos de rellenarla con todos
       los posibles valores, comprobando con una funcion que aun es posible resolver
       el hidato, en cuyo caso seguimos el proceso de backtracking. Cuando nRest == 0,
       augmentamos el numero de soluciones. Si en algun momento nRest == 2, cortamos,
       ya que hemos encontrado que el Hidato tiene mas de una solucion, por lo tanto
       no es valido */
    /* PRE: 'tablero' ha sido rellenado con los datos del contenido del Hidato */
    private void backtrack() {
        if (nRest == 0) {
            solucion = Utiles.copiarMatriz(tablero);
            ++nSols;
        }
        else {
            int minI = 0;
            int minJ = 0;
            int minPosats = 10;

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    if (tablero[i][j] == 0) {
                        int nVolt = 0;
                        int k1 = Math.max(0, i - 1);
                        int k2 = Math.min(n - 1, i + 1);
                        int s1 = Math.max(0, j - 1);
                        int s2 = Math.min(m - 1, j + 1);

                        for (int k = k1; k <= k2; ++k)
                            for (int s = s1; s <= s2; ++s)
                                if (tablero[k][s] == 0)
                                    ++nVolt;

                        if (nVolt < minPosats) {
                            minPosats = nVolt;
                            minI = i;
                            minJ = j;
                        }
                    }
                }
            }

            --nRest;
            for (int k = 1; (nSols < 2) && (k < maxCas - 1); ++k) {
                if(!usado[k]) {
                    usado[k] = true;
                    tablero[minI][minJ] = k + 1;

                    if (fastCheck(minI, minJ) && deepCheck()) backtrack();

                    tablero[minI][minJ] = 0;
                    usado[k] = false;
                }
            }
            ++nRest;
        }
    }
    /*POST: Encuentra (si existe) la/s soluciones del Hidato leido anteriormente */

    /* Se supone que se acaba de
       modificar la posicion (i, j), por lo tanto hay que comprobar todas las casillas
       de alrededor. Para cada casilla adyacente a (i, j), y para (i, j), se cuenta
       cuantas casillas vacias tiene alrededor. Si tiene solo una se comprueba que al
       menos su siguiente o su anterior sean adyacentes, en caso contrario se devuelve
       false. Si no tiene ninguna casilla vacia, se comprueba que su siguiente y
       anterior sean adyacentes. En cualquier caso, si su siguiente o anterior no son
       adyacentes, y ya estan colocadas en otra parte del tablero, devuelve false */
    /* PRE: - */
    private boolean fastCheck(int i, int j) {

        for (int k = Math.max(0, i - 1); k <= Math.min(n - 1, i + 1); ++k) {
            for (int s = Math.max(0, j - 1); s <= Math.min(m - 1, j + 1); ++s) {
                if (tablero[k][s] > 0) {
                    boolean antFound = false;
                    boolean sigFound = false;
                    int nBuits = 0;

                    int t1 = Math.max(0, k - 1);
                    int t2 = Math.min(n - 1, k + 1);
                    int r1 = Math.max(0, s - 1);
                    int r2 = Math.min(m - 1, s + 1);

                    for (int t = t1; t <= t2; ++t) {
                        for (int r = r1; r <= r2; ++r) {
                            if (tablero[t][r] == 0) ++nBuits;
                            else if (tablero[t][r] == tablero[k][s] - 1)
                                antFound = true;
                            else if (tablero[t][r] == tablero[k][s] + 1)
                                sigFound = true;
                        }
                    }

                    if (nBuits == 1 && tablero[k][s] > 1 && tablero[k][s] < maxCas &&
                      !antFound && !sigFound) return false;
                    else if (nBuits == 0) {
                        if (tablero[k][s] == 1) {
                            if (!sigFound) return false;
                        }
                        else if (tablero[k][s] == maxCas) {
                            if (!antFound) return false;
                        }
                        else if (!sigFound || !antFound) return false;
                    }
                    if (!antFound && tablero[k][s] > 1 && usado[tablero[k][s] - 2])
                        return false;
                    if (!sigFound && tablero[k][s] < maxCas && usado[tablero[k][s]])
                        return false;
                }
            }
        }
        return true;
    }
    /* POST: Comprueba que aun se pueda resolver el Hidato */

    /* Para cada numero ya colocado, se
       comprueba que la distancia de la casilla donde esta colocado hasta la casilla donde
       esta colocado el siguiente numero es menor o igual a la diferencia de los dos
       numeros, es decir, que es posible llegar */
    /* PRE: - */
    private boolean deepCheck() {
        int dist[][] = new int[n][m];

        int ant = 0;
        for (int k = 1; k < maxCas; ++k) {
            if (usado[k]) {
                Queue<Integer> cola = new LinkedList<Integer>();
                int pos = 0;
                for (int i = 0; i < n; ++i) {
                    for (int j = 0; j < m; ++j) {
                        if (tablero[i][j] == ant + 1) {
                            pos = i*m + j;
                            dist[i][j] = 0;
                        }
                        else dist[i][j] = n + m;
                    }
                }
                cola.add(pos);
                boolean ok = true;
                while (ok && !cola.isEmpty()) {
                    int val = cola.poll().intValue();
                    int x = val/m;
                    int y = val%m;
                    int nuevaDist = 1 + dist[x][y];
       
                    for (int s = Math.max(0, x - 1); ok && s <= Math.min(n - 1, x + 1); ++s) {
                        for (int t = Math.max(0, y - 1); ok && t <= Math.min(m - 1, y + 1); ++t) {
                            if (tablero[s][t] == k + 1) {
                                if (nuevaDist > k - ant) return false;
                                ok = false;
                            }
                            else if ((tablero[s][t] == 0) && (nuevaDist < dist[s][t])) {
                                dist[s][t] = nuevaDist;
                                cola.add(s*m + t);
                            }
                        }
                    }
                }
                if (ok) return false;
                ant = k;
            }
        }
        return true;
    }
    /* POST: Comprueba que aun se pueda resolver el Hidato */
    
}
