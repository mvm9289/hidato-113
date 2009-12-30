/*  Class TableroHidato:
    Descripcion: Representa el conjunto estructurado de casillas de Hidato
        (tablero de Hidato).
    Autor: alex.catarineu
    Revisado: 20/12/2009 11:22 */

package Dominio;

public abstract class TableroHidato extends Tablero {

    private CasillaHidato[][] casillas;

    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente
        contiene caracteres alfanumericos. 'anchura' y 'altura' son valores mayores
        que 0. */
    public TableroHidato(String id, int anchura, int altura) {

        super(id, anchura, altura);
        
        /* Trabajamos con coordenadas: el primer indice es x y el segundo es y */
        casillas = new CasillaHidato[anchura][altura];
        for (int i = 0; i < anchura; ++i)
            for (int j = 0; j < altura; ++j)
                casillas[i][j] = new CasillaHidato(i, j);

    }
    /* POST: Se crea una instancia de TableroHidato con id = 'id', anchura = 'anchura' y
        altura = 'altura'. */
    
    /* PRE: No existe ninguna instancia de Tablero con id = 'id'. 'id' unicamente
        contiene caracteres alfanumericos. 'tablero' es un TableroHidato perfectamente
        inicializado. */
    public TableroHidato(String id, TableroHidato tablero) {
        
        super(id, tablero);

        int anchura = tablero.getAnchura();
        int altura = tablero.getAltura();

        casillas = new CasillaHidato[anchura][altura];
        for (int i = 0; i < anchura; ++i) {
            for (int j = 0; j < altura; ++j) {
                casillas[i][j] = new CasillaHidato(i, j);
                casillas[i][j].setActiva(tablero.esActiva(i, j));
                casillas[i][j].setPrefijada(tablero.esPrefijada(i, j));
                casillas[i][j].setValor(tablero.obtenerValor(i, j));
            }
        }
    }
    /* POST: Se crea una instancia de TableroHidato a partir de 'id' y 'tablero'. */

    /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. 'valor' >= 1. */
    public void introducirValor(int posicionX, int posicionY, int valor) {

        casillas[posicionX][posicionY].setValor(valor);
    }
    /* POST: Modifica el valor de la casilla ['posicionX']['posicionY'] con 'valor'. */

    /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. */
    public void quitarValor(int posicionX, int posicionY) {
        
        casillas[posicionX][posicionY].setValor(0);
    }
    /* POST: Quita el valor de la casilla ['posicionX']['posicionY']. */

    /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. */
    public boolean esVacia(int posicionX, int posicionY) {
        
        return (casillas[posicionX][posicionY].getValor() == 0 &&
          casillas[posicionX][posicionY].esActiva());
    }
    /* POST: Retorna true si la casilla ['posicionX']['posicionY'] esta vacia. */

    /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. */
    public int obtenerValor(int posicionX, int posicionY) {

        return casillas[posicionX][posicionY].getValor();
    }
    /* POST: Retorna el valor de la casilla ['posicionX']['posicionY'] */

    /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. */
    public void setPrefijada(int posicionX, int posicionY, boolean prefijada) {

        casillas[posicionX][posicionY].setPrefijada(Boolean.valueOf(prefijada));
    }
    /* POST: Marca la casilla ['posicionX']['posicionY'] como prefijada o no segun el
        valor de 'prefijada'. */

    /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. */
    public boolean esPrefijada(int posicionX, int posicionY) {

        return casillas[posicionX][posicionY].esPrefijada().booleanValue();
    }
    /* POST: Retorna true si la casila ['posicionX']['posicionY'] es prefijada. */

     /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. */
    public void setActiva(int posicionX, int posicionY, boolean activa) {

        casillas[posicionX][posicionY].setActiva(activa);
    }
    /* POST: Marca la casilla ['posicionX']['posicionY'] como activa o no segun el valor
        de 'activa'. */

     /* PRE: 'posicionX' >= 0. 'posicionY' >= 0. */
    public boolean esActiva(int posicionX, int posicionY) {

        return casillas[posicionX][posicionY].esActiva();
    }
    /* POST: Retorna true si la casilla ['posicionX']['posicionY'] esta actica. */
    
    /* PRE: - */
    public void resetearTablero() {
        
        for (int i = 0; i < casillas.length; ++i)
            for (int j = 0; j < casillas[i].length; ++j)
                if (!casillas[i][j].esPrefijada())
                    quitarValor(i, j);
    }
    /* POST: Quita el valor de las casillas no prefijadas. */
}
