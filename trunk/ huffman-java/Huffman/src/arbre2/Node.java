package arbre2;

import java.io.Serializable;

public class Node<T>  implements Serializable{
	private static final long serialVersionUID = 1L;

	public Node(){}
	
	public Node(T t){
		this(null,null,t);
	}
	
	public Node(Node<T> leftson,Node<T> rightSon){
		this(leftson,rightSon,null);
	}
	
	public Node(Node<T> leftSon,Node<T> rightSon,T t){
		setLeftSon(leftSon);
		setRightSon(rightSon);
		setValue(t);
	}
	
	private Node<T> leftSon = null;
	public Node<T> getLeftSon(){
		return leftSon;
	}
	public void setLeftSon(Node<T> leftSon){
		this.leftSon = leftSon;
	}
	
	private Node<T> rightSon = null;
	public Node<T> getRightSon(){
		return rightSon;
	}
	public void setRightSon(Node<T> rightSon){
		this.rightSon = rightSon;
	}

	private T value = null;
	public T getValue(){
		return value;
	}
	public void setValue(T value){
		this.value = value;
	}
}