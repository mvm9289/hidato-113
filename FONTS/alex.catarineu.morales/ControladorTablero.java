/*  Class ControladorTablero:
    Descripcion: Gestiona toda la parte de Tableros, y Repositorio.
    Autor: alex.catarineu
    Revisado: 20/11/2009 10:19 */

package Dominio;

import Datos.ControladorDatosTableroOriginal;
import Datos.ControladorDatosTableroEnCurso;
import Datos.ControladorDatosTableroPropuesto;
import Datos.ControladorDatosRepositorio;
import Excepciones.InstanceNotFoundException;
import Tiempo.Fecha;
import Tiempo.Hora;
import Utiles.Utiles;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class ControladorTablero {
    
    /* Constantes de dificultad */
    public final static int DIF_FACIL = 0;
    public final static int DIF_MEDIO = 1;
    public final static int DIF_DIFICIL = 2;
    public final static int DIF_PERSONALIZADA = 3;
    /* Constantes de topologia */
    public final static int TOP_RECTANGULO = 0;
    public final static int TOP_TRIANGULO = 1;
    public final static int TOP_ROMBO = 2;
    public final static int TOP_ELIPSE = 3;
    public final static int TOP_PERSONALIZADA = 4;
    /* Constantes de filtro de tableros */
    public final static int ORD_ID = 1;
    public final static int ORD_TOPOLOGIA = 2;
    public final static int ORD_DIFICULTAD = 4;
    public final static int SEL_TODOS = 8;
    public final static int SEL_PROPUESTOS = 16;
    /* Constantes de estado de casilla */
    public final static int CAS_PREFIJADA = -1;
    public final static int CAS_ACTIVA = -2;
    public final static int CAS_INACTIVA = -3;
    /* Atributos */
    private final static ControladorDatosTableroEnCurso DATOS_EN_CURSO =
     ControladorDatosTableroEnCurso.getInstance();
    private final static ControladorDatosTableroOriginal DATOS_ORIGINAL =
     ControladorDatosTableroOriginal.getInstance();
    private final static ControladorDatosTableroPropuesto DATOS_PROPUESTO =
     ControladorDatosTableroPropuesto.getInstance();
    private final static ControladorDatosRepositorio DATOS_REPOSITORIO =
        ControladorDatosRepositorio.getInstance();
    private final static ControladorTablero INSTANCIA = new ControladorTablero();
    private TableroHidatoOriginal tableroOriginal;
    private TableroHidatoEnCurso tableroEnCurso;
    private Repositorio repositorio;

    /* PRE: - */
    private ControladorTablero() {

        tableroOriginal = null;
        tableroEnCurso = null;
        repositorio = DATOS_REPOSITORIO.obtener();
    }
    /* POST: Se han inicializado los atributos de la clase */

    /* PRE: - */
    public static ControladorTablero getInstance() {

        return INSTANCIA;
    }
    /* POST: Se devuelve la instancia a la clase */

    /* PRE: nombre contiene solo caracteres alfanumericos, anchura y altura estan dentro 
       del rango maximo especificado, dificultad y topologia tienen un valor de entre los
       definidos en sus constantes */
    public void generarTablero(String nombre, int anchura, int altura, int dificultad, 
      int topologia, int numPrefijadas) {
    }
    /* POST: - */

    /* PRE: nombre contiene solo caracteres alfanumericos, anchura y altura estan dentro 
       del rango maximo especificado, dificultad y topologia tienen un valor de entre los
       definidos en sus constantes, usuario existe, la matriz contenido sigue el mismo 
       formato que tableroToArray y todos los numeros que contiene, si no son 0 o -1,
       estan dentro del intervalo [1, numero de casillas vacias], hay una casilla con
       el valor "1" y otra con el valor "num de casillas vacias" */
    public boolean proponerTablero(String nombre, int anchura, int altura,
      int dificultad, int topologia, int contenido[][], String usuario)
      throws Exception {
      
        TableroHidatoPropuesto tabAux = new TableroHidatoPropuesto(nombre,
          anchura, altura, dificultad, topologia, usuario);

        for (int i = 0; i < anchura; ++i) {
            for (int j = 0; j < altura; ++j) {
                if (contenido[i][j] == -1) tabAux.setActiva(i, j, false);
                else if (contenido[i][j] != 0) tabAux.setPrefijada(i, j, true);
            }
        }

        BuscarSolucion sol = new BuscarSolucion(anchura, altura, contenido);
        int solucion[][] = sol.obtenerSolucion();

        if (solucion != null) {
            for (int i = 0; i < anchura; ++i)
                for (int j = 0; j < altura; ++j)
                    tabAux.introducirValor(i, j, solucion[i][j]);
            
            DATOS_PROPUESTO.insertar(tabAux);
            repositorio.addTablero(nombre);
            DATOS_REPOSITORIO.actualizar();
            
            return true;
        }
        return false;
    }
    /* POST: Devuelve cierto si el tablero de Hidato es correcto, falso en caso contrario.
       Si ha devuelto cierto, se ha creado una instancia de tableroHidatoPropuesto, se
       ha hecho persistente y se ha puesto en el repositorio */

    public class BuscarSolucion {

        private int tablero[][]; /* tablero sobre el que buscaremos la solucion */
        private int solucion[][]; /* tablero donde guardaremos la solucion */
        private int nSols; /* numero de soluciones encontradas (como mucho dos) */
        private int nRest; /* numero de casillas que me quedan por poner */
        private boolean usado[]; /* usado[i] nos dice si el numero i + 1 esta colocado */
        private int n; /* anchura del tablero */
        private int m; /* altura del tablero */
        private int maxCas; /* numero de la casilla mas grande */

        public BuscarSolucion(int anchura, int altura, int contenido[][])
          throws Exception {

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

        public int[][] obtenerSolucion() {
            if (nSols < 2) return solucion;
            return null;
        }

        /* La idea es que en cada llamada al backtracking seleccionamos la casilla que
           tiene menos casillas vacias a su alrededor, y probamos de rellenarla con todos
           los posibles valores, comprobando con una funcion que aun es posible resolver
           el hidato, en cuyo caso seguimos el proceso de backtracking. Cuando nRest == 0,
           augmentamos el numero de soluciones. Si en algun momento nRest == 2, cortamos,
           ya que hemos encontrado que el Hidato tiene mas de una solucion, por lo tanto
           no es valido */
        private void backtrack() throws Exception {

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

        /* Comprueba que aun se pueda resolver el Hidato. Se supone que se acaba de
           modificar la posicion (i, j), por lo tanto hay que comprobar todas las casillas
           de alrededor. Para cada casilla adyacente a (i, j), y para (i, j), se cuenta
           cuantas casillas vacias tiene alrededor. Si tiene solo una se comprueba que al
           menos su siguiente o su anterior sean adyacentes, en caso contrario se devuelve
           false. Si no tiene ninguna casilla vacia, se comprueba que su siguiente y
           anterior sean adyacentes. En cualquier caso, si su siguiente o anterior no son
           adyacentes, y ya estan colocadas en otra parte del tablero, devuelve false */
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

        /* Comprueba que aun se pueda resolver el Hidato. Para cada numero ya colocado, se
           comprueba que la distancia de la casilla donde esta colocado hasta la casilla 
           donde esta colocado el siguiente numero es menor o igual a la diferencia de los
           dos numeros, es decir, que es posible llegar */
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

                        int s1 = Math.max(0, x - 1);
                        int s2 = Math.min(n - 1, x + 1);
                        int t1 = Math.max(0, y - 1);
                        int t2 = Math.min(m - 1, y + 1);


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
    }

    /* PRE: El tablero no esta solucionado, tableroEnCurso != null, 
       tableroOriginal != null */
    public int[][] darPista() {
        
        int nLibres = 0;
        int nMal = 0;
        int anchura = tableroEnCurso.getAnchura();
        int altura = tableroEnCurso.getAltura();
        int[][] res = new int[anchura][altura];

        for (int i = 0; i < anchura; ++i) {
            for (int j = 0; j < altura; ++j) {
                res[i][j] = 0;
                if (tableroEnCurso.esVacia(i, j)) ++nLibres;
                else if (tableroEnCurso.esActiva(i, j) &&
                  (tableroEnCurso.obtenerValor(i, j) !=
                  tableroOriginal.obtenerValor(i, j))) {
                    ++nMal;
                }
            }
        }
       
        boolean encontrado = false;

        if (nMal > 0) {
            int nElegido = (new Random()).nextInt(nMal);
            nMal = 0;
            for (int i = 0; !encontrado && (i < anchura); ++i) {
                for (int j = 0; !encontrado && (j < altura); ++j) {
                    if (!tableroEnCurso.esVacia(i, j) && tableroEnCurso.esActiva(i, j) &&
                      (tableroEnCurso.obtenerValor(i, j) !=
                      tableroOriginal.obtenerValor(i, j))) {
                        if (nMal++ == nElegido) {
                            encontrado = true;
                            res[i][j] = -1;
                        }
                    }
                }
            }
        }
        else if (nLibres > 0) {
            int nElegido = (new Random()).nextInt(nLibres);
            nLibres = 0;
            for (int i = 0; !encontrado && (i < anchura); ++i) {
                for (int j = 0; !encontrado && (j < altura); ++j) {
                    if (tableroEnCurso.esVacia(i, j)) {
                        if (nLibres++ == nElegido) {
                            encontrado = true;
                            res[i][j] = tableroOriginal.obtenerValor(i, j);
                            tableroEnCurso.introducirValor(i, j, res[i][j]);
                        }
                    }
                }
            }
        }
        tableroEnCurso.incrementarPistas();
        return res;
    }
    /* POST: Devuelve una matriz en la que todas las posiciones son 0, excepto en la
       casilla que se ha dado la pista. En esa posicion, habra un numero correcto, en caso
       que todas las casillas colocadas fueran correctas, o -1, indicando que el numero
       colocado en esa posicion es incorrecto. Se ha incrementado el numero de pistas.
       Si todas las casillas colocadas eran correctas, se ha actualizado el tableroEnCurso
       con la casilla afectada por la pista */

    /* PRE: - */
    public int obtenerPistas() {
        
        return tableroEnCurso.obtenerPistas();
    }
    /* POST: Devuelve el numero de pistas que se han pedido en el tableroEnCurso */

    /* PRE: tableroOriginal != null */
    public int[][] resolverHidato() {
        
        return tableroToArray(tableroOriginal);
    }
    /* POST: Se ha devuelto una matriz con el contenido del tablero resuelto */

    /* PRE: usuario existe, opciones es producto de hacer or con un flag de {SEL_TODOS, 
       SEL_PROPUESTOS} y otro de {ORD_TOPOLOGIA, ORD_ID, ORD_DIFICULTAD} */
    public List<Object[]> obtenerListaTableros(int opciones, String usuario) 
      throws InstanceNotFoundException {
        
        TableroHidatoPropuesto tabAux;
        ArrayList<String> ids = (ArrayList<String>)repositorio.getTableros();
        ArrayList<TableroHidatoOriginal> tab = new ArrayList<TableroHidatoOriginal>(0);

        for (int i = 0; i < ids.size(); ++i) {
            try {
                tabAux = DATOS_PROPUESTO.obtener(ids.get(i));
                if (((opciones&SEL_PROPUESTOS) == 0) ||
                  (tabAux.obtenerUsuario().equals(usuario))) {
                    tab.add(tabAux);
                }
            }
            catch (InstanceNotFoundException e) {
                if ((opciones&SEL_PROPUESTOS) == 0) {
                    tab.add(DATOS_ORIGINAL.obtener(ids.get(i)));
                }
            }
        }
         
        Comparator<TableroHidatoOriginal> comp;
        if ((opciones & ORD_DIFICULTAD) > 0) comp = new ordTableroDificultad();
        else if ((opciones & ORD_TOPOLOGIA) > 0) comp = new ordTableroTopologia();
        else comp = new ordTableroId();
        Collections.sort(tab, comp);     
           
        int n = tab.size();
        List<Object[]> res = new ArrayList<Object[]>(n);
        
        for (int i = 0; i < n; ++i) {
            Object[] tupla = new Object[3];
            tupla[0] = tab.get(i).getTablero();
            tupla[1] = tab.get(i).obtenerDificultad();
            tupla[2] = tab.get(i).obtenerTopologia();
            res.set(i, tupla);
        }
        
        return res;
    }
    /* POST: Devuelve una lista con los ids de los tableros del repositorio que cumplen 
       las condiciones del filtro. Si el flag SEL_PROPUESTOS estaba puesto, se devuelven
       solo los tableros propuestos por el usuario que se pasa. En funcion del flag de
       ordenacion, se devuelven los ids de los tableros ordenados por id, dificultad o
       topologia */

    /* PRE: tableroEnCurso != null, tableroOriginal != null */
    public int[][][] obtenerContenidoTablero() {

        int res[][][] = new int[2][][];
        
        res[0] = tableroToArray(tableroEnCurso);
        res[1] = estadosToArray(tableroEnCurso);
        
        return res;
    }
    /* POST: Devuelve una definicion de tablero, en forma de dos arrays, una para el
       contenido y la otra para el estado de las casillas */
    
    /* PRE: 'tableroOriginal' existe */
    public String crearTableroEnCurso(String tableroOriginal) throws Exception {
        
        try {
            this.tableroOriginal = DATOS_ORIGINAL.obtener(tableroOriginal);
        }
        catch (InstanceNotFoundException e){
            this.tableroOriginal = DATOS_PROPUESTO.obtener(tableroOriginal);
        }
        Fecha fecha = new Fecha();
        fecha.hoy();
        Hora hora = new Hora();
        hora.ahora();
        String nuevoId = this.tableroOriginal.getTablero();
        nuevoId += Integer.toString(fecha.obtenerDiaDelMes());
        nuevoId += fecha.obtenerMesStr();
        nuevoId += Integer.toString(fecha.obtenerAnio());
        nuevoId += Integer.toString(hora.obtenerHora());
        nuevoId += Integer.toString(hora.obtenerMinuto());
        nuevoId += Integer.toString(hora.obtenerSegundo());
        this.tableroEnCurso = new TableroHidatoEnCurso(nuevoId, this.tableroOriginal);
        return nuevoId;
    }
    /* POST: se crea un tableroEnCurso solo con las casillas prefijadas y se asigna a
       this.tableroEnCurso, tableroOriginal se asigna a this.tableroOriginal */

    /* PRE: 'tableroEnCurso' existe */
    public void cargarTablero(String tableroEnCurso) throws Exception {
        this.tableroEnCurso = DATOS_EN_CURSO.obtener(tableroEnCurso);
        try {
            this.tableroOriginal =
              DATOS_ORIGINAL.obtener(this.tableroEnCurso.obtenerTableroOriginal());
        }
        catch (InstanceNotFoundException e){
            this.tableroOriginal =
              DATOS_PROPUESTO.obtener(this.tableroEnCurso.obtenerTableroOriginal());
        }
    }
    /* POST: se carga el tableroEnCurso y su correspondiente tableroOriginal */

    /* PRE: tableroEnCurso != null */
    public void guardarTablero() throws Exception {
        if (DATOS_EN_CURSO.existe(tableroEnCurso.getTablero())) {
            DATOS_EN_CURSO.actualizar(tableroEnCurso);
        }
        else {
            DATOS_EN_CURSO.insertar(tableroEnCurso);
        }
        
    }
    /* POST: Si el tablero en curso ya se habia guardado, se actualiza, en caso contrario
       se hace persistente */

    /* PRE: tablero != tableroEnCurso.getTablero(), tablero existe */
    public void eliminarTablero(String tablero) throws InstanceNotFoundException {
        
        DATOS_EN_CURSO.borrar(tablero);
    }
    /* POST: El tablero en curso con id tablero ha dejado de ser persistente */

    /* PRE: tableroEnCurso != null, tableroOriginal != , la casilla x, y es vacia */
    public void insertarValor(int x, int y, int valor) {
        
        tableroEnCurso.introducirValor(x, y, valor);
    }
    /* POST: Se ha insertado el valor 'valor' en la casilla (x, y) */

    /* PRE: tableroEnCurso != null, tableroOriginal != null */
    public void resetearTablero() {

        tableroEnCurso.resetearTablero();
    }
    /* POST: Se ha reseteado el tablero */

    /* PRE: tableroEnCurso != null, tableroOriginal != null, la casilla (x, y) no es
       prefijada y es activa*/
    public void quitarValor(int x, int y) {
        
        tableroEnCurso.quitarValor(x, y);
    }
    /* POST: Se ha puesto la casilla (x, y) como vacia */

    /* PRE: tableroEnCurso != null, tableroOriginal != null */
    public int obtenerPuntuacionTablero() {
        
        int anchura = tableroOriginal.getAnchura();
        int altura = tableroOriginal.getAltura();
        int suma = 0;

        for (int i = 0; i < anchura; ++i) {
            for (int j = 0; j < altura; ++j) {
                if (esCasillaEditable(i, j)) {
                    int i1 = Math.max(i - 1, 0);
                    int i2 = Math.min(i + 1, anchura - 1);
                    int j1 = Math.max(j - 1, 0);
                    int j2 = Math.min(j + 1, altura - 1);
                    for (int k = i1; k <= i2; ++k)
                        for (int s = j1; s <= j2; ++s)
                            if (esCasillaEditable(k, s))
                                ++suma;
                }
            }
        }

        return suma*1000;
    }
    /* POST: Se ha devuelto una estimacion de la dificultad del tablero, como 
       puntuacion */

    /* PRE: tableroEnCurso != null, tableroOriginal != null */
    public boolean esTableroValido() {
        
        return Utiles.igualMatriz(tableroToArray(tableroEnCurso),
          tableroToArray(tableroOriginal));
    }
    /* POST: Se ha devuelto si el tableroEnCurso es un tablero correctamente
       solucionado */

    /* FUNCIONES PARA ORDENAR */
    private class ordTableroId implements Comparator<TableroHidatoOriginal> {

        public int compare(TableroHidatoOriginal a, TableroHidatoOriginal b) {
            
            return a.getTablero().compareTo(b.getTablero());
        }
    }

    private class ordTableroTopologia implements Comparator<TableroHidatoOriginal> {

        public int compare(TableroHidatoOriginal a, TableroHidatoOriginal b) {
            
            int topA = a.obtenerTopologia();
            int topB = b.obtenerTopologia();

            if (topA == topB) {
                return a.getTablero().compareTo(b.getTablero());
            }
            else {
                if (topA == TOP_RECTANGULO) return -1;
                if (topB == TOP_RECTANGULO) return 1;
                if (topA == TOP_TRIANGULO) return -1;
                if (topB == TOP_TRIANGULO) return 1;
                if (topA == TOP_ROMBO) return -1;
                if (topB == TOP_ROMBO) return 1;
                if (topA == TOP_ELIPSE) return -1;
                return 1;
            }
        }
    }

    private class ordTableroDificultad implements Comparator<TableroHidatoOriginal> {

        public int compare(TableroHidatoOriginal a, TableroHidatoOriginal b) {
            
            int difA = a.obtenerDificultad();
            int difB = b.obtenerDificultad();

            if (difA == difB) {
                return a.getTablero().compareTo(b.getTablero());
            }
            else {
                if (difA == DIF_FACIL) return -1;
                if (difB == DIF_FACIL) return 1;
                if (difA == DIF_MEDIO) return -1;
                if (difB == DIF_MEDIO) return 1;
                if (difA == DIF_DIFICIL) return -1;
                return 1;
            }
        }
    }
    
    /* PRE: - */
    private int[][] tableroToArray(TableroHidato tablero) {
        
        int anchura = tablero.getAnchura();
        int altura = tablero.getAltura();
        int res[][] = new int[anchura][altura];

        for (int i = 0; i < anchura; ++i) {
            for (int j = 0; j < altura; ++j) {
                if (tablero.esActiva(i, j)) res[i][j] = tablero.obtenerValor(i, j);
                else res[i][j] = -1;
            }
        }
        
        return res;
    }
    /* POST: Devuelve una matriz que representa el contenido del tablero, con un 0
       representando la casilla vacia, un numero mayor que 0 representando el contenido
       de la casilla y un -1 representando la casilla inactiva */

    /* PRE: - */
    private int[][] estadosToArray(TableroHidato tablero) {
        
        int anchura = tablero.getAnchura();
        int altura = tablero.getAltura();
        int res[][] = new int[anchura][altura];

        for (int i = 0; i < anchura; ++i) {
            for (int j = 0; j < altura; ++j) {
                if (tablero.esPrefijada(i, j)) res[i][j] = CAS_PREFIJADA;
                else if (tablero.esActiva(i, j)) res[i][j] = CAS_ACTIVA;
                else res[i][j] = CAS_INACTIVA;
            }
        }

        return res;
    }
    /* POST: Devuelve una matriz que representa el estado de cada casilla, que puede ser
       ACTIVA, INACTIVA o PREFIJADA */
    
    /* Auxiliar de obtenerPuntuacionTablero */
    private boolean esCasillaEditable(int x, int y) {
        
        return !tableroOriginal.esPrefijada(x, y) && tableroOriginal.esActiva(x, y);
    }
}
