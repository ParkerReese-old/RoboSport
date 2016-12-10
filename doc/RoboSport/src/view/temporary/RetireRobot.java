package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import view.SwingOps;

public class RetireRobot extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected ListRobots robotList;
	
	protected JButton retireRobot;
	
	public RetireRobot() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200};
        gridBagLayout.rowHeights = new int[]{20,20,20};
        gridBagLayout.columnWeights = new double[]{1};
        gridBagLayout.rowWeights = new double[]{0,1,0};
        setLayout(gridBagLayout);
		
		
        JLabel title = new JLabel("Retire a Robot");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);
        
		robotList = new ListRobots();
		robotList.testData();
		gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(robotList, gbc);
        
        retireRobot = new JButton("Retire Robot");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(retireRobot, gbc);
		
	}
	
	protected JButton getFileButton() {
		return new JButton(UIManager.getIcon("FileView.directoryIcon"));
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new RetireRobot());
		window.setTitle("Retire Robot");
	}
}
