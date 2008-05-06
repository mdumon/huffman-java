package ihm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

/**
 * The progress bar which progress with events from the kernel
 * 
 * @author Nicolas Carpentier
 * @author Quentin Douillet
 *
 */
@SuppressWarnings("serial")
public class ProgressBarWithEvent extends JProgressBar implements PropertyChangeListener {
	
	/**
	 * Create an instance of "ProgressBarWithEvent"
	 */
	public ProgressBarWithEvent(){
		super();
		this.setMinimum(0);
		this.setMaximum(100);
		this.setValue(0);
		this.setStringPainted(true);
	}

	/**
	 * @param evt The evenement which represents the new state of the bar
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("advance")){
			this.setValue((Integer)evt.getNewValue());
		}
	}
	
}
