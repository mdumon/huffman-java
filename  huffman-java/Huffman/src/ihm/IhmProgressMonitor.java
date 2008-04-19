package ihm;

import javax.swing.ProgressMonitor;

public class IhmProgressMonitor extends Thread {
	
	private ProgressMonitor progressMonitorEncode;
	private ProgressMonitor progressMonitorDecode;
	private Ihm ihm;
	
	public IhmProgressMonitor(Ihm ihm) {
		this.ihm = ihm;
		progressMonitorEncode = new ProgressMonitor(ihm.getJFrame(),"Encode...",null,0,100);
		progressMonitorDecode = new ProgressMonitor(ihm.getJFrame(),"Decode...","",0,100);
	}
	
	public void setProgressEncode(int progress, String completed) {
		progressMonitorEncode.setProgress(progress);
		//progressMonitorEncode.setNote(completed);
	}
	
	public void setProgressDecode(int progress, String completed) {
		progressMonitorDecode.setProgress(progress);
		progressMonitorDecode.setNote(completed);
	}
	
	public void run() {
		System.out.println("Thread démarré");
	}
}
