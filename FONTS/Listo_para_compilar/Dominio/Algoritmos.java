/*  Class Algoritmos:
    Descripcion: Contiene los algoritmos de generacion y resolucion de Hidatos
    Autor: alex.catarineu
    Colaboradores: daniel.camarasa, miguel.angel.vico
    Revisado: 20/12/2009 21:00 */

package Dominio;

import Utiles.Utiles;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Algoritmos implements Runnable {

    private Thread miThread;    /* thread del algoritmo */
    private Thread asesino;     /* thread que puede que mate al algoritmo por tiempo */
    private boolean acabado;    /* indica si la ejecucion del algortimo ha acabado bien */

    private boolean esProponer; /* parametro para saber que constructora se ha usado */
    private int tablero[][];  /* tablero sobre el que buscaremos la solucion */
    private int solucion[][]; /* tablero donde guardaremos la solucion */
    private int generado[][]; /* tablero en el que se guardara el mejor tablero generado
                                 hasta el momento */
    private int nSols;        /* numero de soluciones encontradas (como mucho dos) */
    private int nRest;        /* numero de casillas que me quedan por poner */
    private boolean usado[];  /* usado[i] nos dice si el numero i + 1 esta colocado */
    private int n;            /* anchura del tablero */
    private int m;            /* altura del tablero */
    private int maxCas;       /* numero de la casilla mas grande */
    private int nVacias; /* num. de cas. prefijadas que tiene que tener el tab. generado*/
    private int prefijadas;   /* en la generacion, indica el numero de prefijadas del
                                tablero generado[][] */
    private Random generador; /* generador de numeros aleatorios */

    /* Constructora para proponer */
    public Algoritmos(int contenido[][], Thread asesino) {
        
        n = contenido.length;
        m = contenido[0].length;
        tablero = Utiles.copiarMatriz(contenido);
        nSols = 0;
        nRest = 0;
        maxCas = 0;
        usado = new boolean[n*m];
        esProponer = true;

        for (int i = 0; i < n*m; ++i) usado[i] = false;

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (tablero[i][j] > 0) usado[tablero[i][j] - 1] = true;
                else if (tablero[i][j] == 0) ++nRest;
                maxCas = Math.max(maxCas, tablero[i][j]);
            }
        }

        miThread = new Thread(this);
        this.asesino = asesino;
    }

    /* Constructora para generar */
    public Algoritmos(int numPrefijadas, int contenido[][], Thread asesino) {

        n = contenido.length;
        m = contenido[0].length;
        tablero = Utiles.copiarMatriz(contenido);
        solucion = null;
        nSols = 0;
        maxCas = 0;
        usado = new boolean[n*m];
        esProponer = false;
        generador = new Random();
        
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < m; ++j)
                if (contenido[i][j] == 0)
                    ++maxCas;

        nRest = maxCas;
        nVacias = maxCas - numPrefijadas;
        prefijadas = -1;

        miThread = new Thread(this);
        this.asesino = asesino;
    }

    public Thread obtenerThread() {

        return miThread;
    }

    public void start() {

        miThread.start();
    }

    public boolean esAcabado() {

        return acabado;
    }

    public int[][] obtenerSolucion() {

        return solucion;
    }

    public int obtenerNumSols() {

        return nSols;
    }

    public int obtenerPrefijadas() {

        return prefijadas;
    }

    public int[][] obtenerGenerado() {

        return generado;
    }

    /* Se encarga de llamar a generaCamino y una vez generado a quitarCasillas */
    private void generar() {

        int elegido = generador.nextInt(maxCas);

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (tablero[i][j] == 0 && elegido-- == 0) {
                    generaCamino(i, j);
                    solucion = Utiles.copiarMatriz(tablero);
                    quitarCasillas();
                    return;
                }
            }
        }
    }

    /* Sustituye el valor de la casilla tablero[i][j] por num, a continuacion le coloca a
       la casilla que tenia como valor tablero[i][j] - 1, un valor de num + 1, y asi
       sucesivamente, hasta que quedan = 0. Se usa en generaCamino, cuando llegamos a una
       situacion en la que no podemos avanzar mas, para cambiar el camino incompleto
       encontrado hasta el momento y tener opciones de continuar. Devuelve las coordenadas
       de la ultima casilla modificada, que sera el final del nuevo camino incompleto */
    private int actualizaCamino(int i, int j, int num, int quedan) {
        
        if (quedan == 0) {
            tablero[i][j] = num;
            return i*m + j;
        }
        int ant = tablero[i][j] - 1;
        int i1 = Math.max(0, i - 1);
        int i2 = Math.min(n - 1, i + 1);
        int j1 = Math.max(0, j - 1);
        int j2 = Math.min(m - 1, j + 1);
        for (int ii = i1; ii <= i2; ++ii) {
            for (int jj = j1; jj <= j2; ++jj) {
                if (tablero[ii][jj] == ant) {
                    int ret = actualizaCamino(ii, jj, num + 1, quedan - 1);
                    tablero[i][j] = num;
                    return ret;
                }
            }
        }
        return -1;
    }

    /* Coloca en emptyAdj las casillas vacias adyacentes a tablero[i][j], con la
       coordenada de la casilla codificada en un entero */
    private void getEmptyAdj(int i, int j, ArrayList<Integer> emptyAdj) {

        int i1 = Math.max(0, i - 1);
        int i2 = Math.min(n - 1, i + 1);
        int j1 = Math.max(0, j - 1);
        int j2 = Math.min(m - 1, j + 1);
        for (int ii = i1; ii <= i2; ++ii)
            for (int jj = j1; jj <= j2; ++jj)
                if ((ii != i || jj != j) && tablero[ii][jj] == 0)
                        emptyAdj.add(ii*m + jj);
    }

    /* Coloca en emptyAdj las casillas adyacentes a tablero[i][j], con la
       coordenada de la casilla codificada en un entero */
    private void getAdj(int i, int j, ArrayList<Integer> adj) {

        int i1 = Math.max(0, i - 1);
        int i2 = Math.min(n - 1, i + 1);
        int j1 = Math.max(0, j - 1);
        int j2 = Math.min(m - 1, j + 1);
        for (int ii = i1; ii <= i2; ++ii)
            for (int jj = j1; jj <= j2; ++jj)
                if ((ii != i || jj != j) && tablero[ii][jj] != -1)
                        adj.add(ii*m + jj);
    }

    /* Genera un camino aleatoriamente del que despues se quitaran casillas para generar
       el tablero de Hidato final. Mientras la ultima casilla del camino generado hasta
       el momento tenga adyacentes vacias, elegimos una de esas casillas aleatoriamente y
       alargamos el camino por ahi. En el momento en que no haya adyacentes vacias,
       cambiamos el camino usando actualizaCamino, que nos devolvera la casilla final del
       nuevo camino. Seguiremos hasta que hayamos hecho un camino que recorra el tablero,
       o nos corten por tiempo desde fuera. Con grafos normales, sin vertices de corte, la
       probabilidad de que encuentre solucion tiende a 1, y la encuentra instantaneamente.
       Si el grafo no contiene un camino hamiltoniano, o contiene pocos caminos diferentes
       entonces es posible que el algoritmo no acabe, pero para ello se corta por tiempo.
       Implementamos un bactracking, pero encontramos que era demasiado lento, asi que
       optamos por usar este algoritmo aleatorio que va bien para la gran mayoria de
       topologias propuestas */
    private boolean generaCamino(int i, int j) {

        int cas = 1;
        while (cas < maxCas) {
            tablero[i][j] = cas;
            ArrayList<Integer> adj = new ArrayList<Integer>();
            getEmptyAdj(i, j, adj);

            /* Posible bucle infinito */
            while (adj.isEmpty()) {
                getAdj(i, j, adj);
                int elegido = adj.get(generador.nextInt(adj.size()));
                int ii = elegido/m;
                int jj = elegido%m;
                int val = tablero[ii][jj];
                int end = actualizaCamino(i, j, val + 1, cas - val - 1);
                i = end/m;
                j = end%m;
                adj.clear();
                getEmptyAdj(i, j, adj);
            }
            
            int elegido = adj.get(generador.nextInt(adj.size()));
            i = elegido/m;
            j = elegido%m;
            ++cas;
        }
        tablero[i][j] = cas;
        return true;
    }


    /* A partir del camino generado, a cada paso elige una casilla llena aleatoriamente,
       la vacia y comprueba que el Hidato sigue teniendo solucion. Si no la tiene, elige
       una casilla llena aleatoriamente, pone la anterior como llena y la nueva como vacia
       y vuelve a comprobar que el Hidato tenga una sola solucion. Cuando la tiene, se
       quita definitivamente esa casilla de generado. El algoritmo sigue hasta que llega
       al limite deseado o se corta por tiempo externamente */
    private void quitarCasillas() {
        
        ArrayList<Integer> llenasI = new ArrayList<Integer>();
        ArrayList<Integer> llenasJ = new ArrayList<Integer>();
        ArrayList<Integer> vaciasI = new ArrayList<Integer>();
        ArrayList<Integer> vaciasJ = new ArrayList<Integer>();
        generado = Utiles.copiarMatriz(solucion);
        prefijadas = maxCas;
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (tablero[i][j] > 1 && tablero[i][j] < maxCas) {
                    llenasI.add(new Integer(i));
                    llenasJ.add(new Integer(j));
                }
            }
        }

        for (int i = 0; i < maxCas; ++i)
            usado[i] = true;

        while (vaciasI.size() < nVacias) {
            int elegido = generador.nextInt(llenasI.size());
            int i = llenasI.get(elegido).intValue();
            int j = llenasJ.get(elegido).intValue();
            usado[tablero[i][j] - 1] = false;
            tablero[i][j] = 0;
            vaciasI.add(new Integer(i));
            vaciasJ.add(new Integer(j));
            llenasI.remove(elegido);
            llenasJ.remove(elegido);
            nRest = vaciasI.size();
            nSols = 0;
            backtrackProponer();
            while (nSols != 1) {
                int elegido1 = generador.nextInt(llenasI.size());
                int elegido2 = vaciasI.size() - 1;
                i = llenasI.get(elegido1);
                j = llenasJ.get(elegido1);
                int i2 = vaciasI.get(elegido2).intValue();
                int j2 = vaciasJ.get(elegido2).intValue();

                tablero[i2][j2] = solucion[i2][j2];
                usado[tablero[i][j] - 1] = false;
                usado[tablero[i2][j2] - 1] = true;
                tablero[i][j] = 0;
                llenasI.set(elegido1, new Integer(i2));
                llenasJ.set(elegido1, new Integer(j2));
                vaciasI.set(elegido2, new Integer(i));
                vaciasJ.set(elegido2, new Integer(j));
                nSols = 0;
                nRest = vaciasI.size(); // Por si acaso

                backtrackProponer();
            }

            --prefijadas;
            generado[i][j] = 0;
        }
    }
    
    /* Devuelve el numero de casillas vacias alrededor de (i, j) */
    private int nVaciasAlrededor(int i, int j) {

        int acum = 0;
        int i1 = Math.max(0, i - 1);
        int i2 = Math.min(n - 1, i + 1);
        int j1 = Math.max(0, j - 1);
        int j2 = Math.min(m - 1, j + 1);
        for (int ii = i1; ii <= i2; ++ii)
            for (int jj = j1; jj <= j2; ++jj)
                if ((ii != i || jj != j) && tablero[ii][jj] == 0)
                    ++acum;
        return acum;
    }

    /* La idea es que en cada llamada al backtracking seleccionamos la casilla que
       tiene menos casillas vacias a su alrededor, y probamos de rellenarla con todos
       los posibles valores, comprobando con una funcion que aun es posible resolver
       el hidato, en cuyo caso seguimos el proceso de backtracking. Cuando nRest == 0,
       augmentamos el numero de soluciones. Si en algun momento nRest == 2, cortamos,
       ya que hemos encontrado que el Hidato tiene mas de una solucion, por lo tanto
       no es valido */
    private void backtrackProponer() {

        int minI;
        int minJ;
        int minPuestos;
        int nAlrededor;

        if (nRest == 0) {
            if (esProponer) solucion = Utiles.copiarMatriz(tablero);
            ++nSols;
        }
        else {
            minI = 0;
            minJ = 0;
            minPuestos = 10;

            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    if (tablero[i][j] == 0) {
                        nAlrededor = nVaciasAlrededor(i, j);

                        if (nAlrededor < minPuestos) {
                            minPuestos = nAlrededor;
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
                    if (fastCheck(minI, minJ) && deepCheck()) backtrackProponer();
                    tablero[minI][minJ] = 0;
                    usado[k] = false;
                }
            }
            ++nRest;
        }
    }

    /* Comprueba que aun se pueda resolver el Hidato. Se supone que se acaba de
       modificar la posicion (i, j), por lo tanto hay que comprobar todas las casillas
       de alrededor. Para cada casilla adyacente a (i, j), y para (i, j), se cuenta
       cuantas casillas vacias tiene alrededor. Si tiene solo una se comprueba que al
       menos su siguiente o su anterior sean adyacentes, en caso contrario se devuelve
       false. Si no tiene ninguna casilla vacia, se comprueba que su siguiente y
       anterior sean adyacentes. En cualquier caso, si su siguiente o anterior no son
       adyacentes, y ya estan colocadas en otra parte del tablero, devuelve false */
    private boolean fastCheck(int i, int j) {

        boolean antEncontrado;
        boolean sigEncontrado;
        int nVacios;
        int t1;
        int t2;
        int r1;
        int r2;

        for (int k = Math.max(0, i - 1); k <= Math.min(n - 1, i + 1); ++k) {
            for (int s = Math.max(0, j - 1); s <= Math.min(m - 1, j + 1); ++s) {
                if (tablero[k][s] > 0) {
                    antEncontrado = false;
                    sigEncontrado = false;
                    nVacios = 0;

                    t1 = Math.max(0, k - 1);
                    t2 = Math.min(n - 1, k + 1);
                    r1 = Math.max(0, s - 1);
                    r2 = Math.min(m - 1, s + 1);
                    for (int t = t1; t <= t2; ++t) {
                        for (int r = r1; r <= r2; ++r) {
                            if (tablero[t][r] == 0) ++nVacios;
                            else if (tablero[t][r] == tablero[k][s] - 1)
                                antEncontrado = true;
                            else if (tablero[t][r] == tablero[k][s] + 1)
                                sigEncontrado = true;
                        }
                    }

                    if (nVacios == 1 && tablero[k][s] > 1 && tablero[k][s] < maxCas &&
                      !antEncontrado && !sigEncontrado) return false;
                    else if (nVacios == 0) {
                        if (tablero[k][s] == 1) {
                            if (!sigEncontrado) return false;
                        }
                        else if (tablero[k][s] == maxCas) {
                            if (!antEncontrado) return false;
                        }
                        else if (!sigEncontrado || !antEncontrado) return false;
                    }
                    if (!antEncontrado && tablero[k][s] > 1 &&
                      usado[tablero[k][s] - 2])
                        return false;
                    if (!sigEncontrado && tablero[k][s] < maxCas &&
                      usado[tablero[k][s]])
                        return false;
                }
            }
        }

        return true;
    }

    /* Comprueba que aun se pueda resolver el Hidato. Para cada numero ya colocado, se
       comprueba que la distancia de la casilla donde esta colocado hasta la casilla
       donde esta colocado el siguiente numero es menor o igual a la diferencia de los
       dos numeros, es decir, que es posible llegar */
    private boolean deepCheck() {

        int dist[][] = new int[n][m];
        int ant = 0;
        Queue<Integer> cola;
        int pos;
        boolean ok;
        int val;
        int x;
        int y;
        int nuevaDist;
        int s1;
        int s2;
        int t1;
        int t2;

        for (int k = 1; k < maxCas; ++k) {
            if (usado[k]) {
                cola = new LinkedList<Integer>();
                pos = 0;
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
                ok = true;
                while (ok && !cola.isEmpty()) {
                    val = cola.poll().intValue();
                    x = val/m;
                    y = val%m;
                    nuevaDist = 1 + dist[x][y];

                    s1 = Math.max(0, x - 1);
                    s2 = Math.min(n - 1, x + 1);
                    t1 = Math.max(0, y - 1);
                    t2 = Math.min(m - 1, y + 1);
                    for (int s = s1; ok && s <= s2; ++s) {
                        for (int t = t1; ok && t <= t2; ++t) {
                            if (tablero[s][t] == k + 1) {
                                if (nuevaDist > k - ant) return false;
                                ok = false;
                            }
                            else if ((tablero[s][t] == 0)&&(nuevaDist < dist[s][t])) {
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

    /* Llama a generar o proponer en funcion de la constructora que se haya usado */
    public void run() {
        
        if (esProponer) backtrackProponer();
        else generar();

        acabado = true;
        asesino.interrupt();
    }
}
