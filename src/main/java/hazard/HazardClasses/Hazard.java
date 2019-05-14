package hazard.HazardClasses;

public class Hazard extends Play {
	String hazard;
	String hazardDescription;


	public Hazard(int id, String hazard, String hazardDescription) {
		super(id);
		this.hazard = hazard;
		this.hazardDescription = hazardDescription;
	
	}

	public String getHazard() {
		return hazard;
	}

	public String getHazardDescription() {
		return hazardDescription;
	}



	public void setHazard(String hazard) {
		this.hazard = hazard;
	}

	public void setHazardDescription(String hazardDescription) {
		this.hazardDescription = hazardDescription;
	}

	
}
