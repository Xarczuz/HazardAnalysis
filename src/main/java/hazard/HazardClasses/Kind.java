package hazard.HazardClasses;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class Kind extends Hazard {
	private String kind;
	private Boolean start,runtime,shutdown;
	private CheckBox cbstart,cbruntime,cbshutdown;

	public Kind(int id, String kind, boolean start, boolean runtime, boolean shutdown) {
		super(id);
		this.kind = kind;
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

	public Kind(int id, String kind) {
		super(id);
		this.kind = kind;
	}

	private void addClickEventToCheckBox() {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DataBaseConnection.sqlUpdate("UPDATE kind SET start = " + Kind.this.getCbstart().isSelected()
						+ " WHERE id = " + Kind.this.getId() + ";");
				Kind.this.setStart(Kind.this.getCbstart().isSelected());
			}
		};
		this.cbstart.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DataBaseConnection.sqlUpdate("UPDATE kind SET runtime = " + Kind.this.getCbruntime().isSelected()
						+ " WHERE id = " + Kind.this.getId() + ";");
				Kind.this.setRuntime(Kind.this.getCbruntime().isSelected());
			}
		};
		this.cbruntime.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				DataBaseConnection.sqlUpdate("UPDATE kind SET shutdown = " + Kind.this.getCbshutdown().isSelected()
						+ " WHERE id = " + Kind.this.getId() + ";");
				Kind.this.setShutdown(Kind.this.getCbshutdown().isSelected());
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

	public String getKind() {
		return kind;
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
	public void setKind(String kind) {
		this.kind = kind;
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
