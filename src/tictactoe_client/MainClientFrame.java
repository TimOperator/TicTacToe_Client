/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_client;

import ServerConnection.MainServerConnector;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.ResourceBundle;

/**
 *
 * @author Tim
 */
public final class MainClientFrame extends javax.swing.JFrame {

    private FieldStatusEnum player;
    private String winner;
    private Socket _socket;
    private String serverIP;
    private int serverPort;
    private String username;
    private String enemy;
    private final Connection connection;
    private static final ResourceBundle STRING_BUNDLE = ResourceBundle.getBundle("resources/strings");

    /**
     * Creates new form MainJFrame
     */
    public MainClientFrame() {
        initComponents();
        setIcon();
        setEnabledField(false);
        setConnectionEditable(true);
        connectButton.setEnabled(false);
        setGameEnabled(false);
        connection = new Connection(this);
        setLocationRelativeTo(null);
    }
    public void useAdvancedMode(Boolean value) {
        if (value) {
            //Hide this for release
            addressTextField.setVisible(true);
            addressLabel.setVisible(true);
            portLabel.setVisible(true);
            portTextField.setVisible(true);
        } else {
            addressTextField.setVisible(false);
            addressLabel.setVisible(false);
            portLabel.setVisible(false);
            portTextField.setVisible(false);
        }
    }

    private boolean connect() {
        try {
            _socket = new Socket(serverIP, serverPort);
            send(username);
            return true;
        } catch (HeadlessException | IOException ex) {
            return false;
        }
    }

    protected void setWinner(String w) {
        this.winner = w;
        winning();
    }

    protected boolean send(String content) {
        try {
            OutputStream outputStream = _socket.getOutputStream();
            DataOutputStream dataout = new DataOutputStream(outputStream);
            dataout.writeUTF(content);
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    protected String receive() {
        try {
            return new DataInputStream(_socket.getInputStream()).readUTF();
        } catch (IOException ex) {
            return null;
        }
    }
    
    protected void disableButton(int x, int y, FieldStatusEnum stat) {
        switch (x) {
            case 0:
                //
                switch (y) {
                    case 0:
                        jButton1.setEnabled(false);
                        jButton1.setText(stat.toString());
                        break;
                    case 1:
                        jButton2.setEnabled(false);
                        jButton2.setText(stat.toString());
                        break;
                    case 2:
                        jButton3.setEnabled(false);
                        jButton3.setText(stat.toString());
                        break;
                }
                break;
            case 1:
                //
                switch (y) {
                    case 0:
                        jButton4.setEnabled(false);
                        jButton4.setText(stat.toString());
                        break;
                    case 1:
                        jButton5.setEnabled(false);
                        jButton5.setText(stat.toString());
                        break;
                    case 2:
                        jButton6.setEnabled(false);
                        jButton6.setText(stat.toString());
                        break;
                }
                break;
            case 2:
                //
                switch (y) {
                    case 0:
                        jButton7.setEnabled(false);
                        jButton7.setText(stat.toString());
                        break;
                    case 1:
                        jButton8.setEnabled(false);
                        jButton8.setText(stat.toString());
                        break;
                    case 2:
                        jButton9.setEnabled(false);
                        jButton9.setText(stat.toString());
                        break;
                }
                break;
            default:
                //
                break;
        }
    }

    private void setConnectionEditable(boolean boo) {
        addressTextField.setEditable(boo);
        portTextField.setEditable(boo);
        playerTextField.setEditable(boo);
        connectButton.setEnabled(boo);
    }

    private void setGameEnabled(boolean boo) {
        gamePanel.setEnabled(boo);
    }

    private void setEnableAll(boolean boo) {
        jButton1.setEnabled(boo);
        jButton2.setEnabled(boo);
        jButton3.setEnabled(boo);
        jButton4.setEnabled(boo);
        jButton5.setEnabled(boo);
        jButton6.setEnabled(boo);
        jButton7.setEnabled(boo);
        jButton8.setEnabled(boo);
        jButton9.setEnabled(boo);
    }

    private void clearAll() {
        clearButton(jButton1);
        clearButton(jButton2);
        clearButton(jButton3);
        clearButton(jButton4);
        clearButton(jButton5);
        clearButton(jButton6);
        clearButton(jButton7);
        clearButton(jButton8);
        clearButton(jButton9);
    }

    private void clearButton(JButton button) {
        button.setText("");
    }

    protected void setEnabledField(boolean bo) {
        enableButton(jButton1, bo);
        enableButton(jButton2, bo);
        enableButton(jButton3, bo);
        enableButton(jButton4, bo);
        enableButton(jButton5, bo);
        enableButton(jButton6, bo);
        enableButton(jButton7, bo);
        enableButton(jButton8, bo);
        enableButton(jButton9, bo);
    }

    private void enableButton(JButton button, boolean boo) {
        if (button.getText().equals("")) {
            button.setEnabled(boo);
        }
    }

    protected FieldStatusEnum castEnum(String boo) {
        if (boo.equalsIgnoreCase("X")) {
            return FieldStatusEnum.X;
        } else if (boo.equalsIgnoreCase("O")) {
            return FieldStatusEnum.O;
        } else {
            return FieldStatusEnum.NONE;
        }
    }

    private void setActivePlayer(FieldStatusEnum status) {
        if (status == FieldStatusEnum.X) {
            xRadio.setSelected(true);
        } else if (status == FieldStatusEnum.O) {
            oRadio.setSelected(true);
        }
    }

    protected void winning() {
        if (winner.equalsIgnoreCase(username)) {
            JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_you_won"));
        } else if (winner.equalsIgnoreCase(enemy)) {
            JOptionPane.showMessageDialog(null, enemy + " " + STRING_BUNDLE.getString("Inform_opposition_won"));
        } else {
            JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_draw"));
        }
        setConnectionEditable(true);
    }

    private void playGame() {
        String status = "";
        while (!status.equalsIgnoreCase("STOP")) {
            if (status.equalsIgnoreCase("WAIT_FOR_TURN")) {
                jLabel.setText(STRING_BUNDLE.getString(status));
                setEnabledField(false);
            } else if (status.equalsIgnoreCase("MAKE_A_TURN")) {
                jLabel.setText(STRING_BUNDLE.getString(status));
                setEnabledField(true);
            } else if (status.equalsIgnoreCase("SET_POS")) {
                int x = new Integer(receive());
                int y = new Integer(receive());
                FieldStatusEnum temp = castEnum(receive());
                disableButton(x, y, temp);
                setEnabledField(false);
            }
            status = receive();
        }
        winner = receive();
    }

    public void setLabel(String text) {
        jLabel.setText(text);
    }

    protected void setPos(int x, int y, JButton button) {
        //button.setEnabled(false);
        //button.setText(player.toString());
        send("" + x);
        send("" + y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        connectionPanel = new javax.swing.JPanel();
        addressLabel = new javax.swing.JLabel();
        addressTextField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();
        playerTextField = new javax.swing.JTextField();
        playerLabel = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        updateCheckButton = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();
        oRadio = new javax.swing.JRadioButton();
        xRadio = new javax.swing.JRadioButton();
        fieldPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel = new javax.swing.JLabel();
        playerXOLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TicTacToe v2.1");
        setMinimumSize(new java.awt.Dimension(313, 527));
        setName("frame"); // NOI18N
        setResizable(false);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/strings"); // NOI18N
        connectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("View_connection"))); // NOI18N

        addressLabel.setText(bundle.getString("Field_server")); // NOI18N

        addressTextField.setText("easyuse.hopto.org");
        addressTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addressTextFieldKeyReleased(evt);
            }
        });

        portLabel.setText(bundle.getString("Field_Port")); // NOI18N

        portTextField.setText("50471");
        portTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                portTextFieldKeyReleased(evt);
            }
        });

        playerTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerTextFieldActionPerformed(evt);
            }
        });
        playerTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                playerTextFieldKeyReleased(evt);
            }
        });

        playerLabel.setText(bundle.getString("Field_name")); // NOI18N

        connectButton.setText(bundle.getString("Field_connect")); // NOI18N
        connectButton.setEnabled(false);
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        updateCheckButton.setText(bundle.getString("Field_update")); // NOI18N
        updateCheckButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCheckButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout connectionPanelLayout = new javax.swing.GroupLayout(connectionPanel);
        connectionPanel.setLayout(connectionPanelLayout);
        connectionPanelLayout.setHorizontalGroup(
            connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(connectionPanelLayout.createSequentialGroup()
                        .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addressLabel)
                            .addComponent(portLabel)
                            .addComponent(playerLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerTextField)
                            .addComponent(portTextField)
                            .addComponent(addressTextField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, connectionPanelLayout.createSequentialGroup()
                        .addComponent(updateCheckButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(connectButton)))
                .addContainerGap())
        );
        connectionPanelLayout.setVerticalGroup(
            connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressLabel)
                    .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portLabel)
                    .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(updateCheckButton))
                .addContainerGap())
        );

        gamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("View_field"))); // NOI18N
        gamePanel.setEnabled(false);

        buttonGroup1.add(oRadio);
        oRadio.setText("O");
        oRadio.setEnabled(false);
        oRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(xRadio);
        xRadio.setSelected(true);
        xRadio.setText("X");
        xRadio.setEnabled(false);
        xRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xRadioActionPerformed(evt);
            }
        });

        fieldPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fieldPanel.setMinimumSize(new java.awt.Dimension(50, 50));
        fieldPanel.setLayout(new java.awt.GridLayout(3, 3));

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton1);

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton2);

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton3);

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton4);

        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton5);

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton6);

        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton7);

        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton8);

        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        fieldPanel.add(jButton9);

        jLabel.setText("Nicht verbunden");

        playerXOLabel.setText(bundle.getString("Field_player_type")); // NOI18N

        jLabel1.setText(bundle.getString("Field_information")); // NOI18N
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addComponent(playerXOLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(xRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oRadio))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gamePanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel))
                    .addComponent(fieldPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xRadio)
                    .addComponent(oRadio)
                    .addComponent(playerXOLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gamePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(connectionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setPos(0, 0, this.jButton1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void xRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xRadioActionPerformed

    }//GEN-LAST:event_xRadioActionPerformed

    private void oRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oRadioActionPerformed

    }//GEN-LAST:event_oRadioActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setPos(0, 1, this.jButton2);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        setPos(0, 2, this.jButton3);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        setPos(1, 0, this.jButton4);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        setPos(1, 1, this.jButton5);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        setPos(1, 2, this.jButton6);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        setPos(2, 0, this.jButton7);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        setPos(2, 1, this.jButton8);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        setPos(2, 2, this.jButton9);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void playerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerTextFieldActionPerformed

    }//GEN-LAST:event_playerTextFieldActionPerformed

    private void playerTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_playerTextFieldKeyReleased
        if (this.playerTextField.getText().trim().length() > 0 && this.addressTextField.getText().trim().length() > 0 && this.portTextField.getText().trim().length() > 0) {
            this.connectButton.setEnabled(true);
        } else {
            this.connectButton.setEnabled(false);
        }
    }//GEN-LAST:event_playerTextFieldKeyReleased

    private void portTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_portTextFieldKeyReleased
        if (this.playerTextField.getText().trim().length() > 0 && this.addressTextField.getText().trim().length() > 0 && this.portTextField.getText().trim().length() > 0) {
            this.connectButton.setEnabled(true);
        } else {
            this.connectButton.setEnabled(false);
        }
    }//GEN-LAST:event_portTextFieldKeyReleased

    private void addressTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addressTextFieldKeyReleased
        if (this.playerTextField.getText().trim().length() > 0 && this.addressTextField.getText().trim().length() > 0 && this.portTextField.getText().trim().length() > 0) {
            this.connectButton.setEnabled(true);
        } else {
            this.connectButton.setEnabled(false);
        }
    }//GEN-LAST:event_addressTextFieldKeyReleased

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        clearAll();
        setConnectionEditable(false);
        serverIP = addressTextField.getText().trim();
        serverPort = new Integer(portTextField.getText().trim());
        username = playerTextField.getText().trim();
        if (connect()) {
            //send(username);
            player = castEnum(receive());
            setActivePlayer(player);
            //Waiting for enemy to connect
            jLabel.setText(receive());
            JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_connected_to") + " " + serverIP);
            enemy = receive();
            //Enemy connected
            JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_game_against") + " " + enemy);
            connection.activateGame();
            if (!connection.isAlive()) {
                connection.start();
            }
        } else {
            JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_unable_to_connect") + " " + serverIP);
            setConnectionEditable(true);
        }

    }//GEN-LAST:event_connectButtonActionPerformed

    private void updateCheckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCheckButtonActionPerformed
        updateCheckButton.setEnabled(false);
        String reply = MainServerConnector.checkForUpdates(this.getTitle());
        JOptionPane.showMessageDialog(null, reply);
        updateCheckButton.setEnabled(true);
    }//GEN-LAST:event_updateCheckButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainClientFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTextField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton connectButton;
    private javax.swing.JPanel connectionPanel;
    private javax.swing.JPanel fieldPanel;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton oRadio;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JTextField playerTextField;
    private javax.swing.JLabel playerXOLabel;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTextField;
    private javax.swing.JButton updateCheckButton;
    private javax.swing.JRadioButton xRadio;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img.png")));
    }
}
