package view.temporary;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import view.SwingOps;

public class Rules extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public Rules() {
		JTextArea rules = new JTextArea("Blah blah blah\nSome rules here");
		rules.setLineWrap(true);
		rules.setWrapStyleWord(true);
		JScrollPane scrollRules = new JScrollPane();
		scrollRules.setViewportView(rules);
		scrollRules.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollRules.setPreferredSize(new Dimension(300,300));
		
		add(scrollRules);
	}

	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new Rules());
		window.setTitle("Rules of the Game");
	}
}
