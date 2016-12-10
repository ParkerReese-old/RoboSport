package models;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Meagan */

import java.util.List;
import java.util.Map;

/** Class for model GameBoard */
public class GameBoard {
	
	private List<Team> teams;
	private int size;
	private Map<String, Map<String,Integer>> Statistics;
	
	/**
	 * Constructor for GameBoard
	 * @param teams list of teams on the game board
	 */
	public GameBoard(List<Team> teams){
		
		this.teams = teams;
		this.setSize();
		this.Statistics = new HashMap<String, Map<String, Integer>>();
		
		/** for each robot in the gameboard, we need to allocate a spot for it in the Statistics map
		 * along with a default zero value for each stat */
		for(int i = 0; i < teams.size(); i++){
			for(int j = 0; j < teams.get(i).getRobots().size(); j++){
				
				Map<String,Integer> innerMap = new HashMap<String, Integer>();
				innerMap.put("matches", 0);
				innerMap.put("wins", 0);
				innerMap.put("losses", 0);
				innerMap.put("executions", 0);
				innerMap.put("lived", 0);
				innerMap.put("died", 0);
				innerMap.put("absorbed", 0);
				innerMap.put("killed", 0);
				innerMap.put("distance", 0);
				
				Statistics.put(teams.get(i).getRobots().get(j).getRobotStringID(), innerMap);
				
			}
		}
		
	}
	
	/**
	 * Method that determines the size of the game board based on how many teams there are
	 */
	public void setSize()
	{
		if (teams.size() == 2 || teams.size() == 3)
		{
			this.size = 5;
		}
		else
		{
			this.size = 7;
		}

	}
	
	/**
	 * Method that returns a list of teams on the game board
	 * @return list of teams
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * Method that sets the teams on the game board given a list of teams
	 * @param teams the list of teams to be put on the game board
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	/**
	 * Method to remove a team from the list of teams on the game board
	 * used when a team quits or when a team is defeated
	 * @param teamremove the team to be removed
	 */
	public void removeTeam(Team teamremove){
		if(teams.contains(teamremove)){
			this.teams.remove(teamremove);
		}
		
	}
	
	/**
	 * Method that sets the size of the game board
	 * @param size the size the game board will be set to
	 */
	public int getSize(){
		
		return this.size;	
	}
	
	/**
	 * Method the will return a list of all the robots in the game
	 * @return a List containing all the robots in the game
	 */
	public LinkedList<Robot> getAllRobots(){
		
		LinkedList<Robot> r = new LinkedList<Robot>();
		int teamnum = teams.size();
		for (int i=0;i<teamnum;i++)
		{
			r.addAll(teams.get(i).getRobots());
		}
		return r;
		
	}
	/**
	 * Method that will update a specific statistic for a specific robot
	 * @param ID the identifier of the specific robot
	 * @param stat the statistic being updated
	 * @param value the value to increment the statistic by
	 */
	public void updateImmediateStatistics (String ID, String stat, int value ){
		/** if you try to update a stat that doesn't exist this will throw an error: author - Parker*/
		int total = this.Statistics.get(ID).get(stat) + value;
		
		this.Statistics.get(ID).put(stat, total);
		
	}
	
	/**
	 * Method the will return all statistics at the time it is called
	 * @return a Map containing the statistics
	 */
	public Map<String, Map<String,Integer>> getImmediateStatistics() {
	
		Map<String, Map<String,Integer>> stats = new HashMap<String, Map<String,Integer>>();
		stats.putAll(this.Statistics);
		
		return stats;	
	}
	/**
	 * Method used to determine if a position a robot is attempting to move to is a valid position 
	 * on the board
	 * @param coordinate the position the robot is attempting to move to
	 * @return true if the position is valid, false otherwise
	 */
	public boolean isValid(BoardPosition coordinate){

		if (this.getSize()== 5)
		{
			switch(coordinate.x){
			case 0:
				/** x coordinate is 0 */
				if (coordinate.y > 4 || coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 1:
				if (coordinate.y > 5|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 2:
				if (coordinate.y > 6|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 3:
				if (coordinate.y > 7|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 4:
				if (coordinate.y > 8|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 5:
				if (coordinate.y > 8|| coordinate.y < 1)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 6:
				if (coordinate.y > 8|| coordinate.y < 2)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 7:
				if (coordinate.y > 8|| coordinate.y < 3)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 8:
				if (coordinate.y > 8|| coordinate.y < 4)
				{
					return false;
				}
				else
				{
					return true;
				}
			default: System.out.println("Invalid coordinate");
			return false;
			

			}
		}
		else if (this.getSize() == 7)
		{
			switch(coordinate.x){
			case 0:
				/** x coordinate is 0 */
				if (coordinate.y > 6 || coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 1:
				if (coordinate.y > 7|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 2:
				if (coordinate.y > 8|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 3:
				if (coordinate.y > 9|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 4:
				if (coordinate.y > 10|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 5:
				if (coordinate.y > 11|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 6:
				if (coordinate.y > 12|| coordinate.y < 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 7:
				if (coordinate.y > 12|| coordinate.y < 1)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 8:
				if (coordinate.y > 12|| coordinate.y < 2)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 9:
				if (coordinate.y > 12|| coordinate.y < 3)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 10:
				if (coordinate.y > 12|| coordinate.y < 4)
				{
					return false;
				}
				else
				{
					return true;
				}
			case 11:
			    if(coordinate.y >12 || coordinate.y < 5)
			    {
			        return false;
			    }
			    else
			    {
			        return true;
			    }
			case 12:
			    if(coordinate.y > 12 || coordinate.y < 6)
			    {
			        return false;
			    }
			    else
			    {
			        return true;
			    }
			default: System.out.println("Invalid coordinate");
			return false;
			}
		}
		else
		{	
			return false;
		}
	}
	
	public static void main(String args[]){
		
		
		LinkedList<Robot> listofrobots = new LinkedList<Robot>();
		listofrobots.add(new Robot(Robot.Type.SCOUT,"1"));
		listofrobots.add(new Robot(Robot.Type.SNIPER,"2"));
		listofrobots.add(new Robot(Robot.Type.TANK,"3"));
		Team t = new Team(listofrobots,Team.TeamColor.RED,1);
		List<Team> listofteams = new LinkedList<Team>();
		listofteams.add(t);
		GameBoard g = new GameBoard(listofteams);
		listofrobots = g.getAllRobots();
		System.out.println(listofrobots.get(0).getRobotStringID()+ " " + listofrobots.get(1).getRobotStringID() + " " 
		+ listofrobots.get(2).getRobotStringID());
		
		
		/**Testing isValid on a gameboard with 3 teams*/
		List<Team> T = new LinkedList<Team>();
		Team a = new Team(listofrobots,Team.TeamColor.RED,1);
		T.add(a);
		Team b = new Team(listofrobots,Team.TeamColor.RED,2);
		T.add(b);
		Team cc = new Team(listofrobots,Team.TeamColor.RED,3);
		T.add(cc);
		GameBoard GB = new GameBoard(T);
		BoardPosition c = new BoardPosition (4,4, 0);
		assert(GB.isValid(c) == true);
		c = new BoardPosition (4,11, 0);
		assert(GB.isValid(c) == false);
		/** Testing isValid on a gameboard with 6 teams */
		Team d = new Team(listofrobots,Team.TeamColor.RED,4);
		T.add(d);
		Team e = new Team(listofrobots,Team.TeamColor.RED,5);
		T.add(e);
		Team f = new Team(listofrobots,Team.TeamColor.RED,6);
		T.add(f);
		GB = new GameBoard(T);
		c = new BoardPosition (6,10, 0);
		assert(GB.isValid(c) == true);
		c = new BoardPosition (7,13, 0);
		assert(GB.isValid(c) == false);
		
//		/**Testing updateImmediateStatistics and getImmediateStatistics */
		
		Robot scout = new Robot(Robot.Type.SCOUT, "terminator1"); 
		Robot sniper = new Robot(Robot.Type.SNIPER, "snoopy1");
		Robot tank = new Robot(Robot.Type.TANK, "deathClaw1");
		scout.setLocation(new BoardPosition(4,4, 0));
		sniper.setLocation(new BoardPosition(4,4, 0));
		tank.setLocation(new BoardPosition(4,4, 0));
		/** adding robots to a list */
		LinkedList<Robot> theList = new LinkedList<Robot>();
		theList.add(scout);
		theList.add(sniper);
		theList.add(tank);
		/** creating a team */
		Team team1 = new Team(theList, Team.TeamColor.RED, 1);
		
		Robot scout2 = new Robot(Robot.Type.SCOUT, "terminator2"); 
		Robot sniper2 = new Robot(Robot.Type.SNIPER, "snoopy2");
		Robot tank2 = new Robot(Robot.Type.TANK, "deathClaw2");
		scout2.setLocation(new BoardPosition(4,4, 0));
		sniper2.setLocation(new BoardPosition(4,4, 0));
		tank2.setLocation(new BoardPosition(4,4, 0));
		/** adding robots to a list */
		LinkedList<Robot> theList2 = new LinkedList<Robot>();
		theList.add(scout2);
		theList.add(sniper2);
		theList.add(tank2);
		/** creating a team */
		Team team2 = new Team(theList2, Team.TeamColor.RED, 2);
		
		Robot scout3 = new Robot(Robot.Type.SCOUT, "terminator3"); 
		Robot sniper3 = new Robot(Robot.Type.SNIPER, "snoopy3");
		Robot tank3 = new Robot(Robot.Type.TANK, "deathClaw3");
		scout3.setLocation(new BoardPosition(4,4, 0));
		sniper3.setLocation(new BoardPosition(4,4, 0));
		tank3.setLocation(new BoardPosition(4,4, 0));
		/** adding robots to a list */
		LinkedList<Robot> theList3 = new LinkedList<Robot>();
		theList3.add(scout3);
		theList3.add(sniper3);
		theList3.add(tank3);
		/** creating a team */
		Team team3 = new Team(theList3, Team.TeamColor.RED, 3);
		
		LinkedList<Team> teamList = new LinkedList<Team>();
		teamList.add(team1);
		teamList.add(team2);
		teamList.add(team3);
		
		GameBoard gBoard = new GameBoard(teamList);
		
		//System.out.println(gBoard.Statistics);
		
		gBoard.updateImmediateStatistics("deathClaw2", "wins", 3);
		gBoard.updateImmediateStatistics("deathClaw2", "wins", 2);
		gBoard.updateImmediateStatistics("terminator2", "absorbed", 9);
		//System.out.println(gBoard.Statistics);
		/** updateImmediateStatistics works so long as the name "RobotStringID" is never entered incorrectly" */
		
	}
	

}
