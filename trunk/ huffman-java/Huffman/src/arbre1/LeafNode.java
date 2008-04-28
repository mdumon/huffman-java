package arbre1;

import java.io.Serializable;

import bitutils.BitArray;

public class LeafNode implements Node, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BitArray value;
	
	public LeafNode(BitArray value){
		this.value = value;
	}
	public BitArray getValue(){
		return this.value;
	}
	
	public void setValue(BitArray value) {
		this.value = value;
	}
	
	//n'aura pas à être comparé
	public int getValToCompare() {
		return 0;
	}
	
	//n'aura pas à être comparé
	public int compareTo(Node node) {
		return 0;
	}
	
	public String toString() {
		String res = "LeafNode "+this.getValue();
		return "";
	}
}
