package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import view.SwingOps;

public class ShowRobotsByStatistics extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected ListRobots robotList;
	
	protected JRadioButton team;
	protected JRadioButton name;
	protected JRadioButton wins;
	protected JRadioButton matches;
	protected JRadioButton winLoss;
	
	public ShowRobotsByStatistics() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50,50,50,50,50};
        gridBagLayout.rowHeights = new int[]{20,20,20};
        gridBagLayout.columnWeights = new double[]{0,0,1,0,0};
        gridBagLayout.rowWeights = new double[]{0,0,1};
        setLayout(gridBagLayout);
		
		
        JLabel title = new JLabel("List Robots By Statistics");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);
        
      //Create the radio buttons.
        team = new JRadioButton("Team");
        name = new JRadioButton("Name");
        wins = new JRadioButton("Wins");
        matches = new JRadioButton("# Matches");
        winLoss = new JRadioButton("Win/Loss");
        
        team.setSelected(true);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(team);
        group.add(name);
        group.add(wins);
        group.add(matches);
        group.add(winLoss);
        
        
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
        
        
		robotList = new ListRobots();
		robotList.testData();
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth=5;
        add(robotList, gbc);
		
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new ShowRobotsByStatistics());
		window.setTitle("Robot Statistics");
	}

}
