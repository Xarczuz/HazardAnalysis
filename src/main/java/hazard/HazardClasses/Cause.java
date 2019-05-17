package hazard.HazardClasses;

public class Cause extends Play {
	private String cause;
	private int hazardID;
	private String risk;
	private String mitigation;
	private double severity, probability, riskevaluation;

	public Cause(int id, String cause, int hazardID) {
		super(id);
		this.cause = cause;
		this.hazardID = hazardID;
		this.risk = "";
		this.mitigation = "";
		this.severity = 0;
		this.probability = 0;
		this.riskevaluation = 0;
	}

	public String getCause() {
		return cause;
	}

	public int getHazardID() {
		return hazardID;
	}

	public String getMitigation() {
		return mitigation;
	}

	public double getProbability() {
		return probability;
	}

	public String getRisk() {
		return risk;
	}

	public double getRiskevaluation() {
		return riskevaluation;
	}

	public double getSeverity() {
		return severity;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setHazardID(int hazardID) {
		this.hazardID = hazardID;
	}

	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public void setRiskevaluation(double riskevaluation) {
		this.riskevaluation = riskevaluation;
	}

	public void setSeverity(double severity) {
		this.severity = severity;
	}
}
