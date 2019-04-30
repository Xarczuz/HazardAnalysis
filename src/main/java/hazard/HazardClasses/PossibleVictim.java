package hazard.HazardClasses;

public class PossibleVictim {

	private String kind;
	private String role;
	private String relator;
	private String role2;
	private String kind2;

	public String getKind2() {
		return kind2;
	}

	public void setKind2(String kind2) {
		this.kind2 = kind2;
	}

	public PossibleVictim(String kind, String role, String relator, String role2, String kind2) {

		this.kind = kind;
		this.role = role;
		this.relator = relator;
		this.role2 = role2;
		this.kind2 = kind2;
	}

	public String getRole2() {
		return role2;
	}

	public void setRole2(String role2) {
		this.role2 = role2;
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
