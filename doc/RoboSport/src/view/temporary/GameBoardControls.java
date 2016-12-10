package view.temporary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.SwingOps;

public class GameBoardControls extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected JButton info;
	protected JButton quit;
	protected JButton endTurn;
	
	public GameBoardControls() {
		GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50,100,50};
        gridBagLayout.rowHeights = new int[]{20,100,20};
        gridBagLayout.columnWeights = new double[]{0,1,0};
        gridBagLayout.rowWeights = new double[]{0,1,0};
        setLayout(gridBagLayout);
        
        info = new JButton("Info");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(info, gbc);
        
        quit = new JButton("Quit");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(quit, gbc);
        
        endTurn = new JButton("End Turn");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(endTurn, gbc);
        
        
		try {
			BufferedImage board = ImageIO.read(new File("ext/game_hexes.png"));
			JLabel picLabel = new JLabel(new ImageIcon(board.getScaledInstance(300,300,Image.SCALE_SMOOTH)));
	        gbc = new GridBagConstraints();
	        gbc.insets = new Insets(0, 0, 0, 0);
	        gbc.fill = GridBagConstraints.BOTH;
	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        add(picLabel, gbc);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	
	public static void main(String[] args) {
		JFrame window = SwingOps.newFrame(new GameBoardControls());
		window.setResizable(true);
		window.setTitle("Robot Deathmatch!");
	}

}
