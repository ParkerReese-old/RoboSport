package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import view.SwingOps;

public class RegisterRobot extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected ListRobots robotList;
	protected JTextField robotName;
	protected JButton aifile;
	protected JTextField aipath;
	
	protected JButton addRobot;
	
	public RegisterRobot() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200,100,100,100};
        gridBagLayout.rowHeights = new int[]{20,20,20,20,20,20};
        gridBagLayout.columnWeights = new double[]{1,0,0,0};
        gridBagLayout.rowWeights = new double[]{0,0,0,0,1,0};
        setLayout(gridBagLayout);
		
		
        JLabel title = new JLabel("Register a New Robot");
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
        
        JRadioButton scout = new JRadioButton("Scout");
        scout.setSelected(true);

        JRadioButton sniper = new JRadioButton("Sniper");

        JRadioButton tank = new JRadioButton("Tank");

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(scout);
        group.add(sniper);
        group.add(tank);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(scout, gbc);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(sniper, gbc);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(tank, gbc);
        
        JLabel rn = new JLabel("Robot Name");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(rn, gbc);
        
        robotName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth=2;
        add(robotName, gbc);
        
        aipath = new JTextField("/student/dym533/cmpt370/robots/r1.rob");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth=2;
        add(aipath, gbc);
        
        aifile = getFileButton();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(aifile, gbc);
        
        addRobot = new JButton("Add Robot");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(addRobot, gbc);
		
	}
	
	protected JButton getFileButton() {
		return new JButton(UIManager.getIcon("FileView.directoryIcon"));
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new RegisterRobot());
		window.setTitle("Register Robot");
	}
}
