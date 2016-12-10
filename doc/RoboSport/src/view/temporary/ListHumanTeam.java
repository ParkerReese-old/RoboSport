package view.temporary;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import view.SwingOps;

public class ListHumanTeam extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	protected JList<HumanTeam> teamDisplay;
	protected Vector<HumanTeam> team;
	
	public ListHumanTeam() {
		team = new Vector<HumanTeam>();
		teamDisplay = new JList<HumanTeam>(team);
		setViewportView(teamDisplay);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setPreferredSize(new Dimension(200, 300));
	}
	
	public void addTeam(HumanTeam t) {
		team.add(t);
	}
	
	
	public void testData() {
		addTeam(new HumanTeam("Meatsuits"));
		addTeam(new HumanTeam("Mecha Warriors"));
		addTeam(new HumanTeam("Robo Slayerz"));
	}

	public static void main(String[] args) {
		ListHumanTeam lt = new ListHumanTeam();
		lt.testData();
		JFrame window = SwingOps.newFrame(lt);
		window.setTitle("Display Human Teams");
	}
}
