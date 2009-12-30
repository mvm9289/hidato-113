/*  Class Deshacer:
    Descripcion: Implementa la funcionalidad de deshacer las acciones efectuadas por el
        usuario jugando, proponiendo un tablero o proponiendo una topologia
    Autor: daniel.camarasa
    Revisado: 21/12/2009 01:00 */

package Vistas;

public class Deshacer {

    /* Constantes de modos */
    public final static int JUEGO = 0;
    public final static int PROPONER_TAB = 1;
    public final static int PROPONER_TOP = 2;

    /* Constante de acciones */
    public final static int INSERTAR_VAL_PROP = 0;
    public final static int QUITAR_VAL_PROP = 1;
    public final static int INSERTAR_VAL_JUEGO = 2;
    public final static int QUITAR_VAL_JUEGO = 3;
    public final static int DES_ACTIVAR_CASILLA = 4;

    private final static int NUM_ACCIONES = 10;
    
    private Accion[] colaAcciones;  /* Buffer circular de Acciones del usuario */
    private boolean[] validos;      /* Indica si la i-esima accion se ha deshecho o no */
    private Tablero tablero;

    private int modo;
    private int indiceActual; /* nos indica la posicion de la accion actual */

    /* PRE: tablero != null */
    public Deshacer(Tablero tablero) {

        indiceActual = -1; /* Inicialmente no hay acciones */
        this.tablero = tablero;
        colaAcciones = new Accion[NUM_ACCIONES];
        validos = new boolean[NUM_ACCIONES];

        for (int i = 0; i < NUM_ACCIONES; ++i) validos[i] = false;
    }
    /* POST: Crea una instancia de Deshacer */

    /* PRE: nuevoModo = 'JUEGO' o nuevoModo = 'PROPONER_TAB' o
        nuevoModo = 'PROPONER_TOP' */
    public void cambiaModo(int nuevoModo) {

        modo = nuevoModo;
        clear();
        ControladorVistas.getInstance().bloqueoDeshacer(true);
    }
    /* POST: Define el modo en que el usuario esta jugando. En consecuencia, cuando
        cambiamos de modo las acciones del antiguo no pueden ser deshechas, por tanto, hay
        que invalidar el buffer circular */

    /* PRE: 'accion' es uno de los valores definidos en Constantes de acciones */
    public void insertarAccion(int accion, int x, int y, int valor) {

        indiceActual = (indiceActual + 1)%NUM_ACCIONES;
        colaAcciones[indiceActual] = new Accion(accion, x, y, valor);
        validos[indiceActual] = true;
        
        ControladorVistas.getInstance().bloqueoDeshacer(false);
    }
    /* POST: Se inserta una accion en el buffer circular. Si ya habia una accion valida
        se sobreescribe */

    /* PRE: 'accion' es uno de los valores definidos en Constantes de acciones */
    private void realizaAccionContraria(int accion, int x, int y, int valor) {

        switch (modo) {
            case JUEGO:
                if (accion == INSERTAR_VAL_JUEGO) tablero.quitarValorJugando(x, y);
                else tablero.introducirValorJugando(x, y, valor);
                break;
            case PROPONER_TAB:
                if (accion == INSERTAR_VAL_PROP) tablero.quitarValorProponiendo(x, y);
                else tablero.introducirValorProponiendo(x, y, valor);
                break;
            case PROPONER_TOP:
                tablero.activarDesactivar(x, y);
                break;
            default:
                break;
        }
    }
    /* POST: Realiza la accion contraria a la accion definida por 'accion' */

    /* PRE: - */
    public void undo() {

        int accion = colaAcciones[indiceActual].getAccion();
        int x = colaAcciones[indiceActual].getX();
        int y = colaAcciones[indiceActual].getY();
        int valor = colaAcciones[indiceActual].getValor();

        realizaAccionContraria(accion, x, y, valor);
        
        validos[indiceActual] = false;
        --indiceActual;
        if (indiceActual < 0) indiceActual += NUM_ACCIONES;

        if (!validos[indiceActual]) ControladorVistas.getInstance().bloqueoDeshacer(true);
    }
    /* POST: Deshace la ultima accion guardada en el buffer y actualiza los mecanismos
        de dicho buffer */

    /* PRE: - */
    private void clear() {

        for (int i = 0; i < NUM_ACCIONES; ++i) validos[i] = false;
    }
    /* POST: Resetea el buffer circular de Acciones invalidandolo */

    /* Implementa una accion del usuario sobre una casilla (x, y) con valor 'valor' */
    private class Accion {

        private int action;
        private int x;
        private int y;
        private int valor;

        /* PRE: 'accion' es uno de los valores definidos en Constantes de acciones */
        public Accion(int accion, int x, int y, int valor) {

            action = accion;
            this.x = x;
            this.y = y;
            this.valor = valor;
        }
        /* POST: Crea una instancia de Accion */

        /* PRE: - */
        public int getAccion() {

            return action;
        }
        /* POST: Devuelve la accion almacenada */

        /* PRE: - */
        public int getX() {

            return x;
        }
        /* POST: Devuelve la coordenada x de la casilla */

        /* PRE: - */
        public int getY() {

            return y;
        }
        /* POST: Devuelve la coordenada y de la casilla */

        /* PRE: - */
        public int getValor() {

            return valor;
        }
        /* POST: Devuelve el valor con el que se produjo la accion sobre la
            casilla (x, y) */
    }
}
