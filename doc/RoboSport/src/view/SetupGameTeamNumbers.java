package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SetupGameTeamNumbers extends JPanel {
	private static final long serialVersionUID = 1L;
	private final TeamRow[] teams;
	private final JButton start;
	private int teamsChosen;
	
	private boolean complete;
		
	public SetupGameTeamNumbers() {
	    complete=false;
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100,100,100};
        gridBagLayout.rowHeights = new int[]{20,20,20,20,20,20,20,20,20};
        gridBagLayout.columnWeights = new double[]{0.0,1.0};
        gridBagLayout.rowWeights = new double[]{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        setLayout(gridBagLayout);
        
        JLabel title = new JLabel("Set the number of teams");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);
        
        
        //Create the radio buttons.
        JRadioButton teams2 = new JRadioButton("2 Teams");
        // if 2 teams are selected, only 2 options of human or cpu appear
        teams2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 2; i < 6; i++) {
                    teams[i].setEnabled(false);
                    teams[i].setVisible(false);
                }
                repaint();
                teamsChosen = 2;
            }

        });

        JRadioButton teams3 = new JRadioButton("3 Teams");
        // if 3 teams are selected, only 3 options of human or cpu appear
        teams3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 3; i++) {
                    teams[i].setEnabled(true);
                    teams[i].setVisible(true);
                }
                for (int i = 3; i < 6; i++) {
                    teams[i].setEnabled(false);
                    teams[i].setVisible(false);
                }
                repaint();
                teamsChosen = 3;
            }

        });

        JRadioButton teams6 = new JRadioButton("6 Teams");
        teams6.setSelected(true);
        // initially 6 teams are selected so 6 options of human or cpu appear
        // if teams 6 is selected again 6 options of human or cpu appear
        teams6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 6; i++) {
                    teams[i].setEnabled(true);
                    teams[i].setVisible(true);
                }
                repaint();
                teamsChosen = 6;
            }

        });

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(teams2);
        group.add(teams3);
        group.add(teams6);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(teams2, gbc);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(teams3, gbc);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(teams6, gbc);
        
        teams = new TeamRow[6];
        
        for (int i=0; i<6; i++) {
        	teams[i]=new TeamRow(i+1);
	        gbc = new GridBagConstraints();
	        gbc.insets = new Insets(0, 0, 0, 0);
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.gridx = 0;
	        gbc.gridy = i+2;
	        gbc.gridwidth=3;
	        add(teams[i], gbc);
        }
        teamsChosen=6;
        
        start = new JButton("Start");
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
        gbc.gridy = 8;
        add(start, gbc);
	}
	
	
	public void onComplete(ActionListener onC) {
	    this.start.addActionListener(onC);
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
	
	protected boolean[] isHumanTeams() {
	    boolean[] ht = new boolean[teamsChosen];
	    for (int i=0; i<teamsChosen; i++)
	        ht[i]=teams[i].isHuman();
	    return ht;
	}
	
	private class TeamRow extends JPanel{
		private static final long serialVersionUID = 1L;
		protected final int teamNumber;
		protected final JRadioButton human;
		protected final JRadioButton cpu;
		
		public TeamRow(int n) {
			this.teamNumber = n;
			JLabel l = new JLabel("Team "+teamNumber);
			add(l);
			human = new JRadioButton("Human");
			human.setSelected(true);
			cpu = new JRadioButton("CPU");
			ButtonGroup group = new ButtonGroup();
	        group.add(human);
	        group.add(cpu);
	        add(human);
	        add(cpu);
	        
	        setBorder(BorderFactory.createLineBorder(Color.black));
		}
		
		public boolean isHuman() {
		    return this.human.isSelected();
		}
	
	}
	
	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new SetupGameTeamNumbers());
		window.setTitle("Set # of Players");
	}

}
