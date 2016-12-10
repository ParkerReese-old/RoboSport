package controller;
import java.util.LinkedList;
import java.util.List;

import interpreter.LiteralWord;
import models.BoardPosition;
import models.GameBoard;
import models.Robot;
import models.Team;
import view.GameDisplayer;
/**
 * 
 * @author Parker. Who is the coolest guy you know.
 *
 */
public abstract class RobotController {
	/** each robot controller has a referee that watches it (all the same Referee). When RobotController does certain actions
	    the referee will need to do certain actions, the referee is also the gateway for RobotController 
	    to access the GameBoard */
	Referee myReferee;
	/** Robot that the RobotController is currently doing actions on */
	Robot myRobot;
	
	/** The number of moves remaining for the robot */
	protected int movesLeft;
	protected boolean hasAttacked;
    public boolean done;
	
	/**
	 * Constructor for RobotController
	 * @param referee the referee for this controller
	 * @param robot the robot this controller controlls
	 */
	public RobotController(Referee referee, Robot robot){
		this.myReferee = referee;
		this.myRobot = robot;
	}
	
	/**
	 * getter for myReferee
	 * @return the Referee for RobotController
	 */
	public Referee getMyReferee() {
		return myReferee;
	}

	/**
	 * setter for myReferee (should probably not be used)
	 * @param myReferee the new Referee we want thisRobotController under
	 */
	public void setMyReferee(Referee myReferee) {
		this.myReferee = myReferee;
	}

	/**
	 * getter for myRobot
	 * @return the Robot this RobotController is controlling
	 */
	public Robot getMyRobot() {
		return myRobot;
	}

	/**
	 * setter for myRobot (should porbably not be used, Robot is passed during construction)
	 * @param myRobot the new Robot we want this RobotController controlling
	 */
	public void setMyRobot(Robot myRobot) {
		this.myRobot = myRobot;
	}
	
	/**
	 * this method is called when it is the specific Robot's turn to play
	 * all actions are called inside play
	 */
	public void play() {
	    myReferee.current=this;
	    movesLeft = myRobot.getMovement();
	    hasAttacked = false;
	    GameDisplayer.redraw(myRobot);
	}
	
	/**
	 * this will call the Referee to remove the team that myRobot is on from the GameBoard
	 */
	public void quit(){
		List<Team> teams = new LinkedList<Team>();
		teams = myReferee.getGameBoard().getTeams();
		
		for(int i = 0; i < teams.size(); i++){
			if (teams.get(i).getRobots().contains(myRobot)){
				myReferee.teamQuit(teams.get(i));
			}
		}
	}
	
	/**
	 * used to see what robots, both enemy and team, are within the robots sight range
	 * The robots that are in the range are then displayed on the board.
	 * @return a LinkedList of Robots that are within the scan range of the myRobot
	 */
	public List<Robot> scan(){
		LinkedList<Robot> robotsOnGameBoard = new LinkedList<Robot>();
		LinkedList<Robot> robotsInRange = new LinkedList<Robot>();
		
		robotsOnGameBoard = myReferee.getGameBoard().getAllRobots();
		
		for(int i = 0; i < robotsOnGameBoard.size(); i++){
			if(robotsOnGameBoard.get(i).getLocation().distanceTo(myRobot.getLocation()) <= myRobot.getRange()){
				robotsInRange.add(robotsOnGameBoard.get(i));
			}
		}
		return robotsInRange;
	}
	
	/**
	 * method used to shoot a space. Should anything be on the space indicated, it will take damage.
	 * @param distance the distance from myRobot wished to shoot
	 * @param direction the immediateDirection (0-5) from myRobot wished to shoot
	 */
	public void shoot(int distance, int direction){
		if (distance <= myRobot.getRange() && !hasAttacked){
			/** loop through all Robots on the gameboard, looking at their Coordinate. If their Coordinate
			 * is equal to the Coordinate we are shooting, run the takeDamage() method on that Robot
			 */
			for(Robot r : myReferee.getGameBoard().getAllRobots()){
				
				if((myRobot.getLocation().getRelative(direction, distance).equals(r.getLocation())))
				{
					r.takeDamage(myRobot.getAttack());
				}
			}
			hasAttacked=true;
			
		}
		else{
			throw new RuntimeException("that shot is invalid");
		}
	}
	
	public void turn(int direction) {
	    if (direction>=0 && direction<6)
	        myRobot.getLocation().turn(direction);
	}
	
	public void advance() {
	    if (movesLeft>0) {
	        myRobot.getLocation().advance();
	        movesLeft--;
	    }
	}
	
	/**
	 * Used by NpcRobots to communicate to one another
	 * @param otherRobot the Robot that you wish to send the message to
	 * @param value a string containing information about other Robots or events that have happened
	 */
	public void send(Robot otherRobot, LiteralWord value){
		otherRobot.addMessage(myRobot.getRobotStringID(), value);
	}
	
	/** main method used for testing purposes */
	public static void main(String args[]){
		/** Robot creation is Range, Health, Attack, Movement, RobotID. */
			Robot scout = new Robot(Robot.Type.SCOUT, "1"); 
			Robot sniper = new Robot(Robot.Type.SNIPER, "2");
			Robot tank = new Robot(Robot.Type.TANK, "3");
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
			
			Robot scout2 = new Robot(Robot.Type.SCOUT, "1"); 
            Robot sniper2 = new Robot(Robot.Type.SNIPER, "2");
            Robot tank2 = new Robot(Robot.Type.TANK, "3");
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
			
			Robot scout3 = new Robot(Robot.Type.SCOUT, "1"); 
            Robot sniper3 = new Robot(Robot.Type.SNIPER, "2");
            Robot tank3 = new Robot(Robot.Type.TANK, "3");
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
			/** creating a gameboard with created Robots and teams */
			GameBoard gameboard = new GameBoard(teamList);
			/**creating a Referee given the gameboard */
			Referee ref = new Referee(gameboard);
			/** creating a RobotController */
			Robot robotScout = new Robot(Robot.Type.SCOUT, "70");
			robotScout.setLocation(new BoardPosition(4,4, 0));
			
			RobotController RC = new PcController(ref, robotScout);
			
			/** mock Ref and Robot that are needed by the RobotController tests are made */
			
			/** testing of Scan() */
			LinkedList<Robot> testerList = new LinkedList<Robot>();
			testerList.addAll(theList);
			testerList.addAll(theList2);
			testerList.addAll(theList3);

			assert(testerList.equals(RC.scan()));
			
			/** moving a robots location out of range */
			tank2.setLocation(new BoardPosition(4,8, 0));
			testerList.remove(tank2);
			assert(testerList.equals(RC.scan()));
			/** on edge of range */
			tank.setLocation(new BoardPosition(4,7, 0));
			assert(testerList.equals(RC.scan()));
			/** stll in range */
			tank3.setLocation(new BoardPosition(5,6, 0));
			assert(testerList.equals(RC.scan()));
			/** out of range */
			sniper.setLocation(new BoardPosition(6,8, 0));
			testerList.remove(sniper);
			assert(testerList.equals(RC.scan()));
			/** out of range */
			sniper2.setLocation(new BoardPosition(0,0, 0));
			testerList.remove(sniper2);
			assert(testerList.equals(RC.scan()));
			/** still in range */
			sniper3.setLocation(new BoardPosition(3,4, 0));
			assert(testerList.equals(RC.scan()));
			/** ends testing of scan() */
			
			/** testing for shoot() */
			RC.shoot(0, 0);
			assert(scout.getCurrentHealth() == 0);
			assert(scout2.getCurrentHealth() == 0);
			assert(scout3.getCurrentHealth() == 0);
			
			RC.hasAttacked=false;
			RC.shoot(3,2);
			assert(tank.getCurrentHealth() == 3); // Should not have hit
			
			RC.hasAttacked=false;
			RC.shoot(1, 3);
			assert(sniper3.getCurrentHealth() == 1);
			/** ends testing of shoot() */
			
			/**testing for quit() */
			RC.setMyRobot(tank);
			RC.quit();
			/** team1 should now be removed from GameBaord, wont work untill Referee has a working TeamQuit() method*/
			
			/** ends testing for quit() */
			
			/** testing of send()*/
			RC.setMyRobot(robotScout);
			LiteralWord message = new LiteralWord("thing");
			RC.send(tank, message);
			assert(tank.getMessage(RC.getMyRobot().getRobotStringID()).equals(message));
			/**ends testing for send() */
	}
}
