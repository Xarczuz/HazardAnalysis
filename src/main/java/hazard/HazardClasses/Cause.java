package hazard.HazardClasses;

public class Cause extends Play {
	private String cause;
	private int hazardID;
	private String risk;
	private String mitigation;
private double severity,probability,riskevaluation;
	public Cause(int id, String cause, int hazardID) {
		super(id);
		this.cause = cause;
		this.hazardID = hazardID;
		this.risk = "";
		this.mitigation = "";
		this.severity=0;
		this.probability=0;
		this.riskevaluation=0;
	}

	public double getRiskevaluation() {
		return riskevaluation;
	}

	public void setRiskevaluation(double riskevaluation) {
		this.riskevaluation = riskevaluation;
	}

	public double getSeverity() {
		return severity;
	}

	public void setSeverity(double severity) {
		this.severity = severity;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getMitigation() {
		return mitigation;
	}

	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
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
