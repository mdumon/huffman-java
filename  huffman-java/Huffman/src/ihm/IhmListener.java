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
import java.beans.PropertyChangeListener;

import javax.swing.JFileChooser;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import algo.FakeHuffmaneur;


/**
 * Class Tri
 */

public class IhmListener implements ChangeListener, ActionListener, WindowListener, PropertyChangeListener {

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
				ihm.getJTextFieldFileSrc().setText(ihm.getJFileChooser().getSelectedFile().getAbsolutePath());
		}

		//bouton 'choisir fichier destination'
		else if (e.getSource() == ihm.getJButtonChooseFileDest())
		{
			int returnVal = ihm.getJFileChooser().showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION)
				ihm.getJTextFieldFileDest().setText(ihm.getJFileChooser().getSelectedFile().getAbsolutePath());
		}

		//bouton 'encoder'
		else if (e.getSource() == ihm.getJButtonEncode())
		{
			ihm.setHuffmaneur(new FakeHuffmaneur());
			ihm.getHuffmaneur().addPropertyChangeListener(ihm.getProgressBar());
			ihm.getHuffmaneur().addPropertyChangeListener(this);
			ihm.getHuffmaneur().start();
			ihm.initButtonsStates(false);
		}

		//bouton 'decoder'
		else if (e.getSource() == ihm.getJButtonDecode())
		{
			ihm.setHuffmaneur(new FakeHuffmaneur());
			ihm.getHuffmaneur().addPropertyChangeListener(ihm.getProgressBar());
			ihm.getHuffmaneur().addPropertyChangeListener(this);
			ihm.getHuffmaneur().start();
			ihm.initButtonsStates(false);
		}
		
		//bouton 'pause'
		else if (e.getSource() == ihm.getJButtonPause())
		{
			ihm.getJButtonPause().setEnabled(false);
			ihm.getHuffmaneur().setPaused(!ihm.getHuffmaneur().isPaused());
		}
		
		//bouton 'cancel'
		else if (e.getSource() == ihm.getJButtonCancel())
		{
			ihm.getJButtonCancel().setEnabled(false);
			ihm.getHuffmaneur().cancel();
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

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		
		if (e.getSource() == ihm.getHuffmaneur()){
			
			if(e.getPropertyName().equals("canceled")){
				ihm.initButtonsStates(true);
			}
			
			else if(e.getPropertyName().equals("paused")){
				if((Boolean)e.getNewValue())
					ihm.getJButtonPause().setText("reprendre");
				else
					ihm.getJButtonPause().setText("pause");
				ihm.getJButtonPause().setEnabled(true);
			}
			
			else if(e.getPropertyName().equals("advance")){
				if((Integer)e.getNewValue() == 100){
					ihm.initButtonsStates(true);
				}
			}
		}
		
	}

}
