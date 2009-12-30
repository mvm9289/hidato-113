/*  Class TablaEstadisticas:
    Descripcion: Vista para la tabla de las estadisticas generales y personales.
    Autor: daniel.camarasa
    Revisado: 20/12/2009 16:51 */

package Vistas;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class TablaEstadisticas extends JScrollPane {

    JTable tabla;

    /* PRE: - */
    public TablaEstadisticas() {

        initComponents();
    }
    /* POST: Crea una instancia de TablaEstadisticas */

    /* PRE: - */
    public void setContenido(Object[][] listaElementos) {

        tabla.setModel(new DefaultTableModel(listaElementos,
          new String[] {"Estadística", "Valor"}) {

            Class[] types = new Class [] {String.class, String.class};

            boolean[] canEdit = new boolean [] {false, false};

            public Class getColumnClass(int indiceColumna) {

                return types [indiceColumna];
            }

            public boolean isCellEditable(int indiceFila, int indiceColumna) {

                return canEdit [indiceColumna];
            }
        });
        tabla.getColumnModel().getColumn(0).setResizable(false);
        tabla.getColumnModel().getColumn(0).setHeaderValue("Estadística");
        tabla.getColumnModel().getColumn(0).setMinWidth(150);
        tabla.getColumnModel().getColumn(1).setResizable(false);
        tabla.getColumnModel().getColumn(1).setHeaderValue("Valor");
    }
    /* POST: Establece el contenido de 'listaElementos' en la tabla */

    /* PRE: - */
    private void initComponents() {

        tabla = new JTable() {
            public void changeSelection(int indiceFila, int indiceColumna, boolean toggle,
              boolean extend) {}
        };
        tabla.setFocusable(false);
        tabla.getTableHeader().setReorderingAllowed(false);

        setViewportView(tabla);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la tabla */
}
