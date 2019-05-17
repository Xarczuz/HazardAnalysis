package hazard.HazardClasses;

public class PossibleVictim {
	private String kind, role, relator;
	private int roleID, kindID, relatorID;

	public PossibleVictim(int roleID, String role, int kindID, String kind, int relatorID, String relator) {
		this.kind = kind;
		this.role = role;
		this.relator = relator;
		this.kindID = kindID;
		this.roleID = roleID;
		this.relatorID = relatorID;
	}

	public String getKind() {
		return kind;
	}

	public int getKindID() {
		return kindID;
	}

	public String getRelator() {
		return relator;
	}

	public int getRelatorID() {
		return relatorID;
	}

	public String getRole() {
		return role;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setKindID(int kindID) {
		this.kindID = kindID;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}

	public void setRelatorID(int relatorID) {
		this.relatorID = relatorID;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
}
