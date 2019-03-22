/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_client;

import java.util.ResourceBundle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Tim
 */
public class TicTacToe_Client {

    private static final ResourceBundle STRING_BUNDLE = ResourceBundle.getBundle("resources/strings");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            //Doesn't matter
        }
        MainClientFrame userframe = new MainClientFrame();
        userframe.setVisible(true);
        if (args.length >= 1 && args[0].equalsIgnoreCase("advancedMode")) {
            userframe.useAdvancedMode(true);
            System.out.println(STRING_BUNDLE.getString("Inform_advanced_mode"));
        } else {
            userframe.useAdvancedMode(false);
        }
    }
}
