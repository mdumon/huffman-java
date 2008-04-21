package arbre;

public class FreqNode extends Node {
	private int freq;
	
	public FreqNode(int freq){
		setEstFeuille(false);
		this.freq = freq;
	}
}
