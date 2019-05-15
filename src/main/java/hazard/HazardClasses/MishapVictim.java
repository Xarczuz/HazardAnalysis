package hazard.HazardClasses;

public class MishapVictim extends Play {
	private String kind, role, relator;
	private int roleID, kindID, relatorID;

	public MishapVictim(int id, int roleID, String role, int kindID, String kind, int relatorID, String relator) {
		super(id);
		this.kind = kind;
		this.role = role;
		this.relator = relator;
		this.kindID = kindID;
		this.roleID = roleID;
		this.relatorID = relatorID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public int getKindID() {
		return kindID;
	}

	public void setKindID(int kindID) {
		this.kindID = kindID;
	}

	public int getRelatorID() {
		return relatorID;
	}

	public void setRelatorID(int relatorID) {
		this.relatorID = relatorID;
	}

	public String getKind() {
		return kind;
	}

	public String getRelator() {
		return relator;
	}

	public String getRole() {
		return role;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setRelator(String relator) {
		this.relator = relator;
	}

	public void setRole(String role) {
		this.role = role;
	}
}