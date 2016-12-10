package view;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

class PanelFrame extends JFrame {
	private static final long serialVersionUID = -5159890691903763609L;

	public PanelFrame(Component cont) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("TEST WINDOW");
		getContentPane().add(cont);
		pack();
		setVisible(true);
	}
}

/**
 * A class to contain custom generic Swing Operation methods
 * 
 * @author Dylan McInnes
 *
 */
public abstract class SwingOps {

    /**
     * Creates a new frame with the given Component contained
     * @param cont : the contained Component
     * @return the frame that contains the Component
     */
	public static JFrame newFrame(Component cont) {
		return new PanelFrame(cont);
	}
}
