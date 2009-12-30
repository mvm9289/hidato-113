/*  Class ContenedorTablero:
    Descripcion: Vista para el panel que contiene el tablero.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 20:58 */

package Vistas;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

public class ContenedorTablero extends JScrollPane {

    private JPanel contenedor;
    private Tablero tablero;

    /* PRE: - */
    public ContenedorTablero(Tablero tablero) {

        initComponents(tablero);
        setMyLayout();
    }
    /* POST: Crea una instancia de ContenedorTablero */

    /* PRE: - */
    private void initComponents(Tablero tablero) {

        this.tablero = tablero;

        contenedor = new JPanel();

        setViewportView(contenedor);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }
    /* POST: Inicializa correctamente todos los componentes del contenedor del tablero */

    /* PRE: - */
    private void setMyLayout() {
        
        GroupLayout layout = new GroupLayout(contenedor);

        contenedor.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addComponent(tablero, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE,
                  GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tablero, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y el tama(ny)o del tablero dentro del contenedor */
}
