package hazard.HazardClasses;

public class HazardElement {
	private String kind, role;

	public HazardElement(String kind, String role) {
		this.kind = kind;
		this.role = role;
	}

	public String getKind() {
		return kind;
	}

	public String getRole() {
		return role;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
