package arbre2;

import algo2.FreqCode;

public class ValuedNode extends Node<FreqCode>{
	private static final long serialVersionUID = 1L;
	
	public ValuedNode(FreqCode fc){
		super(fc);
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

	protected void setFreq(int freq) {
		this.freq = freq;
	}
	
}
