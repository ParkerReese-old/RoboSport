package view.temporary;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import view.SwingOps;

public class ListRobotTeam extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	protected JList<RobotTeam> teamDisplay;
	protected Vector<RobotTeam> team;
	
	public ListRobotTeam() {
		team = new Vector<RobotTeam>();
		teamDisplay = new JList<RobotTeam>(team);
		setViewportView(teamDisplay);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(new Dimension(200, 300));
	}
	
	public void addTeam(RobotTeam t) {
		team.add(t);
	}
	
	
	public void testData() {
		addTeam(new RobotTeam("Mashup Robots",new RobotListEntry("Robot #1"),
				new RobotListEntry("Terminator"),
				new RobotListEntry("Lt Commander Data")));
		addTeam(new RobotTeam("Decepticons",new RobotListEntry("Swindle"),
				new RobotListEntry("Megatron"),
				new RobotListEntry("Starscream")));
	}

	public static void main(String[] args) {
		ListRobotTeam lt = new ListRobotTeam();
		lt.testData();
		JFrame window = SwingOps.newFrame(lt);
		window.setTitle("Display Robot Teams");
	}
}
