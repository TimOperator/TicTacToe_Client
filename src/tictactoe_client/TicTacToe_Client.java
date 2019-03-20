/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_client;

import javax.swing.UIManager;

/**
 *
 * @author Tim
 */
public class TicTacToe_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //Doesn't matter
        }
        MainClientFrame userframe = new MainClientFrame();
        userframe.setVisible(true);
        if (args.length >= 1 && args[0].equalsIgnoreCase("advancedMode")) {
            userframe.useAdvancedMode(true);
            System.out.println("You are using the advanced mode now.");
        } else {
            userframe.useAdvancedMode(false);
        }
    }
}
