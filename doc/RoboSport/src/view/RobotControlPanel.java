package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RobotControlPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JRadioButton move;
    private JRadioButton shoot;
    private JButton endTurn;

    public RobotControlPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50,50};
        gridBagLayout.rowHeights = new int[]{20,20};
        gridBagLayout.columnWeights = new double[]{0.5,0.5};
        gridBagLayout.rowWeights = new double[]{0,1};
        setLayout(gridBagLayout);
        
        move = new JRadioButton("Move");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(move, gbc);
        
        shoot = new JRadioButton("Shoot");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(shoot, gbc);
        
        ButtonGroup ms = new ButtonGroup();
        ms.add(move);
        ms.add(shoot);
        move.setSelected(true);
        
        endTurn = new JButton("End Turn");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;
        //gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(endTurn, gbc);
        
        
        onEnd(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move.setSelected(true);
            }
        });
    }
    
    /**
     * @return true if the controls are currently set to "move"
     */
    public boolean isMove() {
        return move.isSelected();
    }
    
    /**
     * 
     * @param end : the listener for the end-turn pressed button
     */
    protected void onEnd(ActionListener end) {
        endTurn.addActionListener(end);
    }
    
    public static void main(String[] args) {
        SwingOps.newFrame(new RobotControlPanel());
    }
}
