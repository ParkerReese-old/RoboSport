package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This panel will be used to display the statistics of robots under our team (A2)
 * @author Dylan. With minor addition by Parker
 *
 */
public class StatisticsDisplay extends JPanel {
    private static final long serialVersionUID = 1L;

    protected RobotList robotList;
    /* radio buttons to change the order in which the robots are listed */
    protected JRadioButton team;
    protected JRadioButton name;
    protected JRadioButton wins;
    protected JRadioButton matches;
    protected JRadioButton winLoss;

    /**
     * Calling this method opens up a panel with the names of Robots will be displayed
     * in the order selected by the radioboxes.
     */
    public StatisticsDisplay() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50,50,50,50,50};
        gridBagLayout.rowHeights = new int[]{20,20,20};
        gridBagLayout.columnWeights = new double[]{0,0,1,0,0};
        gridBagLayout.rowWeights = new double[]{0,0,1};
        setLayout(gridBagLayout);

        /* set the title of the panel */
        JLabel title = new JLabel("List Robots By Statistics");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        /*Create the radio buttons and add an action listener to them */
        team = new JRadioButton("Team");
        team.addActionListener(new ActionListener() {
            /* when team is clicked change the RobotList accordingly */
            public void actionPerformed(ActionEvent ae){
               
            }
        });
        
        name = new JRadioButton("Name");
        name.addActionListener(new ActionListener() {
            /* when name is clicked change the RobotList accordingly */
            public void actionPerformed(ActionEvent ae){
               
            }
        });
        
        wins = new JRadioButton("Wins");
        wins.addActionListener(new ActionListener() {
            /* when wins is clicked change the RobotList accordingly */
            public void actionPerformed(ActionEvent ae){

            }
        });
        
        matches = new JRadioButton("# Matches");
        matches.addActionListener(new ActionListener() {
            /* when matches is clicked change the RobotList accordingly */
            public void actionPerformed(ActionEvent ae){

            }
        });
        
        winLoss = new JRadioButton("Win/Loss");
        winLoss.addActionListener(new ActionListener() {
            /* when winLoss is clicked change the RobotList accordingly */
            public void actionPerformed(ActionEvent ae){         
            }
        });
        
        /* default selection */
        team.setSelected(true);

        /*Group the radio buttons */
        ButtonGroup group = new ButtonGroup();
        group.add(team);
        group.add(name);
        group.add(wins);
        group.add(matches);
        group.add(winLoss);

        /* place radio buttons on panel */
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(team, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(name, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(wins, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(matches, gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 4;
        gbc.gridy = 1;
        add(winLoss, gbc);

        /* default ordering in the text field for the list of Robots */
        robotList = new RobotList();
        robotList.noOrder();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth=5;
        add(robotList, gbc);
          
    }   
    
    public static void main(String[] args) {
    }

}