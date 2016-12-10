package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * This panel will be used to show the rules of the game.
 * @author Dylan. With minor additions by Parker
 *
 */
public class RulesDisplay extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * RulesDisplay() opens a panel with the rules of the game inside of it
     */
    public RulesDisplay() {

        String rulesDisc = new String();
        /* add the rules of the game to this string */
        rulesDisc = "This is a computer played board game called Robot Sport. Using simple point to shoot and "
                + "move interactions it is the goal of each player to eliminate the enemy players from the board. "
                + "With games consisting of either 2, 3, or 6 teams, each team is given 3 robots to do thier battling."
                + " Each robot has their own strengths and weaknesses. Choose your moves wisely and may the odds "
                + "be ever in your favour.";
        
        /* place the rules in the text box on the panel */
        JTextArea rules = new JTextArea(rulesDisc);
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);
        JScrollPane scrollRules = new JScrollPane();
        scrollRules.setViewportView(rules);
        scrollRules.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollRules.setPreferredSize(new Dimension(300, 300));

        add(scrollRules);
    }

    public static void main(String[] args) {
    }
}
