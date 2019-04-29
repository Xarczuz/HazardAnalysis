package hazard.HazardClasses;

public class PossibleVictim {

	private String kind;
	private String role;
	private String relator;

	public PossibleVictim(String kind,String role,String relator) {
	
		this.kind = kind;
		this.role = role;
		this.relator = relator;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRelator() {
		return relator;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}

}
