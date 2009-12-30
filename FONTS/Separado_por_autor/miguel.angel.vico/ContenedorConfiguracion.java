/*  Class ContenedorConfiguracion:
    Descripcion: Vista para el panel que contiene la configuracion de una partida nueva.
    Autor: miguel.angel.vico
    Revisado: 20/12/2009 20:10 */

package Vistas;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;

public class ContenedorConfiguracion extends JPanel {

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

    /* PRE: - */
    public ContenedorConfiguracion(JLabel labelNombre, JTextField fieldNombre,
      JLabel labelTopologia, JComboBox cajaTopologia, JLabel labelAnchura,
      JTextField fieldAnchura, JLabel labelAltura, JTextField fieldAltura,
      JLabel labelPrefijadas, JTextField fieldPrefijadas) {

        initComponents(labelNombre, fieldNombre, labelTopologia, cajaTopologia,
          labelAnchura, fieldAnchura, labelAltura, fieldAltura, labelPrefijadas,
          fieldPrefijadas);
        setMyLayout();
    }
    /* POST: Crea una instancia de ContenedorConfiguracion */

    /* PRE: - */
    private void initComponents(JLabel labelNombre, JTextField fieldNombre,
      JLabel labelTopologia, JComboBox cajaTopologia, JLabel labelAnchura,
      JTextField fieldAnchura, JLabel labelAltura, JTextField fieldAltura,
      JLabel labelPrefijadas, JTextField fieldPrefijadas) {

        this.labelNombre = labelNombre;
        this.fieldNombre = fieldNombre;
        this.labelTopologia = labelTopologia;
        this.cajaTopologia = cajaTopologia;
        this.labelAnchura = labelAnchura;
        this.fieldAnchura = fieldAnchura;
        this.labelAltura = labelAltura;
        this.fieldAltura = fieldAltura;
        this.labelPrefijadas = labelPrefijadas;
        this.fieldPrefijadas = fieldPrefijadas;

        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }
    /* POST: Inicializa correctamente todos los componentes y variables usadas por
        el panel de configuracion */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(this);
        
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING,
                              layout.createSequentialGroup()
                                .addComponent(labelTopologia, GroupLayout.PREFERRED_SIZE,
                                  65, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                  41, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING,
                              layout.createSequentialGroup()
                                .addComponent(labelNombre)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                  11, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelAnchura)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                  62, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelAltura)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                  73, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup
                          (GroupLayout.Alignment.LEADING)
                            .addComponent(cajaTopologia, GroupLayout.PREFERRED_SIZE, 113,
                              GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup
                                  (GroupLayout.Alignment.TRAILING)
                                    .addComponent(fieldAnchura,
                                      GroupLayout.Alignment.LEADING,
                                      GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(fieldAltura, GroupLayout.DEFAULT_SIZE,
                                      130, Short.MAX_VALUE)
                                    .addComponent(fieldNombre,
                                      GroupLayout.Alignment.LEADING,
                                      GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                                .addGap(35, 35, 35))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelPrefijadas)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(fieldPrefijadas, GroupLayout.PREFERRED_SIZE, 110,
                          GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre)
                    .addComponent(fieldNombre, GroupLayout.PREFERRED_SIZE, 20,
                      GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cajaTopologia, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTopologia))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldAnchura, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAnchura))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldAltura, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAltura))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPrefijadas, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPrefijadas))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        panel de configuracion */
}
