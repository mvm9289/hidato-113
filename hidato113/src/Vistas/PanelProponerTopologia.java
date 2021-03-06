/*  Class PanelProponerTopologia:
    Descripcion: Vista para el panel principal de proponer una topologia.
    Autor: alex.catarineu
    Revisado: 21/12/2009 06:37 */

package Vistas;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class PanelProponerTopologia extends JPanel {

    private Tablero tablero;
    private ContenedorTablero contenedorTablero;
    private BarraInferiorProponer barraInferior;

    /* PRE: - */
    public PanelProponerTopologia(int[][] formaTopologia, NuevaPartida nuevaPartida) {

        initComponents(formaTopologia, nuevaPartida);
        setMyLayout();
    }
    /* POST: Se configura un panel en la ventana principal para proponer una topologia */

    /* PRE: - */
    public void deshacer() {

        tablero.deshacer();
    }
    /* POST: Deshace la ultima accion realizada sobre el panel de proponer topologia. Si
        se habia activado una casilla se desactiva, y viceversa */

    /* PRE: - */
    private void initComponents(int[][] formaTopologia, NuevaPartida nuevaPartida) {

        tablero = new Tablero(formaTopologia);
        tablero.modoProponerTopologia();
        tablero.setVisible(true);

        contenedorTablero = new ContenedorTablero(tablero);

        barraInferior = new BarraInferiorProponer(tablero, nuevaPartida);
        barraInferior.setVisible(true);

    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el panel de proponer una topologia */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(this);

        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(barraInferior, GroupLayout.DEFAULT_SIZE,
              GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contenedorTablero)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contenedorTablero)
                .addGap(0, 0, 0)
                .addComponent(barraInferior, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        panel de proponer una topologia */
}