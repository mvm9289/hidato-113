/*  Class ControladorTablero:
    Descripcion: Gestiona toda la parte de Tableros, y Repositorio del sistema.
    Autor: alex.catarineu
    Revisado: 20/12/2009 13:12 */

package Dominio;

import Datos.ControladorDatosTableroOriginal;
import Datos.ControladorDatosTableroEnCurso;
import Datos.ControladorDatosTableroPropuesto;
import Datos.ControladorDatosRepositorio;
import Excepciones.InstanceNotFoundException;
import Tiempo.Fecha;
import Tiempo.Hora;
import Utiles.Utiles;
import java.io.IOException;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    /* Constantes de estado de ejecucion del algoritmo */
    private final static int N_THREADS = 4;
    public final static int ABORTADO = -1;
    public final static int NO_SOLUCION = 0;
    public final static int MAS_UNA_SOLUCION = 2;
    public final static int CORRECTO = 1;

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

    private Algoritmos[] algoritmos;

    /* PRE: - */
    private ControladorTablero() {

        algoritmos= new Algoritmos[N_THREADS];
        tableroOriginal = null;
        tableroEnCurso = null;
        repositorio = DATOS_REPOSITORIO.obtener();
    }
    /* POST: Crea la instancia del ControladorTablero */

    /* PRE: - */
    public static ControladorTablero getInstance() {

        return INSTANCIA;
    }
    /* POST: Retorna la instancia del ControladorTablero */

    /* PRE: 'numPrefijadas' > 2, la matriz 'formaTopologia' sigue el mismo formato que
        tableroToArray y todos los numeros que contiene son 0 o -1 */
    public void generarTablero(int numPrefijadas, int[][] formaTopologia, Thread asesino)
      {

        for (int i = 0; i < N_THREADS; ++i) {
            algoritmos[i] = new Algoritmos(numPrefijadas, formaTopologia, asesino);
            algoritmos[i].start();
        }
        asesino.start();
    }
    /* POST: Inicia la ejecucion del algoritmo de generacion */

    /* PRE: 'nombre' contiene solo caracteres alfanumericos, 'dificultad' y 'topologia'
        tienen un valor de entre los definidos en sus constantes, la matriz
        'formaTopologia' sigue el mismo formato que tableroToArray y todos los numeros
        que contiene son 0 o -1 */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public int confirmarTableroGenerado(String nombre, int dificultad, int topologia,
      int[][] formaTopologia, int[] minimoPrefijadas) throws Exception {

        Algoritmos algoritmoAux = null;
        int minPre = Integer.MAX_VALUE;
        
        for (int i = 0; i < N_THREADS; ++i) {
            if (algoritmos[i].esAcabado()) algoritmoAux = algoritmos[i];
            else algoritmos[i].obtenerThread().stop();
            minPre = Math.min(minPre, algoritmos[i].obtenerPrefijadas());
        }
        
        if (algoritmoAux == null) {
            minimoPrefijadas[0] = minPre;
            return ABORTADO;
        }
        else {
            confirmarTablero(nombre, dificultad, topologia, formaTopologia,
              algoritmoAux.obtenerSolucion(), algoritmoAux.obtenerGenerado());
            return CORRECTO;
        }
    }
    /* POST: Retorna el estado de la ejecución del algoritmo de resolucion, es decir,
        ABORTADO o CORRECTO. Si ha devuelto ABORTADO, se ha devuelto el mejor numero de
        prefijadas encontrado (parametro de salida 'minimoPrefijadas'). Si ha devuelto
        CORRECTO, se ha creado una instancia de tableroHidatoOriginal, se ha hecho
        persistente y se ha puesto en el repositorio */

    /* PRE: 'nombre' contiene solo caracteres alfanumericos, 'dificultad' y 'topologia'
        tienen un valor de entre los definidos en sus constantes, la matriz
        'formaTopologia' sigue el mismo formato que tableroToArray y todos los
        numeros que contiene son 0 o -1 */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void confirmarMejorTableroGenerado(String nombre, int dificultad,
      int topologia, int[][] formaTopologia) throws Exception {

        Algoritmos algoritmoAux = null;
        int mejorPrefijadas = Integer.MAX_VALUE;

        for (int i = 0; i < N_THREADS; ++i) {
            if (algoritmos[i].obtenerPrefijadas() < mejorPrefijadas) {
                algoritmoAux = algoritmos[i];
                mejorPrefijadas = algoritmoAux.obtenerPrefijadas();
            }
        }

        confirmarTablero(nombre, dificultad, topologia, formaTopologia,
          algoritmoAux.obtenerSolucion(), algoritmoAux.obtenerGenerado());
    }
    /* POST: Se ha cogido el mejor tablero encontrado y se ha creado una instancia de
        tableroHidatoOriginal, se ha hecho persistente y se ha puesto en el repositorio */

    /* PRE: La matriz 'contenido' sigue el mismo formato que tableroToArray y todos los
        numeros que contiene, si no son 0 o -1, estan dentro del intervalo
        [1, numero de casillas vacias], hay una casilla con el valor "1" y otra con el
        valor "num de casillas vacias" */
    public void proponerTablero(int contenido[][], Thread asesino) {

        algoritmos[0] = new Algoritmos(contenido, asesino);
        algoritmos[0].start();
        asesino.start();
    }
    /* POST: Inicia la ejecucion del algoritmo de resolucion */

    /* PRE: 'nombre' contiene solo caracteres alfanumericos, 'topologia' tiene un valor de
        entre los definidos en sus constantes, 'usuario' existe, la matriz 'contenido'
        sigue el mismo formato que tableroToArray y todos los numeros que contiene, si no
        son 0 o -1, estan dentro del intervalo [1, numero de casillas vacias], hay una
        casilla con el valor "1" y otra con el valor "num de casillas vacias" */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public int confirmarTableroPropuesto(String nombre, int topologia, int[][] contenido,
      String usuario) throws Exception {

        int soluciones = algoritmos[0].obtenerNumSols();
        int[][] solucion;
        int altura = contenido.length;
        int anchura = contenido[0].length;
        TableroHidatoPropuesto tablero;

        if (!algoritmos[0].esAcabado()) return ABORTADO;
        else if (soluciones == 0) return NO_SOLUCION;
        else if (soluciones > 1) return MAS_UNA_SOLUCION;
        else {
            solucion = algoritmos[0].obtenerSolucion();
            tablero = new TableroHidatoPropuesto(nombre, anchura, altura,
              DIF_PERSONALIZADA, topologia, usuario);
            
            for (int i = 0; i < altura; ++i) {
                for (int j = 0; j < anchura; ++j) {
                    tablero.introducirValor(j, i, solucion[i][j]);
                    if (contenido[i][j] == -1) tablero.setActiva(j, i, false);
                    else if (contenido[i][j] != 0) tablero.setPrefijada(j, i, true);
                }
            }

            tablero.setPuntuacion(calcularPuntuacion(tablero));           

            DATOS_PROPUESTO.insertar(tablero);
            repositorio.addTablero(nombre);
            DATOS_REPOSITORIO.actualizar();

            return CORRECTO;
        }
    }
    /* POST: Retorna el estado de la ejecución del algoritmo de resolucion, es decir,
        ABORTADO, NO_SOLUCION, MAS_UNA_SOLUCION o CORRECTO.
        Si ha devuelto CORRECTO, se ha creado una instancia de tableroHidatoPropuesto, se
        ha hecho persistente y se ha puesto en el repositorio */

    /* PRE: - */
    public void abortarAlgoritmo() {

        for (int i = 0; i < N_THREADS; ++i)
            algoritmos[i].obtenerThread().stop();

    }
    /* POST: Aborta la ejecucion del algoritmo */

    /* PRE: usuario existe, opciones es producto de hacer or con un flag de {SEL_TODOS,
        SEL_PROPUESTOS} y otro de {ORD_TOPOLOGIA, ORD_ID, ORD_DIFICULTAD} */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public List<Object[]> obtenerListaTableros(int opciones, String usuario)
      throws Exception {

        TableroHidatoPropuesto tabAux;
        ArrayList<String> ids = (ArrayList<String>)repositorio.getTableros();
        ArrayList<TableroHidatoOriginal> tab = new ArrayList<TableroHidatoOriginal>();
        Comparator<TableroHidatoOriginal> comp;
        List<Object[]> res = new ArrayList<Object[]>();

        for (int i = 0; i < ids.size(); ++i) {
            try {
                tabAux = DATOS_PROPUESTO.obtener(ids.get(i));
                if (((opciones&SEL_PROPUESTOS) == 0) ||
                  (tabAux.obtenerUsuario().equals(usuario)))
                    tab.add(tabAux);
            }
            catch (InstanceNotFoundException e) {
                if ((opciones&SEL_PROPUESTOS) == 0)
                    try {
                        tab.add(DATOS_ORIGINAL.obtener(ids.get(i)));
                    }
                    catch (InstanceNotFoundException ex) {}
            }
        }

        if ((opciones & ORD_DIFICULTAD) > 0) comp = new comparadorDificultad();
        else if ((opciones & ORD_TOPOLOGIA) > 0) comp = new comparadorTopologia();
        else comp = new comparadorID();
        Collections.sort(tab, comp);

        for (int i = 0; i < tab.size(); ++i) {
            Object[] tupla = new Object[3];
            tupla[0] = tab.get(i).getTablero();
            tupla[1] = tab.get(i).obtenerDificultad();
            tupla[2] = tab.get(i).obtenerTopologia();
            res.add(tupla);
        }

        return res;
    }
    /* POST: Devuelve una lista con los ids de los tableros del repositorio que cumplen
        las condiciones del filtro. Si el flag SEL_PROPUESTOS estaba puesto, se devuelven
        solo los tableros propuestos por el usuario que se pasa. En funcion del flag de
        ordenacion, se devuelven los ids de los tableros ordenados por id, dificultad o
        topologia */

    /* PRE: 'tableroOriginal' existe */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public String crearTableroEnCurso(String tableroOriginal, int[] dificultad)
      throws Exception {
        
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
        nuevoId += Integer.toString(fecha.obtenerMesInt());
        nuevoId += Integer.toString(fecha.obtenerAnio());
        nuevoId += Integer.toString(hora.obtenerHora());
        nuevoId += Integer.toString(hora.obtenerMinuto());
        nuevoId += Integer.toString(hora.obtenerSegundo());
        this.tableroEnCurso = new TableroHidatoEnCurso(nuevoId, this.tableroOriginal);
        dificultad[0] = this.tableroOriginal.obtenerDificultad();
        
        return nuevoId;
    }
    /* POST: se crea un tableroEnCurso solo con las casillas prefijadas y se asigna a
        this.tableroEnCurso, tableroOriginal se asigna a this.tableroOriginal */

    /* PRE: 'tableroEnCurso' existe */
    /* EXC 'IOException': Excepcion de entrada/salida */
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
    /* POST: Se carga el tableroEnCurso y su correspondiente tableroOriginal */

    /* PRE: tableroEnCurso != null */
    /* EXC 'IOException': Excepcion de entrada/salida */
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
    /* EXC 'IOException': Excepcion de entrada/salida */
    public void eliminarTablero(String tablero) throws Exception {
        
        DATOS_EN_CURSO.borrar(tablero);
    }
    /* POST: El tablero en curso con id tablero ha dejado de ser persistente */

    /* PRE: - */
    public int obtenerPistas() {

        return tableroEnCurso.obtenerPistas();
    }
    /* POST: Devuelve el numero de pistas que se han pedido en el tableroEnCurso */

    /* PRE: El tablero no esta solucionado, tableroEnCurso != null,
        tableroOriginal != null */
    public int[][] darPista() {
        
        int nLibres = 0;
        int nMal = 0;
        int anchura = tableroEnCurso.getAnchura();
        int altura = tableroEnCurso.getAltura();
        int[][] res = new int[altura][anchura];
        boolean encontrado;
        int nElegido;

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                res[i][j] = 0;
                if (tableroEnCurso.esVacia(j, i)) ++nLibres;
                else if (tableroEnCurso.esActiva(j, i) &&
                  (tableroEnCurso.obtenerValor(j, i) !=
                  tableroOriginal.obtenerValor(j, i))) {
                    ++nMal;
                }
            }
        }
       
        encontrado = false;
        if (nMal > 0) {
            nElegido = (new Random()).nextInt(nMal);
            nMal = 0;
            for (int i = 0; !encontrado && (i < altura); ++i) {
                for (int j = 0; !encontrado && (j < anchura); ++j) {
                    if (!tableroEnCurso.esVacia(j, i) && tableroEnCurso.esActiva(j, i) &&
                      (tableroEnCurso.obtenerValor(j, i) !=
                      tableroOriginal.obtenerValor(j, i))) {
                        if (nMal == nElegido) {
                            encontrado = true;
                            res[i][j] = -1;
                        }
                        ++nMal;
                    }
                }
            }
        }
        else if (nLibres > 0) {
            nElegido = (new Random()).nextInt(nLibres);
            nLibres = 0;
            for (int i = 0; !encontrado && (i < altura); ++i) {
                for (int j = 0; !encontrado && (j < anchura); ++j) {
                    if (tableroEnCurso.esVacia(j, i)) {
                        if (nLibres++ == nElegido) {
                            encontrado = true;
                            res[i][j] = tableroOriginal.obtenerValor(j, i);
                            tableroEnCurso.introducirValor(j, i, res[i][j]);
                        }
                    }
                }
            }
        }
        tableroEnCurso.incrementarPistas(1);

        return res;
    }
    /* POST: Devuelve una matriz en la que todas las posiciones son 0, excepto en la
        casilla que se ha dado la pista. En esa posicion, habra un numero correcto, en
        caso que todas las casillas colocadas fueran correctas, o -1, indicando que el
        numero colocado en esa posicion es incorrecto. Se ha incrementado el numero de
        pistas. Si todas las casillas colocadas eran correctas, se ha actualizado el
        tableroEnCurso con la casilla afectada por la pista */

    /* PRE: tableroOriginal != null */
    public int[][] resolverHidato() {

        for (int i = 0; i < tableroOriginal.getAltura(); ++i)
            for (int j = 0; j < tableroOriginal.getAnchura(); ++j)
                tableroEnCurso.introducirValor(i, j, tableroOriginal.obtenerValor(i, j));

        return tableroToArray(tableroOriginal);
    }
    /* POST: Se ha devuelto una matriz con el contenido del tablero resuelto */

    /* PRE: tableroEnCurso != null, tableroOriginal != null */
    public int[][][] obtenerContenidoTablero() {

        int res[][][] = new int[2][][];
        
        res[0] = tableroToArray(tableroEnCurso);
        res[1] = estadosToArray(tableroEnCurso);
        
        return res;
    }
    /* POST: Devuelve una definicion de tablero, en forma de dos arrays, una para el
       contenido y la otra para el estado de las casillas */
    
    /* PRE: tableroEnCurso != null, la casilla (x, y) es vacia */
    public void insertarValor(int x, int y, int valor) {
        
        tableroEnCurso.introducirValor(x, y, valor);
    }
    /* POST: Se ha insertado el valor 'valor' en la casilla (x, y) de tableroEnCurso */

    /* PRE: tableroEnCurso != null, la casilla (x, y) no es prefijada y es activa*/
    public void quitarValor(int x, int y) {
        
        tableroEnCurso.quitarValor(x, y);
    }
    /* POST: Se ha puesto la casilla (x, y) como vacia */

    /* PRE: tableroEnCurso != null */
    public void resetearTablero() {

        tableroEnCurso.resetearTablero();
    }
    /* POST: Se ha reseteado el tablero en curso (todas las casillas no prefijadas quedan
        vacias) */

    /* PRE: tableroOriginal != null */
    public int obtenerPuntuacionTablero() {
        
        return tableroOriginal.obtenerPuntuacion();
    }
    /* POST: Retorna una estimacion de la dificultad del tablero como puntuacion */

    /* PRE: tableroEnCurso != null, tableroOriginal != null */
    public boolean esTableroValido() {
        
        return Utiles.igualMatriz(tableroToArray(tableroEnCurso),
          tableroToArray(tableroOriginal));
    }
    /* POST: Se ha devuelto si el tableroEnCurso es un tablero correctamente
        solucionado */
    
    /* PRE: - */
    /* EXC 'IOException': Excepcion de entrada/salida */
    public boolean existeTablero(String idTablero) throws IOException {

        return DATOS_ORIGINAL.existe(idTablero) || DATOS_PROPUESTO.existe(idTablero);
    }
    /* POST: Retorna true si existe un tablero original o propuesto con
        id = 'idTablero' en el sistema */

    /* PRE: 'nombre' contiene solo caracteres alfanumericos, 'dificultad' y 'topologia'
        tienen un valor de entre los definidos en sus constantes, la matriz
        'formaTopologia' sigue el mismo formato que tableroToArray y todos los
        numeros que contiene son 0 o -1 */
    /* EXC 'IOException': Excepcion de entrada/salida */
    private void confirmarTablero(String nombre, int dificultad, int topologia,
      int[][] formaTopologia, int[][] solucion, int[][] generado) throws Exception {

        int altura = formaTopologia.length;
        int anchura = formaTopologia[0].length;
        TableroHidatoOriginal tablero;

        tablero = new TableroHidatoOriginal(nombre, anchura, altura, dificultad,
          topologia);
        for (int i = 0; i < altura; ++i)
            for (int j = 0; j < anchura; ++j) {
                if (formaTopologia[i][j] == -1) tablero.setActiva(j, i, false);
                else {
                    if (generado[i][j] > 0) tablero.setPrefijada(j, i, true);
                    tablero.introducirValor(j, i, solucion[i][j]);
                }
            }

        tablero.setPuntuacion(calcularPuntuacion(tablero));
        
        DATOS_ORIGINAL.insertar(tablero);
        repositorio.addTablero(nombre);
        DATOS_REPOSITORIO.actualizar();
    }
    /* POST: Se ha creado una instancia de tableroHidatoOriginal, se ha hecho persistente
        y se ha puesto en el repositorio */

    /* PRE: - */
    private int calcularPuntuacion(TableroHidatoOriginal tablero) {

        int anchura = tablero.getAnchura();
        int altura = tablero.getAltura();
        int suma = 0;
        int i1;
        int i2;
        int j1;
        int j2;

        for (int i = 0; i < anchura; ++i) {
            for (int j = 0; j < altura; ++j) {
                if (esCasillaEditable(tablero, i, j)) {
                    i1 = Math.max(i - 1, 0);
                    i2 = Math.min(i + 1, anchura - 1);
                    j1 = Math.max(j - 1, 0);
                    j2 = Math.min(j + 1, altura - 1);
                    for (int k = i1; k <= i2; ++k)
                        for (int s = j1; s <= j2; ++s)
                            if (k != i || s != j)
                                if (esCasillaEditable(tablero, k, s)) ++suma;
                }
            }
        }

        return suma/2;
    }
    /* POST: Retorna la puntuacion estimada para el TableroHidatoOriginal 'tablero' */

    /* PRE: tablero != null */
    private boolean esCasillaEditable(TableroHidatoOriginal tablero, int x, int y) {

        return !tablero.esPrefijada(x, y) && tablero.esActiva(x, y);
    }
    /* POST: Devuelve cierto si la casilla ('x', 'y') es activa y no prefijada dentro
        del tablero 'tablero' */

    /* PRE: tablero != null */
    private int[][] tableroToArray(TableroHidato tablero) {

        int anchura = tablero.getAnchura();
        int altura = tablero.getAltura();
        int res[][] = new int[altura][anchura];

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                if (tablero.esActiva(j, i)) res[i][j] = tablero.obtenerValor(j, i);
                else res[i][j] = -1;
            }
        }

        return res;
    }
    /* POST: Devuelve una matriz que representa el contenido del tablero, con un 0
        representando la casilla vacia, un numero mayor que 0 representando el contenido
        de la casilla y un -1 representando la casilla inactiva */

    /* PRE: tablero != null */
    private int[][] estadosToArray(TableroHidato tablero) {

        int anchura = tablero.getAnchura();
        int altura = tablero.getAltura();
        int res[][] = new int[altura][anchura];

        for (int i = 0; i < altura; ++i) {
            for (int j = 0; j < anchura; ++j) {
                if (tablero.esPrefijada(j, i)) res[i][j] = CAS_PREFIJADA;
                else if (tablero.esActiva(j, i)) res[i][j] = CAS_ACTIVA;
                else res[i][j] = CAS_INACTIVA;
            }
        }

        return res;
    }
    /* POST: Devuelve una matriz que representa el estado de cada casilla, que puede ser
        ACTIVA, INACTIVA o PREFIJADA */

    /* FUNCIONES PARA ORDENAR */
    /* Compara dos tableros lexicograficamente mediante el identificador de tablero */
    private class comparadorID implements Comparator<TableroHidatoOriginal> {

        public int compare(TableroHidatoOriginal original1,
          TableroHidatoOriginal original2) {
            
            return original1.getTablero().toLowerCase().compareTo(
              original2.getTablero().toLowerCase());
        }
    }

    /* Compara dos tableros mediante la topologia del tablero. Hemos establecido que el
     orden es el siguiente:
     1) Rectangulo
     2) Triangulo
     3) Rombo
     4) Elipse
     (En caso de ser topologias iguales se ordena por orden lexicografico del
     identificador de tablero)
     */
    private class comparadorTopologia implements Comparator<TableroHidatoOriginal> {

        public int compare(TableroHidatoOriginal original1,
          TableroHidatoOriginal original2) {
            
            int topologia1 = original1.obtenerTopologia();
            int topologia2 = original2.obtenerTopologia();

            if (topologia1 == topologia2)
                return original1.getTablero().compareTo(original2.getTablero());
            else {
                if (topologia1 == TOP_RECTANGULO) return -1;
                if (topologia2 == TOP_RECTANGULO) return 1;
                if (topologia1 == TOP_TRIANGULO) return -1;
                if (topologia2 == TOP_TRIANGULO) return 1;
                if (topologia1 == TOP_ROMBO) return -1;
                if (topologia2 == TOP_ROMBO) return 1;
                if (topologia1 == TOP_ELIPSE) return -1;

                return 1;
            }
        }
    }

    /* Compara dos tableros mediante la dificultad del tablero. Hemos establecido que el
     orden es el siguiente:
     1) Facil
     2) Medio
     3) Dificil
     [En caso de ser dificultades iguales se ordena por orden lexicografico del
     identificador de tablero]
     */
    private class comparadorDificultad implements Comparator<TableroHidatoOriginal> {

        public int compare(TableroHidatoOriginal original1,
          TableroHidatoOriginal original2) {
            
            int dificultad1 = original1.obtenerDificultad();
            int dificultad2 = original2.obtenerDificultad();

            if (dificultad1 == dificultad2)
                return original1.getTablero().compareTo(original2.getTablero());
            else {
                if (dificultad1 == DIF_FACIL) return -1;
                if (dificultad2 == DIF_FACIL) return 1;
                if (dificultad1 == DIF_MEDIO) return -1;
                if (dificultad2 == DIF_MEDIO) return 1;
                if (dificultad1 == DIF_DIFICIL) return -1;
                
                return 1;
            }
        }
    }
}
