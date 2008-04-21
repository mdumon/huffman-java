package arbre;

public class FreqNode extends Node {
	private boolean estFeuille;
	private int freq;
	
	public FreqNode(int freq){
		this.estFeuille = false;
		this.freq = freq;
	}
}
