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
		
		//bouton 'pause'
		else if (e.getSource() == ihm.getJButtonPause())
		{
			
		}
		
		//bouton 'cancel'
		else if (e.getSource() == ihm.getJButtonCancel())
		{
			ihm.getJButtonCancel().setEnabled(false);
		}

	}

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
