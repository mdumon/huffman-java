package ProgressBar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class ProgressBarMain {
	
	public static void main(String args[]){
		ProgressBarFrameTest frame = new ProgressBarFrameTest();
		
		PropertyChangeSupport support = new PropertyChangeSupport(frame);
		support.addPropertyChangeListener(frame.getProgressBar());
		
		for (int i=0; i<=100; i++){
			PropertyChangeEvent evenement = new PropertyChangeEvent(frame, "progress", 
																	0, new Integer(i));
			
			support.firePropertyChange(evenement);
			
			try {
				Thread.sleep(180);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
