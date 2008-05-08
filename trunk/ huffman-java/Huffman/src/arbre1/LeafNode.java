package arbre1;

import java.io.Serializable;

import bitutils.BitArrayBooleanArray;

public class LeafNode implements Node, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BitArrayBooleanArray value;
	
	public LeafNode(BitArrayBooleanArray value){
		this.value = value;
	}
	public BitArrayBooleanArray getValue(){
		return this.value;
	}
	
	public void setValue(BitArrayBooleanArray value) {
		this.value = value;
	}
	
	//n'aura pas � �tre compar�
	public int getValToCompare() {
		return 0;
	}
	
	//n'aura pas � �tre compar�
	public int compareTo(Node node) {
		return 0;
	}
	
	public String toString() {
		return "LeafNode : "+this.getValue()+"\n";
	}
}
