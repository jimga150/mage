package mage.server.console;

import mage.interfaces.MageClient;
import mage.interfaces.callback.ClientCallback;
import mage.remote.Connection;
import mage.remote.Session;
import mage.remote.SessionImpl;
import mage.util.ThreadUtils;
import mage.util.XmageThreadFactory;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ConsoleFrame extends javax.swing.JFrame implements MageClient {

    private static final Logger logger = Logger.getLogger(ConsoleFrame.class);

    private static Session session;
    private ConnectDialog connectDialog;
    private static final Preferences prefs = Preferences.userNodeForPackage(ConsoleFrame.class);
    private static final MageVersion version = new MageVersion(ConsoleFrame.class);

    private static final ScheduledExecutorService PING_SENDER_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            new XmageThreadFactory(ThreadUtils.THREAD_PREFIX_CLIENT_PING_SENDER)
    );

    /**
     * @return the session
     */
    public static Session getSession() {
        return session;
    }

    public static Preferences getPreferences() {
        return prefs;
    }

    @Override
    public MageVersion getVersion() {
        return version;
    }

    /**
     * Creates new form ConsoleFrame
     */
    public ConsoleFrame() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });

        initComponents();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            session = new SessionImpl(this);
            connectDialog = new ConnectDialog();

            // try auto connect
            if (!autoConnect()) {
                SwingUtilities.invokeLater(() -> {
                    connectDialog.showDialog(this);
                });
            }
        } catch (Exception ex) {
            logger.fatal("", ex);
        }

        PING_SENDER_EXECUTOR.scheduleAtFixedRate(() -> session.ping(), 20, 20, TimeUnit.SECONDS);
    }

    public boolean connect(Connection connection) {
        if (session.connectStart(connection)) {
            this.consolePanel1.start();
            return true;
        }
        return false;
    }

    public boolean autoConnect() {
        boolean needAutoConnect = Boolean.parseBoolean(ConsoleFrame.getPreferences().get("autoConnect", "false"));
        boolean status = false;
        if (needAutoConnect) {
            String server = ConsoleFrame.getPreferences().get("serverAddress", "localhost");
            logger.info("Auto-connecting to " + server);
            Connection newConnection = new Connection();
            newConnection.setHost(server);
            newConnection.setPort(ConsoleFrame.getPreferences().getInt("serverPort", 17171));
            newConnection.setUsername(SessionImpl.ADMIN_NAME);
            newConnection.setAdminPassword(ConsoleFrame.getPreferences().get("password", ""));
            newConnection.setProxyType(Connection.ProxyType.valueOf(ConsoleFrame.getPreferences().get("proxyType", "NONE").toUpperCase(Locale.ENGLISH)));
            if (!newConnection.getProxyType().equals(Connection.ProxyType.NONE)) {
                newConnection.setProxyHost(ConsoleFrame.getPreferences().get("proxyAddress", ""));
                newConnection.setProxyPort(ConsoleFrame.getPreferences().getInt("proxyPort", 0));
                newConnection.setProxyUsername(ConsoleFrame.getPreferences().get("proxyUsername", ""));
                newConnection.setProxyPassword(ConsoleFrame.getPreferences().get("proxyPassword", ""));
            }
            status = connect(newConnection);
        }
        return status;
    }

    public void setStatusText(String status) {
        this.lblStatus.setText(status);
    }

    public void enableButtons() {
        btnConnect.setEnabled(true);
        btnConnect.setText("Disconnect & Close");
        btnSendMessage.setEnabled(true);
    }

    public void disableButtons() {
        btnConnect.setEnabled(true);
        btnConnect.setText("Connect");
        btnSendMessage.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnConnect = new javax.swing.JButton();
        btnSendMessage = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        consolePanel1 = new mage.server.console.ConsolePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnConnect.setText("Connect");
        btnConnect.setFocusable(false);
        btnConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConnect.addActionListener(evt -> btnConnectActionPerformed(evt));
        jToolBar1.add(btnConnect);

        btnSendMessage.setActionCommand("SendMessage");
        btnSendMessage.setEnabled(false);
        btnSendMessage.setFocusable(false);
        btnSendMessage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSendMessage.setText("Send Message");
        btnSendMessage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSendMessage.addActionListener(evt -> btnSendMessageActionPerformed(evt));
        jToolBar1.add(btnSendMessage);

        lblStatus.setText("Not Connected");
        jToolBar1.add(Box.createHorizontalGlue());
        jToolBar1.add(lblStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
                        .addComponent(consolePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(consolePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed

        if (session.isConnected()) {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to disconnect?", "Confirm disconnect", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.consolePanel1.stop();
                session.connectStop(false, false);
            }
        } else {
            connectDialog.showDialog(this);
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btnSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendMessageActionPerformed
        String message = JOptionPane.showInputDialog(null, "Type message to send", "Broadcast message", JOptionPane.INFORMATION_MESSAGE);
        if (message != null) {
            session.sendBroadcastMessage(message);
        }
    }//GEN-LAST:event_btnSendMessageActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        logger.info("Starting MAGE ADMIN version " + version);
        logger.info("Java version: " + System.getProperty("java.version"));
        logger.info("Logging level: " + logger.getEffectiveLevel());

        java.awt.EventQueue.invokeLater(() -> {
            new ConsoleFrame().setVisible(true);
            logger.info("Started MAGE server console");
        });
    }

    private ConsoleFrame getFrame() {
        return this;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnSendMessage;
    private mage.server.console.ConsolePanel consolePanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables

    @Override
    public void connected(final String message) {
        if (SwingUtilities.isEventDispatchThread()) {
            setStatusText(message);
            enableButtons();
        } else {
            SwingUtilities.invokeLater(() -> {
                setStatusText(message);
                enableButtons();
            });
        }
    }

    @Override
    public void disconnected(boolean askToReconnect, boolean keepMySessionActive) {
        if (SwingUtilities.isEventDispatchThread()) {
            consolePanel1.stop();
            setStatusText("Not connected");
            disableButtons();
        } else {
            SwingUtilities.invokeLater(() -> {
                consolePanel1.stop();
                setStatusText("Not connected");
                disableButtons();
            });
        }
    }

    @Override
    public void showMessage(final String message) {
        if (SwingUtilities.isEventDispatchThread()) {
            JOptionPane.showMessageDialog(this, message);
        } else {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(getFrame(), message));
        }
    }

    @Override
    public void showError(final String message) {
        if (SwingUtilities.isEventDispatchThread()) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(getFrame(), message, "Error", JOptionPane.ERROR_MESSAGE));
        }
    }

    @Override
    public void onNewConnection() {
        // ignore
    }

    @Override
    public void onCallback(ClientCallback callback) {
        // ignore
    }

    public void exitApp() {
        if (session.isConnected()) {
            if (JOptionPane.showConfirmDialog(this, "You are currently connected.  Are you sure you want to disconnect?", "Confirm disconnect", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
            session.connectStop(false, false);
        } else {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Confirm exit", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
        }
        dispose();
        System.exit(0);
    }
}
