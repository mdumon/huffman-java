package arbre;

import bitutils.BitArray;

public class Node {
	public Node(){
		
	}
	
	private Node leftSon = null;
	public Node getLeftSon(){
		return leftSon;
	}
	public void setLeftSon(Node leftSon){
		this.leftSon = leftSon;
	}
	
	private Node rightSon = null;
	public Node getRightSon(){
		return rightSon;
	}
	public void setRightSon(Node rightSon){
		this.rightSon = rightSon;
	}

	private BitArray value = null;
	public BitArray getValue(){
		return value;
	}
	public void setValue(BitArray value){
		this.value = value;
	}
}
