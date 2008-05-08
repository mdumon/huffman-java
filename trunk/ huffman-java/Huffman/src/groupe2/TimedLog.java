package groupe2;

import java.util.Date;

/**
 * <code>TimedLog</code> print the time
 * used by the different steps of the huffman algorithm
 * 
 * @author Franck Séhédic <sehedic@ecole.ensicaen.fr>
 */
public class TimedLog implements Logger{
	
	private long debut;
	
	public TimedLog(){}
	
	public void start(){
		debut = new Date().getTime();		
	}
	
	public void log(String s){
		long elapsed = (new Date().getTime() - debut);
		System.out.println("" + (elapsed /1000) + "." + (elapsed % 1000)  + " : " + s);
	}
}
