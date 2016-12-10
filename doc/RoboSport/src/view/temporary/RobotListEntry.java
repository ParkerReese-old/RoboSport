package view.temporary;

public class RobotListEntry {
	
	protected String name;
	
	public RobotListEntry(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
