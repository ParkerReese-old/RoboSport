package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.SwingOps;

public class RetireRobotTeam extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected ListRobotTeam teamList;
	
	protected JButton retireTeam;
	
	public RetireRobotTeam() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200};
        gridBagLayout.rowHeights = new int[]{20,20,20};
        gridBagLayout.columnWeights = new double[]{1};
        gridBagLayout.rowWeights = new double[]{0,1,0};
        setLayout(gridBagLayout);
		
		
        JLabel title = new JLabel("Retire a Team");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);
        
		teamList = new ListRobotTeam();
		teamList.testData();
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(teamList, gbc);
        
        retireTeam = new JButton("Retire Team");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(retireTeam, gbc);
		
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new RetireRobotTeam());
		window.setTitle("Retire Robot Team");
	}
}
