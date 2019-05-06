package hazard.HazardClasses;

public class Cause extends Play {
	private String cause;
	private int hazardID;

	public Cause(int id, String cause, int hazardID) {
		super(id);
		this.cause = cause;
		this.hazardID = hazardID;

	}

	public String getCause() {
		return cause;
	}

	public int getHazardID() {
		return hazardID;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setHazardID(int hazardID) {
		this.hazardID = hazardID;
	}

}
