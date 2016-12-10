package models;

/**
 * BoardPosition is used to determine the location and facing orientation
 * @author Mostly Parker, but also Meagan :p
 * 
 */
public class BoardPosition implements Cloneable{

    protected int x, y;
    protected int orientationFromRight;
    static protected final int MAX_ROTATION = 5;

    /**
     * Default Constructor for BoardPosition
     */
    public BoardPosition() {
        this.x = 0;
        this.y = 0;
        this.orientationFromRight = 0;
    }

    /**
     * Constructor for BoardPosition with values given
     * 
     * @param coordinate the coordinate location on hex grid
     * @param directionFacing the direction in which you are looking
     * @param orientationFromRight (0-5). 0 being to the right, which way are you looking
     */
    public BoardPosition(int x, int y, int orientationFromRight) {
        this.x = x;
        this.y = y;
        this.orientationFromRight = orientationFromRight;
    }

    /**
     * Getter for the orientationFromRightVariable
     * 
     * @return the orientation (0-5)
     */
    protected int getOrientationFromRight() {
        return orientationFromRight;
    }

    /**
     * Setter for OrientationFroRight variable
     * 
     * @param orientationFromRight new value for it to be
     */
    protected void setOrientationFromRight(int orientationFromRight) {
        this.orientationFromRight = orientationFromRight;
    }

    /**
     * Setter for both the x and y values (a coordinate)
     * @param x the new x value
     * @param y the new y value
     */
    private void setCoordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * method to add rotation for orientationFromRight variable
     * 
     * @param toAdd rotation to add to the current rotation
     */
    private void addOrientationToCurrent(int toAdd) {
        if ((orientationFromRight + toAdd) > MAX_ROTATION) {
            orientationFromRight = (orientationFromRight + toAdd) - (MAX_ROTATION+1);
        } else {
            orientationFromRight += toAdd;
        }
    }

    /**
     * method to minus orienation from num, used in getImmediate()
     * 
     * @param num the number to be subtracted from
     * @return num minus orientationFromRight to be used by
     */
    private int subtractOrientationFromNum(int num) {
        if ((num - orientationFromRight) < 0) {
            return (num - orientationFromRight) + (MAX_ROTATION+1);
        } else {
            return (num - orientationFromRight);
        }
    }

    /**
     * Turns the robot in the given rotation
     * 
     * @param direction the direction of rototion to turn the robot given its current rotation
     */
    public void turn(int direction) {
        if(direction > 5 || direction < 0){
            throw new RuntimeException();
        }
        addOrientationToCurrent(direction);
    }

    /**
     * used to move the BoardPosition one space in the direction facing
     */
    public void advance() {
        /* could re-write as a switch statement if wanted */
        /* if currently facing to the east */
        if (orientationFromRight == 0) {
            this.x += 1;

        }
        /* if currently facing south-east */
        else if (orientationFromRight == 1) {
            this.x += 1;
            y += 1;

        }
        /* if currently facing north-east */
        else if (orientationFromRight == 5) {
            y -= 1;

        }
        /* if currently facing south-west */
        else if (orientationFromRight == 2) {
            y += 1;

        }
        /* if currently facing west */
        else if (orientationFromRight == 3) {
            x -= 1;

        }
        /* if currently facing north-west */
        else if (orientationFromRight == 4) {
            x -= 1;
            y -= 1;
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Method that returns the direction a robot must go in order to get to space "other"
     * 
     * @param other the position the robot is trying to get to
     * @return the direction of the robot in regards to get to position other
     */
    public int directionTo(BoardPosition other) {
        int originalOrientation = this.orientationFromRight; 

        BoardPosition tracker = this.clone();
        int distance = distanceTo(other);
        int sum = 0;

        for (int i = 0; i < distance; i++) {
            sum += tracker.immediateDirection(other);
            tracker.turn(tracker.immediateDirection(other));
            tracker.advance();
            tracker.orientationFromRight = originalOrientation;

        }

        return sum;
    }

    /**
     * Method that returns the distance between the robots current position and other
     * 
     * @param other the position the robot is trying to get to
     * @return the distance between the robots position and other
     */
    public int distanceTo(BoardPosition other) {
        
        return Math.max(
                Math.abs(other.y - this.y),     
                Math.max(Math.abs(other.x - this.x),
                Math.abs((other.x - other.y)*-1 - (this.x - this.y)*-1))
           );
    }

    /**
     * Method that returns the immediate direction the robot must be facing in order to get to other
     * 
     * @param other the space a robot is trying to get to
     * @return the immediate direction the robot must face
     */
    public int immediateDirection(BoardPosition other) {

        /* other lies on the same space as current */
        if (x == other.x && y == other.y) {
            return 0;
        }
        /* other lies on line going north-east */
        else if (x == other.x && y > other.y) {
            return 5 - orientationFromRight;
        }
        /* other lies on line going south-west */
        else if (x == other.x && y < other.y) {
            return subtractOrientationFromNum(2);
        }
        /* other lies on line going east */
        else if (y == other.y && x < other.x) {
            return subtractOrientationFromNum(0);
        }
        /* other lies on line going west */
        else if (y == other.y && x > other.x) {
            return subtractOrientationFromNum(3);
        }
        /* other lies on line going north-west */
        else if (((x - other.x) == (y - other.y))
                && x > other.x) {
            return subtractOrientationFromNum(4);
        }
        /* other lies on line going south-east */
        else if ((x - other.x) == (y - other.y)
                && x < other.x) {
            return subtractOrientationFromNum(1);
        }
        /* other lies inbetween west and south-west line */
        else if ((x > other.x) && (y < other.y)) {
            return subtractOrientationFromNum(3);
        }
        /* other lies inbetween east and north-east line */
        else if ((x < other.x) && (y > other.y)) {
            return Math.abs(orientationFromRight - 6);
        }
        /* other lies to the left of north-east line and above west line */
        else if (x > other.x) {
            return subtractOrientationFromNum(4);
        }
        /* other lies to the right of south-west line and below east line */
        else if (x < other.x) {
            return subtractOrientationFromNum(1);
        }
        /* method failed */
        else {
            throw new RuntimeException();
        }
    }

    /**
     * Method that returns a new BoardPosition relative to the given direction and distance
     * 
     * @param direction the direction the robot wants to move in
     * @param distance the distance the robot wants to move
     * @return a BoardPosition that is relative to the given direction and distance
     */
    public BoardPosition getRelative(int direction, int distance) {
        if(direction >= 6*distance && distance != 0){
            throw new RuntimeException("impossible direction given distance");
        }
        
        BoardPosition tracker = clone();
        int originalOrientation = tracker.orientationFromRight;
        
        while(distance > 0){

            for(int i = 5; i >=0; i--){
                if(Math.abs(i*distance - direction) <= (distance-1)){
                    tracker.turn(i);
                    tracker.advance();
                    tracker.setOrientationFromRight(originalOrientation);
                    distance -= 1;
                    direction -= i;
                    break;
                }
            }
        }
        

        return tracker;

    }
    
    /**
     * @return the x coordinate of the position
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * @return the y coordinate of the position
     */
    public int getY() {
        return this.y;
    }

    /**
     * returns all value of entity as a string
     */
    public String toString() {
        return "coordinate:" + x + "," + y + " DirectionFacing:"
                + orientationFromRight;
    }


    @Override
    /**
     * Clone method used to clone the BoardPosition 
     */
    public BoardPosition clone(){
        try {
            return (BoardPosition) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    /**
     * quicker way to check the cooridnate of two BoardPosition are identicals
     * @param other the BoardPostion we are comparing coordinate values to
     * @return if the x's and y's matched
     */
    public boolean equals(BoardPosition other){
        if(this.x == other.x && this.y == other.y){
            return true;
        }
        return false;  
    }

    /** Main method is used for all tests of BoardPosition */
    /**
     * turn(), advance(), distanceTo(), immediateDirection(), RelativeTo() have passed tests (so
     * far)
     */
    public static void main(String args[]) {

        /**
         * Testing of turn() method Initial hard coding of position and facing position
         */
        BoardPosition BP = new BoardPosition();
        BP.setCoordinate(4, 4);
        BP.orientationFromRight = 0;
        /* this will make coordinate (5,4) our '0' direction */
        /* rotate in the '5' direction */
        BP.turn(5);
        assert (BP.orientationFromRight == 5);
        /* Our new facingDirection coordinate should now be 5, which is north-east */

        /* tests conintued for all valid turning options */
        BP.turn(0);
        assert (BP.orientationFromRight == 5);
        BP.turn(1);
        assert (BP.orientationFromRight == 0);
        BP.turn(2);
        assert (BP.orientationFromRight == 2);
        BP.turn(3);
        assert (BP.orientationFromRight == 5);
        BP.turn(4);
        assert (BP.orientationFromRight == 3);
        BP.turn(5);
        assert (BP.orientationFromRight == 2);

        /* Testing of non-valid inputs for turn() */
        try {
            BP.turn(-1);
        } catch (RuntimeException e) {
            
        }

        try {
            BP.turn(6);
        } catch (RuntimeException e) {
        }
        /* Ends testing for turn */

        /* Testing of advance() method */

        /* reset position and direction */
        BP.setCoordinate(4, 4);
        BP.setOrientationFromRight(0);
        /* after moving ahead one, coordinate and directionFacing should change */
        BP.advance();
        assert (BP.x == 5 && BP.y == 4);

        BP.turn(1);
        BP.advance();
        assert (BP.x == 6 && BP.y == 5);

        BP.advance();
        BP.advance();
        assert (BP.x == 8 && BP.y == 7);

        BP.turn(1);
        BP.advance();
        assert (BP.x == 8 && BP.y == 8);
        /* End of advance() test */

        /* Testing of immediateDirection(BoardPosition other) : int */

        BP.setCoordinate(4, 4);
        BP.orientationFromRight = 0;
        BoardPosition other = new BoardPosition(4, 1, 0);

        assert (5 == BP.immediateDirection(other));

        /* in this case two returns are valid */
        other.setCoordinate(3, 1);
        assert ((5 == BP.immediateDirection(other)) || (4 == BP.immediateDirection(other)));

        other.setCoordinate(3, 5);
        assert ((2 == BP.immediateDirection(other)) || (3 == BP.immediateDirection(other)));

        other.setCoordinate(4, 4);
        assert (0 == BP.immediateDirection(other));

        other.setCoordinate(6, 8);
        assert ((1 == BP.immediateDirection(other)) || (2 == BP.immediateDirection(other)));
        /* Ends testing for immediateDirection() */

        /* testing for distanceTo(BoardPosition other) : int */
        BP.setCoordinate(4, 4);
        other.setCoordinate(4, 3);
        assert (BP.distanceTo(other) == 1);

        other.setCoordinate(3, 5);
        assert (BP.distanceTo(other) == 2);

        other.setCoordinate(4, 4);
        assert (BP.distanceTo(other) == 0);

        other.setCoordinate(3, 2);
        assert (BP.distanceTo(other) == 2);

        other.setCoordinate(5, 2);
        assert (BP.distanceTo(other) == 3);

        other.setCoordinate(8, 4);
        assert (BP.distanceTo(other) == 4);

        other.setCoordinate(4, 5);
        assert (BP.distanceTo(other) == 1);
        /* Ends testing for distanceTo */

        /* Testing for getRelative(int direction, int distance) : BoardPosition */
        BP.setCoordinate(8, 4);
        BP.orientationFromRight = 0;
  
        assert(BP.getRelative(0, 1).equals(new BoardPosition(9,4,0)));
        assert(BP.getRelative(24, 8).equals(new BoardPosition(0,4,0)));
        assert(BP.getRelative(22, 8).equals(new BoardPosition(2,6,0)));
        assert(BP.getRelative(0, 0).equals(new BoardPosition(8,4,0)));
        assert(BP.getRelative(16, 4).equals(new BoardPosition(4,0,0)));
        assert(BP.getRelative(3, 1).equals(new BoardPosition(7,4,0)));
        assert(BP.getRelative(8, 3).equals(new BoardPosition(6,5,0)));
        assert(BP.getRelative(14, 6).equals(new BoardPosition(6,8,0)));
        assert(BP.getRelative(8, 2).equals(new BoardPosition(6,2,0)));
        
        BP.setCoordinate(4, 4);
        assert(BP.getRelative(0, 0).equals(new BoardPosition(4,4,0)));
        assert(BP.getRelative(0, 4).equals(new BoardPosition(8,4,0)));
        assert(BP.getRelative(4, 4).equals(new BoardPosition(8,8,0)));
        assert(BP.getRelative(6, 4).equals(new BoardPosition(6,8,0)));
        assert(BP.getRelative(23, 4).equals(new BoardPosition(7,3,0)));
        assert(BP.getRelative(1, 3).equals(new BoardPosition(7,5,0)));
        assert(BP.getRelative(5, 3).equals(new BoardPosition(5,7,0)));
        assert(BP.getRelative(10, 3).equals(new BoardPosition(1,3,0)));
        
        BP.setCoordinate(-6, -6);
        assert(BP.getRelative(12, 12).equals(new BoardPosition(6,6,0)));
        /* Ends testing for getRelative() */

        /* testing for directionTo() */

        BP.setCoordinate(4, 4);
        BP.orientationFromRight = 2;

        other.setCoordinate(2, 1);
        assert (7 == BP.directionTo(other));

        BP.turn(4);
        assert (13 == BP.directionTo(other));

        other.setCoordinate(4, 0);
        assert (20 == BP.directionTo(other));

        other.setCoordinate(5, 6);
        assert (3 == BP.directionTo(other));

        BP.advance();
        BP.turn(5);
        assert (6 == BP.directionTo(other));

        BP.turn(5);
        BP.advance();
        assert (11 == BP.directionTo(other));
        /* Ends testing for directionTo() */

    }

}
