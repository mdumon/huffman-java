package arbre1;

import java.io.Serializable;

import bitutils.BitArray;

public class ValueNode implements Node, Serializable {

	private static final long serialVersionUID = 1L;
	
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
	
	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getValToCompare() {
		return this.getFreq();
	}
	
	public int compareTo(Node node) {
		return this.getValToCompare() - node.getValToCompare();
	}
	
	public String toString() {
		return "ValueNode : "+this.getValue()+"\n";
	}

	public boolean equals(Object o){
		boolean resultat = true;
		
		if (o instanceof ValueNode){
			resultat = ((ValueNode)o).getValue().equals(this.value);
		} else resultat = false;
		
		return resultat;
	}
}
