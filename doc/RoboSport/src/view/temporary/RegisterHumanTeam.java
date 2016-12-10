package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.SwingOps;

public class RegisterHumanTeam extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected ListHumanTeam teamList;
	protected JTextField teamName;
	
	protected JButton addTeam;
	
	public RegisterHumanTeam() {
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
        
		teamList = new ListHumanTeam();
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
        
        
        
        
        
        addTeam = new JButton("Add Team");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(addTeam, gbc);
		
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new RegisterHumanTeam());
		window.setTitle("Register Human Team");
	}
}
