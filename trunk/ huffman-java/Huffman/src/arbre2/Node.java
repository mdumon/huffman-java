package arbre2;

import java.io.Serializable;

/**
 * <code>Node<T></code> creates node of with a value which is an element T
 * the node can have a right and a left son which are nodes too.
 * 
 * @author Franck Séhédic <sehedic@ecole.ensicaen.fr>
 */
public class Node<T>  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an empty node
	 */
	public Node(){}
	
	/**
	 * Creates a new node
	 * @param the element T which represents the value of the node
	 */
	public Node(T t){
		this(null,null,t);
	}
	
	/**
	 * Creates a new node
	 * @param the left and right sons which are nodes
	 */
	public Node(Node<T> leftson,Node<T> rightSon){
		this(leftson,rightSon,null);
	}
	
	/**
	 * Creates a new node
	 * @param the left and right sons which are nodes and
	 * the element T which represents the value of the node
	 */
	public Node(Node<T> leftSon,Node<T> rightSon,T t){
		setLeftSon(leftSon);
		setRightSon(rightSon);
		setValue(t);
	}
	
	/**
	 * Return the left son of the node
	 * @param void
	 * @return the left son of the node
	 */
	private Node<T> leftSon = null;
	public Node<T> getLeftSon(){
		return leftSon;
	}
	
	/**
	 * set the left son of the node
	 * @param the left son of the node
	 * @return void
	 */
	public void setLeftSon(Node<T> leftSon){
		this.leftSon = leftSon;
	}
	
	/**
	 * Return the right son of the node
	 * @param void
	 * @return the right son of the node
	 */
	private Node<T> rightSon = null;
	public Node<T> getRightSon(){
		return rightSon;
	}
	
	/**
	 * set the right son of the node
	 * @param the right son of the node
	 * @return void
	 */
	public void setRightSon(Node<T> rightSon){
		this.rightSon = rightSon;
	}
	
	/**
	 * Return the element T representing the value of the node
	 * @param void
	 * @return the element T representing the value of the node
	 */
	private T value = null;
	public T getValue(){
		return value;
	}
	
	/**
	 * set the value of the node
	 * @param the element T representing the value
	 * of the node
	 * @return void
	 */
	public void setValue(T value){
		this.value = value;
	}
}