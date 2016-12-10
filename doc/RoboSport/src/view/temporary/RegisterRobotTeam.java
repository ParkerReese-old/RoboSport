package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.SwingOps;

public class RegisterRobotTeam extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final RobotListEntry[] test = {
			new RobotListEntry("Robot #1"),
			new RobotListEntry("Terminator"),
			new RobotListEntry("Lt Commander Data"),
			new RobotListEntry("Crushinator 5000")};
	
	protected ListRobotTeam teamList;
	protected JTextField teamName;
	
	protected JComboBox<RobotListEntry> scout;
	protected JComboBox<RobotListEntry> tank;
	protected JComboBox<RobotListEntry> sniper;
	
	protected JButton addTeam;
	
	public RegisterRobotTeam() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200,100,100,100};
        gridBagLayout.rowHeights = new int[]{20,20,20,20,20,20};
        gridBagLayout.columnWeights = new double[]{1,0,0,0};
        gridBagLayout.rowWeights = new double[]{0,0,0,0,1,0};
        setLayout(gridBagLayout);
		
		
        JLabel title = new JLabel("Register a New Team");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth=3;
        add(title, gbc);
        
		teamList = new ListRobotTeam();
		teamList.testData();
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight=gridBagLayout.rowHeights.length;
        add(teamList, gbc);
        
        JLabel rn = new JLabel("Team Name");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(rn, gbc);
        
        teamName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth=2;
        add(teamName, gbc);
        
        
        JLabel scout_label = new JLabel("Scout");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor= GridBagConstraints.CENTER;
        add(scout_label, gbc);
        
        scout = getRobotPicker();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(scout, gbc);
        
        
        JLabel sniper_label = new JLabel("Sniper");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor= GridBagConstraints.CENTER;
        add(sniper_label, gbc);
        
        sniper = getRobotPicker();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(sniper, gbc);
        
        
        JLabel tank_label = new JLabel("Tank");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor= GridBagConstraints.CENTER;
        add(tank_label, gbc);
        
        tank = getRobotPicker();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(tank, gbc);
        
        
        
        
        addTeam = new JButton("Add Team");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(addTeam, gbc);
		
	}
	
	protected JComboBox<RobotListEntry> getRobotPicker() {
		return new JComboBox<RobotListEntry>(test);
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new RegisterRobotTeam());
		window.setTitle("Register Robot Team");
	}
}
