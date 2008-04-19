package ProgressBar;

import ihm.ProgressBarWithEvent;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ProgressBarFrameTest extends JFrame {

	private ProgressBarWithEvent progressBar;
	
	ProgressBarFrameTest(){
		this("Barre Progression");
	}
	
	ProgressBarFrameTest(String title) {
		super();
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 50);
		this.setLayout(new BorderLayout());
		
		progressBar = new ProgressBarWithEvent();
		JPanel panel = new JPanel();
		panel.add(progressBar);
		this.add(progressBar);
		this.setVisible(true);
	}
	
	public ProgressBarWithEvent getProgressBar(){
		return progressBar;
	}
	
}
