/*  Class TablaPartidasGuardadas:
    Descripcion: Vista para la tabla de las partidas guardadas en el sistema.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 16:48 */

package Vistas;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class TablaPartidasGuardadas extends JScrollPane {

    JTable tabla;

    /* PRE: - */
    public TablaPartidasGuardadas() {

        initComponents();
    }
    /* POST: Crea una instancia de TablaPartidasGuardadas */

    /* PRE: - */
    public JTable obtenerContenedor() {

        return tabla;
    }
    /* POST: Retorna el contenedor de los objetos de la tabla */

    /* PRE: - */
    public void setContenido(Object[][] listaElementos) {

        tabla.setModel(new DefaultTableModel(listaElementos,
          new String[] {"Nombre de partida", "Fecha y Hora"}) {

            Class[] types = new Class [] {String.class, String.class};

            boolean[] canEdit = new boolean [] {false, false};

            public Class getColumnClass(int indiceColumna) {

                return types [indiceColumna];
            }

            public boolean isCellEditable(int indiceFila, int indiceColumna) {

                return canEdit [indiceColumna];
            }
        });
        tabla.getColumnModel().getColumn(0).setResizable(true);
        tabla.getColumnModel().getColumn(0).setHeaderValue("Nombre de partida");
        tabla.getColumnModel().getColumn(1).setResizable(false);
        tabla.getColumnModel().getColumn(1).setHeaderValue("Fecha y Hora");
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    /* POST: Establece el contenido de 'listaElementos' en la tabla */

    /* PRE: - */
    public boolean esFilaSeleccionada() {

        return tabla.getSelectedRow() != -1;
    }
    /* POST: Retorna cierto si hay alguna partida seleccionada en la tabla */

    /* PRE: - */
    public String obtenerPartidaSeleccionada() {

        int index = tabla.getSelectedRow();
        Object contenido = tabla.getValueAt(index, 0);

        return String.valueOf(contenido);
    }
    /* POST: Retorna el identificador de la partida correspondiente a la partida
        seleccionada en la tabla */

    /* PRE: - */
    private void initComponents() {

        tabla = new JTable();
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFocusable(false);
        tabla.getTableHeader().setReorderingAllowed(false);

        setViewportView(tabla);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la tabla */
}
