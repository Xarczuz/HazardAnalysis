package hazard.HazardClasses;

public class Hazard extends Play {
	String hazard;
	String hazardDescription;
	String risk;

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public Hazard(int id, String hazard, String hazardDescription) {
		super(id);
		this.hazard = hazard;
		this.hazardDescription = hazardDescription;
		this.risk = "";
	}

	public String getHazard() {
		return hazard;
	}

	public void setHazard(String hazard) {
		this.hazard = hazard;
	}

	public String getHazardDescription() {
		return hazardDescription;
	}

	public void setHazardDescription(String hazardDescription) {
		this.hazardDescription = hazardDescription;
	}

}
