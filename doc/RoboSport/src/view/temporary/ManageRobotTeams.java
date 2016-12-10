package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.SwingOps;

public class ManageRobotTeams extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JButton display;
	protected JButton register;
	protected JButton revise;
	protected JButton retire;

	public ManageRobotTeams() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50,100,50};
        gridBagLayout.rowHeights = new int[]{20,20,20,20};
        gridBagLayout.columnWeights = new double[]{0,1,0};
        gridBagLayout.rowWeights = new double[]{1,0,0,0};
        setLayout(gridBagLayout);
        
        JLabel title = new JLabel("Manage Robot Teams");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth=3;
        add(title, gbc);
        
        display = new JButton("Display All");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(display, gbc);
        
        register = new JButton("Register");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(register, gbc);
        
        retire = new JButton("Retire");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(retire, gbc);
	}
	
	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new ManageRobotTeams());
		window.setTitle("Manage Robot Teams");
	}
}
