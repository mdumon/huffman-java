package arbre;

import bitutils.BitArray;

public class LeafNode extends Node {
	private boolean estFeuille;
	private BitArray value;
	
	public LeafNode(BitArray value){
		this.estFeuille = true;
		this.value = value;
	}
	
}
