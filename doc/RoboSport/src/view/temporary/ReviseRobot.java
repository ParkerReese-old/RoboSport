package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import view.SwingOps;

public class ReviseRobot extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected ListRobots robotList;
	protected JLabel robotName;
	protected JButton aifile;
	protected JTextField aipath;
	
	protected JButton updateRobot;
	protected JButton freezeRobot;
	
	public ReviseRobot() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200,100,100,100};
        gridBagLayout.rowHeights = new int[]{20,20,20,20,20};
        gridBagLayout.columnWeights = new double[]{1,0,0,0};
        gridBagLayout.rowWeights = new double[]{0,0,0,1,0};
        setLayout(gridBagLayout);
		
		
        JLabel title = new JLabel("Revise a Robot");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth=3;
        add(title, gbc);
        
		robotList = new ListRobots();
		robotList.testData();
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight=gridBagLayout.rowHeights.length;
        add(robotList, gbc);
        
        robotName = new JLabel("Robot Name: Terminator");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=3;
        add(robotName, gbc);
        
        aipath = new JTextField("/student/dym533/cmpt370/robots/r1.rob");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth=2;
        add(aipath, gbc);
        
        aifile = getFileButton();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(aifile, gbc);
        
        freezeRobot = new JButton("Freeze Robot");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(freezeRobot, gbc);
        
        updateRobot = new JButton("Update Robot");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 4;
        add(updateRobot, gbc);
		
	}
	
	protected JButton getFileButton() {
		return new JButton(UIManager.getIcon("FileView.directoryIcon"));
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new ReviseRobot());
		window.setTitle("Revise Robot");
	}
}
