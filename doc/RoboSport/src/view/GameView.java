package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import models.BoardPosition;


public class GameView extends JPanel {
    private static final long serialVersionUID = 1L;

    protected final BoardDisplay bd;
    protected final RobotControlPanel rcp;
    
    public GameView(int boardSize, int hexSize) {
        int dimensions = (int) (boardSize*hexSize*6/BoardDisplay.ROOT3);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{dimensions/3,dimensions/3,dimensions/3};
        gridBagLayout.rowHeights = new int[]{dimensions,40};
        gridBagLayout.columnWeights = new double[]{0.5,0,0.5};
        gridBagLayout.rowWeights = new double[]{0,1};
        setLayout(gridBagLayout);
        
        this.bd = new BoardDisplay(dimensions, dimensions, hexSize);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=3;
        add(bd, gbc);
        
        this.rcp = new RobotControlPanel();
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(rcp, gbc);
        
        // Add Mouse Controls
        addMouseListener(new PCMouseControl());
        this.rcp.onEnd(new PCTurnEnd());
    }
    
    /**
     * @return the BoardDisplay in the view
     */
    public BoardDisplay getBoardDisplay() {
        return this.bd;
    }
    
    
    protected class PCMouseControl implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (bd.ref!=null) {
                if (bd.ref.isPC) {
                    int[] v = bd.getHexCoordinate(e.getPoint());
                    if (v != null) {
                        // Get the current position (obp) and the mouse position (nbp)
                        BoardPosition obp = bd.ref.current.getMyRobot().getLocation();
                        BoardPosition nbp = new BoardPosition(v[0],v[1],0);
                        
                        // if move enabled:
                        if (rcp.isMove()) {
                            int d = obp.distanceTo(nbp);
                            for (int i=0; i<d; i++) {
                                bd.ref.current.turn(obp.immediateDirection(nbp));
                                bd.ref.current.advance();
                            }
                        } else { // if shoot enabled:
                            try{
                                bd.ref.current.shoot(obp.distanceTo(nbp), obp.directionTo(nbp));
                            } catch (RuntimeException ex) {
                                ex.printStackTrace();
                            }
                        }
                        //ref.current.done=true;
                        bd.redraw(bd.ref.current.getMyRobot());
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    }
    
    
    protected class PCTurnEnd implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (bd.ref.isPC)
                bd.ref.current.done=true;
        }
    }
    
    static public void main(String[] args) {
        SwingOps.newFrame(new GameView(5, 20));
    }
}
