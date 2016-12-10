package controller;

import java.util.List;

import interpreter.ForthException;
import interpreter.ForthInterpreter;
import interpreter.ForthRuntimeException;
import interpreter.LiteralWord;
import interpreter.Word;
import models.BoardPosition;
import models.Robot;

/**
 * 
 * @author
 *
 */
public class NpcController extends RobotController {

    protected ForthInterpreter fi;
    protected List<Robot> roboList;

    public NpcController(Referee referee, Robot robot) {
        super(referee, robot);

        this.fi = new ForthInterpreter();
        try {
            // Define Status Words
            this.fi.words.put("health", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(myRobot.getHealth()));
                }
            });
            this.fi.words.put("healthLeft", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(myRobot.getCurrentHealth()));
                }
            });
            this.fi.words.put("moves", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(myRobot.getMovement()));
                }
            });
            this.fi.words.put("movesLeft", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(movesLeft));
                }
            });
            this.fi.words.put("attack", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(myRobot.getAttack()));
                }
            });
            this.fi.words.put("range", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(myRobot.getRange()));
                }
            });
            this.fi.words.put("team", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(myRobot.getTeam().getColor().toString()));

                }
            });
            this.fi.words.put("type", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    runOn.push(new LiteralWord(myRobot.getType().toString()));
                }
            });

            // Action Words
            this.fi.words.put("turn!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    LiteralWord amt = runOn.pop();
                    if (amt.isInt() && amt.getInt() <= 5 && amt.getInt() >= 0)
                        myRobot.getLocation().turn(amt.getInt());
                    else
                        throw new ForthRuntimeException("turn! variable is not valid");
                }
            });
            this.fi.words.put("move!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    if (movesLeft > 0) {
                        advance();
                    }
                }
            });

            this.fi.words.put("shoot!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    LiteralWord ir = runOn.pop(); // range
                    LiteralWord id = runOn.pop(); // direction
                    if (id.isInt() && id.getInt() >= 0 && id.getInt() <= 5 && ir.isInt()
                            && ir.getInt() >= 0 && ir.getInt() <= myRobot.getRange()) {
                        shoot(ir.getInt(), id.getInt());
                    } else
                        throw new ForthRuntimeException("shoot! variables are not valid");

                }
            });

            // Checking the board
            this.fi.words.put("check!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    LiteralWord dir = runOn.pop();
                    if (dir.isInt() && dir.getInt() >= 0 && dir.getInt() <= 5) {

                        BoardPosition toCheck = myRobot.getLocation().getRelative(dir.getInt(), 1);

                        boolean valid = false;
                        valid = myReferee.getGameBoard().isValid(toCheck);

                        boolean occupied = false;
                        for (Robot r : myReferee.getGameBoard().getAllRobots())
                            occupied = occupied | r.getLocation().equals(toCheck);

                        String ret;
                        // Check if out of bounds
                        if (!valid)
                            ret = "OUT OF BOUNDS";
                        else if (occupied)
                            ret = "OCCUPIED";
                        else
                            ret = "EMPTY";
                        runOn.push(new LiteralWord(ret));
                    } else
                        throw new ForthRuntimeException("Direction is not valid");

                }
            });

            this.fi.words.put("scan!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    roboList = scan();
                    runOn.push(new LiteralWord(roboList.size()));
                }
            });

            this.fi.words.put("identify!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    LiteralWord target = runOn.pop();

                    if (!target.isInt() | target.getInt() < 0
                            | target.getInt() >= roboList.size()) {
                        throw new ForthRuntimeException("identify! argument invalid");
                    }

                    Robot r = roboList.get(target.getInt());

                    runOn.push(new LiteralWord(r.getTeam().getTeamID())); // Team ID.
                    runOn.push(new LiteralWord(myRobot.getLocation().distanceTo(r.getLocation()))); // Range
                    runOn.push(new LiteralWord(myRobot.getLocation().directionTo(r.getLocation())));// Direction
                    runOn.push(new LiteralWord(r.getCurrentHealth()));// Remaining Health.
                }
            });


            this.fi.words.put("send!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    LiteralWord value = runOn.pop();
                    LiteralWord teamMember = runOn.pop();

                    if (!teamMember.isString()) {
                        throw new ForthRuntimeException("send! teamMember is invalid.");
                    }

                    for (Robot r : myReferee.getGameBoard().getAllRobots()) {
                        if (r.getRobotStringID().equals(teamMember.getString())) {
                            send(r, value);
                            break;
                        }
                    }
                }
            });

            this.fi.words.put("mesg?", new Word() {

                @Override
                public void exec(ForthInterpreter runOn) {
                    LiteralWord s = runOn.pop();
                    if (myRobot.getMailbox().containsKey(s.getString())) {
                        if (myRobot.getMailbox().get(s.getString()).isEmpty()) {
                            runOn.push(new LiteralWord(false));
                        } else {
                            runOn.push(new LiteralWord(true));
                        }
                    } else {
                        runOn.push(new LiteralWord(true));
                    }


                }

            });

            this.fi.words.put("recv!", new Word() {
                @Override
                public void exec(ForthInterpreter runOn) {
                    LiteralWord s = runOn.pop();
                    if (myRobot.getMailbox().containsKey(s.getString())) {
                        if (myRobot.getMailbox().get(s.getString()).isEmpty()) {
                            runOn.push(new LiteralWord(false));
                        } else {
                            LiteralWord mes = myRobot.getMessage(s.getString());
                            runOn.push(mes);
                        }
                    } else {
                        runOn.push(new LiteralWord(false));
                    }
                }
            });

            this.fi.exec(robot.getScript());
            
        } catch (ForthException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void play() {
        super.play();
        roboList = null;
        try {
            this.fi.exec("play");
        } catch (Exception e) {
            System.err.println("AI Script Failed: "+e.getMessage());
        }
    }

    /** main method used for testing purposes */
    public static void main(String args[]) {
        /** TESTING BEGINS */
        /** TESTING ENDS */
    }
}
