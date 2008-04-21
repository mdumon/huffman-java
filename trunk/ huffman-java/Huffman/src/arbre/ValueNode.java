package arbre;

import bitutils.BitArray;

public class ValueNode extends Node {
	private boolean estFeuille;
	private BitArray value;
	private int freq;
	
	public ValueNode(BitArray value, int freq){
		this.estFeuille = true;
		this.value = value;
		this.freq = freq;
	}
	
}
