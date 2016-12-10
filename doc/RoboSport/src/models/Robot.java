package models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import interpreter.LiteralWord;
import interpreter.Script;

/**
 * 
 * @author Parker
 *
 */
public class Robot implements Cloneable {

	private Type robotType;
	private int range;
	private int health;
	private int attack;
	private int movement;
	private int currentHealth;
	//protected int robotID;
	private String robotStringID;
	private Team robotTeam;
	private boolean isAI;
	private boolean isDead;
	private Map<String, List<LiteralWord>> mailbox;
	private BoardPosition location;
	private Script script;

	/**
	 * USE THIS CONSTRUCTOR for Pc Robots, ABOVE CONSTRUCTOR IS FOR TESTING ONLY
	 * 
	 * @param range
	 * @param health
	 * @param attack
	 * @param movement
	 * @param robotStringID
	 */
	public Robot(Type rType, String robotStringID) {
	    this.robotType=rType;
		switch (rType) {
		case SCOUT:
			this.range = 3;
			this.health = 1;
			this.movement = 3;
			this.attack = 1;
			break;
		case SNIPER:
			this.range = 2;
			this.health = 2;
			this.movement = 2;
			this.attack = 2;
			break;
		case TANK:
			this.range = 1;
			this.health = 3;
			this.movement = 1;
			this.attack = 3;
			break;
		default:
			this.range = 1;
			this.health = 1;
			this.movement = 1;
			this.attack = 1;
			break;
		};
		this.currentHealth = health;
		this.robotStringID = robotStringID;
		this.isAI = false;
		this.isDead = false;

		this.mailbox = new HashMap<String, List<LiteralWord>>();
		this.location = new BoardPosition();
	}

	public String getRobotStringID() {
		return robotStringID;
	}

	public void setRobotStringID(String robotStringID) {
		this.robotStringID = robotStringID;
	}
	
	public Team getTeam() {
	    return this.robotTeam;
	}
	
	public void setTeam(Team t) {
	    this.robotTeam=t;
	}

	/**
	 * Constructor for AI (script) controlled Robot
	 * 
	 * @param range
	 * @param health
	 * @param attack
	 * @param robotID
	 * @param variables
	 */
	public Robot(Type rType, String robotStringID, Script script) {
	    this.robotType = rType;
		switch (rType) {
		case SCOUT:
			this.range = 3;
			this.health = 1;
			this.movement = 3;
			this.attack = 1;
			break;
		case SNIPER:
			this.range = 2;
			this.health = 2;
			this.movement = 2;
			this.attack = 2;
			break;
		case TANK:
			this.range = 1;
			this.health = 3;
			this.movement = 1;
			this.attack = 3;
			break;
		default:
			this.range = 1;
			this.health = 1;
			this.movement = 1;
			this.attack = 1;
			break;
		};
		this.currentHealth = health;
		this.robotStringID = robotStringID;
		this.isAI = true;
		this.isDead = false;
		this.script = script;

		this.mailbox = new HashMap<String, List<LiteralWord>>();
		this.location = new BoardPosition();
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	/**
	 * Getter for the Range variable
	 * 
	 * @return the range of Robot
	 */
	public int getRange() {
		return range;
	}

	/**
	 * Getter for the Health variable
	 * 
	 * @return the health of the Robot
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Getter for the attack strength of the Robot
	 * 
	 * @return the attack of the Robot
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Getter for the current health of the Robot
	 * 
	 * @return the current health of the Robot
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}

	/**
	 * Setter for the current health of the Robot
	 * 
	 * @param currentHealth
	 *            the new health for the Robot to have (will be used after the
	 *            Robot has taken damage)
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	/**
	 * Getter for the Mailbox of the Robot
	 * 
	 * @return the Robot's mailbox
	 */
	public Map<String, List<LiteralWord>> getMailbox() {
		return mailbox;
	}

	/**
	 * Setter for the Robot's mailbox
	 * 
	 * @param mailbox
	 *            the mailbox for the robot to now posses
	 */
	public void setMailbox(Map<String, List<LiteralWord>>  mailbox) {
		this.mailbox = mailbox;
	}
	
	/**
	 * 
	 * @param from : name of sender robot
	 * @param value : message the robot sent.
	 */
	public void addMessage(String from, LiteralWord value){
		if (!this.mailbox.containsKey(from)){
			this.mailbox.put(from, new LinkedList<LiteralWord>());
		}
		this.mailbox.get(from).add(value);
	}
	
	public LiteralWord getMessage(String from){
		if (!this.mailbox.containsKey(from)){
			return null;
		}
		return this.mailbox.get(from).remove(this.mailbox.get(from).size()-1);
	}

	/**
	 * Getter for the location of the Robot
	 * 
	 * @return the BoardPosition entity of the Robot
	 */
	public BoardPosition getLocation() {
		return location;
	}

	/**
	 * Setter for the location of the Robot
	 * 
	 * @param location
	 *            the BoardPosition the Robot now has
	 */
	public void setLocation(BoardPosition location) {
		this.location = location;
	}

	/**
	 * Method to check if a Robot is Script controlled or not
	 * 
	 * @return boolean value for if it an AI or not
	 */
	public boolean isAI() {
		return isAI;
	}

	/**
	 * Getter for the Script of the Robot
	 * 
	 * @return the script of the Robot
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * getter for Movement variable
	 * 
	 * @return Movement variable
	 */
	public int getMovement() {
		return movement;
	}

	/**
	 * setter for Movement variable
	 * 
	 * @param movement
	 *            the number for movement to now equal
	 */
	public void setMovement(int movement) {
		this.movement = movement;
	}

	/**
	 * method to reduce currentHealth to robot
	 * 
	 * @param damage
	 *            how much you want the robots health to go down
	 */
	public void takeDamage(int damage) {
		currentHealth -= damage;
		if (currentHealth <= 0) {
			setDead(true);
		}
	}

	/**
	 * @return the type of the robot (scout, tank, sniper)
	 */
	public Type getType() {
		return this.robotType;
	}

	/**
	 * brief toString() of Robot
	 */
	public String toString() {
		return robotStringID;
	}
	
	
	@Override
	public Robot clone() {
	    try {
            Robot r = (Robot) super.clone();
            r.location=r.location.clone();
            // Clone mailbox?
            r.mailbox = new HashMap<String, List<LiteralWord>>();
            r.mailbox.putAll(this.mailbox);
            return r;
        } catch (CloneNotSupportedException e) {
            return null;
        }
	}

	public enum Type {
		SCOUT, SNIPER, TANK;
		public String toString() {
			switch (this) {
			case SCOUT:
				return "SCOUT";
			case SNIPER:
				return "SNIPER";
			case TANK:
				return "TANK";
			default:
				return "???";
			}
		};
	}

}
