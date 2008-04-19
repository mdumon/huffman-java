/**
 * ENSICAEN
 * 6 Bd. Mar�chal Juin
 * 14050 Caen
 *
 * @class IhmListener.java
 * 
 * Regroupe les listeners utiles � l'ihm
 * 
 * @author Romain Macureau <macureau@ecole.ensicaen.fr>
 * 
 * @version 0.0.1 - 2008-04-18
 * 
 */

package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class Tri
 */

public class IhmListener implements ChangeListener, ActionListener, WindowListener {

	private Ihm ihm;

	public IhmListener(Ihm ihm) {
		this.ihm = ihm;
	}

	/**
	 * Listener pour la taille dictionnaire choisie
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == ihm.getJSlider())
			ihm.getJLabelSize().setText(""+((JSlider) e.getSource()).getValue());
	}

	/**
	 * Listener pour les boutons
	 */
	public void actionPerformed(ActionEvent e) {
		//bouton 'choisir fichier source'
		if (e.getSource() == ihm.getJButtonChooseFileSrc())
		{
			int returnVal = ihm.getJFileChooser().showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION)
				ihm.getJTextFieldFileSrc().setText(ihm.getJFileChooser().getSelectedFile().getName());
		}

		//bouton 'choisir fichier destination'
		else if (e.getSource() == ihm.getJButtonChooseFileDest())
		{
			int returnVal = ihm.getJFileChooser().showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION)
				ihm.getJTextFieldFileDest().setText(ihm.getJFileChooser().getSelectedFile().getName());
		}

		//bouton 'encoder'
		else if (e.getSource() == ihm.getJButtonEncode())
		{

		}

		//bouton 'decoder'
		else if (e.getSource() == ihm.getJButtonDecode())
		{

		}
		
		//bouton 'test'
		else if (e.getSource() == ihm.getJButtonTest())
		{
			new JupdateLaProgressBar(ihm.getProgressBar()).start();
		}

	}
	
	class JupdateLaProgressBar extends Thread{
		
		ProgressBarWithEvent pbwe;
		
		public JupdateLaProgressBar(ProgressBarWithEvent pbwe){
			this.pbwe = pbwe;
		}
		
		public void run(){
			System.out.println("Test en cours...");
			
			PropertyChangeSupport support = new PropertyChangeSupport(this);
			support.addPropertyChangeListener(pbwe);
			
			int i;
			for (i=0 ; i<=100 ; i++)
			{
				PropertyChangeEvent evt = new PropertyChangeEvent(this,"encode",0,new Integer(i));
				support.firePropertyChange(evt);
				try {
					Thread.sleep(180);
				}
				catch (InterruptedException e) {e.getMessage();}
				System.out.println(i);
			}
		}
	}
	
	/*public void progress(int i) {
		String mess = String.format("Completed %d%%.\n", i);
		ihm.getProgressMonitorEncode().setNote(mess);
		ihm.getProgressMonitorEncode().setProgress(i);
		ihm.getJProgressBar().setValue(i);
		ihm.getJProgressBar().repaint();
	}*/

	public void windowActivated(WindowEvent e) {}
		
	public void windowClosed(WindowEvent e) {}
		
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}
		
	public void windowIconified(WindowEvent e) {}
		
	public void windowOpened(WindowEvent e) {}

}
