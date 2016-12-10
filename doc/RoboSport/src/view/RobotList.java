package view;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import models.RobotLibrarySocket;

/**
 * This entity is used by the Statistics Display to dictate which Robots are being placed
 * inside the textbox
 * @author Dylan. With minor additions by Parker
 *
 */
public class RobotList extends JScrollPane {
    private static final long serialVersionUID = 1L;
    
    protected JList<RobotListEntry> robotDisplay;
    protected Vector<RobotListEntry> robots;
    
    /**
     * Constructor for RobotList
     */
    public RobotList() {       
        robots = new Vector<RobotListEntry>();
        robotDisplay = new JList<RobotListEntry>(robots);       
        setViewportView(robotDisplay);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setPreferredSize(new Dimension(200, 300));                       
    }
    
    /**
     * adds a robot's name to the robotList
     * @param rle the robot being added to the list
     */
    public void addRobot(RobotListEntry rle) {
        robots.add(rle);
    }
    
    /**
     * Grabs all the Robots in the RobotLibrary, and adds them to the list of Robots to be displayed
     */
    public void noOrder(){
        /* call the robot library for all the teams stats and store their values */
        String tStats = RobotLibrarySocket.enumerateRobotsAndStatsBrief();
      
        /* take out characters that are not needed */
        tStats = tStats.replaceAll("\\{\"script\":\\{", "");        
        String[] Stats = tStats.split("\\}\\},");
        
        /* add all the String indexes to the RobotList */
        for(String s : Stats){
            addRobot(new RobotListEntry(s));            
        }                       
    }

    /**
     * Example of how to add robots to the list
     */
    public void testData() {
        addRobot(new RobotListEntry("Robot #1"));
        addRobot(new RobotListEntry("Terminator"));
        addRobot(new RobotListEntry("Lt Commander Data"));
        addRobot(new RobotListEntry("Crushinator 5000"));
    }

    public static void main(String[] args) {
      
    }
}