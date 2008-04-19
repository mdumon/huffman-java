package ihm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class ProgressBarWithEvent extends JProgressBar implements PropertyChangeListener {
	
	public ProgressBarWithEvent(){
		super();
		this.setMinimum(0);
		this.setMaximum(100);
		this.setValue(0);
		this.setStringPainted(true);
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("encode")){
			this.setValue((Integer)evt.getNewValue());
			//System.out.println("zag");
		}
	}
	
}
