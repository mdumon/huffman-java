package algo1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * An <code>Huffmaneur</code> is able to encode/decode or wathever 
 * on an inputFile and save it to an outputFile.
 * @author Franck Séhédic <sehedic@ecole.ensicaen.fr>
 */
public abstract class Huffmaneur extends Thread{
	PropertyChangeSupport pcs;
	
	public Huffmaneur(){
		 pcs = new PropertyChangeSupport(this);
	}

	public Huffmaneur(File inputFile,File outputFile) throws FileNotFoundException{
		 this();
		 setInputFile(inputFile);
		 setOutputFile(outputFile);
	}
	
	protected abstract void huffmaner();
	
	public void run(){
		huffmaner();
	}
	
	private boolean paused = false;
	public synchronized void setPaused(boolean p){
		if(paused != p){
			pcs.firePropertyChange("paused",paused,p);
			paused = p;
			if(!paused) notify();
		}
	}
	public synchronized boolean isPaused(){
		return paused;
	}
	protected synchronized void pauseIfIHaveTo(){
		try{
			if(paused) 
				wait();
		}catch(InterruptedException ignored){}
	}
	
	private boolean canceled = false;
	public synchronized void cancel(){
		if(!canceled){
			pcs.firePropertyChange("canceled",false,true);
			canceled = true;
		}
	}
	public synchronized boolean isCanceled(){
		return canceled;
	}
	
	private int advance = 0;
	protected synchronized void setAdvance(int advance){
		if(this.advance != advance){
			pcs.firePropertyChange("advance",this.advance,advance);
			this.advance = advance;
		}
	}
	protected void incAdvance(){
		setAdvance(advance + 1);
	}
	public synchronized int getAdvance(){
		return advance;
	}
	
	private File inputFile;
	public void setInputFile(File inputFile) throws FileNotFoundException{
		if(!inputFile.exists()) throw new FileNotFoundException(inputFile.getAbsolutePath());
		if(!inputFile.canRead()) throw new FileNotFoundException(inputFile.getAbsolutePath());
		
		this.inputFile = inputFile;
	}
	public File getInputFile(){
		return this.inputFile;
	}
	
	private File outputFile;
	void setOutputFile(File outputFile) throws FileNotFoundException{
		
		if(outputFile.exists()){
			boolean isDeleted;
			try{
				isDeleted = outputFile.delete();
			}catch(SecurityException se){
				isDeleted = false;
			}
			if(!isDeleted) throw new FileNotFoundException(outputFile.getAbsolutePath());
		}
		
		try{
			if(!outputFile.createNewFile()) throw new FileNotFoundException(outputFile.getAbsolutePath());
		}catch(IOException ioe1){
			throw new FileNotFoundException(outputFile.getAbsolutePath());
		}
		
		this.outputFile = outputFile;
	}
	public File getOutputFile(){
		return outputFile;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl){
		pcs.addPropertyChangeListener(pcl);
	}
	public void removePropertyChangeListener(PropertyChangeListener pcl){
		pcs.removePropertyChangeListener(pcl);
	}
}
	