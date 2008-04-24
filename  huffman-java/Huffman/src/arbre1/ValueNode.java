package arbre1;

import bitutils.BitArray;

public class ValueNode extends Node {
	private BitArray value;
	private int freq;
	
	public ValueNode(BitArray value, int freq){
		this.value = value;
		this.freq = freq;
	}
	
	public BitArray getValue(){
		return this.value;
	}
	
	public int getFreq(){
		return this.freq;
	}
	
	public void incFreq(){
		this.freq++;
	}
	
	public boolean equals(Object o){
		boolean resultat = true;
		
		if (o instanceof ValueNode){
			resultat = ((ValueNode)o).getValue().equals(this.value);
		} else resultat = false;
		
		return resultat;
	}
	
}
