package view.temporary;

public class RobotTeam {
	
	protected String name;
	protected RobotListEntry scout, tank, sniper;
	
	public RobotTeam(String name, RobotListEntry scout, RobotListEntry tank, RobotListEntry sniper) {
		this.name = name;
		this.scout = scout;
		this.tank = tank;
		this.sniper = sniper;
	}
	
	public String toString() {
		return this.name;
	}
}
