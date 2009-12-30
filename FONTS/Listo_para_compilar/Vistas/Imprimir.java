/*  Class Imprimir:
    Descripcion: Permite imprimir un tablero de Hidato
    Autor: alex.catarineu
    Revisado: 21/12/2009 11:35 */

package Vistas;

import Dominio.ControladorTablero;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Imprimir {

    private final static double MARGIN = 50.0;
    private CasillaImprimible[][] casillas;
    private JPanel tablero;
    private int n;
    private int m;
    private JFrame jf;

    /* PRE: contenido[0] y contenido[1] son dos matrices no vacias de igual medidas */
    public Imprimir(int[][][] contenido) {

        initComponents(contenido);
        jf = new JFrame();
        jf.add(tablero, BorderLayout.CENTER);
        jf.pack();

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName("Hidato");
        pj.setCopies(1);
        PageFormat format = pj.defaultPage();
        format.setOrientation(PageFormat.PORTRAIT);

        /* Creamos el Printable */
        pj.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum){
                
                if (pageNum > 0){
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D g2 = (Graphics2D) pg;

                /* Si nos pasamos de las medidas, hay que escalar */
                double height = (double)tablero.getHeight();
                double width = (double)tablero.getWidth();
                double scale = 1.0;
                if (height > pf.getHeight() - MARGIN ||
                  width  > pf.getWidth() - MARGIN) {
                    scale = Math.min((pf.getHeight() - MARGIN)/height,
                      (pf.getWidth() - MARGIN)/width);
                }
                height *= scale;
                width *= scale;

                /* Primero hacemos la translacion */
                g2.translate(pf.getImageableX() + (pf.getWidth() - width)/2,
                  pf.getImageableY() + (pf.getHeight() - height)/2);

                /* A continuacion escalamos */
                g2.scale(scale, scale);
                
                tablero.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        });

        /* Mostramos el dialogo de impresion */
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch(PrinterException pe) {
                System.out.println("Error al imprimir: " + pe);
            }
        }
    }
    /* POST: Se ha mostrado un dialogo de impresion. Si el usuario ha aceptado, se ha
        mandado imprimir el Hidato, si no, se ha abortado */

    /* PRE: contenido[0] y contenido[1] son dos matrices no vacias de igual medidas */
    private void initComponents(int[][][] contenido) {

        int maxX = 0;
        int maxY = 0;
        int maxValor = -1;
        
        n = contenido[0].length;
        m = contenido[0][1].length;
        casillas = new CasillaImprimible[n][m];
        tablero = new JPanel();
	tablero.setLayout(new GridLayout(n, m));

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                boolean esPre = contenido[1][i][j] == ControladorTablero.CAS_PREFIJADA;
                boolean esAct = contenido[1][i][j] == ControladorTablero.CAS_ACTIVA;
                casillas[i][j] = new CasillaImprimible(esPre, esAct, contenido[0][i][j]);
                tablero.add(casillas[i][j]);
                if (contenido[0][i][j] == 1) casillas[i][j].setInicialFinal();
                if (contenido[0][i][j] > maxValor) {
                    maxX = i;
                    maxY = j;
                    maxValor = contenido[0][i][j];
                }
            }
        }
        casillas[maxX][maxY].setInicialFinal();
    }
    /* POST: se han creado las casillas y el tablero y se han inicializado en funcion de
        contenido[][][] */
    

    private class CasillaImprimible extends JPanel {

        /* PRE: - */
        private CasillaImprimible(boolean prefijada, boolean activa, int valor) {

                JLabel contenido = new JLabel();
                Dimension dim = new Dimension(40, 40);

                contenido.setSize(dim);
                contenido.setHorizontalAlignment(JLabel.CENTER);
                contenido.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
                if (valor > 0) contenido.setText(Integer.toString(valor));
                else contenido.setText("");
                if (prefijada) setBackground(new Color(174, 174, 174));
                else if (!activa) setBackground(Color.BLACK);

                setLayout(new BorderLayout());
                add(contenido, BorderLayout.CENTER);

                setSize(dim);
                setMinimumSize(dim);
                setMaximumSize(dim);
                setPreferredSize(dim);
                setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        /* POST: Se ha creado una CasillaImprimible con los parametros pasados */

        /* PRE: - */
        private void setInicialFinal() {

            setBackground(new Color(91, 91, 91));
        }
        /* POST: Establece que la casilla en cuestion es la inicial o la final y pinta
            el fondo adecuadamente */
    }
}
