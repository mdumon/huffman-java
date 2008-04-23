package arbre2;

import algo2.FreqCode;

public class ValuedNode extends Node{
	
	public ValuedNode(FreqCode fc){
		super(fc.getKey());
		setFreq(fc.getFreq());
	}
	
	public ValuedNode(ValuedNode leftSon, ValuedNode rightSon){
		super(leftSon,rightSon);
		setFreq(leftSon.getFreq() + rightSon.getFreq());
	}
	
	
	private int freq;
	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}
	
}
