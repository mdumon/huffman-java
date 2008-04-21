package arbre;

import bitutils.BitArray;

public class ValueNode extends Node {
	private BitArray value;
	private int freq;
	
	public ValueNode(BitArray value, int freq){
		setEstFeuille(true);
		this.value = value;
		this.freq = freq;
	}
	
}