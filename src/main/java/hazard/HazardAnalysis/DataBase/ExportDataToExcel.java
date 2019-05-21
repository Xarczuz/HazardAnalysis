package hazard.HazardAnalysis.DataBase;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;

public class ExportDataToExcel extends Thread {
	private File file;
	private ProgressIndicator p1;

	public ExportDataToExcel(File file, ProgressIndicator p1) {
		this.file = file;
		this.p1 = p1;
	}

	@Override
	public void run() {
		DataBaseConnection.exportData(file,p1);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				p1.setProgress(1D);
			}
		});
	}
}
