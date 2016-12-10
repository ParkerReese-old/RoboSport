package controller;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.GameBoard;
import models.Robot;
import models.Team;
import view.GameDisplayer;
import view.GameDrawDisplay;

/**
 * Referee keeps controll of the game by ordering the robots play order as well as
 * doing actions based on what the Robot wants to do (like quit)
 * 
 * 
 *
 */
public class Referee {

    private GameBoard gameboard;
    private int drawCounter;
    // RECALL, a "turn" is.... TODO - Define Turn/Round.
    // A "Peaceful Turn" is a turn where no damage occurs..
    public static final int MAX_CONSECUTIVE_UNDAMAGED_TURNS = 20;
    
    public RobotController current;
    public boolean isPC;

    /**
     * Constructor for Referee
     */
    public Referee(GameBoard gameboard){
        this.gameboard = gameboard;
        this.drawCounter = 0;
    }

    /**
     * sets gameboard
     * @param gameboard to be set
     */
    public void setGameboard(GameBoard gameboard) {
        this.gameboard = gameboard;
    }

    /**
     * method to recieve the gameboard being held by the Referee
     * @return the gameboard held by the Referee
     */
    public GameBoard getGameBoard(){
        return gameboard;
    }
    
    /**
     * Method to recieve the draw counter held by the Referee.
     * @return the gameboard held by the Referee.
     */
    public int getDrawCounter(){
        return drawCounter;
    }
    
    /**
     * Sets drawCounter
     * @param The draw counter to be set..
     */
    public void setDrawCounter(int drawCounter){
        this.drawCounter = drawCounter;
    }

    /**
     * method will begin the playing of the game, allowing red team to begin their first round
     * @param gameBoard the gameboard (holding all team, robots, etc) that will be played on
     */
    public void startGame(GameBoard gameBoard){
        List<Team> teams = new LinkedList<Team>();
        teams = gameBoard.getTeams();
        int teamn = teams.size();
        
        HashMap<Robot, RobotController> controllers = new HashMap<Robot,RobotController>();
        // Initialize the controllers
        for (Team t : teams)
            for (Robot r : t.getRobots()) {
                if (r.isAI())
                    controllers.put(r, new NpcController(this,r));
                else
                    controllers.put(r, new PcController(this,r));
            }

        int[] indeces = new int[teamn];
        int teamsdone = 0;
        while (teamsdone<teamn-1) {
            teamsdone=0;
            for (int i=0; i<teamn; i++)
                indeces[i]=0;
            
            for (int index=0; index<3; index++) {
                for (int t=0; t<teamn; t++) {
                    Team tm = teams.get(t);
                    Robot r;
                    if (indeces[t]<3 && !(r=tm.getRobots().get(indeces[t]++)).isDead())
                        controllers.get(r).play();
                    else if (indeces[t]<3 && !(r=tm.getRobots().get(indeces[t]++)).isDead())
                        controllers.get(r).play();
                    else if (indeces[t]<3 && !(r=tm.getRobots().get(indeces[t]++)).isDead())
                        controllers.get(r).play();
                    else if (index==0)
                        teamsdone++;
                }
            }
        }
        
    }

    /**
     * stops the game in progress completely
     */
    public void abandonGame(){
        System.exit(1);
    }

    /**
     * calls the GameDisplayer entity to update the game display
     */
    public void updateGameBoard(){
        GameDisplayer.redraw(current.getMyRobot());
    }

    /**
     * removes given team from the game while the other teams continue on
     * @param team the team that wishes to leave the game
     */
    public void teamQuit(Team team){
        gameboard.removeTeam(team);
    }
    
    /**
     * Increases the draw counter by one.
     */
    public void incrementDraw(){
        setDrawCounter(getDrawCounter()+1);
    }
    
    /**
     * Resets the draw counter to zero.
     */
    public void resetDraw(){
        setDrawCounter(0);
    }
    
    /**
     * checkAndCallDraw() if draw counter has reached the maximum consecutive undamaged turns,
     * referee ends the game considering it a "Draw". Statistics are kept, but all teams suffer
     * a loss. 
     */
    public void checkAndCallDraw(){
        if (getDrawCounter() > MAX_CONSECUTIVE_UNDAMAGED_TURNS){
            throw new RuntimeException("Game should have ended ages ago! Draw wasn't called correctly.");
        }
        else if (getDrawCounter() == MAX_CONSECUTIVE_UNDAMAGED_TURNS){
            
            // All robots in the game suffer a loss in the statistics.
            for (int i=0; i < getGameBoard().getAllRobots().size() ; i = i+1){
                getGameBoard().updateImmediateStatistics(getGameBoard().getAllRobots().get(i).
                        getRobotStringID(), "losses", 1);   
            }
            GameDrawDisplay drawOccured = new GameDrawDisplay();
            // TODO: ADD abandonGame here.
        }
    }

    /** main method used for testing purposes */
    public static void main(String args[]) {
        /** TESTING BEGINS */
        // Some intialization....
        LinkedList<Robot> listofrobots = new LinkedList<Robot>();
        listofrobots.add(new Robot(Robot.Type.SCOUT,"1"));
        listofrobots.add(new Robot(Robot.Type.SNIPER,"2"));
        listofrobots.add(new Robot(Robot.Type.TANK,"3"));
        Team t = new Team(listofrobots,Team.TeamColor.RED,1);
        List<Team> listofteams = new LinkedList<Team>();
        listofteams.add(t);
            
        GameBoard testGameBoard = new GameBoard(listofteams);
        Referee testRef = new Referee(testGameBoard);
        
        // Testing incrementDraw() and resetDraw();
        testRef.incrementDraw();// Draw Counter should be 1.
        assert(testRef.getDrawCounter() == 1);
        testRef.incrementDraw();// Draw Counter should be 2.
        assert(testRef.getDrawCounter() == 2);
        testRef.resetDraw();    // Draw Counter now 0.
        assert(testRef.getDrawCounter() == 0);
        
        // Testing callDraw() for all cases.
        testRef.setDrawCounter(19); // Draw Counter = 19.
        testRef.checkAndCallDraw(); // Nothing should happen.

        testRef.incrementDraw();    // Draw Counter = 20.
        testRef.checkAndCallDraw(); // Draw should occur.
        assert(testRef.getGameBoard().getImmediateStatistics().toString() == 
                "{1={wins=0, executions=0, distance=0, lived=0, died=0, losses=1, "
                + "matches=0, killed=0, absorbed=0}, 2={wins=0, executions=0, distance=0, "
                + "lived=0, died=0, losses=1, matches=0, killed=0, absorbed=0}, 3={wins=0, "
                + "executions=0, distance=0, lived=0, died=0, losses=1, matches=0, "
                + "killed=0, absorbed=0}}");
        

    //  testRef.incrementDraw();    // Draw Counter = 21.
    //  testRef.checkAndCallDraw(); // RuntimeException thrown.
                                    // game successfully abandoned?
        
        /** TESTING ENDS */
    }
}
