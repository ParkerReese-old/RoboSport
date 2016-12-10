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

public class SetupGameCPUTeam extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected final JTextField teamName;
	protected final JButton ready;
	protected final int teamNumber;
	
	protected final JComboBox<RobotTeam> teams;
	
	public SetupGameCPUTeam(int t) {
		teamNumber = t;
		teamName = new JTextField("Team #"+teamNumber);
		ready = new JButton("Ready");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100,100,100};
        gridBagLayout.rowHeights = new int[]{20,20,20};
        gridBagLayout.columnWeights = new double[]{0.0,1.0};
        gridBagLayout.rowWeights = new double[]{1.0,0.0,0.0};
        setLayout(gridBagLayout);
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=3;
        add(new JLabel("CPU Team "+teamNumber+" Settings"), gbc);
		
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 2;
		add(ready,gbc);
		
		JLabel teaml = new JLabel("Select Team");
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
		add(teaml,gbc);
		
		teams = new JComboBox<RobotTeam>(new RobotTeam[]{new RobotTeam("Decepticons",null,null,null)});
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
		add(teams,gbc);
		
		
	}

	
	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new SetupGameCPUTeam(1));
		window.setTitle("Set CPU Team");
	}

}
