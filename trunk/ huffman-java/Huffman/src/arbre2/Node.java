package arbre2;

import bitutils.BitArray;

public class Node {
	public Node(){}
	
	public Node(BitArray ba){
		this(null,null,ba);
	}
	
	public Node(Node leftson,Node rightSon){
		this(leftson,rightSon,null);
	}
	
	public Node(Node leftson,Node rightSon,BitArray ba){
		setLeftSon(leftSon);
		setRightSon(rightSon);
		setValue(ba);
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