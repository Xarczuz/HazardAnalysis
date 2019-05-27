package hazard.HazardClasses;

public class Cause extends Play {
	private String cause;
	private int hazardID;
	private String risk, postRisk;
	private String mitigation;
	private double severity, probability, riskevaluation, postSeverity, postProbability, postRiskevaluation;;

	public Cause(int id, String cause, int hazardID) {
		super(id);
		this.cause = cause;
		this.hazardID = hazardID;
		this.risk = "";
		this.mitigation = "";
		this.severity = 0;
		this.probability = 0;
		this.riskevaluation = 0;
		this.postRisk = "";
		this.postSeverity = 0;
		this.postProbability = 0;
		this.postRiskevaluation = 0;
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

	public double getPostProbability() {
		return postProbability;
	}

	public String getPostRisk() {
		return postRisk;
	}

	public double getPostRiskevaluation() {
		return postRiskevaluation;
	}

	public double getPostSeverity() {
		return postSeverity;
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

	public void setPostProbability(double postProbability) {
		this.postProbability = postProbability;
	}

	public void setPostRisk(String postRisk) {
		this.postRisk = postRisk;
	}

	public void setPostRiskevaluation(double postRiskevaluation) {
		this.postRiskevaluation = postRiskevaluation;
	}

	public void setPostSeverity(double postSeverity) {
		this.postSeverity = postSeverity;
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
