package hazard.HazardClasses;

public class Relator extends Hazard {
	String relator;

	public Relator(int id, String relator) {
		super(id);
		this.relator = relator;
	}

	public String getRelator() {
		return relator;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}
}
