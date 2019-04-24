package hazard.HazardClasses;

public class Role extends Hazard{
	String role;

	public Role(int id, String role) {
		super(id);
		this.role = role;
	}

	public int getId() {
		return super.getId();
	}

	public void setId(int id) {
		super.setId(id);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
