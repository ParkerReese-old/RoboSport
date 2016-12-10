package view;

import javax.swing.JFrame;

import controller.Referee;
import models.Robot;

public class GameDisplayer {
    
    private static GameView disp;
    private static JFrame window;

    public static void displayGame(Referee ref){
		disp = new GameView(ref.getGameBoard().getSize(), 20);
		disp.getBoardDisplay().draw(ref);
		window = SwingOps.newFrame(disp);
		window.setTitle("Game Window");
	}
    
    public static void redraw(Robot r) {
        disp.getBoardDisplay().redraw(r);
    }
	
	public void close(){
		window.dispose();
	}

}
