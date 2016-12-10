package view.temporary;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import view.SwingOps;

public class ListRobots extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	protected JList<RobotListEntry> robotDisplay;
	protected Vector<RobotListEntry> robots;
	
	public ListRobots() {
		robots = new Vector<RobotListEntry>();
		robotDisplay = new JList<RobotListEntry>(robots);
		setViewportView(robotDisplay);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(new Dimension(200, 300));
	}
	
	public void addRobot(RobotListEntry rle) {
		robots.add(rle);
	}

	public void testData() {
		addRobot(new RobotListEntry("Robot #1"));
		addRobot(new RobotListEntry("Terminator"));
		addRobot(new RobotListEntry("Lt Commander Data"));
		addRobot(new RobotListEntry("Crushinator 5000"));
	}

	public static void main(String[] args) {
		ListRobots lr = new ListRobots();
		lr.testData();
		JFrame window = SwingOps.newFrame(lr);
		window.setTitle("Display Robots");
	}
}
