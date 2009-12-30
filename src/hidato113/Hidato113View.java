/*
 * Hidato113View.java
 */

package hidato113;

import Excepciones.BrowserNotFoundException;
import Excepciones.UnrecognizedOSException;
import Utiles.Navegador;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Arrays;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The application's main frame.
 */
public class Hidato113View extends FrameView {
    boolean gameInitialized;
    boolean sessionInitialized;
    TableroGUI tablero;

    public Hidato113View(SingleFrameApplication app) {
        super(app);
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        initComponents();
        initTable();
        
        gameInitialized=false;
        newgameMenuItem.setEnabled(false);
        loadMenuItem.setEnabled(false);
        saveMenuItem.setEnabled(false);
        endMenuItem.setEnabled(false);
        resetMenuItem.setEnabled(false);
        helpMenuItem.setEnabled(false);
        solveMenuItem.setEnabled(false);
        resetstatsMenuItem.setEnabled(false);
        statsMenuItem.setEnabled(false);
        rankMenuItem.setEnabled(false);
        logoutMenuItem.setEnabled(false);
        changeMenuItem.setEnabled(false);
        jPanel1.setVisible(false);
        deleteMenuItem.setEnabled(false);
        jPanel2.setVisible(false);
        jLabel3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel3.setText("<html><u>Crear una nueva cuenta</u>");
        jTable1.getColumn("Estadística").setPreferredWidth(120);
        jTable1.getColumn("Valor").setPreferredWidth(30);
        jTable2.getColumn("Estadística").setPreferredWidth(120);
        jTable2.getColumn("Valor").setPreferredWidth(30);
        mainFrame.pack();
    }

    @Action
    public void showAboutBox() {
        try {
            /* NORMAL */
            // Navegador.abrirURL(getClass().getResource("/ManualUsuario/ManualUsuario.html").toString());
            /* PARA EL JAR */
            Navegador.abrirURL(getJarFileDir() + "ManualUsuario/ManualUsuario.html");
        }
        catch (BrowserNotFoundException exc) {
            JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (UnrecognizedOSException exc) {
            JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception exc) {
            JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
            JOptionPane.showMessageDialog(mainFrame, exc.getClass().toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getJarFileDir () {
      String urlStr = getClass().getResource("/ManualUsuario").toString();
      int from = "jar:".length();
      int to = urlStr.indexOf("hidato113viewDesign.jar!/");
      return urlStr.substring(from, to);
    }

    @Action
    public void showConfirmRestartDialog() {
        String message = "¿Seguro que quieres resetear la partida?";
        String title = "Resetear Partida";
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();

        int option = JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.YES_NO_OPTION);
    }

    @Action
    public void showSaveDialog() {
        String message = "Introduce un nombre para la partida:";
        String title = "Guardar Partida";
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();

        String option = JOptionPane.showInputDialog(mainFrame, message, title, JOptionPane.QUESTION_MESSAGE);

        if(option!=null)
            JOptionPane.showMessageDialog(mainFrame, "La partida ha sido guardada con éxito.", "Guardar Partida", JOptionPane.INFORMATION_MESSAGE);
    }

    @Action
    public int showConfirmSaveDialog() {
        String message = "¿Quieres guardar la partida?";
        String title = "Guardar Partida";
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();

        int option = JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.YES_NO_CANCEL_OPTION);

        if(option==JOptionPane.YES_OPTION) showSaveDialog();

        return option;
    }

    @Action
    public void showNewGameDialog() {
        int option=0;
        if(gameInitialized) option=showConfirmSaveDialog();
        if(option==JOptionPane.NO_OPTION || option==JOptionPane.YES_OPTION) {
            JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
            newgameFrame.setLocationRelativeTo(mainFrame);
            initPanel();
            newgameFrame.setVisible(true);
        }
    }

    @Action
    public void showStatisticsDialog() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        statisticsFrame.setLocationRelativeTo(mainFrame);
        statisticsFrame.setVisible(true);
    }

    @Action
    public void hideStatisticsDialog() {
        statisticsFrame.setVisible(false);
    }

    @Action
    public void showRankingDialog() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        rankingFrame.setLocationRelativeTo(mainFrame);
        rankingFrame.setVisible(true);
    }

    @Action
    public void hideRankingDialog() {
        rankingFrame.setVisible(false);
    }

    @Action
    public void resetStatistics() {
        String message = "¿Estás seguro de que quieres resetear tus estadísticas?";
        String title = "Resetear Estadísticas";
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();

        int option = JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
        if(option==JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(mainFrame, "Estadísticas reseteadas.", title, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Action
    public void showLoadGameDialog() {
        int option=0;
        if(gameInitialized) option=showConfirmSaveDialog();
        if(option==JOptionPane.NO_OPTION || option==JOptionPane.YES_OPTION) {
            JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
            savegamesFrame.setLocationRelativeTo(mainFrame);
            savegamesFrame.setVisible(true);
        }
    }

    @Action
    public int showEndGameDialog() {
        int option=showConfirmSaveDialog();
        if(option==JOptionPane.NO_OPTION || option==JOptionPane.YES_OPTION) {
            jPanel1.setVisible(false);
            gameInitialized=false;
            saveMenuItem.setEnabled(false);
            endMenuItem.setEnabled(false);
            resetMenuItem.setEnabled(false);
            helpMenuItem.setEnabled(false);
            solveMenuItem.setEnabled(false);
            jPanel2.setVisible(false);
        }
        return option;
    }

    @Action
    public void loadButtonAction() {
        savegamesFrame.setVisible(false);
        jPanel1.setVisible(true);
        gameInitialized=true;
        saveMenuItem.setEnabled(true);
        endMenuItem.setEnabled(true);
        resetMenuItem.setEnabled(true);
        helpMenuItem.setEnabled(true);
        solveMenuItem.setEnabled(true);
    }

    @Action
    public void showRepositoryDialog() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        repositoryFrame.setLocationRelativeTo(mainFrame);
        repositoryFrame.setVisible(true);
    }

    @Action
    public void oktableButtonAction() {
        repositoryFrame.setVisible(false);
        newgameFrame.setVisible(false);
        jPanel1.setVisible(true);
        gameInitialized=true;
        saveMenuItem.setEnabled(true);
        endMenuItem.setEnabled(true);
        resetMenuItem.setEnabled(true);
        helpMenuItem.setEnabled(true);
        solveMenuItem.setEnabled(true);
    }

    @Action
    public void generateHidatoButtonAction() {
        newgameFrame.setVisible(false);
        jPanel1.setVisible(true);
        gameInitialized=true;
        saveMenuItem.setEnabled(true);
        endMenuItem.setEnabled(true);
        resetMenuItem.setEnabled(true);
        helpMenuItem.setEnabled(true);
        solveMenuItem.setEnabled(true);
    }

    @Action
    public void proposHidatoButtonAction() {
        newgameFrame.setVisible(false);
        jPanel1.setVisible(true);
        gameInitialized=true;
        saveMenuItem.setEnabled(true);
        endMenuItem.setEnabled(true);
        resetMenuItem.setEnabled(true);
        helpMenuItem.setEnabled(true);
        solveMenuItem.setEnabled(true);
    }

    @Action
    public void showLogoutDialog() {
        int option = JOptionPane.YES_OPTION;
        if(gameInitialized) option = showEndGameDialog();
        if(option == JOptionPane.YES_OPTION || option == JOptionPane.NO_OPTION) {
            JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
            newgameMenuItem.setEnabled(false);
            loadMenuItem.setEnabled(false);
            resetstatsMenuItem.setEnabled(false);
            statsMenuItem.setEnabled(false);
            rankMenuItem.setEnabled(false);
            logoutMenuItem.setEnabled(false);
            changeMenuItem.setEnabled(false);
            deleteMenuItem.setEnabled(false);
            JOptionPane.showMessageDialog(mainFrame, "Sesión cerrada correctamente.", "Cerrar Sesión", JOptionPane.INFORMATION_MESSAGE);
            StartPasswordEdit.setText("");
            startLogginPanel.setVisible(true);
        }
    }

    @Action
    public void showChangePassDialog() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        changepassDialog.setLocationRelativeTo(mainFrame);
        changepassDialog.setVisible(true);
    }

    @Action
    public void cancelChangeButton() {
        changepassDialog.setVisible(false);
    }

    @Action
    public void okChangeButton() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        if(Arrays.equals(passwordField1.getPassword(),passwordField3.getPassword())) {
            changepassDialog.setVisible(false);
            JOptionPane.showMessageDialog(mainFrame, "Contraseña modificada correctamente.", "Modificar Contraseña", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            changepassDialog.setVisible(false);
            JOptionPane.showMessageDialog(mainFrame, "La nueva contraseña y la repetición no coinciden.", "Modificar Contraseña", JOptionPane.ERROR_MESSAGE);
            changepassDialog.setVisible(true);
        }
    }

    @Action
    public void quit() {
        int option=0;
        if(gameInitialized) option=showConfirmSaveDialog();
        if(option==JOptionPane.NO_OPTION || option==JOptionPane.YES_OPTION) System.exit(0);
    }

    public void showNewAccountDialog() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        newaccountDialog.setLocationRelativeTo(mainFrame);
        newaccountDialog.setVisible(true);
    }
    
    @Action
    public void hideNewAccountDialog() {
        newaccountDialog.setVisible(false);
    }

    @Action
    public void okNewAccount() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        if(Arrays.equals(passwordField6.getPassword(),passwordField4.getPassword())) {
            newaccountDialog.setVisible(false);
            JOptionPane.showMessageDialog(mainFrame, "Cuenta creada correctamente.", "Crear cuenta", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            newaccountDialog.setVisible(false);
            JOptionPane.showMessageDialog(mainFrame, "La contraseña y la repetición no coinciden.", "Crear cuenta", JOptionPane.ERROR_MESSAGE);
            newaccountDialog.setVisible(true);
        }
    }

    @Action
    public void deleteAccount() {
        String message = "¿Seguro que quieres eliminar tu cuenta?";
        String title = "Eliminar Cuenta";
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();

        int option = JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.YES_NO_OPTION);
        if(option==JOptionPane.YES_OPTION) {
            jPanel1.setVisible(false);
            gameInitialized=false;
            saveMenuItem.setEnabled(false);
            endMenuItem.setEnabled(false);
            resetMenuItem.setEnabled(false);
            helpMenuItem.setEnabled(false);
            solveMenuItem.setEnabled(false);
            newgameMenuItem.setEnabled(false);
            loadMenuItem.setEnabled(false);
            resetstatsMenuItem.setEnabled(false);
            statsMenuItem.setEnabled(false);
            rankMenuItem.setEnabled(false);
            logoutMenuItem.setEnabled(false);
            changeMenuItem.setEnabled(false);
            deleteMenuItem.setEnabled(false);
            JOptionPane.showMessageDialog(mainFrame, "Cuenta eliminada.", "Eliminar Cuenta", JOptionPane.INFORMATION_MESSAGE);
            StartPasswordEdit.setText("");
            startLogginPanel.setVisible(true);
        }
    }

    public void initPanel() {
        HabilitaPanel(false);
        proposButton.setEnabled(false);
        WidthEdit.setText("6");
        HeightEdit.setText("6");
        NumPredefEdit.setText("10");
    }

    public void InitStartPanel() {
        jPanel1.setVisible(false);
        startLogginPanel.setVisible(true);
    }

    @Action
    public void okStartLoginButton() {
        JFrame mainFrame = Hidato113App.getApplication().getMainFrame();
        if(Arrays.equals(StartPasswordEdit.getPassword(),new String("PasswordIsIt").toCharArray())) {
            startLogginPanel.setVisible(false);
            newgameMenuItem.setEnabled(true);
            loadMenuItem.setEnabled(true);
            resetstatsMenuItem.setEnabled(true);
            statsMenuItem.setEnabled(true);
            rankMenuItem.setEnabled(true);
            logoutMenuItem.setEnabled(true);
            changeMenuItem.setEnabled(true);
            deleteMenuItem.setEnabled(true);
            JOptionPane.showMessageDialog(mainFrame, "Inicio de sesión correcto.", "Iniciar Sesión", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            JOptionPane.showMessageDialog(mainFrame, "Contraseña incorrecta.", "Iniciar Sesión", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        startLogginPanel = new javax.swing.JPanel();
        StartSessionButton = new javax.swing.JButton();
        StartPasswordLbl = new javax.swing.JLabel();
        StartUserLbl = new javax.swing.JLabel();
        StartUserEdit = new javax.swing.JTextField();
        StartPasswordEdit = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newgameMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        loadMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        endMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        resetMenuItem = new javax.swing.JMenuItem();
        accountMenu = new javax.swing.JMenu();
        logoutMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        changeMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        deleteMenuItem = new javax.swing.JMenuItem();
        statsMenu = new javax.swing.JMenu();
        statsMenuItem = new javax.swing.JMenuItem();
        rankMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        resetstatsMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        solveMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        newgameFrame = new javax.swing.JFrame();
        repositoryButton = new javax.swing.JButton();
        generateButton = new javax.swing.JButton();
        proposButton = new javax.swing.JButton();
        EasyRdbtn = new javax.swing.JRadioButton();
        MediumRdbtn = new javax.swing.JRadioButton();
        HardlRdbtn = new javax.swing.JRadioButton();
        PersonalizeRdbtn = new javax.swing.JRadioButton();
        PanelPersonalitzat = new javax.swing.JPanel();
        TopologyCbBox = new javax.swing.JComboBox();
        TopologyLbl = new javax.swing.JLabel();
        WidthLbl = new javax.swing.JLabel();
        HeightLbl = new javax.swing.JLabel();
        NumPredefLbl = new javax.swing.JLabel();
        WidthEdit = new javax.swing.JTextField();
        HeightEdit = new javax.swing.JTextField();
        NumPredefEdit = new javax.swing.JTextField();
        savegamesFrame = new javax.swing.JFrame();
        SaveGamesScrollPanel = new javax.swing.JScrollPane();
        SaveGamesList = new javax.swing.JList();
        LoadBtn = new javax.swing.JButton();
        SortLbl = new javax.swing.JLabel();
        SortCbBox = new javax.swing.JComboBox();
        repositoryFrame = new javax.swing.JFrame();
        repositoryScrollPane = new javax.swing.JScrollPane();
        repositoryList = new javax.swing.JList();
        oktableButton = new javax.swing.JButton();
        SortLbl1 = new javax.swing.JLabel();
        SortCbBox1 = new javax.swing.JComboBox();
        changepassDialog = new javax.swing.JDialog();
        passwordField1 = new javax.swing.JPasswordField();
        acceptButton1 = new javax.swing.JButton();
        cancelButton1 = new javax.swing.JButton();
        passwordLabel3 = new javax.swing.JLabel();
        passwordLabel1 = new javax.swing.JLabel();
        passwordField2 = new javax.swing.JPasswordField();
        passwordField3 = new javax.swing.JPasswordField();
        passwordLabel2 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        newaccountDialog = new javax.swing.JDialog();
        passwordField4 = new javax.swing.JPasswordField();
        acceptButton2 = new javax.swing.JButton();
        cancelButton2 = new javax.swing.JButton();
        passwordLabel4 = new javax.swing.JLabel();
        passwordLabel5 = new javax.swing.JLabel();
        passwordField6 = new javax.swing.JPasswordField();
        passwordLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        statisticsFrame = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        rankingFrame = new javax.swing.JFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        mainPanel.setMinimumSize(new java.awt.Dimension(400, 330));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(400, 330));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                mostrarTiempo(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        startLogginPanel.setName("startLogginPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(hidato113.Hidato113App.class).getContext().getActionMap(Hidato113View.class, this);
        StartSessionButton.setAction(actionMap.get("okStartLoginButton")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(hidato113.Hidato113App.class).getContext().getResourceMap(Hidato113View.class);
        StartSessionButton.setText(resourceMap.getString("StartSessionButton.text")); // NOI18N
        StartSessionButton.setToolTipText(resourceMap.getString("StartSessionButton.toolTipText")); // NOI18N
        StartSessionButton.setName("StartSessionButton"); // NOI18N

        StartPasswordLbl.setText(resourceMap.getString("StartPasswordLbl.text")); // NOI18N
        StartPasswordLbl.setName("StartPasswordLbl"); // NOI18N

        StartUserLbl.setText(resourceMap.getString("StartUserLbl.text")); // NOI18N
        StartUserLbl.setName("StartUserLbl"); // NOI18N

        StartUserEdit.setText(resourceMap.getString("StartUserEdit.text")); // NOI18N
        StartUserEdit.setName("StartUserEdit"); // NOI18N

        StartPasswordEdit.setName("StartPasswordEdit"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setForeground(resourceMap.getColor("jLabel3.foreground")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NewAccountHandler(evt);
            }
        });

        javax.swing.GroupLayout startLogginPanelLayout = new javax.swing.GroupLayout(startLogginPanel);
        startLogginPanel.setLayout(startLogginPanelLayout);
        startLogginPanelLayout.setHorizontalGroup(
            startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startLogginPanelLayout.createSequentialGroup()
                .addGroup(startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(startLogginPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(StartSessionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, startLogginPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(StartUserLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(StartPasswordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(StartUserEdit, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(StartPasswordEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(startLogginPanelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel3)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        startLogginPanelLayout.setVerticalGroup(
            startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startLogginPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StartUserEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StartUserLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(startLogginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StartPasswordEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StartPasswordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(StartSessionButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setName("jPanel2"); // NOI18N

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setEnabled(false);
        jButton3.setName("jButton3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(33, 33, 33)
                .addComponent(jLabel5)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jButton3)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                    .addContainerGap(78, Short.MAX_VALUE)
                    .addComponent(startLogginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(78, Short.MAX_VALUE)))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                    .addContainerGap(114, Short.MAX_VALUE)
                    .addComponent(startLogginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(70, Short.MAX_VALUE)))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newgameMenuItem.setAction(actionMap.get("showNewGameDialog")); // NOI18N
        newgameMenuItem.setText(resourceMap.getString("newgameMenuItem.text")); // NOI18N
        newgameMenuItem.setToolTipText(resourceMap.getString("newgameMenuItem.toolTipText")); // NOI18N
        newgameMenuItem.setName("newgameMenuItem"); // NOI18N
        fileMenu.add(newgameMenuItem);

        jSeparator4.setName("jSeparator4"); // NOI18N
        fileMenu.add(jSeparator4);

        loadMenuItem.setAction(actionMap.get("showLoadGameDialog")); // NOI18N
        loadMenuItem.setText(resourceMap.getString("loadMenuItem.text")); // NOI18N
        loadMenuItem.setToolTipText(resourceMap.getString("loadMenuItem.toolTipText")); // NOI18N
        loadMenuItem.setName("loadMenuItem"); // NOI18N
        fileMenu.add(loadMenuItem);

        saveMenuItem.setAction(actionMap.get("showSaveDialog")); // NOI18N
        saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); // NOI18N
        saveMenuItem.setToolTipText(resourceMap.getString("saveMenuItem.toolTipText")); // NOI18N
        saveMenuItem.setName("saveMenuItem"); // NOI18N
        fileMenu.add(saveMenuItem);

        jSeparator7.setName("jSeparator7"); // NOI18N
        fileMenu.add(jSeparator7);

        endMenuItem.setAction(actionMap.get("showEndGameDialog")); // NOI18N
        endMenuItem.setText(resourceMap.getString("endMenuItem.text")); // NOI18N
        endMenuItem.setToolTipText(resourceMap.getString("endMenuItem.toolTipText")); // NOI18N
        endMenuItem.setName("endMenuItem"); // NOI18N
        fileMenu.add(endMenuItem);
        endMenuItem.getAccessibleContext().setAccessibleDescription(resourceMap.getString("endMenuItem.AccessibleContext.accessibleDescription")); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setToolTipText(resourceMap.getString("exitMenuItem.toolTipText")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);
        exitMenuItem.getAccessibleContext().setAccessibleDescription(resourceMap.getString("exitMenuItem.AccessibleContext.accessibleDescription")); // NOI18N

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        resetMenuItem.setAction(actionMap.get("showConfirmSaveDialog")); // NOI18N
        resetMenuItem.setText(resourceMap.getString("resetMenuItem.text")); // NOI18N
        resetMenuItem.setToolTipText(resourceMap.getString("resetMenuItem.toolTipText")); // NOI18N
        resetMenuItem.setName("resetMenuItem"); // NOI18N
        editMenu.add(resetMenuItem);

        menuBar.add(editMenu);

        accountMenu.setText(resourceMap.getString("accountMenu.text")); // NOI18N
        accountMenu.setName("accountMenu"); // NOI18N

        logoutMenuItem.setAction(actionMap.get("showLogoutDialog")); // NOI18N
        logoutMenuItem.setText(resourceMap.getString("logoutMenuItem.text")); // NOI18N
        logoutMenuItem.setToolTipText(resourceMap.getString("logoutMenuItem.toolTipText")); // NOI18N
        logoutMenuItem.setName("logoutMenuItem"); // NOI18N
        accountMenu.add(logoutMenuItem);

        jSeparator5.setName("jSeparator5"); // NOI18N
        accountMenu.add(jSeparator5);

        changeMenuItem.setAction(actionMap.get("showChangePassDialog")); // NOI18N
        changeMenuItem.setText(resourceMap.getString("changeMenuItem.text")); // NOI18N
        changeMenuItem.setToolTipText(resourceMap.getString("changeMenuItem.toolTipText")); // NOI18N
        changeMenuItem.setName("changeMenuItem"); // NOI18N
        accountMenu.add(changeMenuItem);

        jSeparator3.setName("jSeparator3"); // NOI18N
        accountMenu.add(jSeparator3);

        deleteMenuItem.setAction(actionMap.get("deleteAccount")); // NOI18N
        deleteMenuItem.setText(resourceMap.getString("deleteMenuItem.text")); // NOI18N
        deleteMenuItem.setToolTipText(resourceMap.getString("deleteMenuItem.toolTipText")); // NOI18N
        deleteMenuItem.setName("deleteMenuItem"); // NOI18N
        accountMenu.add(deleteMenuItem);

        menuBar.add(accountMenu);

        statsMenu.setText(resourceMap.getString("statsMenu.text")); // NOI18N
        statsMenu.setName("statsMenu"); // NOI18N

        statsMenuItem.setAction(actionMap.get("showStatisticsDialog")); // NOI18N
        statsMenuItem.setText(resourceMap.getString("statsMenuItem.text")); // NOI18N
        statsMenuItem.setToolTipText(resourceMap.getString("statsMenuItem.toolTipText")); // NOI18N
        statsMenuItem.setName("statsMenuItem"); // NOI18N
        statsMenu.add(statsMenuItem);

        rankMenuItem.setAction(actionMap.get("showRankingDialog")); // NOI18N
        rankMenuItem.setText(resourceMap.getString("rankMenuItem.text")); // NOI18N
        rankMenuItem.setToolTipText(resourceMap.getString("rankMenuItem.toolTipText")); // NOI18N
        rankMenuItem.setName("rankMenuItem"); // NOI18N
        statsMenu.add(rankMenuItem);

        jSeparator6.setName("jSeparator6"); // NOI18N
        statsMenu.add(jSeparator6);

        resetstatsMenuItem.setAction(actionMap.get("resetStatistics")); // NOI18N
        resetstatsMenuItem.setText(resourceMap.getString("resetstatsMenuItem.text")); // NOI18N
        resetstatsMenuItem.setToolTipText(resourceMap.getString("resetstatsMenuItem.toolTipText")); // NOI18N
        resetstatsMenuItem.setName("resetstatsMenuItem"); // NOI18N
        statsMenu.add(resetstatsMenuItem);

        menuBar.add(statsMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        helpMenuItem.setText(resourceMap.getString("helpMenuItem.text")); // NOI18N
        helpMenuItem.setName("helpMenuItem"); // NOI18N
        helpMenu.add(helpMenuItem);

        solveMenuItem.setText(resourceMap.getString("solveMenuItem.text")); // NOI18N
        solveMenuItem.setName("solveMenuItem"); // NOI18N
        helpMenu.add(solveMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        helpMenu.add(jSeparator2);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setToolTipText(resourceMap.getString("aboutMenuItem.toolTipText")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setValue(30);
        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        newgameFrame.setTitle(resourceMap.getString("newgameFrame.title")); // NOI18N
        newgameFrame.setLocationByPlatform(true);
        newgameFrame.setMinimumSize(new java.awt.Dimension(420, 330));
        newgameFrame.setName("newgameFrame"); // NOI18N
        newgameFrame.setResizable(false);

        repositoryButton.setAction(actionMap.get("showRepositoryDialog")); // NOI18N
        repositoryButton.setText(resourceMap.getString("repositoryButton.text")); // NOI18N
        repositoryButton.setToolTipText(resourceMap.getString("repositoryButton.toolTipText")); // NOI18N
        repositoryButton.setName("repositoryButton"); // NOI18N

        generateButton.setAction(actionMap.get("generateHidatoButtonAction")); // NOI18N
        generateButton.setText(resourceMap.getString("generateButton.text")); // NOI18N
        generateButton.setToolTipText(resourceMap.getString("generateButton.toolTipText")); // NOI18N
        generateButton.setName("generateButton"); // NOI18N

        proposButton.setAction(actionMap.get("proposHidatoButtonAction")); // NOI18N
        proposButton.setText(resourceMap.getString("proposButton.text")); // NOI18N
        proposButton.setToolTipText(resourceMap.getString("proposButton.toolTipText")); // NOI18N
        proposButton.setName("proposButton"); // NOI18N

        buttonGroup1.add(EasyRdbtn);
        EasyRdbtn.setText(resourceMap.getString("EasyRdbtn.text")); // NOI18N
        EasyRdbtn.setName("EasyRdbtn"); // NOI18N
        EasyRdbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EasyRdbtnMouseClicked(evt);
            }
        });

        buttonGroup1.add(MediumRdbtn);
        MediumRdbtn.setText(resourceMap.getString("MediumRdbtn.text")); // NOI18N
        MediumRdbtn.setName("MediumRdbtn"); // NOI18N
        MediumRdbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MediumRdbtnMouseClicked(evt);
            }
        });

        buttonGroup1.add(HardlRdbtn);
        HardlRdbtn.setText(resourceMap.getString("HardlRdbtn.text")); // NOI18N
        HardlRdbtn.setName("HardlRdbtn"); // NOI18N
        HardlRdbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HardlRdbtnMouseClicked(evt);
            }
        });

        buttonGroup1.add(PersonalizeRdbtn);
        PersonalizeRdbtn.setText(resourceMap.getString("PersonalizeRdbtn.text")); // NOI18N
        PersonalizeRdbtn.setName("PersonalizeRdbtn"); // NOI18N
        PersonalizeRdbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PersonalizeRdbtnMouseClicked(evt);
            }
        });

        PanelPersonalitzat.setBackground(resourceMap.getColor("PanelPersonalitzat.background")); // NOI18N
        PanelPersonalitzat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        PanelPersonalitzat.setName("PanelPersonalitzat"); // NOI18N

        TopologyCbBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rectángulo", "Triángulo", "Elipse" }));
        TopologyCbBox.setName("TopologyCbBox"); // NOI18N

        TopologyLbl.setFont(resourceMap.getFont("TopologyLbl.font")); // NOI18N
        TopologyLbl.setText(resourceMap.getString("TopologyLbl.text")); // NOI18N
        TopologyLbl.setName("TopologyLbl"); // NOI18N

        WidthLbl.setText(resourceMap.getString("WidthLbl.text")); // NOI18N
        WidthLbl.setName("WidthLbl"); // NOI18N

        HeightLbl.setText(resourceMap.getString("HeightLbl.text")); // NOI18N
        HeightLbl.setName("HeightLbl"); // NOI18N

        NumPredefLbl.setText(resourceMap.getString("NumPredefLbl.text")); // NOI18N
        NumPredefLbl.setName("NumPredefLbl"); // NOI18N

        WidthEdit.setName("WidthEdit"); // NOI18N

        HeightEdit.setText(resourceMap.getString("HeightEdit.text")); // NOI18N
        HeightEdit.setName("HeightEdit"); // NOI18N

        NumPredefEdit.setName("NumPredefEdit"); // NOI18N

        javax.swing.GroupLayout PanelPersonalitzatLayout = new javax.swing.GroupLayout(PanelPersonalitzat);
        PanelPersonalitzat.setLayout(PanelPersonalitzatLayout);
        PanelPersonalitzatLayout.setHorizontalGroup(
            PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPersonalitzatLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPersonalitzatLayout.createSequentialGroup()
                        .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HeightLbl)
                            .addComponent(WidthLbl)
                            .addComponent(TopologyLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelPersonalitzatLayout.createSequentialGroup()
                                .addComponent(TopologyCbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelPersonalitzatLayout.createSequentialGroup()
                                .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(WidthEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                    .addComponent(HeightEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                                .addGap(108, 108, 108))))
                    .addGroup(PanelPersonalitzatLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NumPredefLbl)
                        .addGap(18, 18, 18)
                        .addComponent(NumPredefEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        PanelPersonalitzatLayout.setVerticalGroup(
            PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPersonalitzatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TopologyLbl)
                    .addComponent(TopologyCbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(WidthLbl)
                    .addComponent(WidthEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HeightLbl)
                    .addComponent(HeightEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelPersonalitzatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NumPredefEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NumPredefLbl))
                .addContainerGap())
        );

        javax.swing.GroupLayout newgameFrameLayout = new javax.swing.GroupLayout(newgameFrame.getContentPane());
        newgameFrame.getContentPane().setLayout(newgameFrameLayout);
        newgameFrameLayout.setHorizontalGroup(
            newgameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newgameFrameLayout.createSequentialGroup()
                .addGroup(newgameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newgameFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(repositoryButton)
                        .addGap(18, 18, 18)
                        .addComponent(proposButton)
                        .addGap(18, 18, 18)
                        .addComponent(generateButton))
                    .addGroup(newgameFrameLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(EasyRdbtn)
                        .addGap(18, 18, 18)
                        .addComponent(MediumRdbtn)
                        .addGap(30, 30, 30)
                        .addComponent(HardlRdbtn)
                        .addGap(18, 18, 18)
                        .addComponent(PersonalizeRdbtn))
                    .addGroup(newgameFrameLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(PanelPersonalitzat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        newgameFrameLayout.setVerticalGroup(
            newgameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newgameFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelPersonalitzat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(newgameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EasyRdbtn)
                    .addComponent(PersonalizeRdbtn)
                    .addComponent(HardlRdbtn)
                    .addComponent(MediumRdbtn))
                .addGap(37, 37, 37)
                .addGroup(newgameFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(repositoryButton)
                    .addComponent(proposButton)
                    .addComponent(generateButton))
                .addContainerGap())
        );

        savegamesFrame.setTitle(resourceMap.getString("savegamesFrame.title")); // NOI18N
        savegamesFrame.setMinimumSize(new java.awt.Dimension(370, 340));
        savegamesFrame.setName("savegamesFrame"); // NOI18N

        SaveGamesScrollPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        SaveGamesScrollPanel.setName("SaveGamesScrollPanel"); // NOI18N

        SaveGamesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "savegame001", "savegame002", "savegame003", "savegame004", "savegame005", "savegame006", "savegame007", "savegame008", "savegame009", "savegame010", "savegame011", "savegame012", "savegame013", "savegame014", "savegame015", "savegame016" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        SaveGamesList.setName("SaveGamesList"); // NOI18N
        SaveGamesScrollPanel.setViewportView(SaveGamesList);
        SaveGamesList.getAccessibleContext().setAccessibleParent(savegamesFrame);

        LoadBtn.setAction(actionMap.get("loadButtonAction")); // NOI18N
        LoadBtn.setText(resourceMap.getString("LoadBtn.text")); // NOI18N
        LoadBtn.setToolTipText(resourceMap.getString("LoadBtn.toolTipText")); // NOI18N
        LoadBtn.setName("LoadBtn"); // NOI18N

        SortLbl.setText(resourceMap.getString("SortLbl.text")); // NOI18N
        SortLbl.setName("SortLbl"); // NOI18N

        SortCbBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre de la partida", "Fecha" }));
        SortCbBox.setName("SortCbBox"); // NOI18N

        javax.swing.GroupLayout savegamesFrameLayout = new javax.swing.GroupLayout(savegamesFrame.getContentPane());
        savegamesFrame.getContentPane().setLayout(savegamesFrameLayout);
        savegamesFrameLayout.setHorizontalGroup(
            savegamesFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(savegamesFrameLayout.createSequentialGroup()
                .addGroup(savegamesFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, savegamesFrameLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(savegamesFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SaveGamesScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addGroup(savegamesFrameLayout.createSequentialGroup()
                                .addComponent(SortLbl)
                                .addGap(34, 34, 34)
                                .addComponent(SortCbBox, 0, 172, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, savegamesFrameLayout.createSequentialGroup()
                        .addContainerGap(224, Short.MAX_VALUE)
                        .addComponent(LoadBtn)))
                .addContainerGap())
        );
        savegamesFrameLayout.setVerticalGroup(
            savegamesFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(savegamesFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(savegamesFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SortLbl)
                    .addComponent(SortCbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(SaveGamesScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LoadBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        repositoryFrame.setTitle(resourceMap.getString("repositoryFrame.title")); // NOI18N
        repositoryFrame.setMinimumSize(new java.awt.Dimension(300, 240));
        repositoryFrame.setName("repositoryFrame"); // NOI18N
        repositoryFrame.setResizable(false);

        repositoryScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        repositoryScrollPane.setName("repositoryScrollPane"); // NOI18N

        repositoryList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "table001", "table002", "table003", "table004", "table005", "table006", "table007", "table008", "table009", "table010", "table011", "table012", "table013", "table014", "table015", "table016" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        repositoryList.setName("repositoryList"); // NOI18N
        repositoryScrollPane.setViewportView(repositoryList);

        oktableButton.setAction(actionMap.get("oktableButtonAction")); // NOI18N
        oktableButton.setText(resourceMap.getString("oktableButton.text")); // NOI18N
        oktableButton.setToolTipText(resourceMap.getString("oktableButton.toolTipText")); // NOI18N
        oktableButton.setName("oktableButton"); // NOI18N

        SortLbl1.setText(resourceMap.getString("SortLbl1.text")); // NOI18N
        SortLbl1.setName("SortLbl1"); // NOI18N

        SortCbBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nombre del tablero", "Topología", "Dificultad" }));
        SortCbBox1.setName("SortCbBox1"); // NOI18N

        javax.swing.GroupLayout repositoryFrameLayout = new javax.swing.GroupLayout(repositoryFrame.getContentPane());
        repositoryFrame.getContentPane().setLayout(repositoryFrameLayout);
        repositoryFrameLayout.setHorizontalGroup(
            repositoryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repositoryFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(repositoryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(repositoryScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addGroup(repositoryFrameLayout.createSequentialGroup()
                        .addComponent(SortLbl1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(SortCbBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(oktableButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        repositoryFrameLayout.setVerticalGroup(
            repositoryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(repositoryFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(repositoryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SortCbBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SortLbl1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(repositoryScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(oktableButton)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        changepassDialog.setTitle(resourceMap.getString("changepassDialog.title")); // NOI18N
        changepassDialog.setMinimumSize(new java.awt.Dimension(290, 180));
        changepassDialog.setModal(true);
        changepassDialog.setName("changepassDialog"); // NOI18N
        changepassDialog.setResizable(false);

        passwordField1.setName("passwordField1"); // NOI18N

        acceptButton1.setAction(actionMap.get("okChangeButton")); // NOI18N
        acceptButton1.setText(resourceMap.getString("acceptButton1.text")); // NOI18N
        acceptButton1.setToolTipText(resourceMap.getString("acceptButton1.toolTipText")); // NOI18N
        acceptButton1.setName("acceptButton1"); // NOI18N

        cancelButton1.setAction(actionMap.get("cancelChangeButton")); // NOI18N
        cancelButton1.setText(resourceMap.getString("cancelButton1.text")); // NOI18N
        cancelButton1.setToolTipText(resourceMap.getString("cancelButton1.toolTipText")); // NOI18N
        cancelButton1.setName("cancelButton1"); // NOI18N

        passwordLabel3.setText(resourceMap.getString("passwordLabel3.text")); // NOI18N
        passwordLabel3.setName("passwordLabel3"); // NOI18N

        passwordLabel1.setText(resourceMap.getString("passwordLabel1.text")); // NOI18N
        passwordLabel1.setName("passwordLabel1"); // NOI18N

        passwordField2.setName("passwordField2"); // NOI18N

        passwordField3.setName("passwordField3"); // NOI18N

        passwordLabel2.setText(resourceMap.getString("passwordLabel2.text")); // NOI18N
        passwordLabel2.setName("passwordLabel2"); // NOI18N

        javax.swing.GroupLayout changepassDialogLayout = new javax.swing.GroupLayout(changepassDialog.getContentPane());
        changepassDialog.getContentPane().setLayout(changepassDialogLayout);
        changepassDialogLayout.setHorizontalGroup(
            changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changepassDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(passwordLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                    .addComponent(passwordLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
            .addGroup(changepassDialogLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(acceptButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(cancelButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
        changepassDialogLayout.setVerticalGroup(
            changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changepassDialogLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(changepassDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(acceptButton1)
                    .addComponent(cancelButton1))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        newaccountDialog.setTitle(resourceMap.getString("newaccountDialog.title")); // NOI18N
        newaccountDialog.setMinimumSize(new java.awt.Dimension(290, 180));
        newaccountDialog.setModal(true);
        newaccountDialog.setName("newaccountDialog"); // NOI18N
        newaccountDialog.setResizable(false);

        passwordField4.setName("passwordField4"); // NOI18N

        acceptButton2.setAction(actionMap.get("okNewAccount")); // NOI18N
        acceptButton2.setText(resourceMap.getString("acceptButton2.text")); // NOI18N
        acceptButton2.setToolTipText(resourceMap.getString("acceptButton2.toolTipText")); // NOI18N
        acceptButton2.setName("acceptButton2"); // NOI18N

        cancelButton2.setAction(actionMap.get("hideNewAccountDialog")); // NOI18N
        cancelButton2.setText(resourceMap.getString("cancelButton2.text")); // NOI18N
        cancelButton2.setToolTipText(resourceMap.getString("cancelButton2.toolTipText")); // NOI18N
        cancelButton2.setName("cancelButton2"); // NOI18N

        passwordLabel4.setText(resourceMap.getString("passwordLabel4.text")); // NOI18N
        passwordLabel4.setName("passwordLabel4"); // NOI18N

        passwordLabel5.setText(resourceMap.getString("passwordLabel5.text")); // NOI18N
        passwordLabel5.setName("passwordLabel5"); // NOI18N

        passwordField6.setName("passwordField6"); // NOI18N

        passwordLabel6.setText(resourceMap.getString("passwordLabel6.text")); // NOI18N
        passwordLabel6.setName("passwordLabel6"); // NOI18N

        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        javax.swing.GroupLayout newaccountDialogLayout = new javax.swing.GroupLayout(newaccountDialog.getContentPane());
        newaccountDialog.getContentPane().setLayout(newaccountDialogLayout);
        newaccountDialogLayout.setHorizontalGroup(
            newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newaccountDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newaccountDialogLayout.createSequentialGroup()
                        .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passwordLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(passwordLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(acceptButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(passwordLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(passwordField4, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(passwordField6, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addGap(41, 41, 41))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newaccountDialogLayout.createSequentialGroup()
                        .addComponent(cancelButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
        );
        newaccountDialogLayout.setVerticalGroup(
            newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newaccountDialogLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(newaccountDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton2)
                    .addComponent(acceptButton2))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        statisticsFrame.setTitle(resourceMap.getString("statisticsFrame.title")); // NOI18N
        statisticsFrame.setMinimumSize(new java.awt.Dimension(305, 480));
        statisticsFrame.setName("statisticsFrame"); // NOI18N
        statisticsFrame.setResizable(false);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Tableros totales", "0"},
                {"Tableros generados", "0"},
                {"Tableros propuestos", "0"},
                {"Usuarios totales", "0"},
                {"Partidas totales", "0"},
                {"Hidatos resueltos (Principiante)", "0"},
                {"Hidatos resueltos (Intermedio)", "0"},
                {"Hidatos resueltos (Experto)", "0"}
            },
            new String [] {
                "Estadística", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setEnabled(false);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Nivel de experiencia", "Principiante"},
                {"Tiempo total de juego", "0"},
                {"Tiempo medio de resolución", "0"},
                {"Hidatos resueltos", "0"},
                {"Porcentaje de Hidatos resueltos", "0"},
                {"Topología preferida", "-"},
                {"Posición en el ranking", "-"},
                {"Número de tableros propuestos", "0"},
                {"Puntuación total", "0"},
                {"Puntuación media", "0"}
            },
            new String [] {
                "Estadística", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setName("jTable2"); // NOI18N
        jTable2.getTableHeader().setResizingAllowed(false);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable2.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable2.columnModel.title1")); // NOI18N

        jButton1.setAction(actionMap.get("hideStatisticsDialog")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        javax.swing.GroupLayout statisticsFrameLayout = new javax.swing.GroupLayout(statisticsFrame.getContentPane());
        statisticsFrame.getContentPane().setLayout(statisticsFrameLayout);
        statisticsFrameLayout.setHorizontalGroup(
            statisticsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statisticsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statisticsFrameLayout.createSequentialGroup()
                .addContainerGap(224, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        statisticsFrameLayout.setVerticalGroup(
            statisticsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        rankingFrame.setTitle(resourceMap.getString("rankingFrame.title")); // NOI18N
        rankingFrame.setMinimumSize(new java.awt.Dimension(305, 270));
        rankingFrame.setName("rankingFrame"); // NOI18N
        rankingFrame.setResizable(false);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Ramoncín", "7919"},
                {"Rogelio", "7823"},
                {"Eulogio", "7703"},
                {"Bonifacio", "7607"},
                {"Ermenegildo", "7541"},
                {"Ildefonso", "7459"},
                {"Casimiro", "7331"},
                {"Sandalio", "7219"},
                {"Gumersindo", "7121"},
                {"Leoncio", "7001"}
            },
            new String [] {
                "Usuario", "Puntuación"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setName("jTable3"); // NOI18N
        jTable3.getTableHeader().setResizingAllowed(false);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);
        jTable3.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable3.columnModel.title0")); // NOI18N
        jTable3.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable3.columnModel.title1")); // NOI18N

        jButton2.setAction(actionMap.get("hideRankingDialog")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        javax.swing.GroupLayout rankingFrameLayout = new javax.swing.GroupLayout(rankingFrame.getContentPane());
        rankingFrame.getContentPane().setLayout(rankingFrameLayout);
        rankingFrameLayout.setHorizontalGroup(
            rankingFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rankingFrameLayout.createSequentialGroup()
                .addContainerGap(217, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
            .addGroup(rankingFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        rankingFrameLayout.setVerticalGroup(
            rankingFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rankingFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void initTable() {
        tablero = new TableroGUI(8, 10);
        tablero.setPreficada(0, 0, true);
        tablero.setValor(0, 0, 1);
        tablero.setPreficada(0, 1, true);
        tablero.setValor(0, 1, 80);
        tablero.setPreficada(1, 8, true);
        tablero.setValor(1, 8, 24);
        tablero.setPreficada(2, 3, true);
        tablero.setValor(2, 3, 18);
        tablero.setPreficada(3, 6, true);
        tablero.setValor(3, 6, 38);
        tablero.setPreficada(4, 2, true);
        tablero.setValor(4, 2, 54);
        tablero.setPreficada(4, 9, true);
        tablero.setValor(4, 9, 47);
        tablero.setPreficada(6, 7, true);
        tablero.setValor(6, 7, 66);
        tablero.setPreficada(7, 2, true);
        tablero.setValor(7, 2, 72);
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(tablero)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(tablero)
                .addContainerGap(39, Short.MAX_VALUE))
        );
    }

    private void HabilitaPanel (boolean b) {
        TopologyCbBox.setEnabled(b);
        WidthEdit.setEnabled(b);
        HeightEdit.setEnabled(b);
        NumPredefEdit.setEnabled(b);
    }
    private void EasyRdbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EasyRdbtnMouseClicked
        proposButton.setEnabled(false);
        WidthEdit.setText("6");
        HeightEdit.setText("6");
        NumPredefEdit.setText("10");
        HabilitaPanel(false);
    }//GEN-LAST:event_EasyRdbtnMouseClicked

    private void MediumRdbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MediumRdbtnMouseClicked
        proposButton.setEnabled(false);
        WidthEdit.setText("9");
        HeightEdit.setText("9");
        NumPredefEdit.setText("20");
        HabilitaPanel(false);
    }//GEN-LAST:event_MediumRdbtnMouseClicked

    private void HardlRdbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HardlRdbtnMouseClicked
        proposButton.setEnabled(false);
        WidthEdit.setText("12");
        HeightEdit.setText("12");
        NumPredefEdit.setText("45");
        HabilitaPanel(false);
    }//GEN-LAST:event_HardlRdbtnMouseClicked

    private void PersonalizeRdbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PersonalizeRdbtnMouseClicked
        proposButton.setEnabled(true);
        HabilitaPanel(true);
    }//GEN-LAST:event_PersonalizeRdbtnMouseClicked

    private void NewAccountHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NewAccountHandler
        showNewAccountDialog();
    }//GEN-LAST:event_NewAccountHandler

    private void mostrarTiempo(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_mostrarTiempo
        jPanel2.setVisible(true);
        jButton3.setEnabled(true);
    }//GEN-LAST:event_mostrarTiempo

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton EasyRdbtn;
    private javax.swing.JRadioButton HardlRdbtn;
    private javax.swing.JTextField HeightEdit;
    private javax.swing.JLabel HeightLbl;
    private javax.swing.JButton LoadBtn;
    private javax.swing.JRadioButton MediumRdbtn;
    private javax.swing.JTextField NumPredefEdit;
    private javax.swing.JLabel NumPredefLbl;
    private javax.swing.JPanel PanelPersonalitzat;
    private javax.swing.JRadioButton PersonalizeRdbtn;
    private javax.swing.JList SaveGamesList;
    private javax.swing.JScrollPane SaveGamesScrollPanel;
    private javax.swing.JComboBox SortCbBox;
    private javax.swing.JComboBox SortCbBox1;
    private javax.swing.JLabel SortLbl;
    private javax.swing.JLabel SortLbl1;
    private javax.swing.JPasswordField StartPasswordEdit;
    private javax.swing.JLabel StartPasswordLbl;
    private javax.swing.JButton StartSessionButton;
    private javax.swing.JTextField StartUserEdit;
    private javax.swing.JLabel StartUserLbl;
    private javax.swing.JComboBox TopologyCbBox;
    private javax.swing.JLabel TopologyLbl;
    private javax.swing.JTextField WidthEdit;
    private javax.swing.JLabel WidthLbl;
    private javax.swing.JButton acceptButton1;
    private javax.swing.JButton acceptButton2;
    private javax.swing.JMenu accountMenu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton1;
    private javax.swing.JButton cancelButton2;
    private javax.swing.JMenuItem changeMenuItem;
    private javax.swing.JDialog changepassDialog;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem endMenuItem;
    private javax.swing.JButton generateButton;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuItem logoutMenuItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JDialog newaccountDialog;
    private javax.swing.JFrame newgameFrame;
    private javax.swing.JMenuItem newgameMenuItem;
    private javax.swing.JButton oktableButton;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JPasswordField passwordField2;
    private javax.swing.JPasswordField passwordField3;
    private javax.swing.JPasswordField passwordField4;
    private javax.swing.JPasswordField passwordField6;
    private javax.swing.JLabel passwordLabel1;
    private javax.swing.JLabel passwordLabel2;
    private javax.swing.JLabel passwordLabel3;
    private javax.swing.JLabel passwordLabel4;
    private javax.swing.JLabel passwordLabel5;
    private javax.swing.JLabel passwordLabel6;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton proposButton;
    private javax.swing.JMenuItem rankMenuItem;
    private javax.swing.JFrame rankingFrame;
    private javax.swing.JButton repositoryButton;
    private javax.swing.JFrame repositoryFrame;
    private javax.swing.JList repositoryList;
    private javax.swing.JScrollPane repositoryScrollPane;
    private javax.swing.JMenuItem resetMenuItem;
    private javax.swing.JMenuItem resetstatsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JFrame savegamesFrame;
    private javax.swing.JMenuItem solveMenuItem;
    private javax.swing.JPanel startLogginPanel;
    private javax.swing.JFrame statisticsFrame;
    private javax.swing.JMenu statsMenu;
    private javax.swing.JMenuItem statsMenuItem;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private JDialog aboutBox;
}
