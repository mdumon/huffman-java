package arbre1;

import bitutils.BitArray;

public class LeafNode extends Node {
	private BitArray value;
	
	public LeafNode(BitArray value){
		this.value = value;
	}
	
	public BitArray getValue(){
		return this.value;
	}
}
