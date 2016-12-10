package controller;

import models.Robot;

/**
 * 
 * @author
 *
 */
public class PcController extends RobotController {    
	public PcController(Referee referee, Robot robot) {
        super(referee, robot);
    }
	
	public void play() {
	    super.play();
	    this.myReferee.isPC=true;
	    waitTillDone();
	    this.myReferee.isPC=false;
	}
	
	protected void waitTillDone() {
	    this.done=false;
	    while(!done) {
	        try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
	    }
	}

    /** main method used for testing purposes */
	public static void main(String args[]){
		/** TESTING BEGINS */
		/** TESTING ENDS */
	}
}
