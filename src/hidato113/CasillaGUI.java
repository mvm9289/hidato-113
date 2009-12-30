/*  Class CasillaGUI:
    Descripcion: "DESCRIPTION"
    Autor: miguel.angel.vico
    Revisado: "DATE" */

package hidato113;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CasillaGUI extends JPanel {

    public final static int CASE_SIZE = 40;
    private boolean inicialFinal;
    private boolean prefijada;
    private JLabel contenido;

    public CasillaGUI() {

        super();
        initComponents();
        setMyLayout();
    }

    private void initComponents() {

        Dimension size;

        size = new Dimension(CASE_SIZE, CASE_SIZE);
        setMinimumSize(size);
        setMaximumSize(size);
        setPreferredSize(size);
        inicialFinal = false;
        prefijada = false;
        contenido = new JLabel();
        contenido.setSize(size);
        contenido.setHorizontalAlignment(JLabel.CENTER);
        contenido.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
    }

    private void setMyLayout() {

        setLayout(new BorderLayout());
        add(contenido, BorderLayout.CENTER);
    }

    private String getFondo() {

        if (inicialFinal) return "/Images/roja.png";
        else if (prefijada) return "/Images/gris.png";
        else return "/Images/amarilla.png";
    }

    public void paintComponent(Graphics g) {

        Dimension tamanio = getSize();
        ImageIcon fondo = new ImageIcon(getClass().getResource(getFondo()));

        g.drawImage(fondo.getImage(), 0, 0, tamanio.width, tamanio.height, null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public void setPrefijada(boolean prefijada) {

        this.prefijada = prefijada;
    }

    public void setInicialFinal(boolean inicialFinal) {

        this.inicialFinal = inicialFinal;
    }

    public void setActiva(boolean activa) {

        setVisible(activa);
    }

    public void setValor(String text) {

        contenido.setText(text);
    }
}
