package ihm;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import algo.Huffmaneur;


public class Ihm {

	private IhmListener ihmListener;
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="6,8"
	private JPanel jPanel = null;
	private JButton jButtonEncode = null;  //  @jve:decl-index=0:visual-constraint="523,247"
	private JButton jButtonDecode = null;  //  @jve:decl-index=0:visual-constraint="692,195"
	private JButton jButtonChooseFileSrc = null;  //  @jve:decl-index=0:visual-constraint="968,48"
	private JButton jButtonChooseFileDest = null;  //  @jve:decl-index=0:visual-constraint="962,88"
	private JLabel jLabelFileSrc = null;  //  @jve:decl-index=0:visual-constraint="723,57"
	private JLabel jLabelFileDest = null;  //  @jve:decl-index=0:visual-constraint="776,92"
	private JTextField jTextFieldFileSrc = null;  //  @jve:decl-index=0:visual-constraint="909,52"
	private JTextField jTextFieldFileDest = null;  //  @jve:decl-index=0:visual-constraint="921,97"
	private JSlider jSlider = null;  //  @jve:decl-index=0:visual-constraint="705,112"
	private JLabel jLabelDico = null;  //  @jve:decl-index=0:visual-constraint="600,112"
	private JLabel jLabelSize = null;  //  @jve:decl-index=0:visual-constraint="992,121"
	private	JFileChooser jFileChooser;
	private ProgressBarWithEvent progressBar = null;  //  @jve:decl-index=0:visual-constraint="654,45"
	private JButton jButtonCancel = null;  //  @jve:decl-index=0:visual-constraint="603,133"
	private JButton jButtonPause = null;  //  @jve:decl-index=0:visual-constraint="665,100"

	/**
	 * This method initializes ihmListener	
	 * 	
	 * @return IhmListener
	 */
	IhmListener getIhmListener() {
		if (ihmListener == null) {
			ihmListener = new IhmListener(this);
		}
		return ihmListener;
	}
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame("Huffmanzip");
			jFrame.setSize(new Dimension(515, 201));
			jFrame.setResizable(false);
			jFrame.setContentPane(getJPanel());
			jFrame.addWindowListener(getIhmListener());
			initButtonsStates(true);
		}
		return jFrame;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {

			GridBagConstraints gridBagConstraintsLabelDico = new GridBagConstraints();
			gridBagConstraintsLabelDico.gridx = 0;
			gridBagConstraintsLabelDico.gridy = 0;
			gridBagConstraintsLabelDico.anchor = GridBagConstraints.NORTH;
			GridBagConstraints gridBagConstraintsSlider = new GridBagConstraints();
			gridBagConstraintsSlider.gridx = 1;
			gridBagConstraintsSlider.gridy = 0;
			gridBagConstraintsSlider.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraintsLabelSize = new GridBagConstraints();
			gridBagConstraintsLabelSize.gridx = 2;
			gridBagConstraintsLabelSize.gridy = 0;
			gridBagConstraintsLabelSize.anchor = GridBagConstraints.NORTH;
			GridBagConstraints gridBagConstraintsLabelFileSrc = new GridBagConstraints();
			gridBagConstraintsLabelFileSrc.gridx = 0;
			gridBagConstraintsLabelFileSrc.gridy = 2;
			GridBagConstraints gridBagConstraintsLabelFileDest = new GridBagConstraints();
			gridBagConstraintsLabelFileDest.gridx = 0;
			gridBagConstraintsLabelFileDest.gridy = 3;
			GridBagConstraints gridBagConstraintsTextFieldFileSrc = new GridBagConstraints();
			gridBagConstraintsTextFieldFileSrc.gridx = 1;
			gridBagConstraintsTextFieldFileSrc.gridy = 2;
			gridBagConstraintsTextFieldFileSrc.weightx = 2;
			gridBagConstraintsTextFieldFileSrc.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraintsTextFieldFileDest = new GridBagConstraints();
			gridBagConstraintsTextFieldFileDest.gridx = 1;
			gridBagConstraintsTextFieldFileDest.gridy = 3;
			gridBagConstraintsTextFieldFileDest.weightx = 2;
			gridBagConstraintsTextFieldFileDest.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraintsButtonFileSrc = new GridBagConstraints();
			gridBagConstraintsButtonFileSrc.gridx = 2;
			gridBagConstraintsButtonFileSrc.gridy = 2;
			GridBagConstraints gridBagConstraintsButtonFileDest = new GridBagConstraints();
			gridBagConstraintsButtonFileDest.gridx = 2;
			gridBagConstraintsButtonFileDest.gridy = 3;
			GridBagConstraints gridBagConstraintsButtonEncode = new GridBagConstraints();
			gridBagConstraintsButtonEncode.gridx = 0;
			gridBagConstraintsButtonEncode.gridy = 4;
			GridBagConstraints gridBagConstraintsButtonDecode = new GridBagConstraints();
			gridBagConstraintsButtonDecode.gridx = 0;
			gridBagConstraintsButtonDecode.gridy = 5;
			GridBagConstraints gridBagConstraintsProgress = new GridBagConstraints();
			gridBagConstraintsProgress.gridx = 1;
			gridBagConstraintsProgress.gridy = 4;
			gridBagConstraintsProgress.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraintsCancel = new GridBagConstraints();
			gridBagConstraintsCancel.gridx = 2;
			gridBagConstraintsCancel.gridy = 5;
			GridBagConstraints gridBagConstraintsPause = new GridBagConstraints();
			gridBagConstraintsPause.gridx = 1;
			gridBagConstraintsPause.gridy = 5;
			//gridBagConstraintsPause.fill = GridBagConstraints.VERTICAL;

			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(getJLabelDico(), gridBagConstraintsLabelDico);
			jPanel.add(getJSlider(), gridBagConstraintsSlider);
			jPanel.add(getJLabelSize(), gridBagConstraintsLabelSize);
			jPanel.add(getJLabelFileSrc(), gridBagConstraintsLabelFileSrc);
			jPanel.add(getJLabelFileDest(), gridBagConstraintsLabelFileDest);
			jPanel.add(getJTextFieldFileSrc(), gridBagConstraintsTextFieldFileSrc);
			jPanel.add(getJTextFieldFileDest(), gridBagConstraintsTextFieldFileDest);
			jPanel.add(getJButtonChooseFileSrc(), gridBagConstraintsButtonFileSrc);
			jPanel.add(getJButtonChooseFileDest(), gridBagConstraintsButtonFileDest);
			jPanel.add(getJButtonEncode(), gridBagConstraintsButtonEncode);
			jPanel.add(getJButtonDecode(), gridBagConstraintsButtonDecode);
			jPanel.add(getProgressBar(), gridBagConstraintsProgress);
			jPanel.add(getJButtonCancel(), gridBagConstraintsCancel);
			jPanel.add(getJButtonPause(), gridBagConstraintsPause);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButtonEncode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJButtonEncode() {
		if (jButtonEncode == null) {
			jButtonEncode = new JButton("Encode");
			jButtonEncode.addActionListener(getIhmListener());
		}
		return jButtonEncode;
	}

	/**
	 * This method initializes jButtonDecode	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJButtonDecode() {
		if (jButtonDecode == null) {
			jButtonDecode = new JButton("Decode");
			jButtonDecode.addActionListener(getIhmListener());
		}
		return jButtonDecode;
	}

	/**
	 * This method initializes jButtonChooseFileSrc	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJButtonChooseFileSrc() {
		if (jButtonChooseFileSrc == null) {
			jButtonChooseFileSrc = new JButton("...");
			jButtonChooseFileSrc.addActionListener(getIhmListener());
		}
		return jButtonChooseFileSrc;
	}

	/**
	 * This method initializes jButtonChooseFileDest	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJButtonChooseFileDest() {
		if (jButtonChooseFileDest == null) {
			jButtonChooseFileDest = new JButton("...");
			jButtonChooseFileDest.addActionListener(getIhmListener());
		}
		return jButtonChooseFileDest;
	}

	/**
	 * This method initializes jLabelFileSrc	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	JLabel getJLabelFileSrc() {
		if (jLabelFileSrc == null) {
			jLabelFileSrc = new JLabel();
			jLabelFileSrc.setText("File to encode / decode : ");
		}
		return jLabelFileSrc;
	}

	/**
	 * This method initializes jLabelFileDest	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	JLabel getJLabelFileDest() {
		if (jLabelFileDest == null) {
			jLabelFileDest = new JLabel();
			jLabelFileDest.setText("Destination file : ");
		}
		return jLabelFileDest;
	}

	/**
	 * This method initializes jTextFieldFileSrc	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	JTextField getJTextFieldFileSrc() {
		if (jTextFieldFileSrc == null) {
			jTextFieldFileSrc = new JTextField(19);
		}
		return jTextFieldFileSrc;
	}

	/**
	 * This method initializes jTextFieldFileDest	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	JTextField getJTextFieldFileDest() {
		if (jTextFieldFileDest == null) {
			jTextFieldFileDest = new JTextField(19);
		}
		return jTextFieldFileDest;
	}

	/**
	 * This method initializes jSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider(0,16,8);
			jSlider.setMajorTickSpacing(4);
			jSlider.setMinorTickSpacing(2);
			jSlider.setPaintTicks(true);
			jSlider.setPaintLabels(true);
			jSlider.addChangeListener(getIhmListener());
		}
		return jSlider;
	}

	/**
	 * This method initializes jLabelDico	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	JLabel getJLabelDico() {
		if (jLabelDico == null) {
			jLabelDico = new JLabel();
			jLabelDico.setText("Dictionnary size : ");
		}
		return jLabelDico;
	}

	/**
	 * This method initializes jLabelSize	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	JLabel getJLabelSize() {
		if (jLabelSize == null) {
			jLabelSize = new JLabel();
			jLabelSize.setText(""+getJSlider().getValue());
		}
		return jLabelSize;
	}

	/**
	 * This method initializes jFileChooser	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	JFileChooser getJFileChooser() {
		if (jFileChooser == null) {
			jFileChooser = new JFileChooser();
		}
		return jFileChooser;
	}
	
	/**
	 * This method initializes progressBar	
	 * 	
	 * @return ProgressBarWithEvent
	 */
	ProgressBarWithEvent getProgressBar() {
		if (progressBar == null) {
			progressBar = new ProgressBarWithEvent();
			progressBar.addPropertyChangeListener(getIhmListener());
		}
		return progressBar;
	}

	/**
	 * This method initializes jButtonCancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJButtonCancel() {
		if (jButtonCancel == null) {
			jButtonCancel = new JButton("Cancel");
			jButtonCancel.addActionListener(getIhmListener());
		}
		return jButtonCancel;
	}

	/**
	 * This method initializes jButtonPause	
	 * 	
	 * @return javax.swing.JButton	
	 */
	JButton getJButtonPause() {
		if (jButtonPause == null) {
			jButtonPause = new JButton("Pause");
			jButtonPause.addActionListener(getIhmListener());
		}
		return jButtonPause;
	}
	
	Huffmaneur huffmaneur;
	Huffmaneur getHuffmaneur() {
		/* if(huffmaneur.isCanceled())
			huffmaneur = null; */
		
		return huffmaneur;
	}
	void setHuffmaneur(Huffmaneur huffmaneur) {
		this.huffmaneur = huffmaneur;
	}
	
	public void initButtonsStates(boolean init){		
		if(init){
			getJButtonDecode().setEnabled(true);
			getJButtonEncode().setEnabled(true);
			getJButtonCancel().setEnabled(false);
			getJButtonPause().setEnabled(false);
		}else{
			getJButtonDecode().setEnabled(false);
			getJButtonEncode().setEnabled(false);
			getJButtonCancel().setEnabled(true);
			getJButtonPause().setEnabled(true);
		}
		
		getJButtonPause().setText("Pause");
		
		getProgressBar().setValue(0);
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Ihm ihm = new Ihm();
		ihm.getJFrame().setVisible(true);
	}
}
