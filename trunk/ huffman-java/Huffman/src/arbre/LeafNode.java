package arbre;

import bitutils.BitArray;

public class LeafNode extends Node {
	private BitArray value;
	
	public LeafNode(BitArray value){
		setEstFeuille(true);
		this.value = value;
	}
	
}
