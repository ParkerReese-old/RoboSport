package models;

import java.awt.Color;
import java.util.List;

/**
 * 
 * @author Parker
 *
 */
public class Team {

	private int teamID;
	private List<Robot> robots;
	private TeamColor color; 
	
	/**
	 * An enumerator for the Team Colors
	 * @author Dylan McInnes
	 *
	 */
	public enum TeamColor {
	    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO;
	    public Color getColor() {
	        switch (this) {
	            case RED:    return Color.RED;
	            case GREEN:  return Color.GREEN;
	            case BLUE:   return Color.BLUE;
	            case YELLOW: return Color.YELLOW;
	            case INDIGO: return Color.MAGENTA;
	            case ORANGE: return Color.ORANGE;
	        }
	        return null;
	    }
	    public int getDirection() {
	        switch (this) {
	            case RED:    return 3;
                case GREEN:  return 0;
                case BLUE:   return 1;
                case YELLOW: return 5;
                case INDIGO: return 2;
                case ORANGE: return 4;
	        }
	        return 0;
	    }
	}
	
	/**
	 * Constructor for a Team. (which is described as a team of Robot's)
	 * @param color the color of the Team
	 * @param robots the Robot's in the Team
	 * @param teamID the unique ID for the Team to address it by
	 */
	public Team(List<Robot> robots, TeamColor color, int teamID){
		this.teamID = teamID;
		this.setColor(color);
		this.robots = robots;
		for (Robot r : robots)
		    r.setTeam(this);
	}

	/**
	 * Getter for the unique teamID of the Team
	 * @return the Team's teamID
	 */
	public int getTeamID() {
		return teamID;
	}

	/**
	 * Getter for the List of Robot's in a Team (three Robot's to a Team)
	 * @return the List of Robot's
	 */
	public List<Robot> getRobots() {
		return robots;
	}

	/**
	 * Getter for the color of the Team
	 * @return the hexidecimal integer of the Team's color
	 */
	public TeamColor getColor() {
		return color;
	}

	/**
	 * setter for the color of the Team
	 * @param color the color for the Team to be assigned
	 */
	public void setColor(TeamColor color) {
		this.color = color;
	}
}
