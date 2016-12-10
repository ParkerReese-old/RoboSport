package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.GameInitializer;
import controller.Referee;
import models.GameBoard;
import view.SwingOps;

/**
 * Main Menu is the first GUI to appear in our system. It will then be used to navigate to the rest
 * of our system.
 * 
 * @author Dylan. With minor additions by Parker
 */
public class MainMenu extends JPanel {
    private static final long serialVersionUID = 1L;

    /* we will have three button on our main menu */
    protected JButton start;
    protected JButton robotStatistics;
    protected JButton rules;
    
    
    protected boolean started=false;

    /**
     * calling MainMenu will create a GUI that is the Main Menu of our system.
     */
    public MainMenu() {

        /* setup a grid for buttons and text to be placed on */
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {75, 100, 75};
        gridBagLayout.rowHeights = new int[] {20, 20, 20, 20, 20, 20, 20, 20};
        gridBagLayout.columnWeights = new double[] {0, 1, 0};
        gridBagLayout.rowWeights = new double[] {1, 0, 0, 0, 0, 0, 0};
        setLayout(gridBagLayout);

        /* add the text Main Menu to the top of the panel */
        JLabel title = new JLabel("Main Menu");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 3;
        add(title, gbc);

        /* add a button called Start Game */
        start = new JButton("Start Game");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(start, gbc);


        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                started=true;
            }

        });
        // /* create a listener to react to an event on the Start Game button */
        // robotStatistics.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent ae){
        // /* clicking this button opens a new panel called Setup Display */
        // JFrame window = SwingOps.newFrame(new SetupDisplay());
        // window.setTitle("Team Choices");
        // }
        // });

        /* add a button called Robot Statistics */
        robotStatistics = new JButton("Robot Statistics");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(robotStatistics, gbc);

        /* create a listener to react to an event on the robotStatistics button */
        robotStatistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                /* clicking this button opens a new panel called StatisticsDisplay */
                JFrame window = SwingOps.newFrame(new StatisticsDisplay());
                window.setTitle("Robot Statistics");
            }
        });

        /* create a button labeled Rules */
        rules = new JButton("Rules");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(rules, gbc);

        /* add a listener to react to an action on the rules button */
        rules.addActionListener(new ActionListener() {

            /* when rules is clicked open a new panel called RulesDisplay */
            public void actionPerformed(ActionEvent ae) {
                JFrame window = SwingOps.newFrame(new RulesDisplay());
                window.setTitle("Rules of the Game");
            }
        });

        /* add text to the bottom of the panel */
        JLabel credits = new JLabel("Made by Brady, Dylan, Meagan, and Parker");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 3;
        add(credits, gbc);


    }
    
    public void waitForStart() {
        while (!started) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * This main method is the entry point to our entire system.
     */
    public static void main(String[] args) {
        MainMenu m = new MainMenu();
        JFrame window = SwingOps.newFrame(m);
        window.setTitle("RoboSport Menu");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        while (true) {
            m.waitForStart();
            
            GameInitializer gi = new GameInitializer();
            GameBoard gb = gi.initializeGame();
            Referee ref = new Referee(gb);
            
            GameDisplayer.displayGame(ref);
            
            ref.startGame(gb);
            m.started=false;
        }
        
        // JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit",
        // JOptionPane.YES_NO_OPTION);
    }
}
