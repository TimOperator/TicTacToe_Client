/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hamp_it.tictactoe_client;

import de.hamp_it.EasyuseServerConnector.MainServerConnector;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
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
    private static final ResourceBundle STRING_BUNDLE = ResourceBundle.getBundle("strings");
    private FieldStatusEnum fieldArray[][] = new FieldStatusEnum[3][3];

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
        jLabel.setText(STRING_BUNDLE.getString("Inform_game_over"));
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

    /**
     * Clears the field (all buttons are set to default)
     */
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

    /**
     * Set status of field
     *
     * @param bo Enable field
     */
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
        startGameButton.setEnabled(true);
        jTabbedPane.setEnabledAt(1, true);
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

    private void swapPlayer() {
        if (player == FieldStatusEnum.O) {
            player = FieldStatusEnum.X;
        } else if (player == FieldStatusEnum.X) {
            player = FieldStatusEnum.O;
        }
        if (player == FieldStatusEnum.X) {
            xOfflineRadio.setSelected(true);
            oOfflineRadio.setSelected(false);
        } else {
            oOfflineRadio.setSelected(true);
            xOfflineRadio.setSelected(false);
        }
    }

    /**
     * Check if one of the player has won the game
     *
     * @return Player, who won or NONE if no one won yet
     */
    public FieldStatusEnum isWon() {
        int x = 0;
        int o = 0;

        //Zeilen -
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (fieldArray[i][j] == FieldStatusEnum.O) {
                    o++;
                } else if (fieldArray[i][j] == FieldStatusEnum.X) {
                    x++;
                }
            }
            if (o == 3) {
                return FieldStatusEnum.O;
            } else if (x == 3) {
                return FieldStatusEnum.X;
            } else {
                x = 0;
                o = 0;
            }
        }

        //Spalten |
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (fieldArray[j][i] == FieldStatusEnum.O) {
                    o++;
                } else if (fieldArray[j][i] == FieldStatusEnum.X) {
                    x++;
                }
            }
            if (o == 3) {
                return FieldStatusEnum.O;
            } else if (x == 3) {
                return FieldStatusEnum.X;
            } else {
                x = 0;
                o = 0;
            }
        }

        //Diagonal \
        for (int i = 0; i < 3; ++i) {
            if (fieldArray[i][i] == FieldStatusEnum.O) {
                o++;
            } else if (fieldArray[i][i] == FieldStatusEnum.X) {
                x++;
            }
        }
        if (o == 3) {
            return FieldStatusEnum.O;
        } else if (x == 3) {
            return FieldStatusEnum.X;
        } else {
            x = 0;
            o = 0;
        }

        //Diagonal /
        for (int i = 0; i < 3; ++i) {
            if (fieldArray[2 - i][i] == FieldStatusEnum.O) {
                o++;
            } else if (fieldArray[2 - i][i] == FieldStatusEnum.X) {
                x++;
            }
        }
        if (o == 3) {
            return FieldStatusEnum.O;
        } else if (x == 3) {
            return FieldStatusEnum.X;
        } else {
            x = 0;
            o = 0;
        }
        return FieldStatusEnum.NONE;
    }

    /**
     * Check if the game has ended with a draw
     *
     * @return true, if the game is draw, else false
     */
    public boolean isDraw() {
        int x = 0;
        int o = 0;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (fieldArray[i][j] == FieldStatusEnum.O) {
                    o++;
                } else if (fieldArray[i][j] == FieldStatusEnum.X) {
                    x++;
                }
            }
        }
        if (x + o >= 9) {
            return true;
        } else {
            return false;
        }
    }

    public void setLabel(String text) {
        jLabel.setText(text);
    }

    protected void setPos(int x, int y, JButton button) {
        //button.setEnabled(false);
        //button.setText(player.toString());
        if (connection.isActiveGame()) {
            send("" + x);
            send("" + y);
        } else {
            disableButton(x, y, player);
            fieldArray[x][y] = player;

            // Playing against computer
            if (localAIRadioButton.isSelected()) {
                FieldStatusEnum winner = isWon();
                if (winner == FieldStatusEnum.NONE) {
                    if (isDraw()) {
                        // Draw
                        endGame(winner);
                    } else {
                        // Computer set position and check again
                        setComputerPosition();
                        winner = isWon();
                        if (winner != FieldStatusEnum.NONE || isDraw()) {
                            endGame(winner);
                        }
                        return;
                    }
                } else {
                    endGame(winner);
                }
            } 
            
            // Local multiplayer
            if (localMPRadioButton.isSelected()) {
                FieldStatusEnum winner = isWon();
                if (winner == FieldStatusEnum.NONE) {
                    if (isDraw()) {
                        // Draw
                        endGame(winner);
                    } else {
                        swapPlayer();
                        jLabel.setText(STRING_BUNDLE.getString("Inform_current_turn") + player.toString());
                        return;
                    }
                } else {
                    // Some player won
                    endGame(winner);
                }

            }
        }
    }

    private void setComputerPosition() {
        FieldStatusEnum player2;
        if (player == FieldStatusEnum.O) {
            player2 = FieldStatusEnum.X;
        } else {
            player2 = FieldStatusEnum.O;
        }

        Random rd = new Random();
        int xRand;
        int yRand;
        do {
            xRand = rd.nextInt(3);
            yRand = rd.nextInt(3);
        } while (fieldArray[xRand][yRand] != FieldStatusEnum.NONE);
        disableButton(xRand, yRand, player2);
        fieldArray[xRand][yRand] = player2;
    }

    private void endGame(FieldStatusEnum winner) {
        if (localAIRadioButton.isSelected()) {
            if (winner == player) {
                // Play won against computer
                JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_you_won"));
            } else if (winner == FieldStatusEnum.NONE) {
                // draw
                JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_draw"));
            } else {
                // computer won
                JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_computer_won"));
            }
        } else {
            if (winner == FieldStatusEnum.NONE) {
                // draw
                JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Infom_draw"));
            } else {
                // Player winner won
                JOptionPane.showMessageDialog(null, winner.toString() + " " + STRING_BUNDLE.getString("Inform_opposition_won"));
            }
        }
        startGameButton.setEnabled(true);
        localAIRadioButton.setEnabled(true);
        localMPRadioButton.setEnabled(true);
        xOfflineRadio.setEnabled(true);
        oOfflineRadio.setEnabled(true);
        jTabbedPane.setEnabledAt(0, true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playerOnlineTypeButtonGroup = new javax.swing.ButtonGroup();
        aiButtonGroup = new javax.swing.ButtonGroup();
        playerOfflineButtonGroup = new javax.swing.ButtonGroup();
        gamePanel = new javax.swing.JPanel();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        addressTextField = new javax.swing.JTextField();
        addressLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();
        playerTextField = new javax.swing.JTextField();
        playerLabel = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        playerXOLabel = new javax.swing.JLabel();
        xRadio = new javax.swing.JRadioButton();
        oRadio = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        updateCheckButton = new javax.swing.JButton();
        localMPRadioButton = new javax.swing.JRadioButton();
        localAIRadioButton = new javax.swing.JRadioButton();
        offlinePlayerLabel = new javax.swing.JLabel();
        xOfflineRadio = new javax.swing.JRadioButton();
        oOfflineRadio = new javax.swing.JRadioButton();
        startGameButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TicTacToe v3.1");
        setMinimumSize(new java.awt.Dimension(313, 527));
        setName("frame"); // NOI18N
        setResizable(false);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("strings"); // NOI18N
        gamePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("View_field"))); // NOI18N
        gamePanel.setEnabled(false);

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

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setText(bundle.getString("Field_information")); // NOI18N
        jLabel1.setToolTipText("");

        jLabel.setText("Nicht verbunden");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("View_connection"))); // NOI18N

        addressTextField.setText("easyuse.hopto.org");
        addressTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addressTextFieldKeyReleased(evt);
            }
        });

        addressLabel.setText(bundle.getString("Field_server")); // NOI18N

        portLabel.setText(bundle.getString("Field_Port")); // NOI18N

        portTextField.setText("50471");
        portTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                portTextFieldKeyReleased(evt);
            }
        });

        playerTextField.setMinimumSize(new java.awt.Dimension(150, 24));
        playerTextField.setPreferredSize(new java.awt.Dimension(150, 24));
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

        playerXOLabel.setText(bundle.getString("Field_player_type")); // NOI18N

        playerOnlineTypeButtonGroup.add(xRadio);
        xRadio.setSelected(true);
        xRadio.setText("X");
        xRadio.setEnabled(false);
        xRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xRadioActionPerformed(evt);
            }
        });

        playerOnlineTypeButtonGroup.add(oRadio);
        oRadio.setText("O");
        oRadio.setEnabled(false);
        oRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addressLabel)
                            .addComponent(portLabel)
                            .addComponent(playerXOLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(xRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(oRadio))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(portTextField)
                                .addComponent(addressTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(connectButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(playerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(playerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerXOLabel)
                    .addComponent(xRadio)
                    .addComponent(oRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(connectButton)
                .addContainerGap())
        );

        jTabbedPane.addTab(bundle.getString("View_online"), jPanel1); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("View_settings"))); // NOI18N

        updateCheckButton.setText(bundle.getString("Field_update")); // NOI18N
        updateCheckButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCheckButtonActionPerformed(evt);
            }
        });

        aiButtonGroup.add(localMPRadioButton);
        localMPRadioButton.setSelected(true);
        localMPRadioButton.setText(bundle.getString("Field_local_mp")); // NOI18N

        aiButtonGroup.add(localAIRadioButton);
        localAIRadioButton.setText(bundle.getString("Field_local_ai")); // NOI18N

        offlinePlayerLabel.setText(bundle.getString("Field_player_type")); // NOI18N

        playerOfflineButtonGroup.add(xOfflineRadio);
        xOfflineRadio.setText("X");

        playerOfflineButtonGroup.add(oOfflineRadio);
        oOfflineRadio.setText("O");

        startGameButton.setText(bundle.getString("Field_start_game")); // NOI18N
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(offlinePlayerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(xOfflineRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oOfflineRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(startGameButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updateCheckButton)
                            .addComponent(localAIRadioButton)
                            .addComponent(localMPRadioButton))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(updateCheckButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(localMPRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(localAIRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xOfflineRadio)
                    .addComponent(oOfflineRadio)
                    .addComponent(offlinePlayerLabel)
                    .addComponent(startGameButton))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(bundle.getString("View_offline"), jPanel2); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel))
                    .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel))
                .addGap(18, 18, 18)
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane.getAccessibleContext().setAccessibleName(bundle.getString("View_offline")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setPos(0, 0, this.jButton1);
    }//GEN-LAST:event_jButton1ActionPerformed

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

    private void startGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameButtonActionPerformed
        if (connection.isActiveGame()) {
            JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_game_running"));
            return;
        }

        localAIRadioButton.setEnabled(false);
        localMPRadioButton.setEnabled(false);
        xOfflineRadio.setEnabled(false);
        oOfflineRadio.setEnabled(false);
        startGameButton.setEnabled(false);
        jTabbedPane.setEnabledAt(0, false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fieldArray[i][j] = FieldStatusEnum.NONE;
            }
        }

        // No player type selected
        if (!xOfflineRadio.isSelected() && !oOfflineRadio.isSelected()) {
            // Play against ai
            if (localAIRadioButton.isSelected()) {
                JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Field_player_type") + " X");
            } else {
                JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_start_with") + " X");
            }
            xOfflineRadio.setSelected(true);
        }
        if (xOfflineRadio.isSelected()) {
            player = FieldStatusEnum.X;
        } else {
            player = FieldStatusEnum.O;
        }
        clearAll();
        setEnabledField(true);
        jLabel.setText(STRING_BUNDLE.getString("Inform_current_turn") + " " + player.toString());
    }//GEN-LAST:event_startGameButtonActionPerformed

    private void updateCheckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCheckButtonActionPerformed
        updateCheckButton.setEnabled(false);
        String reply = MainServerConnector.checkForUpdates(this.getTitle());
        JOptionPane.showMessageDialog(null, reply);
        updateCheckButton.setEnabled(true);
    }//GEN-LAST:event_updateCheckButtonActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        clearAll();
        setEnabledField(false);
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
                startGameButton.setEnabled(false);
                jTabbedPane.setEnabledAt(1, false);
            }
        } else {
            JOptionPane.showMessageDialog(null, STRING_BUNDLE.getString("Inform_unable_to_connect") + " " + serverIP);
            setConnectionEditable(true);
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void oRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oRadioActionPerformed

    }//GEN-LAST:event_oRadioActionPerformed

    private void xRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xRadioActionPerformed

    }//GEN-LAST:event_xRadioActionPerformed

    private void playerTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_playerTextFieldKeyReleased
        if (this.playerTextField.getText().trim().length() > 0 && this.addressTextField.getText().trim().length() > 0 && this.portTextField.getText().trim().length() > 0) {
            this.connectButton.setEnabled(true);
        } else {
            this.connectButton.setEnabled(false);
        }
    }//GEN-LAST:event_playerTextFieldKeyReleased

    private void playerTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerTextFieldActionPerformed

    }//GEN-LAST:event_playerTextFieldActionPerformed

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
    private javax.swing.ButtonGroup aiButtonGroup;
    private javax.swing.JButton connectButton;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JRadioButton localAIRadioButton;
    private javax.swing.JRadioButton localMPRadioButton;
    private javax.swing.JRadioButton oOfflineRadio;
    private javax.swing.JRadioButton oRadio;
    private javax.swing.JLabel offlinePlayerLabel;
    private javax.swing.JLabel playerLabel;
    private javax.swing.ButtonGroup playerOfflineButtonGroup;
    private javax.swing.ButtonGroup playerOnlineTypeButtonGroup;
    private javax.swing.JTextField playerTextField;
    private javax.swing.JLabel playerXOLabel;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTextField;
    private javax.swing.JButton startGameButton;
    private javax.swing.JButton updateCheckButton;
    private javax.swing.JRadioButton xOfflineRadio;
    private javax.swing.JRadioButton xRadio;
    // End of variables declaration//GEN-END:variables

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("img.png")));
    }
}
