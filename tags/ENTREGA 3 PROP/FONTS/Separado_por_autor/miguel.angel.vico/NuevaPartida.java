/*  Class NuevaPartida:
    Descripcion: Vista para la ventana de creacion de una nueva partida.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 22:28 */

package Vistas;

import Dominio.ControladorTablero;
import Excepciones.InstanceRepeatedException;
import Utiles.FabricaTopologias;
import Utiles.Utiles;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class NuevaPartida extends JDialog {

    private final static int MIN_SIZE_NOMBRE = 6;
    private final static int MAX_SIZE_NOMBRE = 20;
    private final static int MIN_SIZE_TABLERO = 3;
    private final static int MAX_SIZE_TABLERO = 25;
    private final static int x100_INF_PREFIJADAS = 30;
    private final static int x100_SUP_PREFIJADAS = 70;

    private final static int SIZE_FACIL = 5;
    private final static int PREFIJADAS_FACIL = 11;
    private final static int SIZE_MEDIO = 9;
    private final static int PREFIJADAS_MEDIO = 36;
    private final static int SIZE_DIFICIL = 13;
    private final static int PREFIJADAS_DIFICIL = 76;

    /* Componentes */
    private JLabel labelNombre;
    private JTextField fieldNombre;
    private JLabel labelTopologia;
    private JComboBox cajaTopologia;
    private JLabel labelAnchura;
    private JTextField fieldAnchura;
    private JLabel labelAltura;
    private JTextField fieldAltura;
    private JLabel labelPrefijadas;
    private JTextField fieldPrefijadas;
    private ContenedorConfiguracion contenedorConfiguracion;
    private JRadioButton radioFacil;
    private JRadioButton radioMedio;
    private JRadioButton radioDificil;
    private JRadioButton radioPersonalizado;
    private ButtonGroup grupoBotones;
    private JLabel labelMensajes;
    private JButton botonGenerar;
    private JButton botonProponerTablero;
    private JButton botonProponerTopologia;
    private JButton botonRepositorio;
    private JButton botonCancelar;

    private int[][] formaTopologia;
    private int[][] formaTopologiaAnterior;
    private int topologia;
    private int dificultad;

    /* PRE: - */
    public NuevaPartida() {

        initComponents();
        setMyLayout();
    }
    /* POST: Crea una instancia de NuevaPartida */

    /* PRE: - */
    public int numeroCasillasTopologia(int[][] topologia) {

        int numeroCasillas = 0;

        for (int i = 0; i < topologia.length; ++i)
            for (int j = 0; j < topologia[i].length; ++j)
                if (topologia[i][j] == 0) ++numeroCasillas;

        return numeroCasillas;
    }
    /* POST: Retorna el numero de casillas activas que posee la topologia 'topolotia' */

    /* PRE: - */
    public void setFormaTopologia(int[][] formaTopologia) {

        this.formaTopologia = formaTopologia;
    }
    /* POST: Establece la topologia actual a 'formaTopologia' */

    /* PRE: - */
    public void cancelarTopologia() {

        if (formaTopologiaAnterior == null)
            ControladorVistas.getInstance().ocultarPanel();
        else ControladorVistas.getInstance()
          .proponerTopologia(formaTopologiaAnterior, this);

        formaTopologia = formaTopologiaAnterior;
        formaTopologiaAnterior = null;
    }
    /* POST: Cancela la topologia seleccionada actualmente. Si existia una topologia
        anterior a la actual, dicha topologia se recupera */

    /* PRE: - */
    private void obtenerTopologia() {

        int altura = Integer.parseInt(fieldAltura.getText().trim());
        int anchura = Integer.parseInt(fieldAnchura.getText().trim());

        switch (cajaTopologia.getSelectedIndex()) {
            case 0:
                formaTopologia = new int[altura][anchura];
                break;
            case 1:
                formaTopologia = FabricaTopologias.triangulo(anchura,
                  altura);
                break;
            case 2:
                formaTopologia = FabricaTopologias.rombo(anchura, altura);
                break;
            case 3:
                formaTopologia =
                  FabricaTopologias.elipse(anchura, altura);
                break;
            default:
                break;
        }
    }
    /* POST: Contruye la topologia en forma de matriz segun la topologia seleccionada en
        cajaTopologia */

    /* PRE: - */
    private void seleccionarFacil() {
        
        cajaTopologia.setSelectedIndex(0);
        cajaTopologia.setEnabled(false);
        fieldAnchura.setText(Integer.toString(SIZE_FACIL));
        fieldAnchura.setEnabled(false);
        fieldAltura.setText(Integer.toString(SIZE_FACIL));
        fieldAltura.setEnabled(false);
        fieldPrefijadas.setText(Integer.toString(PREFIJADAS_FACIL));
        fieldPrefijadas.setEnabled(false);
        botonProponerTablero.setEnabled(false);
        botonProponerTopologia.setEnabled(false);
        dificultad = ControladorTablero.DIF_FACIL;
        formaTopologia = null;
    }
    /* POST: Modifica los valores de los atributos de manera que quede seleccionada la
        dificultad facil */

    /* PRE: - */
    private void seleccionarMedio() {

        cajaTopologia.setSelectedIndex(0);
        cajaTopologia.setEnabled(false);
        fieldAnchura.setText(Integer.toString(SIZE_MEDIO));
        fieldAnchura.setEnabled(false);
        fieldAltura.setText(Integer.toString(SIZE_MEDIO));
        fieldAltura.setEnabled(false);
        fieldPrefijadas.setText(Integer.toString(PREFIJADAS_MEDIO));
        fieldPrefijadas.setEnabled(false);
        botonProponerTablero.setEnabled(false);
        botonProponerTopologia.setEnabled(false);
        dificultad = ControladorTablero.DIF_MEDIO;
        formaTopologia = null;
    }
    /* POST: Modifica los valores de los atributos de manera que quede seleccionada la
        dificultad media */

    /* PRE: - */
    private void seleccionarDificil() {

        cajaTopologia.setSelectedIndex(0);
        cajaTopologia.setEnabled(false);
        fieldAnchura.setText(Integer.toString(SIZE_DIFICIL));
        fieldAnchura.setEnabled(false);
        fieldAltura.setText(Integer.toString(SIZE_DIFICIL));
        fieldAltura.setEnabled(false);
        fieldPrefijadas.setText(Integer.toString(PREFIJADAS_DIFICIL));
        fieldPrefijadas.setEnabled(false);
        botonProponerTablero.setEnabled(false);
        botonProponerTopologia.setEnabled(false);
        dificultad = ControladorTablero.DIF_DIFICIL;
        formaTopologia = null;
    }
    /* POST: Modifica los valores de los atributos de manera que quede seleccionada la
        dificultad dificil */

    /* PRE: - */
    private void seleccionarPersonalizado() {

        cajaTopologia.setSelectedIndex(0);
        cajaTopologia.setEnabled(true);
        fieldAnchura.setText("");
        fieldAnchura.setEnabled(true);
        fieldAltura.setText("");
        fieldAltura.setEnabled(true);
        fieldPrefijadas.setText("");
        fieldPrefijadas.setEnabled(true);
        botonProponerTablero.setEnabled(true);
        botonProponerTopologia.setEnabled(true);
        dificultad = ControladorTablero.DIF_PERSONALIZADA;
        formaTopologia = null;
    }
    /* POST: Modifica los valores de los atributos de manera que quede seleccionada la
        dificultad personalizada */

    /* PRE: - */
    private void calcularLimitePrefijadas(int[] limite) {

        int numeroCasillas;

        if (formaTopologia == null) {
            obtenerTopologia();
            numeroCasillas = numeroCasillasTopologia(formaTopologia);
            formaTopologia = null;
        }
        else numeroCasillas = numeroCasillasTopologia(formaTopologia);

        limite[0] = (int)Utiles.redondearDouble(
          ((double)numeroCasillas * (double)x100_INF_PREFIJADAS) / 100.0, 0);
        limite[1] = (int)Utiles.redondearDouble(
          ((double)numeroCasillas * (double)x100_SUP_PREFIJADAS) / 100.0, 0);
    }
    /* POST: Calcula el limite inferior y superior de casillas prefijadas que debe tener
        la topologia actual, y retorna ambos valores (parametro de salida) */

    /* PRE: - */
    private void accionCambioTopologia() {

        if (formaTopologia != null) formaTopologiaAnterior = formaTopologia;
        formaTopologia = null;
        switch (cajaTopologia.getSelectedIndex()) {
            case 0:
                topologia = ControladorTablero.TOP_RECTANGULO;
                break;
            case 1:
                topologia = ControladorTablero.TOP_TRIANGULO;
                break;
            case 2:
                topologia = ControladorTablero.TOP_ROMBO;
                break;
            case 3:
                topologia = ControladorTablero.TOP_ELIPSE;
                break;
            default:
                break;
        }
    }
    /* POST: Cambia la topologia actual a la seleccionada por el usuario. Si existe una
        topologia ya seleccionada, esta es guardada para la posibilidad de recuperarla en
        en un futuro proximo */

    /* PRE: - */
    private void accionGenerar() {

        String idTablero = fieldNombre.getText().trim();
        String anchuraStr = fieldAnchura.getText().trim();
        String alturaStr = fieldAltura.getText().trim();
        String numPrefijadasStr = fieldPrefijadas.getText().trim();
        int anchura;
        int altura;
        int numPrefijadas;
        int[] limitePrefijadas = new int[2];

        labelMensajes.setText("");

        if (idTablero.length() < MIN_SIZE_NOMBRE || idTablero.length() > MAX_SIZE_NOMBRE)
            labelMensajes.setText("Nombre de tablero debe tener de " +
              Integer.toString(MIN_SIZE_NOMBRE) + " a " +
              Integer.toString(MAX_SIZE_NOMBRE) + " caracteres.");
        else if (!Utiles.alfanumericos(idTablero))
            labelMensajes.setText("Nombre de tablero solo debe tener números y/o " +
              "letras.");
        else if (!Utiles.esInt(anchuraStr))
            labelMensajes.setText("Anchura debe ser un número.");
        else if (!Utiles.esInt(alturaStr))
            labelMensajes.setText("Altura debe ser un número.");
        else if (!Utiles.esInt(numPrefijadasStr))
            labelMensajes.setText("Numero de casillas prefijadas debe ser un número.");
        else {
            anchura = Integer.parseInt(anchuraStr);
            altura = Integer.parseInt(alturaStr);
            numPrefijadas = Integer.parseInt(numPrefijadasStr);
            calcularLimitePrefijadas(limitePrefijadas);

            if (anchura < MIN_SIZE_TABLERO || anchura > MAX_SIZE_TABLERO)
                labelMensajes.setText("Anchura debe ser un número entre " +
                  Integer.toString(MIN_SIZE_TABLERO) + " y " +
                  Integer.toString(MAX_SIZE_TABLERO) + ".");
            else if (altura < MIN_SIZE_TABLERO || altura > MAX_SIZE_TABLERO)
                labelMensajes.setText("Altura debe ser un número entre " +
                  Integer.toString(MIN_SIZE_TABLERO) + " y " +
                  Integer.toString(MAX_SIZE_TABLERO) + ".");
            else if (numPrefijadas < limitePrefijadas[0] ||
              numPrefijadas > limitePrefijadas[1])
                labelMensajes.setText("Número de casillas prefijadas debe ser un " +
                  "número entre " + Integer.toString(limitePrefijadas[0]) + " y " +
                  Integer.toString(limitePrefijadas[1]) + ".");
            else {
                if (formaTopologia == null) obtenerTopologia();
                try {
                    if (ControladorVistas.getInstance().generarTablero(idTablero,
                      dificultad, topologia, numPrefijadas, formaTopologia))
                        setVisible(false);
                }
                catch (InstanceRepeatedException ex) {
                    labelMensajes.setText("Nombre de tablero ya existe.");
                }
            }
        }
    }
    /* POST: Recoge los valores de los campos y manda a generar un nuevo tablero
        con dichos valores. En el caso de que alguno de los valores no sea correcto
        muestra el error correspondiente */

    /* PRE: - */
    private void accionProponerTablero() {

        String idTablero = fieldNombre.getText().trim();
        String anchuraStr = fieldAnchura.getText().trim();
        String alturaStr = fieldAltura.getText().trim();
        int anchura;
        int altura;
        int[] limitePrefijadas = new int[2];

        labelMensajes.setText("");

        if (idTablero.length() < MIN_SIZE_NOMBRE || idTablero.length() > MAX_SIZE_NOMBRE)
            labelMensajes.setText("Nombre de tablero debe tener de " +
              Integer.toString(MIN_SIZE_NOMBRE) + " a " +
              Integer.toString(MAX_SIZE_NOMBRE) + " caracteres.");
        else if (!Utiles.alfanumericos(idTablero))
            labelMensajes.setText("Nombre de tablero solo debe tener números y/o " +
              "letras.");
        else if (!Utiles.esInt(anchuraStr))
            labelMensajes.setText("Anchura debe ser un número.");
        else if (!Utiles.esInt(alturaStr))
            labelMensajes.setText("Altura debe ser un número.");
        else {
            anchura = Integer.parseInt(anchuraStr);
            altura = Integer.parseInt(alturaStr);

            if (anchura < MIN_SIZE_TABLERO || anchura > MAX_SIZE_TABLERO)
                labelMensajes.setText("Anchura debe ser un número entre " +
                  Integer.toString(MIN_SIZE_TABLERO) + " y " +
                  Integer.toString(MAX_SIZE_TABLERO) + ".");
            else if (altura < MIN_SIZE_TABLERO || altura > MAX_SIZE_TABLERO)
                labelMensajes.setText("Altura debe ser un número entre " +
                  Integer.toString(MIN_SIZE_TABLERO) + " y " +
                  Integer.toString(MAX_SIZE_TABLERO) + ".");
            else {
                if (formaTopologia == null) obtenerTopologia();
                try {
                    calcularLimitePrefijadas(limitePrefijadas);
                    ControladorVistas.getInstance().crearProponerTablero(idTablero,
                      topologia, formaTopologia, limitePrefijadas, this);
                    setVisible(false);
                }
                catch (InstanceRepeatedException ex) {
                    labelMensajes.setText("Nombre de tablero ya existe.");
                }
            }
        }
    }
    /* POST: Recoge los valores de los campos y manda a crear un nuevo panel para
        proponer una un tablero con dichos valores. En el caso de que alguno de los
        valores no sea correcto muestra el error correspondiente */

    /* PRE: - */
    private void accionProponerTopologia() {

        String anchuraStr = fieldAnchura.getText().trim();
        String alturaStr = fieldAltura.getText().trim();
        int anchura;
        int altura;

        labelMensajes.setText("");

        if (!Utiles.esInt(anchuraStr))
            labelMensajes.setText("Anchura debe ser un número.");
        else if (!Utiles.esInt(alturaStr))
            labelMensajes.setText("Altura debe ser un número.");
        else {
            anchura = Integer.parseInt(anchuraStr);
            altura = Integer.parseInt(alturaStr);

            if (anchura < MIN_SIZE_TABLERO || anchura > MAX_SIZE_TABLERO)
                labelMensajes.setText("Anchura debe ser un número entre " +
                  Integer.toString(MIN_SIZE_TABLERO) + " y " +
                  Integer.toString(MAX_SIZE_TABLERO) + ".");
            else if (altura < MIN_SIZE_TABLERO || altura > MAX_SIZE_TABLERO)
                labelMensajes.setText("Altura debe ser un número entre " +
                  Integer.toString(MIN_SIZE_TABLERO) + " y " +
                  Integer.toString(MAX_SIZE_TABLERO) + ".");
            else {
                topologia = ControladorTablero.TOP_PERSONALIZADA;
                if (formaTopologia != null) formaTopologiaAnterior = formaTopologia;
                obtenerTopologia();
                ControladorVistas.getInstance().proponerTopologia(formaTopologia, this);
                setVisible(false);
            }
        }
    }
    /* POST: Recoge los valores de los campos y manda a crear un nuevo panel para
        proponer una topologia con dichos valores. En el caso de que alguno de los valores
        no sea correcto muestra el error correspondiente */

    /* PRE: - */
    private void initComponents() {

        labelNombre = new JLabel();
        labelNombre.setText("Nombre del tablero:");

        fieldNombre = new JTextField();

        labelTopologia = new JLabel();
        labelTopologia.setText("Topología:");

        cajaTopologia = new JComboBox();
        cajaTopologia.setModel(new DefaultComboBoxModel(new String[] {"Rectángulo",
          "Triángulo", "Rombo", "Elipse"}));
        cajaTopologia.addItemListener(new ItemListener(){

            public void itemStateChanged(ItemEvent e) {

                accionCambioTopologia();
            }
        });

        labelAnchura = new JLabel();
        labelAnchura.setText("Anchura:");

        fieldAnchura = new JTextField();
        fieldAnchura.addCaretListener(new CaretListener() {

            public void caretUpdate(CaretEvent e) {

                if (formaTopologia != null) formaTopologiaAnterior = formaTopologia;
                formaTopologia = null;
            }
        });

        labelAltura = new JLabel();
        labelAltura.setText("Altura:");

        fieldAltura = new JTextField();
        fieldAltura.addCaretListener(new CaretListener() {

            public void caretUpdate(CaretEvent e) {

                if (formaTopologia != null) formaTopologiaAnterior = formaTopologia;
                formaTopologia = null;
            }
        });

        labelPrefijadas = new JLabel();
        labelPrefijadas.setText("Número de casillas prefijadas:");

        fieldPrefijadas = new JTextField();

        contenedorConfiguracion = new ContenedorConfiguracion(labelNombre, fieldNombre,
          labelTopologia, cajaTopologia, labelAnchura, fieldAnchura, labelAltura,
          fieldAltura, labelPrefijadas, fieldPrefijadas);

        radioFacil = new JRadioButton();
        radioFacil.setText("Fácil");
        radioFacil.setSelected(true);
        radioFacil.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) seleccionarFacil();
            }
        });

        radioMedio = new JRadioButton();
        radioMedio.setText("Intermedio");
        radioMedio.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) seleccionarMedio();
            }
        });

        radioDificil = new JRadioButton();
        radioDificil.setText("Difícil");
        radioDificil.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) seleccionarDificil();
            }
        });

        radioPersonalizado = new JRadioButton();
        radioPersonalizado.setText("Personalizado");
        radioPersonalizado.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) seleccionarPersonalizado();
            }
        });

        grupoBotones = new ButtonGroup();
        grupoBotones.add(radioFacil);
        grupoBotones.add(radioMedio);
        grupoBotones.add(radioDificil);
        grupoBotones.add(radioPersonalizado);

        labelMensajes = new JLabel();
        labelMensajes.setText(" ");
        labelMensajes.setHorizontalAlignment(JLabel.CENTER);
        labelMensajes.setForeground(Color.red);

        botonGenerar = new JButton();
        botonGenerar.setText("Generar");
        botonGenerar.setToolTipText("Generar un tablero de hidato.");
        botonGenerar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                accionGenerar();
            }
        });

        botonProponerTablero = new JButton();
        botonProponerTablero.setText("Proponer");
        botonProponerTablero.setToolTipText("Proponer un tablero de hidato.");
        botonProponerTablero.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                accionProponerTablero();
            }
        });

        botonProponerTopologia = new JButton();
        botonProponerTopologia.setText("Proponer Topología");
        botonProponerTopologia.setToolTipText("Proponer la topología del tablero a " +
          "generar.");
        botonProponerTopologia.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                accionProponerTopologia();
            }
        });

        botonRepositorio = new JButton();
        botonRepositorio.setText("Repositorio");
        botonRepositorio.setToolTipText("Repositorio de hidatos.");
        botonRepositorio.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                if (ControladorVistas.getInstance().repositorio())
                    setVisible(false);
            }
        });

        botonCancelar = new JButton();
        botonCancelar.setText("Cancelar");
        botonCancelar.setToolTipText("Cancelar");
        botonCancelar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setVisible(false);
            }
        });

        seleccionarFacil();
        formaTopologia = null;
        topologia = ControladorTablero.TOP_RECTANGULO;
        dificultad = ControladorTablero.DIF_FACIL;

        setTitle("Nueva Partida - Configuración");
        setMinimumSize(new Dimension(435, 395));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la ventana de nueva partida */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(labelMensajes, GroupLayout.Alignment.LEADING,
                      GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(botonProponerTopologia)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 95,
                          Short.MAX_VALUE)
                        .addComponent(botonCancelar)
                        .addGap(30, 30, 30))
                    .addGroup(GroupLayout.Alignment.LEADING,
                      layout.createSequentialGroup()
                        .addComponent(botonGenerar, GroupLayout.PREFERRED_SIZE, 108,
                          GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonProponerTablero, GroupLayout.PREFERRED_SIZE,
                          111, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonRepositorio, GroupLayout.DEFAULT_SIZE, 140,
                          Short.MAX_VALUE))
                    .addComponent(contenedorConfiguracion, GroupLayout.Alignment.LEADING,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(radioFacil)
                        .addGap(10, 10, 10)
                        .addComponent(radioMedio)
                        .addGap(10, 10, 10)
                        .addComponent(radioDificil)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 87,
                          Short.MAX_VALUE)
                        .addComponent(radioPersonalizado)))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorConfiguracion, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(radioFacil)
                    .addComponent(radioPersonalizado)
                    .addComponent(radioMedio)
                    .addComponent(radioDificil))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelMensajes)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31,
                  Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonGenerar)
                    .addComponent(botonRepositorio)
                    .addComponent(botonProponerTablero))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(botonCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(botonProponerTopologia)))
                .addGap(15, 15, 15))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        ventana de nueva partida */
}