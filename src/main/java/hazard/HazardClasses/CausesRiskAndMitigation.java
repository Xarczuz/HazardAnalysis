package hazard.HazardClasses;

public class CausesRiskAndMitigation {
	private String nr, hml, cause, harm, rq, mitigation;
	private double probability, severity, rpn;
	private boolean risk;

	public String getCause() {
		return cause;
	}

	public String getHarm() {
		return harm;
	}

	public String getHml() {
		return hml;
	}

	public String getMitigation() {
		return mitigation;
	}

	public String getNr() {
		return nr;
	}

	public double getProbability() {
		return probability;
	}

	public double getRpn() {
		return rpn;
	}

	public String getRq() {
		return rq;
	}

	public double getSeverity() {
		return severity;
	}

	public boolean isRisk() {
		return risk;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setHarm(String harm) {
		this.harm = harm;
	}

	public void setHml(String hml) {
		this.hml = hml;
	}

	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public void setRisk(boolean risk) {
		this.risk = risk;
	}

	public void setRpn(double rpn) {
		this.rpn = rpn;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public void setSeverity(double severity) {
		this.severity = severity;
	}
}
