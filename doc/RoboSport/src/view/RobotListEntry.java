package view;

/**
 * Helper entity of the RobotList
 * @author Dylan.
 *
 */
public class RobotListEntry {
    
    protected String name;
    
    /**
     * Makes the entry in the Robot List that of the String passed in
     * @param name the name of the Robot
     */
    public RobotListEntry(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
