package hazard.HazardClasses;

public class Mitigation extends Play {
	private String mitigation;
	private int hazardID;

	public Mitigation(int id, String mitigation, int hazardID) {
		super(id);
		this.mitigation = mitigation;
		this.hazardID = hazardID;
	}

	public int getHazardID() {
		return hazardID;
	}

	public String getMitigation() {
		return mitigation;
	}

	public void setHazardID(int hazardID) {
		this.hazardID = hazardID;
	}

	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}
}
