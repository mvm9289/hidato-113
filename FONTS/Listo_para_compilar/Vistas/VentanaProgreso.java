/*  Class VentanaProgreso:
    Descripcion: Vista para el progreso de la ejecucion del algoritmo, tanto de generacion
        de tableros como de verficacion. Muestra el tiempo restante para abortar la
        ejecucion del algoritmo y una barra de progreso ilustrativa.
    Autor: alex.catarineu
    Revisado: 21/12/2009 06:39 */

package Vistas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;

public class VentanaProgreso extends JDialog implements Runnable {
    
    private int tiempoRestante;
    private Thread miThread;
    private JLabel labelMensaje;
    private JLabel labelMensaje2;
    private JLabel labelTiempo;
    private JProgressBar barraProgreso;

    /* PRE: tiempo >= 0, 'titulo' es "Generando" o "Verificando" */
    public VentanaProgreso(int tiempo, String titulo) {

        initComponents(tiempo, titulo);
        setMyLayout();
    }
    /* POST: Crea una instancia de VentanaProgreso */

    /* PRE: - */
    public Thread obtenerThread() {

        return miThread;
    }
    /* POST: Retorna el thread de ejecucion de la ventana de progreso */

    /* PRE: - */
    public void start() {

        miThread.start();
    }
    /* POST: Inicia la ejecucion del thread */

    /* PRE: tiempoRestante >= 0 */
    private String tiempoToString() {

        String minutos = Integer.toString(tiempoRestante / 60);
        String segundos = Integer.toString(tiempoRestante % 60);

        if (tiempoRestante / 60 < 10) minutos = "0" + minutos;
        if (tiempoRestante % 60 < 10) segundos = "0" + segundos;

        return minutos + ":" + segundos;
    }
    /* POST: Retorna un String en el formato "MM:SS" a partir del tiempo indicado por
        tiempoRestante */

    /* PRE: tiempo >= 0, 'titulo' es "Generando" o "Verificando" */
    private void initComponents(int tiempo, String titulo) {

        String mensaje = titulo + " el tablero. Espere...";

        tiempoRestante = tiempo;
        miThread = new Thread(this);

        labelMensaje = new JLabel();
        labelMensaje.setText(mensaje);

        labelMensaje2 = new JLabel();
        labelMensaje2.setFont(new Font(null, Font.PLAIN, 10));
        labelMensaje2.setText("Tiempo restante para abortar:");

        labelTiempo = new JLabel();
        labelTiempo.setFont(new Font(null, Font.PLAIN, 10));
        labelTiempo.setText(tiempoToString());

        barraProgreso = new JProgressBar();
        barraProgreso.setMaximum(tiempo * 10);
        barraProgreso.setMinimum(0);
        barraProgreso.setValue(0);

        setTitle(titulo);
        setMinimumSize(new Dimension(285, 150));
        setResizable(false);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                ControladorVistas.getInstance().abortarAlgoritmo();
                setVisible(false);
                miThread.stop();
            }
        });
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        la ventana de progreso */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(getContentPane());
        
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(barraProgreso, GroupLayout.PREFERRED_SIZE, 221,
                      GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelMensaje2)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelTiempo))
                    .addComponent(labelMensaje))
                .addGap(74, 74, 74))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(labelMensaje)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMensaje2)
                    .addComponent(labelTiempo))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barraProgreso, GroupLayout.PREFERRED_SIZE,
                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro de la
        ventana de progreso */

    /* PRE: - */
    public void run() {

        while (tiempoRestante > 0) {
            --tiempoRestante;
            try {
                for (int i = 0; i < 10; ++i) {
                    Thread.sleep(100);
                    barraProgreso.setValue(barraProgreso.getValue() + 1);
                }
                labelTiempo.setText(tiempoToString());
            }
            catch (InterruptedException ex) {
                setVisible(false);
                miThread.stop();
            }
        }
        ControladorVistas.getInstance().abortarAlgoritmo();
        setVisible(false);
    }
    /* POST: Ejecucion del thread: descuenta cada segundo transcurrido al tiempo restante
        hasta que este llegue a 0 o el thread sea interrumpido o parado. A cada decima
        de segundo se actualiza la barra de progreso. Si el tiempo restante llega a 0 se
        aborta la ejecucion del algoritmo y se oculta la ventana de dialogo */
}