/*  Class TablaRanking:
    Descripcion: Vista para la tabla de las posiciones mostradas en el ranking.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 16:45 */

package Vistas;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class TablaRanking extends JScrollPane {

    JTable tabla;

    /* PRE: - */
    public TablaRanking() {

        initComponents();
    }
    /* POST: Crea una instancia de TablaRanking */

    /* PRE: - */
    public void setContenido(Object[][] listaElementos) {

        tabla.setModel(new DefaultTableModel(listaElementos,
          new String [] {"Posición", "Nombre del Jugador", "Puntuación", "Fecha"}) {

            Class[] types = new Class [] {String.class, String.class, String.class,
              String.class};

            boolean[] canEdit = new boolean [] {false, false, false, false};

            public Class getColumnClass(int indiceColumna) {

                return types [indiceColumna];
            }

            public boolean isCellEditable(int indiceFila, int indiceColumna) {

                return canEdit [indiceColumna];
            }
        });
        tabla.getColumnModel().getColumn(0).setResizable(true);
        tabla.getColumnModel().getColumn(0).setHeaderValue("#");
        tabla.getColumnModel().getColumn(0).setMaxWidth(30);
        tabla.getColumnModel().getColumn(1).setResizable(false);
        tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre del Jugador");
        tabla.getColumnModel().getColumn(1).setMinWidth(150);
        tabla.getColumnModel().getColumn(2).setResizable(false);
        tabla.getColumnModel().getColumn(2).setHeaderValue("Puntuación");
        tabla.getColumnModel().getColumn(3).setResizable(false);
        tabla.getColumnModel().getColumn(3).setHeaderValue("Fecha");
    }
    /* POST: Establece el contenido de 'listaElementos' en la tabla */

    /* PRE: - */
    private void initComponents() {

        tabla = new JTable() {
            public void changeSelection(int indiceFila, int indiceColumna, boolean toggle,
              boolean extend) {}
        };
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFocusable(false);
        tabla.getTableHeader().setReorderingAllowed(false);

        setViewportView(tabla);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la tabla */
}
