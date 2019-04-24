package hazard.HazardClasses;

public class Kind extends Hazard {
	String kind;

	public Kind(int id, String kind) {
		super(id);
		this.kind = kind;
	}

	public int getId() {
		return super.getId();
	}

	public String getKind() {
		return kind;
	}

	public void setId(int id) {
		super.setId(id);
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
