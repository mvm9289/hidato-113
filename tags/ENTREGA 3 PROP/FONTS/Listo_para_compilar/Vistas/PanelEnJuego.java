/*  Class PanelEnJuego:
    Descripcion: Vista para el panel principal de juego.
    Autor: alex.catarineu
    Revisado: 21/12/2009 07:44 */

package Vistas;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class PanelEnJuego extends JPanel {

    private BarraSuperiorEnJuego barraSuperior;
    private Tablero tablero;
    private ContenedorTablero contenedorTablero;
    private BarraInferiorEnJuego barraInferior;

    /* PRE: - */
    public PanelEnJuego(Tablero tablero) {

        initComponents(tablero);
        setMyLayout();
    }
    /* POST: Se configura un panel en la ventana principal para jugar */

    /* PRE: - */
    public int obtenerTiempo() {

        return barraSuperior.obtenerTiempo();
    }
    /* POST: Devuelve el tiempo actual del juego en curso */

    /* PRE: - */
    public void setTiempo(int tiempo) {

        barraSuperior.setTiempo(tiempo);
    }
    /* POST: Establece el tiempo del juego en curso en 'tiempo' segundos */

    /* PRE: - */
    public void iniciarTiempo() {

        barraSuperior.iniciarTiempo();
    }
    /* POST: Inicia/continua la contabilizacion del tiempo del juego en curso */

    /* PRE: - */
    public void pararTiempo() {

        barraSuperior.pararTiempo();
    }
    /* POST: Para la contabilizacion del tiempo del juego en curso */

    /* PRE: 'pista' contiene 1 pista para el tablero actual */
    public void introducirPista(int[][] pista) {

        boolean encontrado = false;

        for (int i = 0; i < pista.length && !encontrado; ++i)
            for (int j = 0; j < pista[i].length && !encontrado; ++j) {
                if (pista[i][j] != 0) {
                    encontrado = true;
                    if (pista[i][j] == -1) tablero.setValorIncorrecto(i, j);
                    else tablero.setValor(i, j, pista[i][j]);
                }
            }
    }
    /* POST: Introduce la pista marcada por 'pista' en el tablero actual */

    /* PRE: El hidato aun no esta resuelto */
    public void resolverTablero(int[][] solucion) {

        for (int i = 0; i < solucion.length; ++i)
            for (int j = 0; j < solucion[i].length; ++j)
                tablero.setValor(i, j, solucion[i][j]);
    }
    /* POST: Resuelve el hidato completo */

    /* PRE: - */
    public void resetearTablero() {

        tablero.resetear();
    }
    /* POST: Devuelve el tablero al estado inicial, es decir, con todas las casillas no
        prefijadas vacias */

    /* PRE: - */
    public void deshacer() {

        tablero.deshacer();
    }
    /* POST: Deshace la ultima accion realizada sobre el panel de juego. Si se ha
        insertado un numero se quita, y viceversa */

    /* PRE: - */
    private void initComponents(Tablero tablero) {

        barraSuperior = new BarraSuperiorEnJuego();
        barraSuperior.setVisible(true);

        this.tablero = tablero;
        this.tablero.setVisible(true);

        contenedorTablero = new ContenedorTablero(this.tablero);

        barraInferior = new BarraInferiorEnJuego(this.tablero.obtenerBarraCasillas());
        barraInferior.setVisible(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el panel de juego */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(barraSuperior, GroupLayout.DEFAULT_SIZE,
              GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(barraInferior, GroupLayout.DEFAULT_SIZE,
              GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contenedorTablero)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barraSuperior, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contenedorTablero)
                .addGap(0, 0, 0)
                .addComponent(barraInferior, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        panel de juego */
}