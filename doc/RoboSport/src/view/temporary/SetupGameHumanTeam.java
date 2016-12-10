package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.SwingOps;

public class SetupGameHumanTeam extends JPanel {
	private static final long serialVersionUID = 1L;
	protected final JComboBox<HumanTeam> teamName;
	protected final JButton ready;
	protected final int teamNumber;
	
	private static final HumanTeam[] test = new HumanTeam[]{
			new HumanTeam("Meatsuits"),
			new HumanTeam("Warriors of Asimov"),
			new HumanTeam("Mecha Warriors"),
			new HumanTeam("Robo Slayerz")
	};
	
	public SetupGameHumanTeam(int t) {
		teamNumber = t;
		teamName = new JComboBox<HumanTeam>(test);
		ready = new JButton("Ready");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100,100,100};
        gridBagLayout.rowHeights = new int[]{20,20,20};
        gridBagLayout.columnWeights = new double[]{0.0,1.0};
        gridBagLayout.rowWeights = new double[]{1.0,0.0,0.0};
        setLayout(gridBagLayout);
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=3;
        add(new JLabel("Human Team "+teamNumber+" Settings"), gbc);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
		add(new JLabel("Team Name"), gbc);
		
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=2;
		add(teamName, gbc);
		
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 2;
		add(ready,gbc);
	}

	
	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new SetupGameHumanTeam(1));
		window.setResizable(true);
		window.setTitle("Set Human Team");
	}
}
