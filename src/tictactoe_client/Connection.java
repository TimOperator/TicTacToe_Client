/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Tim
 */
public class Connection extends Thread {

    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private final MainClientFrame window;
    private boolean activeGame;

    public Connection(MainClientFrame mcf) {
        this.window = mcf;
        activeGame = false;
    }
    
    public void activateGame() {
        activeGame = true;
    }

    @Override
    public void run() {
        while (true) {
            while (!activeGame) {
                sleep();
            }
            String status = "";
            while (!status.equalsIgnoreCase("STOP")) {
                status = window.receive();
                if (status.equalsIgnoreCase("WAIT_FOR_TURN")) {
                    window.setLabel("Warte auf Spielzug");
                    window.setEnabledField(false);
                } else if (status.equalsIgnoreCase("MAKE_A_TURN")) {
                    window.setLabel("Mache einen Spielzug");
                    window.setEnabledField(true);
                } else if (status.equalsIgnoreCase("SET_POS")) {
                    int x = new Integer(window.receive());
                    int y = new Integer(window.receive());
                    FieldStatusEnum temp = window.castEnum(window.receive());
                    window.disableButton(x, y, temp);
                    window.setEnabledField(false);
                }
            }
            activeGame = false;
            window.setWinner(window.receive());
        }
    }
    
    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            //Do nothing
        }
    }
}
