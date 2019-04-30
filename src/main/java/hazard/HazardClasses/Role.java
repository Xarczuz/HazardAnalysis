package hazard.HazardClasses;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class Role extends Play{
	String role;
	private Boolean start,runtime,shutdown;
	private CheckBox cbstart,cbruntime,cbshutdown;

	public Role(int id, String role, boolean start, boolean runtime, boolean shutdown) {
		super(id);
		this.role = role;
		this.cbstart = new CheckBox();
		this.cbruntime = new CheckBox();
		this.cbshutdown = new CheckBox();
		this.start = start;
		this.runtime = runtime;
		this.shutdown = shutdown;
		this.cbstart.setSelected(start);
		this.cbruntime.setSelected(runtime);
		this.cbshutdown.setSelected(shutdown);
		addClickEventToCheckBox();
	}

	public Role(int id, String role) {
		super(id);
		this.role = role;
	}

	private void addClickEventToCheckBox() {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DataBaseConnection.sqlUpdate("UPDATE role SET start = " + Role.this.getCbstart().isSelected()
						+ " WHERE id = " + Role.this.getId() + ";");
				Role.this.setStart(Role.this.getCbstart().isSelected());
			}
		};
		this.cbstart.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DataBaseConnection.sqlUpdate("UPDATE role SET runtime = " + Role.this.getCbruntime().isSelected()
						+ " WHERE id = " + Role.this.getId() + ";");
				Role.this.setRuntime(Role.this.getCbruntime().isSelected());
			}
		};
		this.cbruntime.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DataBaseConnection.sqlUpdate("UPDATE role SET shutdown = " + Role.this.getCbshutdown().isSelected()
						+ " WHERE id = " + Role.this.getId() + ";");
				Role.this.setShutdown(Role.this.getCbshutdown().isSelected());
			}
		};
		this.cbshutdown.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

	}

	public CheckBox getCbruntime() {
		return cbruntime;
	}

	public CheckBox getCbshutdown() {
		return cbshutdown;
	}

	public CheckBox getCbstart() {
		return cbstart;
	}

	@Override
	public int getId() {
		return super.getId();
	}

	public String getRole() {
		return role;
	}

	public Boolean getRuntime() {
		return runtime;
	}

	public Boolean getShutdown() {
		return shutdown;
	}

	public Boolean getStart() {
		return start;
	}

	public void setCbruntime(CheckBox cbruntime) {
		this.cbruntime = cbruntime;
	}

	public void setCbshutdown(CheckBox cbshutdown) {
		this.cbshutdown = cbshutdown;
	}

	public void setCbstart(CheckBox cbstart) {
		this.cbstart = cbstart;
	}

	@Override
	public void setId(int id) {
		super.setId(id);
	}
	public void setRole(String role) {
		this.role = role;
	}

	public void setRuntime(Boolean runtime) {
		this.runtime = runtime;
	}

	public void setShutdown(Boolean shutdown) {
		this.shutdown = shutdown;
	}

	public void setStart(Boolean start) {
		this.start = start;
	}

}
