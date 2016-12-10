package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameDrawDisplay extends JPanel {
    
    /**
     * gameDrawsDisplay() opens a panel with the "game is a draw" message inside of it.
     */
    public GameDrawDisplay() {
        final JFrame parent = new JFrame();
        final JPanel panel = new JPanel();
        parent.getContentPane().add(panel);
        parent.setVisible(true);
        final JLabel label = new JLabel("Game is a draw! Losses for everyone!");
        panel.add(label);
        panel.setVisible(true);
        parent.pack(); 
    }
    
    public static void main(String[] args) {
    }
}
