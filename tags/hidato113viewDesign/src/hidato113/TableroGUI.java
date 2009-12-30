/*  Class TableroGUI:
    Descripcion: "DESCRIPTION"
    Autor: miguel.angel.vico
    Revisado: "DATE" */

package hidato113;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class TableroGUI extends JPanel {

    private JPanel contenedor;
    private CasillaGUI casillas[][];
    private int maximoNumero;

    public TableroGUI(int altura, int anchura) {

        super();
        initComponents(altura, anchura);
        setMyLayout();
    }

    private void initComponents(int altura, int anchura) {

        Dimension size;

        size = new Dimension(anchura*CasillaGUI.CASE_SIZE,
          altura*CasillaGUI.CASE_SIZE);
        contenedor = new JPanel();
        contenedor.setPreferredSize(size);
        contenedor.setMinimumSize(size);
        contenedor.setMaximumSize(size);
        contenedor.setLayout(new GridLayout(altura, anchura));
        casillas = new CasillaGUI[altura][anchura];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new CasillaGUI();
                contenedor.add(casillas[i][j]);
            }
        }
        maximoNumero = altura*anchura;
    }

    private void setMyLayout() {

        GroupLayout tableroLayout;

        tableroLayout = new GroupLayout(this);
        setLayout(tableroLayout);
        tableroLayout.setHorizontalGroup(
            tableroLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(tableroLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(contenedor)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tableroLayout.setVerticalGroup(
            tableroLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(tableroLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(contenedor)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    public void setPreficada(int x, int y, boolean prefijada) {

        casillas[x][y].setPrefijada(prefijada);
    }

    public void setActiva(int x, int y, boolean activa) {

        casillas[x][y].setActiva(activa);
    }

    public void setValor(int x, int y, int valor) {

        casillas[x][y].setValor(String.valueOf(valor));
        if (valor == 1 || valor == maximoNumero) casillas[x][y].setInicialFinal(true);
    }
}