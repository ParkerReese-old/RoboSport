package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import view.SwingOps;

public class MainMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JButton start;
	protected JButton manageRobots;
	protected JButton manageRobotTeams;
	protected JButton manageHumanTeams;
	protected JButton robotStatistics;
	protected JButton rules;
	
	public MainMenu() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{75,100,75};
        gridBagLayout.rowHeights = new int[]{20,20,20,20,20,20,20,20};
        gridBagLayout.columnWeights = new double[]{0,1,0};
        gridBagLayout.rowWeights = new double[]{1,0,0,0,0,0,0};
        setLayout(gridBagLayout);
        
        JLabel title = new JLabel("Main Menu");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth=3;
        add(title, gbc);
        
        start = new JButton("Start Game");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(start, gbc);
        
        manageRobots = new JButton("Manage Robots");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(manageRobots, gbc);
        
        manageRobotTeams = new JButton("Manage Robot Teams");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(manageRobotTeams, gbc);
        
        manageHumanTeams = new JButton("Manage Human Teams");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(manageHumanTeams, gbc);
        
        robotStatistics = new JButton("Robot Statistics");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(robotStatistics, gbc);
        
        
        rules = new JButton("Rules");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(rules, gbc);
        
        JLabel credits = new JLabel("Made by Brady, Dylan, Meagan, and Parker");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth=3;
        add(credits, gbc);
        
        
	}
	
	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new MainMenu());
		window.setTitle("RoboSport Menu");
		
		JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
	}
}
