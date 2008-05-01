package groupe2;

import java.util.Date;

public class TimedLog {
	
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
