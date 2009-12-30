/*  Class PanelLogin:
    Descripcion: Vista para el panel principal de inicar sesion.
    Autor: alex.catarineu
    Revisado: 21/12/2009 07:43 */

package Vistas;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

public class PanelLogin extends JPanel {

    private Login login;

    /* PRE: - */
    public PanelLogin() {

        initComponents();
        setMyLayout();
    }
    /* POST: Se configura un panel en la ventana principal para iniciar sesion */

    /* PRE: - */
    private void initComponents() {

        login = new Login();
        login.setVisible(true);
    }
    /* POST: Crea e inicializa correctamente todos los componentes y variables usadas por
        el panel de login */

    /* PRE: - */
    private void setMyLayout() {

        GroupLayout layout = new GroupLayout(this);
        
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                  GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                  GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );
    }
    /* POST: Establece la localizacion y los tama(ny)os de los componentes dentro del
        panel de login */
}