package arbre;

import bitutils.BitArray;

public class LeafNode extends Node {
	private BitArray value;
	
	public LeafNode(BitArray value){
		this.value = value;
	}
	
}
