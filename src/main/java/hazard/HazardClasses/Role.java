package hazard.HazardClasses;

public class Role extends Hazard{
	String role;

	public Role(int id, String role) {
		super(id);
		this.role = role;
	}

	@Override
	public int getId() {
		return super.getId();
	}

	public String getRole() {
		return role;
	}

	@Override
	public void setId(int id) {
		super.setId(id);
	}

	public void setRole(String role) {
		this.role = role;
	}

}
