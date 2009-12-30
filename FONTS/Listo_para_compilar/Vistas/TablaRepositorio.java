/*  Class TablaRepositorio:
    Descripcion: Vista para la tabla de los tableros mostrados en el repositorio.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 16:40 */

package Vistas;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class TablaRepositorio extends JScrollPane {

    JTable tabla;

    /* PRE: - */
    public TablaRepositorio() {

        initComponents();
    }
    /* POST: Crea una instancia de TablaRepositorio */

    /* PRE: - */
    public JTable obtenerContenedor() {

        return tabla;
    }
    /* POST: Retorna el contenedor de los objetos de la tabla */

    /* PRE: - */
    public void setContenido(Object[][] listaElementos) {

        tabla.setModel(new DefaultTableModel(listaElementos,
          new String[] {"Nombre", "Topología", "Dificultad"}) {

            Class[] types = new Class [] {String.class, String.class, String.class};

            boolean[] canEdit = new boolean [] {false, false, false};

            public Class getColumnClass(int indiceColumna) {

                return types [indiceColumna];
            }

            public boolean isCellEditable(int indiceFila, int indiceColumna) {

                return canEdit [indiceColumna];
            }
        });
        tabla.getColumnModel().getColumn(0).setResizable(true);
        tabla.getColumnModel().getColumn(0).setHeaderValue("Nombre");
        tabla.getColumnModel().getColumn(1).setResizable(false);
        tabla.getColumnModel().getColumn(1).setHeaderValue("Topología");
        tabla.getColumnModel().getColumn(2).setResizable(false);
        tabla.getColumnModel().getColumn(2).setHeaderValue("Dificultad");
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    /* POST: Establece el contenido de 'listaElementos' en la tabla */

    /* PRE: - */
    public boolean esFilaSeleccionada() {

        return tabla.getSelectedRow() != -1;
    }
    /* POST: Retorna cierto si hay algun tablero seleccionado en la tabla */

    /* PRE: - */
    public String obtenerTableroSeleccionado() {

        int index = tabla.getSelectedRow();
        Object contenido = tabla.getValueAt(index, 0);

        return String.valueOf(contenido);
    }
    /* POST: Retorna el identificador de tablero correspondiente al tablero seleccionado
        en la tabla */

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
