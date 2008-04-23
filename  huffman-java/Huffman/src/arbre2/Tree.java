package arbre2;

import java.io.Serializable;

public class Tree<T>  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Tree(){
		root = new Node<T>();
	}
	
	public Tree(Node<T> root){
		setRoot(root);
	}
	
	private Node<T> root;
	public Node<T> getRoot(){
		return root;
	}
	protected void setRoot(Node<T> root){
		this.root = root;
	}
}