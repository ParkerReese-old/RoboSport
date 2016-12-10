package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Robot;
import models.RobotLibrarySocket;

public class SelectNPCTeam extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static List<Robot> robots=null;
	
	protected JTextField teamName;
	
	protected JComboBox<Robot> scout;
	protected JComboBox<Robot> tank;
	protected JComboBox<Robot> sniper;
	
	protected JButton confirm;
	
	private boolean complete;
	
	public SelectNPCTeam(int teamnum) {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100,100,100};
        gridBagLayout.rowHeights = new int[]{20,20,20,20,20,20};
        gridBagLayout.columnWeights = new double[]{0.33,0.33,0.33};
        gridBagLayout.rowWeights = new double[]{0,0,0,0,1,0};
        setLayout(gridBagLayout);
		
		
        JLabel title = new JLabel("Set NPC Team #"+teamnum);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth=3;
        add(title, gbc);
        
        
        JLabel rn = new JLabel("Team Name");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(rn, gbc);
        
        teamName = new JTextField();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth=2;
        add(teamName, gbc);
        
        
        JLabel scout_label = new JLabel("Scout");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor= GridBagConstraints.CENTER;
        add(scout_label, gbc);
        
        scout = getRobotPicker(Robot.Type.SCOUT);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(scout, gbc);
        
        
        JLabel sniper_label = new JLabel("Sniper");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor= GridBagConstraints.CENTER;
        add(sniper_label, gbc);
        
        sniper = getRobotPicker(Robot.Type.SNIPER);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(sniper, gbc);
        
        
        JLabel tank_label = new JLabel("Tank");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor= GridBagConstraints.CENTER;
        add(tank_label, gbc);
        
        tank = getRobotPicker(Robot.Type.TANK);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(tank, gbc);
        
        
        
        
        confirm = new JButton("Confirm");
        onComplete(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                complete=true;
            }
        });
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(confirm, gbc);
		
	}
	
	private JComboBox<Robot> getRobotPicker(Robot.Type t) {
	    if (robots==null)
	        robots = RobotLibrarySocket.getAllRobotsFast();
	    
	    LinkedList<Robot> ret = new LinkedList<Robot>();
	    for (Robot r : robots)
	        if (r.getType()==t)
	            ret.add(r);
	    
		return new JComboBox<Robot>(ret.toArray(new Robot[ret.size()]));
		// TODO repair this
	}
	
	public void onComplete(ActionListener onC) {
        this.confirm.addActionListener(onC);
    }
    
    /**
     * Wait till the data is finished being entered
     */
    public void waitForCompletion() {
        while (!complete) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
    
    protected List<Robot> getRobots() {
        waitForCompletion();
        List<Robot> ret = new ArrayList<Robot>(3);

        ret.add(((Robot)tank.getSelectedItem()).clone());
        ret.add(((Robot)scout.getSelectedItem()).clone());
        ret.add(((Robot)sniper.getSelectedItem()).clone());
        
        return ret;
    }

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new SelectNPCTeam(3));
		window.setTitle("Register Robot Team");
	}
}
